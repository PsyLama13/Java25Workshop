# Virtual Threads

Virtual Threads sind leichtgewichtige Threads, die von der JVM verwaltet werden. Sie ermöglichen Millionen von gleichzeitigen Threads ohne den Overhead von OS-Threads.

## Erstellung

```java
// Einfacher Virtual Thread
Thread.startVirtualThread(() -> {
    System.out.println("Running in virtual thread");
});

// Mit Builder
Thread vThread = Thread.ofVirtual()
    .name("my-virtual-thread")
    .start(() -> doWork());

// ExecutorService für Virtual Threads
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    executor.submit(() -> task1());
    executor.submit(() -> task2());
}
```

## Vergleich

| Eigenschaft | Platform Thread | Virtual Thread |
|-------------|-----------------|----------------|
| Anzahl | Tausende | Millionen |
| Speicher | ~1 MB Stack | ~1 KB |
| Scheduling | OS | JVM |
| Blocking | Blockiert OS-Thread | Wird unmounted |

## Best Practices

```java
// Gut: Viele kurzlebige, blockierende Tasks
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    List<Future<String>> futures = urls.stream()
        .map(url -> executor.submit(() -> fetchUrl(url)))
        .toList();

    for (var future : futures) {
        System.out.println(future.get());
    }
}
```

## Wichtig

- Virtual Threads sind ideal für I/O-bound Tasks
- Nicht für CPU-intensive Berechnungen (nutzen Sie Platform Threads)
- Keine Thread-Pools nötig - erstellen Sie einen Thread pro Task
- `synchronized` kann Virtual Threads "pinnen" - bevorzugen Sie `ReentrantLock`
