package exercises.e03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgaben:
 * - describePoint(): Refaktoriere mit Record Patterns in instanceof.
 *   Eliminiere die manuellen Getter-Aufrufe. Nutze: if (obj instanceof Point(int x, int y))
 * - lineLength(): Refaktoriere mit verschachtelten Record Patterns in switch.
 *   Dekonstruiere Line direkt in start/end-Koordinaten.
 * - describeColoredPoint(): Refaktoriere mit verschachtelten Record Patterns.
 *   Dekonstruiere ColoredPoint und den enthaltenen Point komplett.
 * - classifyBoundingBox(): Refaktoriere mit Record Patterns und when-Klauseln in switch.
 *   Bestimme, ob eine BoundingBox ein Punkt, eine horizontale/vertikale Linie
 *   oder ein reguläres Rechteck ist.
 *
 * Hinweise:
 * - Record Patterns: case Point(int x, int y) ->
 * - Verschachtelte Patterns sind möglich
 * - Kombination mit when-Klauseln
 */
@DisplayName("Übung 3.1: Record Patterns")
class E3_1_RecordPatternsTest {

    @Test
    void ursprung() {
        assertEquals("Ursprung", RecordPatterns.describePoint(new Point(0, 0)));
    }

    @Test
    void aufDerXAchse() {
        assertEquals("Auf der X-Achse bei x=5", RecordPatterns.describePoint(new Point(5, 0)));
    }

    @Test
    void aufDerYAchse() {
        assertEquals("Auf der Y-Achse bei y=3", RecordPatterns.describePoint(new Point(0, 3)));
    }

    @Test
    void allgemeinerPunkt() {
        assertEquals("Punkt bei (2, 7)", RecordPatterns.describePoint(new Point(2, 7)));
    }

    @Test
    void keinPunkt() {
        assertEquals("Kein Punkt", RecordPatterns.describePoint("not a point"));
    }

    @Test
    void berechnetLinienlaenge() {
        double length = RecordPatterns.lineLength(new Line(new Point(0, 0), new Point(3, 4)));
        assertEquals(5.0, length, 0.01);
    }

    @Test
    void keineLinie() {
        assertEquals(-1.0, RecordPatterns.lineLength("not a line"), 0.01);
    }

    @Test
    void beschreibtFarbigenPunkt() {
        assertEquals("Rot Punkt bei (1, 2)",
                RecordPatterns.describeColoredPoint(new ColoredPoint(new Point(1, 2), "Rot")));
    }

    @Test
    void keinFarbigerPunkt() {
        assertEquals("Kein farbiger Punkt", RecordPatterns.describeColoredPoint("something"));
    }

    @Test
    void boundingBoxPunkt() {
        assertEquals("Punkt bei (1, 1)",
                RecordPatterns.classifyBoundingBox(new BoundingBox(new Point(1, 1), new Point(1, 1))));
    }

    @Test
    void boundingBoxHorizontaleLinie() {
        assertEquals("Horizontale Linie von x=0 bis x=10",
                RecordPatterns.classifyBoundingBox(new BoundingBox(new Point(0, 5), new Point(10, 5))));
    }

    @Test
    void boundingBoxVertikaleLinie() {
        assertEquals("Vertikale Linie von y=0 bis y=8",
                RecordPatterns.classifyBoundingBox(new BoundingBox(new Point(3, 0), new Point(3, 8))));
    }

    @Test
    void boundingBoxRechteck() {
        assertEquals("Rechteck 10x5",
                RecordPatterns.classifyBoundingBox(new BoundingBox(new Point(0, 0), new Point(10, 5))));
    }
}
