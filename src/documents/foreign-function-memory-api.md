# Foreign Function & Memory API

Die Foreign Function & Memory API (FFM API) ermöglicht den sicheren Zugriff auf nativen Code und Speicher ausserhalb der JVM, als moderne Ersatz für JNI.

## Memory Segments

```java
// Nativen Speicher allozieren
try (Arena arena = Arena.ofConfined()) {
    MemorySegment segment = arena.allocate(100);  // 100 Bytes

    // Schreiben
    segment.set(ValueLayout.JAVA_INT, 0, 42);

    // Lesen
    int value = segment.get(ValueLayout.JAVA_INT, 0);
}  // Speicher wird automatisch freigegeben
```

## Native Funktionen aufrufen

```java
// C-Funktion: size_t strlen(const char *s)
Linker linker = Linker.nativeLinker();
SymbolLookup stdlib = linker.defaultLookup();

MethodHandle strlen = linker.downcallHandle(
    stdlib.find("strlen").orElseThrow(),
    FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)
);

try (Arena arena = Arena.ofConfined()) {
    MemorySegment str = arena.allocateFrom("Hello");
    long len = (long) strlen.invoke(str);  // 5
}
```

## Structs

```java
// C-Struct: struct Point { int x; int y; }
StructLayout pointLayout = MemoryLayout.structLayout(
    ValueLayout.JAVA_INT.withName("x"),
    ValueLayout.JAVA_INT.withName("y")
);

VarHandle xHandle = pointLayout.varHandle(PathElement.groupElement("x"));
VarHandle yHandle = pointLayout.varHandle(PathElement.groupElement("y"));

try (Arena arena = Arena.ofConfined()) {
    MemorySegment point = arena.allocate(pointLayout);
    xHandle.set(point, 0L, 10);
    yHandle.set(point, 0L, 20);
}
```

## Vorteile gegenüber JNI

- Kein nativer Code zum Kompilieren (kein C/C++ Header generieren)
- Sicherer durch Arena-basiertes Speichermanagement
- Bessere Performance durch optimierte Speicherzugriffe
- Reine Java-Lösung
