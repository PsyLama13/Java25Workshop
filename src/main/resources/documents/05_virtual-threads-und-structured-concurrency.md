# 5 · Virtual Threads & Structured Concurrency

## Virtual Threads

Virtual Threads sind leichtgewichtige, von der JVM verwaltete Threads. Sie ermöglichen Millionen gleichzeitiger Threads ohne den Overhead von OS-Threads.

### Erstellung

```java
// Einfacher Virtual Thread
Thread.startVirtualThread(() -> doWork());

// Mit Builder (benannt)
Thread vt = Thread.ofVirtual()
    .name("worker-", 0)
    .start(() -> doWork());

// ExecutorService – ein Virtual Thread pro Task
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    executor.submit(() -> task1());
    executor.submit(() -> task2());
}  // wartet automatisch auf alle Tasks
```

### Vergleich

| Eigenschaft | Platform Thread | Virtual Thread |
|-------------|-----------------|----------------|
| Anzahl | Tausende | Millionen |
| Stack-Speicher | ~1 MB | ~1 KB |
| Scheduling | OS | JVM |
| Blocking | Blockiert OS-Thread | Wird unmounted |

### Best Practices

- Ideal für **I/O-bound Tasks** (HTTP, DB, Dateizugriff)
- Kein Thread-Pool nötig – einen Thread pro Task erstellen
- Nicht für CPU-intensive Berechnungen (dafür Platform Threads / ForkJoinPool)
- `synchronized` kann Virtual Threads „pinnen" → bevorzuge `ReentrantLock`

---

## Structured Concurrency

Structured Concurrency behandelt mehrere parallele Tasks als eine Einheit – analog zu strukturierter Programmierung mit Blöcken. Der zentrale Gedanke: **die Lebensdauer von nebenläufigen Tasks ist an einen lexikalischen Scope gebunden**. Wenn der Scope endet, sind garantiert alle Tasks beendet.

### Das Problem ohne Structured Concurrency

Mit klassischem `ExecutorService` entstehen schnell Probleme:

```java
// ❌ Probleme mit klassischem Ansatz
ExecutorService executor = Executors.newFixedThreadPool(3);
Future<String> userFuture   = executor.submit(() -> fetchUser(id));
Future<String> ordersFuture = executor.submit(() -> fetchOrders(id));

String user = userFuture.get();    // blockiert – wenn hier eine Exception fliegt...
String orders = ordersFuture.get(); // ...läuft dieser Task trotzdem weiter (verwaist!)
```

Typische Probleme:
- **Verwaiste Threads**: Schlägt ein Task fehl, laufen die anderen weiter – sie verbrauchen Ressourcen für ein Ergebnis, das niemand mehr braucht
- **Thread Leaks**: Wirft der aufrufende Code eine Exception, werden gestartete Tasks nicht automatisch abgebrochen
- **Unklare Fehlerbehandlung**: Welche Exception hat Priorität? Was passiert bei mehreren Fehlern?
- **Schlechte Observability**: Thread-Dumps zeigen keine Beziehung zwischen Aufrufer und Tasks

### StructuredTaskScope – der Grundbaustein

`StructuredTaskScope` löst diese Probleme durch drei Regeln:

1. **Fork** – Tasks werden innerhalb eines Scopes gestartet
2. **Join** – der Scope wartet auf alle Tasks
3. **Close** – beim Schliessen (try-with-resources) werden alle Tasks garantiert beendet

```java
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    // 1. Fork: Tasks starten (laufen auf Virtual Threads)
    Subtask<String> user   = scope.fork(() -> fetchUser(userId));
    Subtask<String> orders = scope.fork(() -> fetchOrders(userId));

    // 2. Join: Warten bis alle Tasks fertig sind
    scope.join();

    // 3. Ergebnisse verwenden
    scope.throwIfFailed();  // wirft Exception wenn ein Task fehlschlug
    return new Dashboard(user.get(), orders.get());
}  // 4. Close: garantiert, dass alle Threads beendet sind
```

> **Wichtig**: `scope.fork()` startet jeden Task auf einem eigenen Virtual Thread. Es wird kein Thread-Pool benötigt.

### Subtask – Zustand eines Tasks

`scope.fork()` gibt ein `Subtask<T>` zurück. Dieses hat einen von vier Zuständen:

| Zustand       | Bedeutung                                    | `get()` liefert    |
|---------------|----------------------------------------------|--------------------|
| `UNAVAILABLE` | Task läuft noch                              | → `IllegalStateException` |
| `SUCCESS`     | Task erfolgreich beendet                     | → Ergebnis         |
| `FAILED`      | Task hat eine Exception geworfen             | → `IllegalStateException` |

> `Subtask.get()` darf erst **nach** `scope.join()` aufgerufen werden. Vorher wirft es eine `IllegalStateException`.

