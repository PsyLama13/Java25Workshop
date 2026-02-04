# Unnamed Variables & Patterns

Unnamed Variables (`_`) ermöglichen es, unbenutzte Variablen explizit zu kennzeichnen und die Lesbarkeit zu verbessern.

## Unnamed Variables in Patterns

```java
record Point(int x, int y) {}

// Nur x interessiert uns
if (obj instanceof Point(int x, _)) {
    System.out.println("x = " + x);
}
```

## In switch

```java
switch (obj) {
    case Point(int x, _) when x > 0 -> "Positive x";
    case Point _ -> "Some point";  // Unnamed type pattern
    default -> "Not a point";
}
```

## In try-catch

```java
try {
    // ...
} catch (IOException _) {
    // Exception wird nicht verwendet
    System.out.println("IO error occurred");
}
```

## In for-each

```java
int count = 0;
for (var _ : collection) {
    count++;
}
```

## In Lambda-Parametern

```java
map.forEach((_, value) -> System.out.println(value));
```

## Mehrfache Verwendung

`_` kann mehrfach im selben Scope verwendet werden:

```java
record Rectangle(Point topLeft, Point bottomRight) {}

if (obj instanceof Rectangle(Point(int x1, _), Point(_, int y2))) {
    // Nur x1 und y2 interessieren uns
}
```
