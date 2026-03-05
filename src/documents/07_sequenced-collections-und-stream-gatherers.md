# 7 · Sequenced Collections & Stream Gatherers

## Sequenced Collections

Sequenced Collections fügen eine einheitliche API für Collections mit definierter Reihenfolge hinzu. Erstes/letztes Element und umgekehrte Iteration sind jetzt standardisiert.

### Neue Interface-Hierarchie

```
SequencedCollection
    ├── SequencedSet
    └── (implementiert von List, Deque, LinkedHashSet, ...)
SequencedMap
    └── (implementiert von LinkedHashMap, ...)
```

### SequencedCollection – neue Methoden

```java
List<String> list = new ArrayList<>(List.of("a", "b", "c"));

// Vorher                            Nachher
list.get(0)                       // list.getFirst()
list.get(list.size() - 1)         // list.getLast()
list.add(0, "x")                  // list.addFirst("x")
list.remove(list.size() - 1)      // list.removeLast()

// Umgekehrte Iteration – reversed() ist eine View, keine Kopie!
for (String s : list.reversed()) {
    System.out.println(s);  // c, b, a
}
```

### SequencedMap

```java
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
map.put("eins", 1); map.put("zwei", 2); map.put("drei", 3);

map.firstEntry()   // eins=1
map.lastEntry()    // drei=3
map.reversed()     // umgekehrte View
```

---

## Stream Gatherers

Stream Gatherers sind benutzerdefinierte Zwischenoperationen für Streams. Sie schliessen die Lücke zwischen eingebauten Operationen (`map`, `filter`) und dem Terminal-Collector.

### Eingebaute Gatherers

```java
// Feste Fenster (nicht überlappend)
Stream.of(1, 2, 3, 4, 5, 6)
    .gather(Gatherers.windowFixed(3))
    .toList();  // [[1, 2, 3], [4, 5, 6]]

// Gleitende Fenster (überlappend)
Stream.of(1, 2, 3, 4, 5)
    .gather(Gatherers.windowSliding(3))
    .toList();  // [[1, 2, 3], [2, 3, 4], [3, 4, 5]]

// Scan (wie reduce, aber mit Zwischenergebnissen)
Stream.of(1, 2, 3, 4)
    .gather(Gatherers.scan(() -> 0, Integer::sum))
    .toList();  // [1, 3, 6, 10]

// Parallele Verarbeitung mit Limit
Stream.of(url1, url2, url3)
    .gather(Gatherers.mapConcurrent(4, this::fetchUrl))
    .toList();
```

### Eigene Gatherer erstellen

```java
// Nur jedes n-te Element durchlassen
static <T> Gatherer<T, ?, T> everyNth(int n) {
    return Gatherer.ofSequential(
        () -> new int[]{0},                   // Initializer (Zähler)
        (state, element, downstream) -> {
            if (state[0]++ % n == 0) {
                downstream.push(element);
            }
            return true;                      // true = weitermachen
        }
    );
}

Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
    .gather(everyNth(3))
    .toList();  // [1, 4, 7]
```

### Struktur eines Gatherers

```java
Gatherer.of(
    initializer,   // erstellt den Zustand
    integrator,    // verarbeitet jedes Element
    combiner,      // kombiniert parallele Zustände (optional)
    finisher       // Abschlusslogik (optional)
);
```
