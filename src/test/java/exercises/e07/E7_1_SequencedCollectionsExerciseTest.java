package exercises.e07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgaben:
 * - getFirstAndLast(): Refaktoriere mit Sequenced Collections API.
 *   Nutze list.getFirst() / list.getLast() statt list.get(0) / list.get(list.size()-1).
 * - reverseList(): Refaktoriere mit list.reversed().
 *   Hinweis: reversed() gibt eine View zurück – nutze new ArrayList<>(...) für eine Kopie.
 * - getFirstAndLastEntry(): Refaktoriere mit SequencedMap-Methoden.
 *   Nutze map.firstEntry() / map.lastEntry().
 * - manipulateList(): Refaktoriere mit addFirst/addLast und removeFirst/removeLast.
 */
@DisplayName("Übung 7.1: Sequenced Collections")
class E7_1_SequencedCollectionsExerciseTest {

    @Test
    void erstesUndLetztesElement() {
        assertEquals("Erstes: Alpha, Letztes: Delta",
                SequencedCollectionsExercise.getFirstAndLast(List.of("Alpha", "Beta", "Gamma", "Delta")));
    }

    @Test
    void leereListe() {
        assertEquals("Liste ist leer", SequencedCollectionsExercise.getFirstAndLast(List.of()));
    }

    @Test
    void einElement() {
        assertEquals("Erstes: X, Letztes: X", SequencedCollectionsExercise.getFirstAndLast(List.of("X")));
    }

    @Test
    void kehrtListeUm() {
        assertEquals(List.of("D", "C", "B", "A"),
                SequencedCollectionsExercise.reverseList(List.of("A", "B", "C", "D")));
    }

    @Test
    void reverseLeereList() {
        assertEquals(List.of(), SequencedCollectionsExercise.reverseList(List.of()));
    }

    @Test
    void erstesUndLetztesEntry() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("eins", 1);
        map.put("zwei", 2);
        map.put("drei", 3);
        String result = SequencedCollectionsExercise.getFirstAndLastEntry(map);
        assertTrue(result.contains("eins=1"), "Sollte erstes Entry enthalten");
        assertTrue(result.contains("drei=3"), "Sollte letztes Entry enthalten");
    }

    @Test
    void leereMap() {
        assertEquals("Map ist leer", SequencedCollectionsExercise.getFirstAndLastEntry(new LinkedHashMap<>()));
    }

    @Test
    void manipuliertListe() {
        assertEquals(List.of("A", "B", "C"), SequencedCollectionsExercise.manipulateList(List.of("A", "B", "C")));
    }
}
