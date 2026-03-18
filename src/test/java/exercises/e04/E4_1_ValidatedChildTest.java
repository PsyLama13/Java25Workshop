package exercises.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgabe: Refaktoriere den Konstruktor von ValidatedChild so, dass die Validierung
 *          VOR dem super()-Aufruf passiert. Aktuell wird erst nach super() validiert –
 *          wenn super() Seiteneffekte hätte, wäre das problematisch.
 *
 * Hinweise:
 * - Statements dürfen jetzt vor super()/this() stehen (Flexible Constructor Bodies)
 * - Vor super() darf nicht auf "this" zugegriffen werden
 * - Kompilierung mit --enable-preview nötig
 */
@DisplayName("Übung 4.1: ValidatedChild – Validierung vor super()")
class E4_1_ValidatedChildTest {

    @Test
    void gueltigerWert() {
        var vc = new ValidatedChild("Hallo");
        assertEquals("Hallo", vc.value);
    }

    @Test
    void leererWertWirftException() {
        assertThrows(IllegalArgumentException.class,
                () -> new ValidatedChild(""));
    }

    @Test
    void nullWertWirftException() {
        assertThrows(IllegalArgumentException.class,
                () -> new ValidatedChild(null));
    }

    @Test
    void blankWertWirftException() {
        assertThrows(IllegalArgumentException.class,
                () -> new ValidatedChild("   "));
    }
}
