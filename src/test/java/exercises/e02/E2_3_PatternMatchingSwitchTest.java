package exercises.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgaben:
 * - formatObject(): Refaktoriere die if-else-Kette zu switch mit Type Patterns.
 *   Tipp: null kann direkt als "case null ->" behandelt werden.
 * - categorizeInput(): Refaktoriere zu switch mit Guarded Patterns (when).
 *   z.B. case String s when s.isEmpty() ->
 */
@DisplayName("Übung 2.3: Pattern Matching im switch")
class E2_3_PatternMatchingSwitchTest {

    @Test
    void formatNull() {
        assertEquals("null", PatternMatchingSwitch.formatObject(null));
    }

    @Test
    void formatGanzzahl() {
        assertEquals("Ganzzahl: 42", PatternMatchingSwitch.formatObject(42));
    }

    @Test
    void formatLong() {
        assertEquals("Long: 100", PatternMatchingSwitch.formatObject(100L));
    }

    @Test
    void formatDezimalzahl() {
        assertEquals("Dezimalzahl: 3.14", PatternMatchingSwitch.formatObject(3.14159));
    }

    @Test
    void formatText() {
        assertEquals("Text: \"Hallo Welt\"", PatternMatchingSwitch.formatObject("Hallo Welt"));
    }

    @Test
    void formatListe() {
        assertEquals("Liste[3]", PatternMatchingSwitch.formatObject(List.of(1, 2, 3)));
    }

    @Test
    void nullEingabe() {
        assertEquals("Keine Eingabe", PatternMatchingSwitch.categorizeInput(null));
    }

    @Test
    void leererText() {
        assertEquals("Leerer Text", PatternMatchingSwitch.categorizeInput(""));
    }

    @Test
    void normalerText() {
        assertEquals("Text: Hallo", PatternMatchingSwitch.categorizeInput("Hallo"));
    }

    @Test
    void negativeZahl() {
        assertEquals("Negative Zahl", PatternMatchingSwitch.categorizeInput(-5));
    }

    @Test
    void nullWert() {
        assertEquals("Null", PatternMatchingSwitch.categorizeInput(0));
    }

    @Test
    void kleineZahl() {
        assertEquals("Kleine Zahl: 42", PatternMatchingSwitch.categorizeInput(42));
    }

    @Test
    void grosseZahl() {
        assertEquals("Grosse Zahl: 999", PatternMatchingSwitch.categorizeInput(999));
    }

    @Test
    void leereListe() {
        assertEquals("Leere Liste", PatternMatchingSwitch.categorizeInput(List.of()));
    }
}
