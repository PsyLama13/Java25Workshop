# Record Patterns

Record Patterns ermöglichen das Dekonstruieren von Records direkt im Pattern Matching.

## Grundlagen

```java
record Point(int x, int y) {}

void printSum(Object obj) {
    if (obj instanceof Point(int x, int y)) {
        System.out.println(x + y);
    }
}
```

## In switch

```java
record Point(int x, int y) {}

String describe(Object obj) {
    return switch (obj) {
        case Point(int x, int y) when x == 0 && y == 0 -> "Origin";
        case Point(int x, int y) when x == y -> "Diagonal";
        case Point(int x, int y) -> "Point at (" + x + ", " + y + ")";
        default -> "Not a point";
    };
}
```

## Verschachtelte Patterns

```java
record Point(int x, int y) {}
record Line(Point start, Point end) {}

void printLineStart(Object obj) {
    if (obj instanceof Line(Point(int x, int y), _)) {
        System.out.println("Line starts at: " + x + ", " + y);
    }
}
```

## Kombination mit Type Patterns

```java
record Box<T>(T content) {}

String describe(Box<Object> box) {
    return switch (box) {
        case Box(String s) -> "Box contains string: " + s;
        case Box(Integer i) -> "Box contains integer: " + i;
        case Box(var v) -> "Box contains: " + v;
    };
}
```
