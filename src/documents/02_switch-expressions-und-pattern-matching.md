# 2 · Switch Expressions & Pattern Matching

## Switch Expressions

Switch Expressions machen `switch` zu einem Ausdruck mit Rückgabewert. Die Arrow-Syntax (`->`) verhindert Fall-Through.

### Vorher (Statement)

```java
String result;
switch (day) {
    case MONDAY:
    case FRIDAY:
        result = "Working";
        break;
    case SATURDAY:
    case SUNDAY:
        result = "Weekend";
        break;
    default:
        result = "Midweek";
}
```

### Nachher (Expression)

```java
String result = switch (day) {
    case MONDAY, FRIDAY        -> "Working";
    case SATURDAY, SUNDAY      -> "Weekend";
    default                    -> "Midweek";
};
```

### `yield` für Blöcke

```java
String result = switch (day) {
    case MONDAY -> {
        logger.log("Start of week");
        yield "Working"; //returns "Working"
    }
    default -> "Midweek";
};
```

### Vorteile

- Kein Fall-Through-Problem mehr
- Compiler prüft Vollständigkeit (exhaustiveness)
- Kann direkt als Ausdruck verwendet werden (Zuweisung, Return, Argument)

---

## Pattern Matching (instanceof)

Eliminiert redundante Casts nach einer Typprüfung.

### Vorher

```java
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.length());
}
```

### Nachher

```java
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

### Flow Scoping & Kombination mit Bedingungen

```java
// Mit && kombinieren
if (obj instanceof String s && s.length() > 5) {
    System.out.println("Langer String: " + s);
}

// Negiertes Pattern für Early Return
if (!(obj instanceof String s)) {
    return -1;
}
// s ist hier verfügbar
System.out.println(s.length());
```

---

## Pattern Matching for switch

Kombiniert Switch Expressions mit Typ-Prüfungen – der eleganteste Weg, Typfälle zu behandeln.

### Type Patterns

```java
String format(Object obj) {
    return switch (obj) {
        case Integer i -> "Integer: " + i;
        case Long l    -> "Long: " + l;
        case String s  -> "String: " + s;
        case null      -> "null";
        default        -> "Sonstiges: " + obj;
    };
}
```

### Guarded Patterns (`when`)

```java
String describe(Object obj) {
    return switch (obj) {
        case String s when s.isEmpty()     -> "Leerer String";
        case String s when s.length() > 10 -> "Langer String";
        case String s                      -> "String: " + s;
        case Integer i when i > 0          -> "Positive Zahl";
        case Integer i                     -> "Nicht-positive Zahl";
        default                            -> "Sonstiges";
    };
}
```
**Hinweis:** die Reihenfolge im Code entscheidet über den Ablauf, nicht die logische Reihenfolge

### Mit Sealed Classes – kein `default` nötig

```java
sealed interface Shape permits Circle, Rectangle {}
record Circle(double radius)         implements Shape {}
record Rectangle(double w, double h) implements Shape {}

double area(Shape s) {
    return switch (s) {
        case Circle c    -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.w() * r.h();
        // exhaustive – kein default nötig!
    };
}
```

### null-Handling

```java
switch (obj) {
    case null      -> System.out.println("null!");
    case String s  -> System.out.println(s);
    default        -> System.out.println("other");
}
```
