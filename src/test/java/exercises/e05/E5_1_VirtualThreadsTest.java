package exercises.e05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgaben:
 * - fetchAllData(): Refaktoriere von fixem Thread-Pool zu Virtual Threads.
 *   Nutze Executors.newVirtualThreadPerTaskExecutor().
 * - runParallelTasks(): Refaktoriere von manueller Thread-Erstellung zu Virtual Threads.
 *   Nutze Thread.ofVirtual() oder Thread.startVirtualThread().
 * - computeResults(): Refaktoriere zu Virtual Threads mit try-with-resources.
 *   Der ExecutorService soll automatisch geschlossen werden.
 *
 * Hinweise:
 * - Thread.startVirtualThread(() -> ...) für einfache Fälle
 * - Thread.ofVirtual().name(...).start(() -> ...) für benannte Threads
 * - Executors.newVirtualThreadPerTaskExecutor() für ExecutorService
 * - Kein Thread-Pool nötig – ein Thread pro Task
 */
@DisplayName("Übung 5.1: Virtual Threads")
class E5_1_VirtualThreadsTest {

    @Test
    void holltAlleDaten() throws Exception {
        List<String> sources = List.of("API-1", "API-2", "API-3", "DB-1", "DB-2");
        List<String> results = VirtualThreads.fetchAllData(sources);

        assertEquals(5, results.size());
        for (int i = 0; i < sources.size(); i++) {
            assertTrue(results.get(i).contains(sources.get(i)),
                    "Ergebnis sollte Quelle '" + sources.get(i) + "' enthalten");
        }
    }

    @Test
    void leereQuellen() throws Exception {
        List<String> results = VirtualThreads.fetchAllData(List.of());
        assertTrue(results.isEmpty());
    }

    @Test
    void parallelTasksLaufenOhneFehler() {
        assertDoesNotThrow(() -> VirtualThreads.runParallelTasks());
    }

    @Test
    void berechnetQuadrate() throws Exception {
        List<Integer> results = VirtualThreads.computeResults(5);
        assertEquals(List.of(0, 1, 4, 9, 16), results);
    }

    @Test
    void leereBerechnung() throws Exception {
        List<Integer> results = VirtualThreads.computeResults(0);
        assertTrue(results.isEmpty());
    }
}
