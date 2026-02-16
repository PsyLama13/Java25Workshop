package exercises;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Übung 9: Virtual Threads
 *
 * Aufgabe: Refaktoriere den Code von Platform Threads / Thread Pools
 *          zu Virtual Threads. Nutze die neuen APIs.
 *
 * Hinweise:
 * - Thread.startVirtualThread(() -> ...) fuer einfache Faelle
 * - Thread.ofVirtual().name(...).start(() -> ...) fuer benannte Threads
 * - Executors.newVirtualThreadPerTaskExecutor() fuer ExecutorService
 * - Virtual Threads sind ideal fuer I/O-bound Tasks
 * - Kein Thread-Pool noetig - ein Thread pro Task
 */
public class E09_VirtualThreads {

    // Simuliert einen I/O-Aufruf (z.B. HTTP-Request)
    static String fetchData(String source) {
        try {
            Thread.sleep(100); // Simuliert Netzwerk-Latenz
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Daten von " + source + " [" + Thread.currentThread() + "]";
    }

    // ========================================================================
    // TODO 1: Refaktoriere von fixem Thread-Pool zu Virtual Threads.
    //         Nutze Executors.newVirtualThreadPerTaskExecutor().
    // ========================================================================
    static List<String> fetchAllData(List<String> sources) throws Exception {
        // Alt: Fixer Thread-Pool mit 10 Threads
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

    // ========================================================================
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

        for (Thread t : threads) {
            t.join();
        }
    }

    // ========================================================================
    // TODO 3: Refaktoriere zu Virtual Threads mit try-with-resources.
    //         Der ExecutorService soll automatisch geschlossen werden.
    //         Nutze das AutoCloseable-Interface von newVirtualThreadPerTaskExecutor().
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
    // Testcode
    // ========================================================================
    public static void main(String[] args) throws Exception {
        // Test fetchAllData
        System.out.println("=== Fetch All Data ===");
        List<String> sources = List.of("API-1", "API-2", "API-3", "DB-1", "DB-2");
        List<String> results = fetchAllData(sources);
        results.forEach(System.out::println);

        System.out.println();

        // Test runParallelTasks
        System.out.println("=== Parallel Tasks ===");
        runParallelTasks();

        System.out.println();

        // Test computeResults
        System.out.println("=== Compute Results ===");
        List<Integer> computed = computeResults(10);
        System.out.println("Ergebnisse: " + computed);
    }
}
