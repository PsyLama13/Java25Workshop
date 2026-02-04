# Sequenced Collections

Sequenced Collections fügen eine einheitliche API für Collections mit definierter Reihenfolge hinzu. Erstes/letztes Element und umgekehrte Iteration sind jetzt standardisiert.

## Neue Interfaces

```
SequencedCollection
    ├── SequencedSet
    └── SequencedMap
```

## SequencedCollection

```java
interface SequencedCollection<E> extends Collection<E> {
    SequencedCollection<E> reversed();

    void addFirst(E e);
    void addLast(E e);
    E getFirst();
    E getLast();
    E removeFirst();
    E removeLast();
}
```

## Beispiele

```java
List<String> list = new ArrayList<>(List.of("a", "b", "c"));

// Erstes und letztes Element
String first = list.getFirst();  // "a"
String last = list.getLast();    // "c"

// Hinzufügen am Anfang/Ende
list.addFirst("start");
list.addLast("end");

// Umgekehrte Iteration
for (String s : list.reversed()) {
    System.out.println(s);  // end, c, b, a, start
}
```

## SequencedMap

```java
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
map.put("one", 1);
map.put("two", 2);
map.put("three", 3);

// Erster und letzter Eintrag
Map.Entry<String, Integer> first = map.firstEntry();  // one=1
Map.Entry<String, Integer> last = map.lastEntry();    // three=3

// Umgekehrte Ansicht
SequencedMap<String, Integer> reversed = map.reversed();
```

## Vorteile

- Einheitliche API über `List`, `Deque`, `LinkedHashSet`, `LinkedHashMap`, etc.
- Kein Workaround mehr nötig für letztes Element einer `List`
- `reversed()` ist eine View, keine Kopie
