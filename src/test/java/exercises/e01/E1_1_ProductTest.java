package exercises.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgabe: Refaktoriere die Klasse Product zu einem Record.
 *
 * Hinweise:
 * - Records generieren automatisch Konstruktor, Getter, equals(), hashCode() und toString()
 * - Getter heissen wie die Komponenten (z.B. name() statt getName())
 */
@DisplayName("Übung 1.1: Product als Record")
class E1_1_ProductTest {

    @Test
    void productSpeichertWerte() {
        var p = new Product("Laptop", 999.99);
        assertEquals("Laptop", p.getName());
        assertEquals(999.99, p.getPrice());
    }

    @Test
    void productEqualsUndHashCode() {
        var p1 = new Product("Laptop", 999.99);
        var p2 = new Product("Laptop", 999.99);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void productToString() {
        var p = new Product("Laptop", 999.99);
        assertNotNull(p.toString());
        assertTrue(p.toString().contains("Laptop"));
    }

    @Test
    void productUngleichBeiAnderemNamen() {
        var p1 = new Product("Laptop", 999.99);
        var p2 = new Product("Phone", 999.99);
        assertNotEquals(p1, p2);
    }
}
