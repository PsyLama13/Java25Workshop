package exercises.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgabe: Refaktoriere die this()-Delegation in Employee so, dass die Berechnungen
 *          (fullName, id) direkt vor this() stehen, ohne statische Hilfsmethoden.
 */
@DisplayName("Übung 4.4: Employee – Berechnung vor this()")
class E4_4_EmployeeTest {

    @Test
    void bautFullNameKorrekt() {
        var emp = new Employee("Max", "Mustermann");
        assertEquals("Max Mustermann", emp.fullName);
    }

    @Test
    void generiertIdKorrekt() {
        var emp = new Employee("Max", "Mustermann");
        assertEquals("mmustermann", emp.id);
    }

    @Test
    void nullVornameWirftException() {
        assertThrows(NullPointerException.class,
                () -> new Employee(null, "Mustermann"));
    }

    @Test
    void stripptLeerzeichen() {
        var emp = new Employee("  Max  ", "  Mustermann  ");
        assertEquals("Max Mustermann", emp.fullName);
    }
}
