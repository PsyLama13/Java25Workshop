package exercises.e05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgaben:
 * - loadDashboard(): Refaktoriere zu Structured Concurrency mit ShutdownOnFailure.
 *   Wenn ein Task fehlschlägt, sollen alle anderen abgebrochen werden.
 * - fetchFastest(): Refaktoriere zu Structured Concurrency mit ShutdownOnSuccess.
 *   Wir wollen das Ergebnis des schnellsten Servers.
 *   Sobald einer antwortet, sollen die anderen abgebrochen werden.
 *
 * Hinweise:
 * - StructuredTaskScope.ShutdownOnFailure: Bricht alle ab wenn einer fehlschlägt
 * - StructuredTaskScope.ShutdownOnSuccess: Nimmt das erste erfolgreiche Ergebnis
 * - scope.fork(() -> ...) für parallele Tasks
 * - scope.join() wartet auf alle Tasks
 * - Preview-Feature (--enable-preview)
 */
@DisplayName("Übung 5.2: Structured Concurrency")
class E5_2_StructuredConcurrencyExerciseTest {

    @Test
    void laedtDashboardErfolgreich() throws Exception {
        var dashboard = StructuredConcurrencyExercise.loadDashboard(42);

        assertEquals("User-42", dashboard.user());
        assertTrue(dashboard.orders().contains("User-42"));
        assertTrue(dashboard.recommendations().contains("User-42"));
    }

    @Test
    void fehlerBeiUngueltigerUserId() {
        assertThrows(Exception.class,
                () -> StructuredConcurrencyExercise.loadDashboard(-1));
    }

    @Test
    void gibtSchnellstesErgebnis() throws Exception {
        String result = StructuredConcurrencyExercise.fetchFastest();
        assertNotNull(result);
        assertTrue(result.startsWith("Daten von"),
                "Ergebnis sollte mit 'Daten von' beginnen");
    }

    @Test
    void secondaryIstAmSchnellsten() throws Exception {
        String result = StructuredConcurrencyExercise.fetchFastest();
        assertEquals("Daten von Secondary", result);
    }
}
