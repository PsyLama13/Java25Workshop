package exercises.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgabe: Refaktoriere die Klasse Address zu einem Record. Behalte die Methode fullAddress().
 */
@DisplayName("Übung 1.3: Address als Record")
class E1_3_AddressTest {

    @Test
    void addressSpeichertWerte() {
        var a = new Address("Bahnhofstr. 1", "Zürich", "8001", "Schweiz");
        assertEquals("Bahnhofstr. 1", a.getStreet());
        assertEquals("Zürich", a.getCity());
        assertEquals("8001", a.getZipCode());
        assertEquals("Schweiz", a.getCountry());
    }

    @Test
    void addressFullAddress() {
        var a = new Address("Bahnhofstr. 1", "Zürich", "8001", "Schweiz");
        assertEquals("Bahnhofstr. 1, 8001 Zürich, Schweiz", a.fullAddress());
    }
}
