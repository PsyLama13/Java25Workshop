package exercises;

import java.util.*;
import java.util.stream.*;

/**
 * Übung 7: Sequenced Collections & Stream Gatherers
 *
 * Teil 1 – Sequenced Collections: Ersetze Workarounds für erstes/letztes Element
 *          und umgekehrte Iteration durch die neue einheitliche API.
 *
 * Teil 2 – Stream Gatherers: Refaktoriere manuelle Stream-Verarbeitungen zu
 *          Stream Gatherers (eingebaute und eigene).
 *
 * Hinweise Sequenced Collections:
 * - list.getFirst() / list.getLast() statt list.get(0) / list.get(list.size()-1)
 * - list.addFirst() / list.addLast() / list.removeFirst() / list.removeLast()
 * - list.reversed() für umgekehrte Ansicht (View, keine Kopie!)
 * - map.firstEntry() / map.lastEntry() / map.reversed()
 *
 * Hinweise Stream Gatherers:
 * - Gatherers.windowFixed(n) für feste Fenster
 * - Gatherers.windowSliding(n) für gleitende Fenster
 * - Gatherers.scan(init, op) für kumulative Operationen
 * - Gatherers.mapConcurrent(n, fn) für parallele Verarbeitung
 * - Eigene Gatherer mit Gatherer.ofSequential(init, integrator)
 */
public class E07_SequencedCollectionsUndStreamGatherers {

    // ========================================================================
    // TEIL 1: SEQUENCED COLLECTIONS
    // ========================================================================

    // TODO 1: Refaktoriere mit Sequenced Collections API.
    //         Ersetze die Index-basierten Zugriffe.
    // ========================================================================
    static String getFirstAndLast(List<String> list) {
        if (list.isEmpty()) { return "Liste ist leer"; }
        // Alt: Index-basierter Zugriff
        String first = list.get(0);
        String last  = list.get(list.size() - 1);
        return "Erstes: " + first + ", Letztes: " + last;
    }

    // TODO 2: Refaktoriere die umgekehrte Iteration mit reversed().
    //         Hinweis: reversed() gibt eine View zurück – nutze new ArrayList<>(...) um eine Kopie zu erhalten
    // ========================================================================
    static List<String> reverseList(List<String> list) {
        // Alt: Manuell umgekehrte Kopie erstellen
        List<String> reversed = new ArrayList<>(list);
        Collections.reverse(reversed);
        return reversed;
    }

    // TODO 3: Refaktoriere mit SequencedMap-Methoden.
    //         Ersetze die Workarounds für erstes/letztes Map-Entry.
    // ========================================================================
    static String getFirstAndLastEntry(LinkedHashMap<String, Integer> map) {
        if (map.isEmpty()) { return "Map ist leer"; }

        // Alt: Umständlicher Zugriff auf erstes Element
        Map.Entry<String, Integer> first = map.entrySet().iterator().next();

        // Alt: Noch umständlicher Zugriff auf letztes Element
        Map.Entry<String, Integer> last = null;
        for (Map.Entry<String, Integer> entry : map.entrySet()) { last = entry; }

        return "Erstes: " + first + ", Letztes: " + last;
    }

    // TODO 4: Refaktoriere mit addFirst/addLast und removeFirst/removeLast.
    // ========================================================================
    static List<String> manipulateList(List<String> original) {
        LinkedList<String> list = new LinkedList<>(original);

        list.add(0, "START");           // → list.addFirst("START")
        list.add(list.size(), "END");   // → list.addLast("END")
        list.remove(0);                 // → list.removeFirst()
        list.remove(list.size() - 1);   // → list.removeLast()

        return list;
    }

    // ========================================================================
    // TEIL 2: STREAM GATHERERS
    // ========================================================================

    // TODO 5: Refaktoriere mit Gatherers.windowFixed().
    //         Teile die Liste in Gruppen fester Grösse auf.
    // ========================================================================
    static <T> List<List<T>> partition(List<T> list, int size) {
        // Alt: Manuelle Partitionierung
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }

    // TODO 6: Refaktoriere mit Gatherers.windowSliding().
    //         Berechne den gleitenden Durchschnitt.
    // ========================================================================
    static List<Double> movingAverage(List<Integer> numbers, int windowSize) {
        // Alt: Manueller gleitender Durchschnitt
        List<Double> averages = new ArrayList<>();
        for (int i = 0; i <= numbers.size() - windowSize; i++) {
            double sum = 0;
            for (int j = i; j < i + windowSize; j++) { sum += numbers.get(j); }
            averages.add(sum / windowSize);
        }
        return averages;
    }

    // TODO 7: Refaktoriere mit Gatherers.scan().
    //         Berechne die kumulativen Summen.
    // ========================================================================
    static List<Integer> cumulativeSum(List<Integer> numbers) {
        // Alt: Manuelle kumulative Summe
        List<Integer> result = new ArrayList<>();
        int sum = 0;
        for (int num : numbers) { sum += num; result.add(sum); }
        return result;
    }

    // TODO 8: Erstelle einen eigenen Gatherer, der nur jedes n-te Element
    //         durchlässt (everyNth). Refaktoriere die manuelle Implementierung.
    // ========================================================================
    static <T> List<T> everyNth(List<T> list, int n) {
        // Alt: Manuelle Filterung
        List<T> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i % n == 0) { result.add(list.get(i)); }
        }
        return result;
    }

    // TODO 9: Erstelle einen eigenen Gatherer, der aufeinanderfolgende
    //         Duplikate entfernt (distinctConsecutive).
    //         z.B. [1, 1, 2, 2, 2, 3, 1, 1] -> [1, 2, 3, 1]
    // ========================================================================
    static <T> List<T> distinctConsecutive(List<T> list) {
        // Alt: Manuelle Duplikat-Entfernung
        List<T> result = new ArrayList<>();
        T last = null;
        for (T item : list) {
            if (!item.equals(last)) { result.add(item); last = item; }
        }
        return result;
    }

    // TODO 10 (Bonus): Erstelle einen Gatherer, der Elemente gruppiert,
    //         solange eine Bedingung erfüllt ist (takeWhileGrouping).
    //         z.B. Gruppiere aufsteigende Zahlen:
    //         [1, 2, 3, 1, 2, 5, 4] -> [[1, 2, 3], [1, 2, 5], [4]]
    // ========================================================================

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== Sequenced Collections ===");
        System.out.println(getFirstAndLast(List.of("Alpha", "Beta", "Gamma", "Delta")));
        System.out.println(reverseList(List.of("A", "B", "C", "D")));
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("eins", 1); map.put("zwei", 2); map.put("drei", 3);
        System.out.println(getFirstAndLastEntry(map));
        System.out.println(manipulateList(List.of("A", "B", "C")));

        System.out.println();

        System.out.println("=== Stream Gatherers ===");
        System.out.println("Partition:    " + partition(List.of(1, 2, 3, 4, 5, 6, 7), 3));
        // [[1, 2, 3], [4, 5, 6], [7]]

        System.out.println("MovingAvg:    " + movingAverage(List.of(1, 2, 3, 4, 5, 6, 7, 8), 3));
        // [2.0, 3.0, 4.0, 5.0, 6.0, 7.0]

        System.out.println("CumSum:       " + cumulativeSum(List.of(1, 2, 3, 4, 5)));
        // [1, 3, 6, 10, 15]

        System.out.println("EveryNth(3):  " + everyNth(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), 3));
        // [1, 4, 7]

        System.out.println("DistinctCons: " + distinctConsecutive(List.of(1, 1, 2, 2, 2, 3, 1, 1)));
        // [1, 2, 3, 1]
    }
}
