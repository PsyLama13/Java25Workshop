# Structured Concurrency

Structured Concurrency behandelt mehrere parallele Tasks als eine Einheit. Wenn ein Task fehlschlägt oder abgebrochen wird, werden alle anderen automatisch beendet.

## Grundprinzip

```java
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    Subtask<String> user = scope.fork(() -> fetchUser(userId));
    Subtask<List<Order>> orders = scope.fork(() -> fetchOrders(userId));

    scope.join();           // Warten auf alle Tasks
    scope.throwIfFailed();  // Exception werfen falls ein Task fehlschlug

    // Beide Ergebnisse sind jetzt verfügbar
    return new UserData(user.get(), orders.get());
}
```

## Strategien

### ShutdownOnFailure

Bricht alle Tasks ab, sobald einer fehlschlägt:

```java
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    var task1 = scope.fork(() -> riskyOperation1());
    var task2 = scope.fork(() -> riskyOperation2());

    scope.join().throwIfFailed();
    return combine(task1.get(), task2.get());
}
```

### ShutdownOnSuccess

Bricht ab, sobald ein Task erfolgreich ist (z.B. für Redundanz):

```java
try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
    scope.fork(() -> fetchFromServer1());
    scope.fork(() -> fetchFromServer2());
    scope.fork(() -> fetchFromServer3());

    scope.join();
    return scope.result();  // Erstes erfolgreiches Ergebnis
}
```

## Vorteile

- Klare Eltern-Kind-Beziehung zwischen Threads
- Automatisches Cleanup bei Fehlern
- Keine verwaisten Threads
- Bessere Observability (Thread-Dumps zeigen Hierarchie)
