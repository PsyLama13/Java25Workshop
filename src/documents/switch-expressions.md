# Switch Expressions

Switch Expressions machen `switch` zu einem Ausdruck, der einen Wert zurückgibt. Die neue Arrow-Syntax (`->`) verhindert Fall-Through.

## Vorher (Statement)

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

## Nachher (Expression)

```java
String result = switch (day) {
    case MONDAY, FRIDAY -> "Working";
    case SATURDAY, SUNDAY -> "Weekend";
    default -> "Midweek";
};
```

## yield für Blöcke

Wenn mehr als ein Statement benötigt wird:

```java
String result = switch (day) {
    case MONDAY -> {
        logger.log("Start of week");
        yield "Working";
    }
    case SATURDAY, SUNDAY -> "Weekend";
    default -> "Midweek";
};
```

## Vorteile

- Kein Fall-Through-Problem mehr
- Compiler prüft Vollständigkeit (exhaustiveness)
- Kompaktere Syntax
- Kann als Ausdruck verwendet werden
