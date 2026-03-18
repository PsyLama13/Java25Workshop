package exercises.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgabe: Refaktoriere die Klasse Customer zu einem Record mit Validierung (kompakter Konstruktor).
 *          Email darf nicht leer sein, Alter muss zwischen 0 und 150 liegen.
 *
 * Hinweise:
 * - Kompakte Konstruktoren können für Validierung verwendet werden
 */
@DisplayName("Übung 1.2: Customer als Record mit Validierung")
class E1_2_CustomerTest {

    @Test
    void customerSpeichertWerte() {
        var c = new Customer("Anna", "anna@example.com", 30);
        assertEquals("Anna", c.getName());
        assertEquals("anna@example.com", c.getEmail());
        assertEquals(30, c.getAge());
    }

    @Test
    void customerLeereEmailWirftException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Customer("Bob", "", 25));
    }

    @Test
    void customerNullEmailWirftException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Customer("Bob", null, 25));
    }

    @Test
    void customerNegativesAlterWirftException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Customer("Bob", "bob@test.com", -1));
    }

    @Test
    void customerAlterUeber150WirftException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Customer("Bob", "bob@test.com", 151));
    }

    @Test
    void customerGueltigesAlterAmRand() {
        assertDoesNotThrow(() -> new Customer("A", "a@b.com", 0));
        assertDoesNotThrow(() -> new Customer("A", "a@b.com", 150));
    }
}
