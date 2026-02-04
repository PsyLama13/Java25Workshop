# Sealed Classes

Sealed Classes ermöglichen es, die Vererbungshierarchie explizit einzuschränken. Nur die erlaubten Klassen dürfen erben.

## Syntax

```java
public sealed class Shape permits Circle, Rectangle, Triangle {
    // ...
}

public final class Circle extends Shape {
    private final double radius;
}

public final class Rectangle extends Shape {
    private final double width, height;
}

public non-sealed class Triangle extends Shape {
    // kann weiter vererbt werden
}
```

## Modifikatoren für Unterklassen

| Modifier | Bedeutung |
|----------|-----------|
| `final` | Keine weitere Vererbung möglich |
| `sealed` | Weitere eingeschränkte Vererbung |
| `non-sealed` | Öffnet die Hierarchie wieder |

## Vorteile

- Compiler kennt alle möglichen Subtypen
- Ermöglicht exhaustive Pattern Matching in `switch`
- Bessere Dokumentation der Domäne
- Mehr Kontrolle über die API
