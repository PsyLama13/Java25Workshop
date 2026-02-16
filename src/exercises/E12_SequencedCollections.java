package exercises;

import java.util.*;

/**
 * Übung 12: Sequenced Collections
 *
 * Aufgabe: Refaktoriere die Workarounds fuer erstes/letztes Element und
 *          umgekehrte Iteration zur neuen Sequenced Collections API.
 *
 * Hinweise:
 * - list.getFirst() / list.getLast() statt list.get(0) / list.get(list.size()-1)
 * - list.addFirst() / list.addLast()
 * - list.removeFirst() / list.removeLast()
 * - list.reversed() fuer umgekehrte Ansicht (View, keine Kopie)
 * - map.firstEntry() / map.lastEntry() / map.reversed()
 */
public class E12_SequencedCollections {

    // ========================================================================
    // TODO 1: Refaktoriere mit Sequenced Collections API.
    //         Ersetze die Index-basierten Zugriffe.
    // ========================================================================
    static String getFirstAndLast(List<String> list) {
        if (list.isEmpty()) {
            return "Liste ist leer";
        }
        // Alt: Index-basierter Zugriff
        String first = list.get(0);
        String last = list.get(list.size() - 1);
        return "Erstes: " + first + ", Letztes: " + last;
    }

    // ========================================================================
    // TODO 2: Refaktoriere die umgekehrte Iteration mit reversed().
    // ========================================================================
    static List<String> reverseList(List<String> list) {
        // Alt: Manuell umgekehrte Kopie erstellen
        List<String> reversed = new ArrayList<>(list);
        Collections.reverse(reversed);
        return reversed;
    }

    // ========================================================================
    // TODO 3: Refaktoriere mit SequencedMap-Methoden.
    //         Ersetze die Workarounds fuer erstes/letztes Map-Entry.
    // ========================================================================
    static String getFirstAndLastEntry(LinkedHashMap<String, Integer> map) {
        if (map.isEmpty()) {
            return "Map ist leer";
        }

        // Alt: Umstaendlicher Zugriff auf erstes Element
        Map.Entry<String, Integer> first = map.entrySet().iterator().next();

        // Alt: Noch umstaendlicher Zugriff auf letztes Element
        Map.Entry<String, Integer> last = null;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            last = entry;
        }

        return "Erstes: " + first + ", Letztes: " + last;
    }

    // ========================================================================
    // TODO 4: Refaktoriere mit addFirst/addLast und removeFirst/removeLast.
    // ========================================================================
    static List<String> manipulateList(List<String> original) {
        LinkedList<String> list = new LinkedList<>(original);

        // Alt: Element am Anfang einfuegen
        list.add(0, "START");

        // Alt: Element am Ende einfuegen
        list.add(list.size(), "END");

        // Alt: Erstes Element entfernen
        list.remove(0);

        // Alt: Letztes Element entfernen
        list.remove(list.size() - 1);

        return list;
    }

    // ========================================================================
    // TODO 5: Nutze reversed() um eine Map rueckwaerts zu iterieren.
    // ========================================================================
    static void printReversedMap(LinkedHashMap<String, Integer> map) {
        // Alt: Schluessel in Liste kopieren und umkehren
        List<String> keys = new ArrayList<>(map.keySet());
        Collections.reverse(keys);
        for (String key : keys) {
            System.out.println(key + " = " + map.get(key));
        }
    }

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) {
        // Test getFirstAndLast
        System.out.println("=== First & Last ===");
        System.out.println(getFirstAndLast(List.of("Alpha", "Beta", "Gamma", "Delta")));
        System.out.println(getFirstAndLast(List.of()));

        System.out.println();

        // Test reverseList
        System.out.println("=== Reversed List ===");
        System.out.println(reverseList(List.of("A", "B", "C", "D")));

        System.out.println();

        // Test getFirstAndLastEntry
        System.out.println("=== First & Last Entry ===");
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("eins", 1);
        map.put("zwei", 2);
        map.put("drei", 3);
        System.out.println(getFirstAndLastEntry(map));

        System.out.println();

        // Test manipulateList
        System.out.println("=== Manipulate List ===");
        System.out.println(manipulateList(List.of("A", "B", "C")));

        System.out.println();

        // Test printReversedMap
        System.out.println("=== Reversed Map ===");
        printReversedMap(map);
    }
}