### ShutdownOnFailure – alle oder keiner

Die häufigste Strategie: Alle Tasks müssen erfolgreich sein. Schlägt einer fehl, werden alle anderen sofort abgebrochen (**Shutdown**), und die Exception wird weitergegeben.

```java
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    Subtask<String> user   = scope.fork(() -> fetchUser(userId));
    Subtask<String> orders = scope.fork(() -> fetchOrders(userId));
    Subtask<String> recos  = scope.fork(() -> fetchRecommendations(userId));

    scope.join();           // wartet auf ALLE Tasks (oder bis einer fehlschlägt)
    scope.throwIfFailed();  // wirft die Exception des fehlgeschlagenen Tasks

    // Hier sind garantiert alle Tasks erfolgreich
    return new Dashboard(user.get(), orders.get(), recos.get());
}
```

**Was passiert bei einem Fehler?**

```
Timeline:
  fetchUser(...)     → ✅ fertig nach 100ms
  fetchOrders(...)   → ❌ Exception nach 80ms  ← löst Shutdown aus
  fetchRecos(...)    → 🛑 wird abgebrochen (interrupt)

→ scope.join() kehrt sofort zurück (nicht erst nach 100ms)
→ scope.throwIfFailed() wirft die Exception von fetchOrders
```

### ShutdownOnSuccess – schnellster gewinnt

Startet mehrere gleichwertige Tasks und nimmt das Ergebnis des **ersten erfolgreichen**. Alle anderen werden sofort abgebrochen. Ideal für Redundanz-Szenarien.

```java
try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
    scope.fork(() -> fetchFromPrimary());     // 200ms
    scope.fork(() -> fetchFromSecondary());   // 100ms ← gewinnt
    scope.fork(() -> fetchFromTertiary());    // 300ms

    scope.join();
    return scope.result();  // "Daten von Secondary"
}
```

**Was passiert bei Fehlern?**
- Schlägt ein Task fehl, laufen die anderen weiter (es wird ja auf den ersten *Erfolg* gewartet)
- Erst wenn **alle** Tasks fehlschlagen, wirft `scope.result()` eine `ExecutionException`

### Timeout mit Deadline

Beide Scope-Typen unterstützen ein Zeitlimit über `joinUntil()`:

```java
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    scope.fork(() -> fetchUser(userId));
    scope.fork(() -> fetchOrders(userId));

    // Maximal 5 Sekunden warten
    scope.joinUntil(Instant.now().plusSeconds(5));
    scope.throwIfFailed();

    // ...
}  // Tasks, die noch laufen, werden beim Close abgebrochen
```

### Verschachtelung von Scopes

Scopes können verschachtelt werden – jeder innere Scope ist unabhängig vom äusseren:

```java
try (var outer = new StructuredTaskScope.ShutdownOnFailure()) {
    outer.fork(() -> {
        // Innerer Scope – eigene Fehlerbehandlung
        try (var inner = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            inner.fork(() -> fetchFromPrimary());
            inner.fork(() -> fetchFromSecondary());
            inner.join();
            return inner.result();
        }
    });

    outer.fork(() -> fetchOrders(userId));

    outer.join();
    outer.throwIfFailed();
    // ...
}
```

### Vergleich: ExecutorService vs. Structured Concurrency

| Eigenschaft             | ExecutorService                          | StructuredTaskScope                     |
|-------------------------|------------------------------------------|-----------------------------------------|
| Thread-Typ              | Platform Threads (Pool)                  | Virtual Threads (ein Thread pro Task)   |
| Lebensdauer             | Ungebunden – muss manuell geschlossen werden | An Scope gebunden (try-with-resources)  |
| Fehlerbehandlung        | Manuell pro Future                       | Automatisch (Shutdown-Strategien)       |
| Abbruch bei Fehler      | Manuell implementieren                   | Automatisch (`ShutdownOnFailure`)       |
| Verwaiste Threads       | Möglich                                  | Unmöglich – Scope garantiert Cleanup    |
| Thread-Hierarchie       | Flach                                    | Eltern-Kind (sichtbar in Thread-Dumps) |
| Scoped Values           | Nicht weitergegeben                      | Automatisch an Kind-Threads vererbt    |
| Verschachtelung         | Unstrukturiert                           | Natürliche Verschachtelung              |

### Zusammenfassung: Wann welche Strategie?

| Anwendungsfall                               | Strategie               |
|----------------------------------------------|-------------------------|
| Mehrere Teile eines Ergebnisses parallel laden | `ShutdownOnFailure`    |
| Redundante Quellen – schnellste Antwort nehmen | `ShutdownOnSuccess`   |
| Parallele Tasks mit Zeitlimit                 | `joinUntil(deadline)`  |
