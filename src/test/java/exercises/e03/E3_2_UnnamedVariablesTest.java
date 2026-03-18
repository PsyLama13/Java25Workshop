package exercises.e03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgaben:
 * - countElements(): Nutze Unnamed Variables (_) in der for-each-Schleife.
 *   Wir brauchen nur die Anzahl der Elemente, nicht die Elemente selbst.
 * - isValidNumber(): Nutze Unnamed Variables im try-catch – die Exception-Variable wird nicht verwendet.
 * - sumValues(): Nutze Unnamed Variables in der Lambda – beim Iterieren über eine Map
 *   brauchen wir nur die Values, nicht die Keys.
 * - describeX(): Nutze Unnamed Patterns in Record Patterns – nur die x-Koordinate interessiert.
 * - describeFirst(): Nutze Unnamed Patterns – bei Pair nur das erste Element.
 * - classifyEmployee(): Nutze Unnamed Patterns – bei Employee nur Name und Gehalt (department ignorieren).
 *
 * Hinweise:
 * - _ kann in for-each, try-catch, Lambdas und Record Patterns verwendet werden
 * - _ kann mehrfach im selben Scope verwendet werden
 */
@DisplayName("Übung 3.2: Unnamed Variables (_)")
class E3_2_UnnamedVariablesTest {

    @Test
    void zaehltElemente() {
        assertEquals(3, UnnamedVariables.countElements(List.of("a", "b", "c")));
    }

    @Test
    void zaehltLeereList() {
        assertEquals(0, UnnamedVariables.countElements(List.of()));
    }

    @Test
    void gueltigeZahl() {
        assertTrue(UnnamedVariables.isValidNumber("42"));
    }

    @Test
    void ungueltigeZahl() {
        assertFalse(UnnamedVariables.isValidNumber("abc"));
    }

    @Test
    void summiertValues() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 10);
        map.put("b", 20);
        map.put("c", 30);
        assertEquals(60, UnnamedVariables.sumValues(map));
    }

    @Test
    void describeXNurXKoordinate() {
        assertEquals("x = 5", UnnamedVariables.describeX(new Point(5, 10)));
    }

    @Test
    void describeXKeinPunkt() {
        assertEquals("Kein Punkt", UnnamedVariables.describeX("something"));
    }

    @Test
    void describeFirstErstesElement() {
        assertEquals("Erstes Element: Hello", UnnamedVariables.describeFirst(new Pair<>("Hello", 42)));
    }

    @Test
    void describeFirstKeinPaar() {
        assertEquals("Kein Paar", UnnamedVariables.describeFirst("something"));
    }

    @Test
    void classifyEmployeeTopverdiener() {
        assertEquals("Anna ist Topverdiener",
                UnnamedVariables.classifyEmployee(new Employee("Anna", "IT", 120_000)));
    }

    @Test
    void classifyEmployeeNormalesGehalt() {
        assertEquals("Bob hat ein normales Gehalt",
                UnnamedVariables.classifyEmployee(new Employee("Bob", "HR", 80_000)));
    }

    @Test
    void classifyEmployeeKeinMitarbeiter() {
        assertEquals("Kein Mitarbeiter", UnnamedVariables.classifyEmployee("something"));
    }
}
