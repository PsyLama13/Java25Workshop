package exercises.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgabe: Refaktoriere den Konstruktor von ValidatedDimension so, dass die Validierung
 *          direkt im Konstruktor steht, nicht in einer statischen Hilfsmethode.
 */
@DisplayName("Übung 4.3: ValidatedDimension – Validierung vor super()")
class E4_3_ValidatedDimensionTest {

    @Test
    void gueltigeDimension() {
        var vd = new ValidatedDimension(10, 20);
        assertEquals(10, vd.width);
        assertEquals(20, vd.height);
    }

    @Test
    void negativeBreiteWirftException() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> new ValidatedDimension(-1, 5));
        assertTrue(ex.getMessage().contains("Breite"));
    }

    @Test
    void negativeHoeheWirftException() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> new ValidatedDimension(5, -1));
        assertTrue(ex.getMessage().contains("Höhe"));
    }

    @Test
    void nullBreiteWirftException() {
        assertThrows(IllegalArgumentException.class,
                () -> new ValidatedDimension(0, 5));
    }
}
