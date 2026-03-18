package exercises.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgabe: Refaktoriere calculateArea() zu switch mit Pattern Matching – kein if-else mehr.
 *
 * Bonus: Mache Shape zu einem sealed interface (nutze Records aus Übung 1).
 */
@DisplayName("Übung 2.4: Shape-Fläche mit switch Pattern Matching")
class E2_4_ShapeCalculatorTest {

    @Test
    void kreisFlaeche() {
        assertEquals(Math.PI * 25, ShapeCalculator.calculateArea(new Circle(5)), 0.01);
    }

    @Test
    void rechteckFlaeche() {
        assertEquals(12.0, ShapeCalculator.calculateArea(new Rectangle(3, 4)), 0.01);
    }

    @Test
    void dreieckFlaeche() {
        assertEquals(9.0, ShapeCalculator.calculateArea(new Triangle(6, 3)), 0.01);
    }
}
