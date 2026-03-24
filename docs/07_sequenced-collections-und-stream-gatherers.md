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

Stream Gatherers sind benutzerdefinierte Zwischenoperationen für Streams. Sie schliessen die Lücke zwischen eingebauten Operationen (`map`, `filter`) und dem Terminal-Collector. Während Collectors das **Ende** einer Pipeline definieren, erweitern Gatherers die **Mitte** – dort, wo bisher nur die fest eingebauten Operationen zur Verfügung standen.

### Eingebaute Gatherers

Java stellt in der Klasse `java.util.stream.Gatherers` fünf vorgefertigte Gatherer bereit:

#### `windowFixed(int size)` – Feste Fenster (nicht überlappend)

Teilt den Stream in aufeinanderfolgende, nicht überlappende Listen fester Grösse auf. Ist das letzte Fenster kleiner als `size`, wird es trotzdem ausgegeben.

```java
Stream.of(1, 2, 3, 4, 5, 6, 7)
    .gather(Gatherers.windowFixed(3))
    .toList();  // [[1, 2, 3], [4, 5, 6], [7]]
```

#### `windowSliding(int size)` – Gleitende Fenster (überlappend)

Erzeugt überlappende Fenster, wobei jedes Fenster um ein Element versetzt startet. Nützlich für gleitende Durchschnitte oder Nachbar-Vergleiche.

```java
Stream.of(1, 2, 3, 4, 5)
    .gather(Gatherers.windowSliding(3))
    .toList();  // [[1, 2, 3], [2, 3, 4], [3, 4, 5]]

// Gleitender Durchschnitt
Stream.of(10.0, 20.0, 30.0, 40.0, 50.0)
    .gather(Gatherers.windowSliding(3))
    .map(window -> window.stream().mapToDouble(d -> d).average().orElse(0))
    .toList();  // [20.0, 30.0, 40.0]
```

#### `scan(Supplier<R> initial, BiFunction<R, T, R> scanner)` – Laufende Akkumulation

Funktioniert ähnlich wie die Terminal-Operation `Stream.reduce()`, gibt aber **jeden Zwischenwert** als Element in den Stream aus – nicht nur das Endergebnis. Ideal für laufende Summen, Zustandsmaschinen usw.

```java
// Laufende Summe
Stream.of(1, 2, 3, 4)
    .gather(Gatherers.scan(() -> 0, Integer::sum))
    .toList();  // [1, 3, 6, 10]

// Laufendes Maximum
Stream.of(3, 1, 4, 1, 5, 9, 2, 6)
    .gather(Gatherers.scan(() -> Integer.MIN_VALUE, Integer::max))
    .toList();  // [3, 3, 4, 4, 5, 9, 9, 9]

// String-Verkettung mit Zwischenschritten
Stream.of("a", "b", "c", "d")
    .gather(Gatherers.scan(() -> "", (acc, s) -> acc + s))
    .toList();  // ["a", "ab", "abc", "abcd"]
```

#### `fold(Supplier<R> initial, BiFunction<R, T, R> folder)` – Reduktion auf einen Wert

Ähnlich wie `scan`, gibt aber nur das **Endergebnis** aus (ein einzelnes Element). Unterschied zu `reduce`: `fold` erlaubt einen anderen Ergebnistyp als den Elementtyp.

```java
// Alle Strings zu einem zusammenfügen
Stream.of("Hallo", " ", "Welt", "!")
    .gather(Gatherers.fold(() -> "", String::concat))
    .toList();  // ["Hallo Welt!"]

// Elemente zählen (Ergebnistyp ≠ Elementtyp)
Stream.of("a", "b", "c")
    .gather(Gatherers.fold(() -> 0, (count, _) -> count + 1))
    .toList();  // [3]
```

#### `mapConcurrent(int maxConcurrency, Function<T, R> mapper)` – Parallele Verarbeitung

Führt die Mapping-Funktion parallel auf virtuellen Threads aus, begrenzt auf `maxConcurrency` gleichzeitige Aufrufe. Perfekt für I/O-lastige Operationen. Der Stream muss **sequentiell** sein.

```java
// Bis zu 4 URLs gleichzeitig abrufen
Stream.of(url1, url2, url3, url4, url5)
    .gather(Gatherers.mapConcurrent(4, this::fetchUrl))
    .toList();

// Parallele Datenbank-Abfragen
userIds.stream()
    .gather(Gatherers.mapConcurrent(10, userService::findById))
    .filter(Optional::isPresent)
    .map(Optional::get)
    .toList();
```

### Gatherer verketten

Gatherer lassen sich mit `andThen` kombinieren – genau wie Streams mit mehreren `.gather()`-Aufrufen:

```java
// Gleitende Fenster → Durchschnitte → laufende Summe
Stream.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
    .gather(
        Gatherers.windowSliding(3).andThen(
            Gatherers.scan(() -> 0.0, (sum, window) ->
                sum + window.stream().mapToDouble(d -> d).average().orElse(0))
        )
    )
    .toList();

// Oder einfach mehrere gather()-Aufrufe hintereinander
Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    .gather(Gatherers.windowFixed(5))
    .gather(Gatherers.scan(() -> List.<Integer>of(), (acc, window) ->
        Stream.concat(acc.stream(), window.stream()).toList()))
    .toList();
```

---

### Eigene Gatherer erstellen

Ein Gatherer besteht aus bis zu vier Komponenten:

```
Gatherer<T, A, R>
  T = Eingabetyp (Elemente aus dem Stream)
  A = Zustandstyp (interner, veränderlicher Zustand)
  R = Ausgabetyp (Elemente, die weitergegeben werden)
```

