# Records

Records sind kompakte, unveränderliche Datenklassen, die automatisch Konstruktor, Getter, `equals()`, `hashCode()` und `toString()` generieren.

## Vorher (klassische Klasse)

```java
public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public boolean equals(Object o) { /* ... */ }
    @Override
    public int hashCode() { /* ... */ }
    @Override
    public String toString() { /* ... */ }
}
```

## Nachher (Record)

```java
public record Person(String name, int age) {}
```

## Wichtige Eigenschaften

- Records sind implizit `final`
- Alle Felder sind implizit `final`
- Getter haben denselben Namen wie die Komponenten (z.B. `person.name()`)
- Kompakte Konstruktoren für Validierung möglich:

```java
public record Person(String name, int age) {
    public Person {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
    }
}
```
