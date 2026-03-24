# 4 · Flexible Constructor Bodies

Flexible Constructor Bodies (Java 22+, Preview) erlauben Statements **vor** dem `super()`- oder `this()`-Aufruf im Konstruktor – solange nicht auf `this` zugegriffen wird.

## Problem vorher

```java
public class PositiveInteger {
    public PositiveInteger(int value) {
        super();  // muss als erstes stehen – Validierung kommt zu spät!
        if (value <= 0) throw new IllegalArgumentException("Must be positive");
        this.value = value;
    }
}
```

## Lösung nachher

```java
public class PositiveInteger {
    public PositiveInteger(int value) {
        if (value <= 0) throw new IllegalArgumentException("Must be positive");
        super();  // kann jetzt nach der Validierung kommen
        this.value = value;
    }
}
```
**Big Impact:** bei Klassen mit einem aufwändigen super Constructor 

## Praktisches Beispiel: Transformation vor super()

### Vorher – Workaround mit statischer Hilfsmethode

```java
public class DatabaseConnection extends Connection {
    private static String normalize(String url) {
        Objects.requireNonNull(url);
        return url.toLowerCase().trim();
    }

    public DatabaseConnection(String url) {
        super(normalize(url));  // Workaround nötig
    }
}
```

### Nachher – Direkt im Konstruktor

```java
public class DatabaseConnection extends Connection {
    public DatabaseConnection(String url) {
        Objects.requireNonNull(url, "URL darf nicht null sein");
        String normalizedUrl = url.toLowerCase().trim();
        super(normalizedUrl);  // super() erhält den transformierten Wert
        this.url = normalizedUrl;
    }
}
```

## Einschränkungen

- Vor `super()`/`this()` darf **nicht** auf `this` zugegriffen werden
- Instanzvariablen können erst **nach** dem Super-Aufruf zugewiesen werden
- Aktivierung: `--enable-preview` beim Kompilieren und Ausführen nötig
