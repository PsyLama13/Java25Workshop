# Pattern Matching (instanceof)

Pattern Matching für `instanceof` eliminiert redundante Casts nach einer Typprüfung.

## Vorher

```java
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.length());
}
```

## Nachher

```java
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

## Flow Scoping

Die Pattern-Variable ist nur dort verfügbar, wo der Compiler sicher ist, dass das Pattern gematcht hat:

```java
// Funktioniert - s ist im then-Block verfügbar
if (obj instanceof String s) {
    System.out.println(s.toUpperCase());
}

// Funktioniert auch mit negierter Bedingung
if (!(obj instanceof String s)) {
    return;
}
// s ist hier verfügbar, da wir nur hierher kommen wenn obj ein String ist
System.out.println(s.length());
```

## Kombination mit anderen Bedingungen

```java
if (obj instanceof String s && s.length() > 5) {
    System.out.println("Long string: " + s);
}
```
