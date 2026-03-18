package exercises.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgabe: Refaktoriere den Konstruktor von NormalizedChild so, dass die Transformation
 *          (Normalisierung) VOR dem super()-Aufruf passiert, damit super() den normalisierten
 *          Wert erhält. Eliminiere den Workaround mit der statischen Hilfsmethode.
 *
 * Hinweise:
 * - Statements dürfen jetzt vor super()/this() stehen
 * - Instanzvariablen erst nach super() zuweisbar
 */
@DisplayName("Übung 4.2: NormalizedChild – Normalisierung vor super()")
class E4_2_NormalizedChildTest {

    @Test
    void normalisiertWert() {
        var nc = new NormalizedChild("  HELLO World  ");
        assertEquals("hello world", nc.value);
    }

    @Test
    void nullWirftException() {
        assertThrows(NullPointerException.class,
                () -> new NormalizedChild(null));
    }
}
