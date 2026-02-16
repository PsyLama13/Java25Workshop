package exercises;

import java.util.*;
import java.util.stream.*;

/**
 * Übung 13: Stream Gatherers
 *
 * Aufgabe: Refaktoriere die manuellen Stream-Verarbeitungen zu
 *          Stream Gatherers (eingebaute und eigene).
 *
 * Hinweise:
 * - Gatherers.windowFixed(n) fuer feste Fenster
 * - Gatherers.windowSliding(n) fuer gleitende Fenster
 * - Gatherers.scan(init, op) fuer kumulative Operationen
 * - Gatherers.mapConcurrent(n, fn) fuer parallele Verarbeitung
 * - Eigene Gatherer mit Gatherer.ofSequential(init, integrator)
 */
public class E13_StreamGatherers {

    // ========================================================================
    // TODO 1: Refaktoriere mit Gatherers.windowFixed().
    //         Teile die Liste in Gruppen fester Groesse auf.
    // ========================================================================
    static <T> List<List<T>> partition(List<T> list, int size) {
        // Alt: Manuelle Partitionierung
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }

    // ========================================================================
    // TODO 2: Refaktoriere mit Gatherers.windowSliding().
    //         Berechne den gleitenden Durchschnitt.
    // ========================================================================
    static List<Double> movingAverage(List<Integer> numbers, int windowSize) {
        // Alt: Manueller gleitender Durchschnitt
        List<Double> averages = new ArrayList<>();
        for (int i = 0; i <= numbers.size() - windowSize; i++) {
            double sum = 0;
            for (int j = i; j < i + windowSize; j++) {
                sum += numbers.get(j);
            }
            averages.add(sum / windowSize);
        }
        return averages;
    }

    // ========================================================================
    // TODO 3: Refaktoriere mit Gatherers.scan().
    //         Berechne die kumulativen Summen.
    // ========================================================================
    static List<Integer> cumulativeSum(List<Integer> numbers) {
        // Alt: Manuelle kumulative Summe
        List<Integer> result = new ArrayList<>();
        int sum = 0;
        for (int num : numbers) {
            sum += num;
            result.add(sum);
        }
        return result;
    }

    // ========================================================================
    // TODO 4: Erstelle einen eigenen Gatherer, der nur jedes n-te Element
    //         durchlaesst (everyNth). Refaktoriere die manuelle Implementierung.
    // ========================================================================
    static <T> List<T> everyNth(List<T> list, int n) {
        // Alt: Manuelle Filterung
        List<T> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i % n == 0) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    // ========================================================================
    // TODO 5: Erstelle einen eigenen Gatherer, der aufeinanderfolgende
    //         Duplikate entfernt (distinctConsecutive).
    //         z.B. [1, 1, 2, 2, 2, 3, 1, 1] -> [1, 2, 3, 1]
    // ========================================================================
    static <T> List<T> distinctConsecutive(List<T> list) {
        // Alt: Manuelle Duplikat-Entfernung
        List<T> result = new ArrayList<>();
        T last = null;
        for (T item : list) {
            if (!item.equals(last)) {
                result.add(item);
                last = item;
            }
        }
        return result;
    }

    // ========================================================================
    // TODO 6 (Bonus): Erstelle einen Gatherer der Elemente gruppiert,
    //         solange eine Bedingung erfuellt ist (takeWhileGrouping).
    //         z.B. Gruppiere aufsteigende Zahlen:
    //         [1, 2, 3, 1, 2, 5, 4] -> [[1, 2, 3], [1, 2, 5], [4]]
    // ========================================================================

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) {
        // Test partition
        System.out.println("=== Partition ===");
        System.out.println(partition(List.of(1, 2, 3, 4, 5, 6, 7), 3));
        // [[1, 2, 3], [4, 5, 6], [7]]

        System.out.println();

        // Test movingAverage
        System.out.println("=== Moving Average ===");
        System.out.println(movingAverage(List.of(1, 2, 3, 4, 5, 6, 7, 8), 3));
        // [2.0, 3.0, 4.0, 5.0, 6.0, 7.0]

        System.out.println();

        // Test cumulativeSum
        System.out.println("=== Cumulative Sum ===");
        System.out.println(cumulativeSum(List.of(1, 2, 3, 4, 5)));
        // [1, 3, 6, 10, 15]

        System.out.println();

        // Test everyNth
        System.out.println("=== Every Nth ===");
        System.out.println(everyNth(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), 3));
        // [1, 4, 7]

        System.out.println();

        // Test distinctConsecutive
        System.out.println("=== Distinct Consecutive ===");
        System.out.println(distinctConsecutive(List.of(1, 1, 2, 2, 2, 3, 1, 1)));
        // [1, 2, 3, 1]
    }
}
