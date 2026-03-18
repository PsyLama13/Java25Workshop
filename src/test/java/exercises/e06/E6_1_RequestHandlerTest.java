package exercises.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgabe: Refaktoriere von ThreadLocal zu ScopedValue.
 *          Eliminiere das manuelle remove() und die Mutierbarkeit.
 *
 * Hinweise:
 * - ScopedValue.newInstance() statt new ThreadLocal<>()
 * - ScopedValue.where(KEY, value).run(() -> ...) für den Scope
 * - ScopedValues sind immutable innerhalb eines Scopes
 * - Preview-Feature (--enable-preview)
 */
@DisplayName("Übung 6.1: Request Handling mit ScopedValues")
class E6_1_RequestHandlerTest {

    @Test
    void requestVerarbeitungOhneFehler() {
        assertDoesNotThrow(() -> RequestHandler.handleRequest("Alice", "REQ-001"));
    }

    @Test
    void mehrereRequestsNacheinander() {
        assertDoesNotThrow(() -> {
            RequestHandler.handleRequest("Alice", "REQ-001");
            RequestHandler.handleRequest("Bob", "REQ-002");
        });
    }
}
