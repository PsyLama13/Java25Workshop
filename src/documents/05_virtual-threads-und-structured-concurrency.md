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

Structured Concurrency behandelt mehrere parallele Tasks als eine Einheit. Fehlschlägt ein Task, werden automatisch alle anderen abgebrochen.

### ShutdownOnFailure – alle oder keiner

```java
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    Subtask<String> user   = scope.fork(() -> fetchUser(userId));
    Subtask<String> orders = scope.fork(() -> fetchOrders(userId));

    scope.join();           // Warten auf alle Tasks
    scope.throwIfFailed();  // Exception wenn ein Task fehlschlug

    return new Dashboard(user.get(), orders.get());
}
```

### ShutdownOnSuccess – schnellster gewinnt

```java
try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
    scope.fork(() -> fetchFromServer1());
    scope.fork(() -> fetchFromServer2());
    scope.fork(() -> fetchFromServer3());

    scope.join();
    return scope.result();  // Ergebnis des ersten erfolgreichen Tasks
}
```

### Vorteile gegenüber ExecutorService

- Klare Eltern-Kind-Beziehung zwischen Threads
- Automatisches Cleanup bei Fehlern – keine verwaisten Threads
- Bessere Observability (Thread-Dumps zeigen Hierarchie)
- Scoped Values werden automatisch an Child-Threads weitergegeben
