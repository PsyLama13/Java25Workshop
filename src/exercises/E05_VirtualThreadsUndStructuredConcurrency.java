package exercises;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Übung 5: Virtual Threads & Structured Concurrency
 *
 * Teil 1 – Virtual Threads: Refaktoriere von Platform Threads / Thread Pools
 *          zu Virtual Threads. Nutze die neuen APIs.
 *
 * Teil 2 – Structured Concurrency: Refaktoriere unstrukturierte Parallelverarbeitung
 *          zu StructuredTaskScope. Tasks werden als Einheit behandelt.
 *
 * Hinweise Virtual Threads:
 * - Thread.startVirtualThread(() -> ...) für einfache Fälle
 * - Thread.ofVirtual().name(...).start(() -> ...) für benannte Threads
 * - Executors.newVirtualThreadPerTaskExecutor() für ExecutorService
 * - Kein Thread-Pool nötig – ein Thread pro Task
 *
 * Hinweise Structured Concurrency:
 * - StructuredTaskScope.ShutdownOnFailure: Bricht alle ab wenn einer fehlschlägt
 * - StructuredTaskScope.ShutdownOnSuccess: Nimmt das erste erfolgreiche Ergebnis
 * - scope.fork(() -> ...) für parallele Tasks
 * - scope.join() wartet auf alle Tasks
 * - Preview-Feature (--enable-preview)
 */
public class E05_VirtualThreadsUndStructuredConcurrency {

    // ========================================================================
    // TEIL 1: VIRTUAL THREADS
    // ========================================================================

    // Simuliert einen I/O-Aufruf (z.B. HTTP-Request)
    static String fetchData(String source) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Daten von " + source + " [" + Thread.currentThread() + "]";
    }

    // TODO 1: Refaktoriere von fixem Thread-Pool zu Virtual Threads.
    //         Nutze Executors.newVirtualThreadPerTaskExecutor().
    // ========================================================================
    static List<String> fetchAllData(List<String> sources) throws Exception {
        // Alt: Fixer Thread-Pool
        ExecutorService executor = Executors.newFixedThreadPool(10);
        try {
            List<Future<String>> futures = new ArrayList<>();
            for (String source : sources) {
                futures.add(executor.submit(() -> fetchData(source)));
            }
            List<String> results = new ArrayList<>();
            for (Future<String> future : futures) {
                results.add(future.get());
            }
            return results;
        } finally {
            executor.shutdown();
        }
    }

    // TODO 2: Refaktoriere von manueller Thread-Erstellung zu Virtual Threads.
    //         Nutze Thread.ofVirtual() oder Thread.startVirtualThread().
    // ========================================================================
    static void runParallelTasks() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            final int taskId = i;
            // Alt: Platform Threads manuell erstellen
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(50);
                    System.out.println("Task " + taskId + " erledigt auf " + Thread.currentThread());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            t.start();
            threads.add(t);
        }
        for (Thread t : threads) { t.join(); }
    }

    // TODO 3: Refaktoriere zu Virtual Threads mit try-with-resources.
    //         Der ExecutorService soll automatisch geschlossen werden.
    // ========================================================================
    static List<Integer> computeResults(int count) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final int num = i;
            futures.add(executor.submit(() -> {
                Thread.sleep(50);
                return num * num;
            }));
        }
        List<Integer> results = new ArrayList<>();
        for (Future<Integer> future : futures) {
            results.add(future.get());
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        return results;
    }

    // ========================================================================
    // TEIL 2: STRUCTURED CONCURRENCY
    // ========================================================================

    static String fetchUser(int userId) throws InterruptedException {
        Thread.sleep(100);
        if (userId < 0) throw new RuntimeException("User not found: " + userId);
        return "User-" + userId;
    }

    static String fetchOrders(int userId) throws InterruptedException {
        Thread.sleep(150);
        return "Orders für User-" + userId + ": [Order-1, Order-2]";
    }

    static String fetchRecommendations(int userId) throws InterruptedException {
        Thread.sleep(120);
        return "Empfehlungen für User-" + userId + ": [Prod-A, Prod-B]";
    }

    record UserDashboard(String user, String orders, String recommendations) {}

    // TODO 4: Refaktoriere zu Structured Concurrency mit ShutdownOnFailure.
    //         Wenn ein Task fehlschlägt, sollen alle anderen abgebrochen werden.
    // ========================================================================
    static UserDashboard loadDashboard(int userId) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            Future<String> userFuture   = executor.submit(() -> fetchUser(userId));
            Future<String> ordersFuture = executor.submit(() -> fetchOrders(userId));
            Future<String> recoFuture   = executor.submit(() -> fetchRecommendations(userId));

            // Problem: Wenn fetchUser fehlschlägt, laufen die anderen weiter!
            String user            = userFuture.get();
            String orders          = ordersFuture.get();
            String recommendations = recoFuture.get();

            return new UserDashboard(user, orders, recommendations);
        } finally {
            executor.shutdown();
        }
    }

    static String fetchFromPrimary()   throws InterruptedException { Thread.sleep(200); return "Daten von Primary"; }
    static String fetchFromSecondary() throws InterruptedException { Thread.sleep(100); return "Daten von Secondary"; }
    static String fetchFromTertiary()  throws InterruptedException { Thread.sleep(300); return "Daten von Tertiary"; }

    // TODO 5: Refaktoriere zu Structured Concurrency mit ShutdownOnSuccess.
    //         Wir wollen das Ergebnis des schnellsten Servers.
    //         Sobald einer antwortet, sollen die anderen abgebrochen werden.
    // ========================================================================
    static String fetchFastest() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            CompletionService<String> cs = new ExecutorCompletionService<>(executor);
            cs.submit(() -> fetchFromPrimary());
            cs.submit(() -> fetchFromSecondary());
            cs.submit(() -> fetchFromTertiary());
            // Problem: Die anderen Tasks laufen weiter, auch nach dem ersten Ergebnis!
            return cs.take().get();
        } finally {
            executor.shutdown();
        }
    }

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) throws Exception {
        System.out.println("=== Fetch All Data ===");
        List<String> sources = List.of("API-1", "API-2", "API-3", "DB-1", "DB-2");
        fetchAllData(sources).forEach(System.out::println);

        System.out.println();

        System.out.println("=== Parallel Tasks ===");
        runParallelTasks();

        System.out.println();

        System.out.println("=== Compute Results ===");
        System.out.println("Ergebnisse: " + computeResults(5));

        System.out.println();

        System.out.println("=== Dashboard laden ===");
        UserDashboard dashboard = loadDashboard(42);
        System.out.println("User:            " + dashboard.user());
        System.out.println("Orders:          " + dashboard.orders());
        System.out.println("Recommendations: " + dashboard.recommendations());

        System.out.println();

        System.out.println("=== Dashboard mit Fehler ===");
        try {
            loadDashboard(-1);
        } catch (Exception e) {
            System.out.println("Fehler (erwartet): " + e.getMessage());
        }

        System.out.println();

        System.out.println("=== Schnellster Server ===");
        System.out.println("Ergebnis: " + fetchFastest());
    }
}
