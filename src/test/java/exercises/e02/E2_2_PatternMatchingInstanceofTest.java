package exercises.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgaben:
 * - formatValue(): Refaktoriere mit Pattern Matching für instanceof.
 *   Nutze "if (obj instanceof String s)" statt expliziter Casts.
 * - safeLengthSum(): Refaktoriere mit negiertem Pattern Matching (Flow Scoping / Early Return).
 *   Tipp: !(obj instanceof Type t) als Guard.
 */
@DisplayName("Übung 2.2: Pattern Matching (instanceof)")
class E2_2_PatternMatchingInstanceofTest {

    @Test
    void formatString() {
        assertEquals("String mit Länge 5: HELLO", PatternMatchingInstanceof.formatValue("Hello"));
    }

    @Test
    void formatInteger() {
        assertEquals("Integer: 84", PatternMatchingInstanceof.formatValue(42));
    }

    @Test
    void formatDouble() {
        assertEquals("Double gerundet: 3", PatternMatchingInstanceof.formatValue(3.14));
    }

    @Test
    void formatList() {
        assertEquals("Liste mit 3 Elementen", PatternMatchingInstanceof.formatValue(List.of(1, 2, 3)));
    }

    @Test
    void beideStrings() {
        assertEquals(10, PatternMatchingInstanceof.safeLengthSum("Hello", "World"));
    }

    @Test
    void erstesKeinString() {
        assertEquals(-1, PatternMatchingInstanceof.safeLengthSum(42, "World"));
    }

    @Test
    void zweitesKeinString() {
        assertEquals(-1, PatternMatchingInstanceof.safeLengthSum("Hello", 42));
    }
}
