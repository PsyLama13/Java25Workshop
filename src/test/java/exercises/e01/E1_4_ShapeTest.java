package exercises.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgabe: Refaktoriere die Shape-Klassenhierarchie zu einem sealed interface
 *          mit Records für die konkreten Formen (Circle, Rectangle, Triangle).
 *          Entferne die area()-Methoden – die werden in Übung 2 via switch berechnet.
 *
 * Hinweise:
 * - Verwende "sealed" und "permits" bei der Basisklasse/-interface
 * - Unterklassen müssen final, sealed oder non-sealed sein
 */
@DisplayName("Übung 1.4: Shape als sealed interface mit Records")
class E1_4_ShapeTest {

    @Test
    void circleArea() {
        var circle = new Circle(5);
        assertEquals(Math.PI * 25, circle.area(), 0.001);
    }

    @Test
    void rectangleArea() {
        var rect = new Rectangle(3, 4);
        assertEquals(12.0, rect.area(), 0.001);
    }

    @Test
    void triangleArea() {
        var tri = new Triangle(6, 3);
        assertEquals(9.0, tri.area(), 0.001);
    }
}
