package exercises;

import java.util.concurrent.*;

/**
 * Übung 10: Structured Concurrency
 *
 * Aufgabe: Refaktoriere die unstrukturierte Parallelverarbeitung zu
 *          Structured Concurrency mit StructuredTaskScope.
 *
 * Hinweise:
 * - StructuredTaskScope.ShutdownOnFailure: Bricht alle ab wenn einer fehlschlaegt
 * - StructuredTaskScope.ShutdownOnSuccess: Nimmt das erste erfolgreiche Ergebnis
 * - scope.fork(() -> ...) fuer parallele Tasks
 * - scope.join() wartet auf alle Tasks
 * - Structured Concurrency ist ein Preview-Feature (--enable-preview)
 */
public class E10_StructuredConcurrency {

    // Simulierte Services
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

    // ========================================================================
    // TODO 1: Refaktoriere zu Structured Concurrency mit ShutdownOnFailure.
    //         Wenn ein Task fehlschlaegt, sollen alle anderen abgebrochen werden.
    //         Aktuell: Tasks laufen weiter auch wenn einer fehlschlaegt.
    // ========================================================================
    static UserDashboard loadDashboard(int userId) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            Future<String> userFuture = executor.submit(() -> fetchUser(userId));
            Future<String> ordersFuture = executor.submit(() -> fetchOrders(userId));
            Future<String> recoFuture = executor.submit(() -> fetchRecommendations(userId));

            // Problem: Wenn fetchUser fehlschlaegt, laufen die anderen weiter
            String user = userFuture.get();
            String orders = ordersFuture.get();
            String recommendations = recoFuture.get();

            return new UserDashboard(user, orders, recommendations);
        } finally {
            executor.shutdown();
        }
    }

    // Simulierte redundante Services (verschiedene Server)
    static String fetchFromPrimary() throws InterruptedException {
        Thread.sleep(200);
        return "Daten von Primary Server";
    }

    static String fetchFromSecondary() throws InterruptedException {
        Thread.sleep(100);
        return "Daten von Secondary Server";
    }

    static String fetchFromTertiary() throws InterruptedException {
        Thread.sleep(300);
        return "Daten von Tertiary Server";
    }

    // ========================================================================
    // TODO 2: Refaktoriere zu Structured Concurrency mit ShutdownOnSuccess.
    //         Wir wollen das Ergebnis des schnellsten Servers.
    //         Sobald einer antwortet, sollen die anderen abgebrochen werden.
    // ========================================================================
    static String fetchFastest() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            // CompletionService um den Schnellsten zu finden
            CompletionService<String> completionService =
                new ExecutorCompletionService<>(executor);

            completionService.submit(() -> fetchFromPrimary());
            completionService.submit(() -> fetchFromSecondary());
            completionService.submit(() -> fetchFromTertiary());

            // Nimm das erste Ergebnis - aber die anderen Tasks laufen weiter!
            return completionService.take().get();
        } finally {
            executor.shutdown();
        }
    }

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) throws Exception {
        // Test loadDashboard
        System.out.println("=== Dashboard laden ===");
        UserDashboard dashboard = loadDashboard(42);
        System.out.println("User: " + dashboard.user());
        System.out.println("Orders: " + dashboard.orders());
        System.out.println("Recommendations: " + dashboard.recommendations());

        System.out.println();

        // Test mit Fehler
        System.out.println("=== Dashboard mit Fehler ===");
        try {
            loadDashboard(-1);
        } catch (Exception e) {
            System.out.println("Fehler (erwartet): " + e.getMessage());
        }

        System.out.println();

        // Test fetchFastest
        System.out.println("=== Schnellster Server ===");
        String fastest = fetchFastest();
        System.out.println("Ergebnis: " + fastest);
    }
}
