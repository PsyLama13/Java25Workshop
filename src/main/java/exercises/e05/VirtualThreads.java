package exercises.e05;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class VirtualThreads {

    public static String fetchData(String source) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Daten von " + source + " [" + Thread.currentThread() + "]";
    }

    public static List<String> fetchAllData(List<String> sources) throws Exception {
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

    public static void runParallelTasks() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            final int taskId = i;
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

    public static List<Integer> computeResults(int count) throws Exception {
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
}
