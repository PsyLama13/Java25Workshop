# Stream Gatherers

Stream Gatherers sind benutzerdefinierte Zwischenoperationen für Streams. Sie schliessen die Lücke zwischen den eingebauten Operationen (`map`, `filter`) und dem Terminal-Collector.

## Eingebaute Gatherers

```java
import java.util.stream.Gatherers;

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

// mapConcurrent (parallele Verarbeitung mit Limit)
Stream.of(url1, url2, url3)
    .gather(Gatherers.mapConcurrent(4, this::fetchUrl))
    .toList();
```

## Eigene Gatherer erstellen

```java
// Gatherer der nur jedes n-te Element durchlässt
static <T> Gatherer<T, ?, T> everyNth(int n) {
    return Gatherer.ofSequential(
        () -> new int[]{0},  // Initializer (Zähler)
        (state, element, downstream) -> {
            if (state[0]++ % n == 0) {
                downstream.push(element);
            }
            return true;  // Weitermachen
        }
    );
}

// Verwendung
Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
    .gather(everyNth(3))
    .toList();  // [1, 4, 7]
```

## Struktur eines Gatherers

```java
Gatherer.of(
    initializer,    // Erstellt den Zustand
    integrator,     // Verarbeitet Elemente
    combiner,       // Kombiniert parallele Zustände (optional)
    finisher        // Abschlusslogik (optional)
);
```
