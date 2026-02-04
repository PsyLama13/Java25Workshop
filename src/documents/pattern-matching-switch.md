# Pattern Matching for switch

Pattern Matching erweitert `switch` um die Möglichkeit, auf Typen und Patterns zu matchen.

## Type Patterns

```java
String format(Object obj) {
    return switch (obj) {
        case Integer i -> "Integer: " + i;
        case Long l    -> "Long: " + l;
        case String s  -> "String: " + s;
        case null      -> "null";
        default        -> "Unknown: " + obj;
    };
}
```

## Guarded Patterns (when)

```java
String describe(Object obj) {
    return switch (obj) {
        case String s when s.isEmpty() -> "Empty string";
        case String s when s.length() > 10 -> "Long string";
        case String s -> "String: " + s;
        case Integer i when i > 0 -> "Positive: " + i;
        case Integer i -> "Non-positive: " + i;
        default -> "Other";
    };
}
```

## Mit Sealed Classes

```java
sealed interface Shape permits Circle, Rectangle {}
record Circle(double radius) implements Shape {}
record Rectangle(double w, double h) implements Shape {}

double area(Shape shape) {
    return switch (shape) {
        case Circle c -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.w() * r.h();
        // kein default nötig - exhaustive!
    };
}
```

## null-Handling

```java
switch (obj) {
    case null -> System.out.println("null!");
    case String s -> System.out.println(s);
    default -> System.out.println("other");
}
```
