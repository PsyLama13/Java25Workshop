# 1 · Records & Sealed Classes

## Records

Records sind kompakte, unveränderliche Datenklassen – der Compiler generiert automatisch Konstruktor, Getter, `equals()`, `hashCode()` und `toString()`.

### Vorher (klassische Klasse)

```java
public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge()     { return age; }

    @Override public boolean equals(Object o) { /* ... */ }
    @Override public int hashCode()           { /* ... */ }
    @Override public String toString()        { /* ... */ }
}
```

### Nachher (Record)

```java
public record Person(String name, int age) {}
```

### Wichtige Eigenschaften

- Records sind implizit `final`, alle Felder implizit `final`
- Getter heissen wie die Komponenten: `person.name()`, `person.age()`
- Kompakter Konstruktor für Validierung:

```java
public record Person(String name, int age) {
    public Person {
        if (age < 0) throw new IllegalArgumentException("Alter darf nicht negativ sein");
    }
}
```

- Records können zusätzliche Methoden enthalten:

```java
public record Rectangle(double width, double height) {
    public double area() { return width * height; }
}
```

---

## Sealed Classes

Sealed Classes schränken die Vererbungshierarchie explizit ein – nur die erlaubten Typen dürfen erben.

### Syntax

```java
public sealed interface Shape permits Circle, Rectangle, Triangle {}

public record Circle(double radius)         implements Shape {}
public record Rectangle(double w, double h) implements Shape {}
public non-sealed class Triangle extends Shape { ... }
```

### Modifikatoren für Unterklassen

| Modifier | Bedeutung |
|----------|-----------|
| `final` | Keine weitere Vererbung möglich |
| `sealed` | Weitere eingeschränkte Vererbung |
| `non-sealed` | Öffnet die Hierarchie wieder |

### Vorteile

- Compiler kennt alle Subtypen → exhaustive `switch` ohne `default`
- Selbstdokumentierende, geschlossene Domänenmodelle
- Ideale Kombination mit Records und Pattern Matching:

```java
double area(Shape shape) {
    return switch (shape) {
        case Circle c      -> Math.PI * c.radius() * c.radius();
        case Rectangle r   -> r.w() * r.h();
        case Triangle t    -> 0.5 * t.base() * t.height();
        // kein default nötig – exhaustive!
    };
}
```
