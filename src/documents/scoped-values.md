# Scoped Values

Scoped Values sind eine moderne Alternative zu `ThreadLocal`. Sie ermöglichen das Teilen von unveränderlichen Daten innerhalb eines Scopes ohne explizite Parameterübergabe.

## Grundlagen

```java
private static final ScopedValue<User> CURRENT_USER = ScopedValue.newInstance();

void handleRequest(Request request) {
    User user = authenticate(request);

    ScopedValue.where(CURRENT_USER, user).run(() -> {
        processRequest();  // Kann auf CURRENT_USER zugreifen
    });
}

void processRequest() {
    User user = CURRENT_USER.get();  // Kein Parameter nötig!
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
| Vererbung | Manuell | Automatisch (Virtual Threads) |
| Scope | Unbegrenzt | Explizit begrenzt |
| Performance | Langsamer | Schneller |
| Memory Leaks | Möglich | Nicht möglich |

## Verschachtelte Scopes

```java
ScopedValue.where(USER, admin).run(() -> {
    // USER ist hier "admin"

    ScopedValue.where(USER, guest).run(() -> {
        // USER ist hier "guest" (rebind)
    });

    // USER ist wieder "admin"
});
```

## Mit Structured Concurrency

```java
ScopedValue.where(CURRENT_USER, user).run(() -> {
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
        // Beide Tasks sehen CURRENT_USER automatisch
        scope.fork(() -> taskThatNeedsUser());
        scope.fork(() -> anotherTaskThatNeedsUser());
        scope.join();
    }
});
```
