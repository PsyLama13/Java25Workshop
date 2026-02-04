# Flexible Constructor Bodies

Flexible Constructor Bodies (Preview in Java 22+) erlauben Statements vor dem `super()`- oder `this()`-Aufruf im Konstruktor.

## Vorher (Problem)

```java
public class PositiveInteger {
    private final int value;

    public PositiveInteger(int value) {
        // Validierung VOR super() war nicht möglich!
        super();
        if (value <= 0) {
            throw new IllegalArgumentException("Must be positive");
        }
        this.value = value;
    }
}
```

## Nachher

```java
public class PositiveInteger {
    private final int value;

    public PositiveInteger(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Must be positive");
        }
        super();  // Kann jetzt nach der Validierung kommen
        this.value = value;
    }
}
```

## Praktisches Beispiel

```java
public class DatabaseConnection extends Connection {
    private final String url;

    public DatabaseConnection(String url) {
        // Validierung und Transformation vor super()
        Objects.requireNonNull(url, "URL cannot be null");
        String normalizedUrl = url.toLowerCase().trim();

        super(normalizedUrl);  // super() mit transformiertem Wert
        this.url = normalizedUrl;
    }
}
```

## Einschränkungen

- Vor `super()`/`this()` darf nicht auf `this` zugegriffen werden
- Instanzvariablen können erst nach dem Super-Aufruf zugewiesen werden
- Feature ist in Preview, muss mit `--enable-preview` aktiviert werden