```java
Gatherer.ofSequential(initializer, integrator);           // ohne Finisher
Gatherer.ofSequential(initializer, integrator, finisher); // mit Finisher
Gatherer.of(initializer, integrator, combiner, finisher); // parallelisierbar
```

| Komponente      | Typ                                                | Aufgabe                                               |
|-----------------|----------------------------------------------------|-------------------------------------------------------|
| **Initializer** | `Supplier<A>`                                      | Erstellt den initialen Zustand                        |
| **Integrator**  | `Gatherer.Integrator<A, T, R>`                     | Verarbeitet jedes Element; kann Elemente weitergeben  |
| **Combiner**    | `BinaryOperator<A>`                                | Kombiniert Zustände bei paralleler Verarbeitung       |
| **Finisher**    | `BiConsumer<A, Gatherer.Downstream<? super R>>`    | Wird am Ende aufgerufen; kann letzte Elemente ausgeben|

#### Der Integrator im Detail

Der Integrator ist das Herzstück. Er erhält drei Parameter und gibt einen `boolean` zurück:

```java
(state, element, downstream) -> {
    // state     – der veränderliche Zustand
    // element   – das aktuelle Stream-Element
    // downstream – damit werden Ergebniselemente weitergegeben

    downstream.push(transformedElement);  // Element weitergeben

    return true;   // true  = weitermachen
                   // false = Stream vorzeitig beenden (short-circuit)
}
```

#### Beispiel 1: Nur jedes n-te Element

```java
static <T> Gatherer<T, ?, T> everyNth(int n) {
    return Gatherer.ofSequential(
        () -> new int[]{0},                   // Zähler als Zustand
        (state, element, downstream) -> {
            if (state[0]++ % n == 0) {
                downstream.push(element);
            }
            return true;
        }
    );
}

Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
    .gather(everyNth(3))
    .toList();  // [1, 4, 7]
```

#### Beispiel 2: Distinct By – Deduplizierung nach Schlüssel

```java
static <T, K> Gatherer<T, ?, T> distinctBy(Function<T, K> keyExtractor) {
    return Gatherer.ofSequential(
        HashSet<K>::new,                      // Set als Zustand
        (seen, element, downstream) -> {
            if (seen.add(keyExtractor.apply(element))) {
                downstream.push(element);     // nur neue Schlüssel weitergeben
            }
            return true;
        }
    );
}

record Person(String name, String city) {}

Stream.of(
    new Person("Anna", "Bern"),
    new Person("Beat", "Zürich"),
    new Person("Carla", "Bern"),      // Bern schon gesehen → wird gefiltert
    new Person("Dani", "Basel")
)
    .gather(distinctBy(Person::city))
    .toList();  // [Anna/Bern, Beat/Zürich, Dani/Basel]
```

#### Beispiel 3: Mit Finisher – Gruppierung in Batches mit Rest

```java
static <T> Gatherer<T, ?, List<T>> batch(int size) {
    return Gatherer.ofSequential(
        ArrayList<T>::new,                    // aktuelle Gruppe
        (batch, element, downstream) -> {
            batch.add(element);
            if (batch.size() == size) {
                downstream.push(new ArrayList<>(batch));
                batch.clear();
            }
            return true;
        },
        // Finisher: restliche Elemente ausgeben
        (batch, downstream) -> {
            if (!batch.isEmpty()) {
                downstream.push(new ArrayList<>(batch));
            }
        }
    );
}

Stream.of(1, 2, 3, 4, 5, 6, 7)
    .gather(batch(3))
    .toList();  // [[1, 2, 3], [4, 5, 6], [7]]
```

#### Beispiel 4: Short-Circuiting – Stream vorzeitig beenden

Wenn der Integrator `false` zurückgibt, wird der Stream sofort beendet:

```java
// Nimm Elemente, solange ein Prädikat gilt (wie takeWhile, aber als Gatherer)
static <T> Gatherer<T, ?, T> takeWhile(Predicate<T> predicate) {
    return Gatherer.ofSequential(
        () -> null,                           // kein Zustand nötig
        (_, element, downstream) -> {
            if (predicate.test(element)) {
                downstream.push(element);
                return true;                  // weitermachen
            }
            return false;                     // Stream beenden
        }
    );
}

Stream.of(2, 4, 6, 7, 8, 10)
    .gather(takeWhile(n -> n % 2 == 0))
    .toList();  // [2, 4, 6]
```

#### Beispiel 5: Zustandsbehaftete Transformation – Differenz zum Vorgänger

```java
static Gatherer<Integer, ?, Integer> deltas() {
    return Gatherer.ofSequential(
        () -> new int[]{Integer.MIN_VALUE},   // vorheriger Wert
        (state, element, downstream) -> {
            if (state[0] != Integer.MIN_VALUE) {
                downstream.push(element - state[0]);
            }
            state[0] = element;
            return true;
        }
    );
}

Stream.of(10, 13, 17, 20, 28)
    .gather(deltas())
    .toList();  // [3, 4, 3, 8]
```

### Vergleich: Gatherer vs. Collector

| Eigenschaft             | Gatherer (Zwischenoperation)       | Collector (Terminaloperation)     |
|-------------------------|------------------------------------|-----------------------------------|
| Position in Pipeline    | Mitte (`.gather(...)`)             | Ende (`.collect(...)`)            |
| Kann Elemente ausgeben  | Ja, beliebig viele pro Eingabe     | Nein, nur Endergebnis             |
| Short-Circuit möglich   | Ja (`return false`)                | Nein                              |
| Stream läuft weiter     | Ja                                 | Nein                              |
| Seit                    | Java 24                            | Java 8                            |
