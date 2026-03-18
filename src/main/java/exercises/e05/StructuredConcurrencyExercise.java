package exercises.e05;

import java.util.concurrent.*;

public class StructuredConcurrencyExercise {

    public static String fetchUser(int userId) throws InterruptedException {
        Thread.sleep(100);
        if (userId < 0) throw new RuntimeException("User not found: " + userId);
        return "User-" + userId;
    }

    public static String fetchOrders(int userId) throws InterruptedException {
        Thread.sleep(150);
        return "Orders für User-" + userId + ": [Order-1, Order-2]";
    }

    public static String fetchRecommendations(int userId) throws InterruptedException {
        Thread.sleep(120);
        return "Empfehlungen für User-" + userId + ": [Prod-A, Prod-B]";
    }

    public static UserDashboard loadDashboard(int userId) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            Future<String> userFuture   = executor.submit(() -> fetchUser(userId));
            Future<String> ordersFuture = executor.submit(() -> fetchOrders(userId));
            Future<String> recoFuture   = executor.submit(() -> fetchRecommendations(userId));

            String user            = userFuture.get();
            String orders          = ordersFuture.get();
            String recommendations = recoFuture.get();

            return new UserDashboard(user, orders, recommendations);
        } finally {
            executor.shutdown();
        }
    }

    public static String fetchFromPrimary()   throws InterruptedException { Thread.sleep(200); return "Daten von Primary"; }
    public static String fetchFromSecondary() throws InterruptedException { Thread.sleep(100); return "Daten von Secondary"; }
    public static String fetchFromTertiary()  throws InterruptedException { Thread.sleep(300); return "Daten von Tertiary"; }

    public static String fetchFastest() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            CompletionService<String> cs = new ExecutorCompletionService<>(executor);
            cs.submit(() -> fetchFromPrimary());
            cs.submit(() -> fetchFromSecondary());
            cs.submit(() -> fetchFromTertiary());
            return cs.take().get();
        } finally {
            executor.shutdown();
        }
    }
}
