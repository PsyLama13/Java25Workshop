package exercises.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgaben:
 * - dayType(): Refaktoriere das switch-Statement zu einer Switch Expression (Arrow-Syntax).
 * - seasonGreeting(): Refaktoriere zu einer Switch Expression mit yield
 *   (die Seiteneffekte System.out.println(...) sollen erhalten bleiben).
 * - daysInMonth(): Refaktoriere zu einer Switch Expression.
 *
 * Hinweise:
 * - Switch Expression gibt einen Wert zurück (kein break nötig)
 * - Arrow-Syntax (->) verhindert Fall-Through
 * - yield für Blöcke mit Seiteneffekten
 */
@DisplayName("Übung 2.1: Switch Expressions")
class E2_1_SwitchExpressionsTest {

    @Test
    void werktage() {
        assertEquals("Werktag", SwitchExpressions.dayType(Day.MONDAY));
        assertEquals("Werktag", SwitchExpressions.dayType(Day.TUESDAY));
        assertEquals("Werktag", SwitchExpressions.dayType(Day.WEDNESDAY));
        assertEquals("Werktag", SwitchExpressions.dayType(Day.THURSDAY));
        assertEquals("Werktag", SwitchExpressions.dayType(Day.FRIDAY));
    }

    @Test
    void wochenende() {
        assertEquals("Wochenende", SwitchExpressions.dayType(Day.SATURDAY));
        assertEquals("Wochenende", SwitchExpressions.dayType(Day.SUNDAY));
    }

    @Test
    void fruehling() {
        assertEquals("Frühlingsgefühle", SwitchExpressions.seasonGreeting(Season.SPRING));
    }

    @Test
    void sommer() {
        assertEquals("Sommerfreude", SwitchExpressions.seasonGreeting(Season.SUMMER));
    }

    @Test
    void herbst() {
        assertEquals("Herbststimmung", SwitchExpressions.seasonGreeting(Season.AUTUMN));
    }

    @Test
    void winter() {
        assertEquals("Winterzauber", SwitchExpressions.seasonGreeting(Season.WINTER));
    }

    @Test
    void monateMit31Tagen() {
        for (int m : new int[]{1, 3, 5, 7, 8, 10, 12}) {
            assertEquals(31, SwitchExpressions.daysInMonth(m, false), "Monat " + m);
        }
    }

    @Test
    void monateMit30Tagen() {
        for (int m : new int[]{4, 6, 9, 11}) {
            assertEquals(30, SwitchExpressions.daysInMonth(m, false), "Monat " + m);
        }
    }

    @Test
    void februarNormal() {
        assertEquals(28, SwitchExpressions.daysInMonth(2, false));
    }

    @Test
    void februarSchaltjahr() {
        assertEquals(29, SwitchExpressions.daysInMonth(2, true));
    }

    @Test
    void ungueltigerMonat() {
        assertThrows(IllegalArgumentException.class, () -> SwitchExpressions.daysInMonth(13, false));
    }
}
