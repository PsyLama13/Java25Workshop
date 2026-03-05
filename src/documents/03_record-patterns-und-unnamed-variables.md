# 3 · Record Patterns & Unnamed Variables

## Record Patterns

Record Patterns ermöglichen das Dekonstruieren von Records direkt im Pattern Matching – ohne manuelle Getter-Aufrufe.

### Grundlagen

```java
record Point(int x, int y) {}

void printSum(Object obj) {
    if (obj instanceof Point(int x, int y)) {
        System.out.println(x + y);
    }
}
```

### In switch

```java
String describe(Object obj) {
    return switch (obj) {
        case Point(int x, int y) when x == 0 && y == 0 -> "Ursprung";
        case Point(int x, int y) when x == y           -> "Auf der Diagonale";
        case Point(int x, int y)                       -> "Punkt (" + x + ", " + y + ")";
        default                                        -> "Kein Punkt";
    };
}
```

### Verschachtelte Patterns

```java
record Point(int x, int y) {}
record Line(Point start, Point end) {}

void printStart(Object obj) {
    if (obj instanceof Line(Point(int x, int y), Point end)) {
        System.out.println("Startet bei: " + x + ", " + y);
    }
}
```

### Kombination mit generischen Records

```java
record Box<T>(T content) {}

String describe(Box<Object> box) {
    return switch (box) {
        case Box(String s)  -> "Box enthält String: " + s;
        case Box(Integer i) -> "Box enthält Integer: " + i;
        case Box(var v)     -> "Box enthält: " + v;
    };
}
```

---

## Unnamed Variables & Patterns

`_` kennzeichnet explizit unbenutzte Variablen und Pattern-Komponenten und verbessert so die Lesbarkeit.

### In Record Patterns

```java
// Nur x interessiert uns
if (obj instanceof Point(int x, _)) {
    System.out.println("x = " + x);
}
```

### In switch

```java
switch (obj) {
    case Point(int x, _) when x > 0 -> "Positive x-Koordinate";
    case Point _                     -> "Irgendein Punkt";
    default                          -> "Kein Punkt";
}
```

### In try-catch, for-each und Lambdas

```java
// try-catch – Exception wird nicht verwendet
try {
    Integer.parseInt(input);
} catch (NumberFormatException _) {
    return false;
}

// for-each – nur zählen
int count = 0;
for (var _ : collection) { count++; }

// Lambda – nur Value brauchen wir
map.forEach((_, value) -> System.out.println(value));
```

### `_` kann mehrfach im selben Scope verwendet werden

```java
record Rectangle(Point topLeft, Point bottomRight) {}

if (obj instanceof Rectangle(Point(int x1, _), Point(_, int y2))) {
    // Nur x1 (links) und y2 (unten) interessieren uns
}
```
