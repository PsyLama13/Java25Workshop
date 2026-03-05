# 6 · Scoped Values

Scoped Values sind eine moderne Alternative zu `ThreadLocal`. Sie ermöglichen das Teilen unveränderlicher Daten innerhalb eines definierten Scopes – ohne explizite Parameterübergabe.

## Grundlagen

```java
private static final ScopedValue<User> CURRENT_USER = ScopedValue.newInstance();

void handleRequest(Request request) {
    User user = authenticate(request);

    ScopedValue.where(CURRENT_USER, user).run(() -> {
        processRequest();  // kann auf CURRENT_USER zugreifen
    });
}

void processRequest() {
    User user = CURRENT_USER.get();  // kein Parameter nötig!
    // ...
}
```

## Mit Rückgabewert

```java
String result = ScopedValue.where(CURRENT_USER, user).call(() -> {
    return computeResult();
});
```

## Vergleich mit ThreadLocal

| Eigenschaft | ThreadLocal | ScopedValue |
|-------------|-------------|-------------|
| Mutierbar | Ja | Nein (immutable) |
| Scope-Ende | Manuelles `remove()` | Automatisch |
| Memory Leaks | Möglich | Nicht möglich |
| Virtual Threads | Problematisch | Erstklassige Unterstützung |
| Performance | Langsamer | Schneller |

## Verschachtelte Scopes (Rebinding)

```java
ScopedValue.where(USER, admin).run(() -> {
    // USER ist hier "admin"
    USER.get();  // → admin

    ScopedValue.where(USER, guest).run(() -> {
        USER.get();  // → guest (rebind im inneren Scope)
    });

    USER.get();  // → wieder admin
});
```

## Mit Structured Concurrency

Scoped Values werden automatisch an Child-Threads weitergegeben:

```java
ScopedValue.where(CURRENT_USER, user).run(() -> {
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
        scope.fork(() -> taskA());  // sieht CURRENT_USER automatisch
        scope.fork(() -> taskB());  // sieht CURRENT_USER automatisch
        scope.join();
    }
});
```
