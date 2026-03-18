package exercises.e07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgaben:
 * - partition(): Refaktoriere mit Gatherers.windowFixed(). Teile die Liste in Gruppen fester Grösse.
 * - movingAverage(): Refaktoriere mit Gatherers.windowSliding(). Berechne den gleitenden Durchschnitt.
 * - cumulativeSum(): Refaktoriere mit Gatherers.scan(). Berechne die kumulativen Summen.
 * - everyNth(): Erstelle einen eigenen Gatherer, der nur jedes n-te Element durchlässt.
 * - distinctConsecutive(): Erstelle einen eigenen Gatherer, der aufeinanderfolgende Duplikate entfernt.
 *   z.B. [1, 1, 2, 2, 2, 3, 1, 1] -> [1, 2, 3, 1]
 *
 * Bonus: Erstelle einen Gatherer, der Elemente gruppiert, solange eine Bedingung erfüllt ist
 *        (takeWhileGrouping). z.B. Gruppiere aufsteigende Zahlen:
 *        [1, 2, 3, 1, 2, 5, 4] -> [[1, 2, 3], [1, 2, 5], [4]]
 *
 * Hinweise:
 * - Gatherers.windowFixed(n) für feste Fenster
 * - Gatherers.windowSliding(n) für gleitende Fenster
 * - Gatherers.scan(init, op) für kumulative Operationen
 * - Eigene Gatherer mit Gatherer.ofSequential(init, integrator)
 */
@DisplayName("Übung 7.2: Stream Gatherers")
class E7_2_StreamGatherersExerciseTest {

    @Test
    void partitioniertKorrekt() {
        assertEquals(List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7)),
                StreamGatherersExercise.partition(List.of(1, 2, 3, 4, 5, 6, 7), 3));
    }

    @Test
    void partitionExakteGruppen() {
        assertEquals(List.of(List.of(1, 2), List.of(3, 4)),
                StreamGatherersExercise.partition(List.of(1, 2, 3, 4), 2));
    }

    @Test
    void partitionLeereListe() {
        assertEquals(List.of(), StreamGatherersExercise.partition(List.of(), 3));
    }

    @Test
    void berechnetGleitendenDurchschnitt() {
        List<Double> result = StreamGatherersExercise.movingAverage(List.of(1, 2, 3, 4, 5, 6, 7, 8), 3);
        assertEquals(6, result.size());
        assertEquals(2.0, result.get(0), 0.001);
        assertEquals(3.0, result.get(1), 0.001);
        assertEquals(4.0, result.get(2), 0.001);
        assertEquals(5.0, result.get(3), 0.001);
        assertEquals(6.0, result.get(4), 0.001);
        assertEquals(7.0, result.get(5), 0.001);
    }

    @Test
    void berechnetKumulativeSumme() {
        assertEquals(List.of(1, 3, 6, 10, 15),
                StreamGatherersExercise.cumulativeSum(List.of(1, 2, 3, 4, 5)));
    }

    @Test
    void kumulativeSummeLeereListe() {
        assertEquals(List.of(), StreamGatherersExercise.cumulativeSum(List.of()));
    }

    @Test
    void jedesNteElement() {
        assertEquals(List.of(1, 4, 7),
                StreamGatherersExercise.everyNth(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), 3));
    }

    @Test
    void jedesElement() {
        assertEquals(List.of(1, 2, 3), StreamGatherersExercise.everyNth(List.of(1, 2, 3), 1));
    }

    @Test
    void entferntAufeinanderFolgendeDuplikate() {
        assertEquals(List.of(1, 2, 3, 1),
                StreamGatherersExercise.distinctConsecutive(List.of(1, 1, 2, 2, 2, 3, 1, 1)));
    }

    @Test
    void keineDuplikate() {
        assertEquals(List.of(1, 2, 3), StreamGatherersExercise.distinctConsecutive(List.of(1, 2, 3)));
    }

    @Test
    void distinctConsecutiveLeereListe() {
        assertEquals(List.of(), StreamGatherersExercise.distinctConsecutive(List.of()));
    }
}
