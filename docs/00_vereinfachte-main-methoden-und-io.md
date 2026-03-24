# 0 · Vereinfachte Main-Methoden & IO

## Vereinfachte Main-Methoden

Seit Java 21 (Preview) bzw. Java 25 (Final) kann die `main`-Methode drastisch vereinfacht werden. Ziel: Der Einstieg in Java soll einfacher werden – weniger Boilerplate für einfache Programme.

### Vorher (klassisch)

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hallo Welt!");
    }
}
```

### Nachher (vereinfacht)

```java
void main() {
    println("Hallo Welt!");
}
```

### Was hat sich geändert?

| Klassisch | Vereinfacht |
|-----------|-------------|
| `public class` erforderlich | Implizit deklarierte Klasse – keine Klassendeklaration nötig |
| `public static void main(String[] args)` | `void main()` genügt |
| `System.out.println(...)` | `println(...)` direkt verfügbar |
| `package (...)` | braucht es nicht mehr |

### Regeln für vereinfachte Main-Methoden

- Die `main`-Methode darf **nicht-statisch** und **ohne Parameter** sein:

```java
void main() { }           // ✔ gültig
void main(String[] args) { }  // ✔ gültig
static void main() { }        // ✔ gültig
```

- Die Sichtbarkeit darf eingeschränkt werden (kein `public` nötig)
- Der `String[] args`-Parameter ist **optional** – nur nötig, wenn Kommandozeilenargumente benötigt werden

### Implizit deklarierte Klassen

Dateien ohne explizite Klassendeklaration werden als **implizit deklarierte Klasse** behandelt:

- Die Datei enthält nur Methoden und Felder auf oberster Ebene
- Der Compiler erzeugt automatisch eine Klasse im unbenannten Paket
- Ideal für kleine Skripte und Lernprogramme

```java
// Datei: Greeting.java – keine class-Deklaration nötig

String gruss = "Hallo";

void main() {
    println(gruss + " Welt!");
}
```

---

## Vereinfachtes IO

Mit Java 24 (Preview) bzw. Java 25 werden häufig gebrauchte IO-Methoden direkt in implizit deklarierten Klassen verfügbar – ohne `System.out` oder `Scanner`.

### Vorher (klassisch)

```java
import java.util.Scanner;

public class Greeting {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wie heisst du? ");
        String name = scanner.nextLine();
        System.out.println("Hallo, " + name + "!");
    }
}
```

### Nachher (vereinfacht)

```java
void main() {
    print("Wie heisst du? ");
    String name = readln("");
    println("Hallo, " + name + "!");
}
```

### Verfügbare IO-Methoden

| Methode | Beschreibung |
|---------|-------------|
| `println(Object obj)` | Gibt `obj` aus, gefolgt von einem Zeilenumbruch |
| `println()` | Gibt nur einen Zeilenumbruch aus |
| `print(Object obj)` | Gibt `obj` aus, ohne Zeilenumbruch |
| `readln(String prompt)` | Zeigt `prompt` an und liest eine Zeile von der Konsole |

### Wichtige Hinweise

- Diese Methoden sind über die Klasse `java.io.IO` verfügbar
- In implizit deklarierten Klassen werden sie **automatisch importiert**
- In regulären Klassen können sie per `import static java.io.IO.*` genutzt werden

```java
import static java.io.IO.*;

public class MyApp {
    public static void main(String[] args) {
        String name = readln("Name: ");
        println("Willkommen, " + name);
    }
}
```

---

## Zusammenfassung

Die Kombination aus vereinfachten Main-Methoden und IO macht Java deutlich zugänglicher:

```java
void main() {
    String name = readln("Wie heisst du? ");
    int alter = Integer.parseInt(readln("Wie alt bist du? "));

    if (alter >= 18) {
        println(name + ", du bist volljährig.");
    } else {
        println(name + ", du bist noch minderjährig.");
    }
}
```

Kein `public class`, kein `static`, kein `String[] args`, kein `System.out`, kein `Scanner` – einfach loslegen.

---

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
