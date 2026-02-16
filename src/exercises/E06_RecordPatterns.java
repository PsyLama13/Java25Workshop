package exercises;

/**
 * Übung 6: Record Patterns
 *
 * Aufgabe: Nutze Record Patterns um Records direkt in instanceof und switch
 *          zu dekonstruieren. Eliminiere manuelle Getter-Aufrufe.
 *
 * Hinweise:
 * - Record Patterns: case Point(int x, int y) ->
 * - Verschachtelte Patterns sind moeglich
 * - Kombination mit when-Klauseln
 */
public class E06_RecordPatterns {

    record Point(int x, int y) {}
    record Line(Point start, Point end) {}
    record ColoredPoint(Point point, String color) {}
    record BoundingBox(Point topLeft, Point bottomRight) {}

    // ========================================================================
    // TODO 1: Refaktoriere mit Record Patterns in instanceof
    //         Eliminiere die manuellen Getter-Aufrufe
    // ========================================================================
    static String describePoint(Object obj) {
        if (obj instanceof Point) {
            Point p = (Point) obj;
            int x = p.x();
            int y = p.y();
            if (x == 0 && y == 0) {
                return "Ursprung";
            } else if (y == 0) {
                return "Auf der X-Achse bei x=" + x;
            } else if (x == 0) {
                return "Auf der Y-Achse bei y=" + y;
            } else {
                return "Punkt bei (" + x + ", " + y + ")";
            }
        }
        return "Kein Punkt";
    }

    // ========================================================================
    // TODO 2: Refaktoriere mit Record Patterns in switch
    //         Nutze verschachtelte Patterns fuer Line
    // ========================================================================
    static double lineLength(Object obj) {
        if (obj instanceof Line) {
            Line line = (Line) obj;
            Point start = line.start();
            Point end = line.end();
            int dx = end.x() - start.x();
            int dy = end.y() - start.y();
            return Math.sqrt(dx * dx + dy * dy);
        }
        return -1;
    }

    // ========================================================================
    // TODO 3: Refaktoriere mit verschachtelten Record Patterns
    //         Dekonstruiere ColoredPoint komplett
    // ========================================================================
    static String describeColoredPoint(Object obj) {
        if (obj instanceof ColoredPoint) {
            ColoredPoint cp = (ColoredPoint) obj;
            Point p = cp.point();
            String color = cp.color();
            int x = p.x();
            int y = p.y();
            return color + " Punkt bei (" + x + ", " + y + ")";
        }
        return "Kein farbiger Punkt";
    }

    // ========================================================================
    // TODO 4: Refaktoriere mit Record Patterns und when-Klauseln in switch.
    //         Bestimme ob eine BoundingBox:
    //         - ein Punkt ist (topLeft == bottomRight)
    //         - eine horizontale Linie ist (gleiche Y-Werte)
    //         - eine vertikale Linie ist (gleiche X-Werte)
    //         - ein regulaeres Rechteck ist
    // ========================================================================
    static String classifyBoundingBox(BoundingBox box) {
        Point tl = box.topLeft();
        Point br = box.bottomRight();

        if (tl.x() == br.x() && tl.y() == br.y()) {
            return "Punkt bei (" + tl.x() + ", " + tl.y() + ")";
        } else if (tl.y() == br.y()) {
            return "Horizontale Linie von x=" + tl.x() + " bis x=" + br.x();
        } else if (tl.x() == br.x()) {
            return "Vertikale Linie von y=" + tl.y() + " bis y=" + br.y();
        } else {
            int width = br.x() - tl.x();
            int height = br.y() - tl.y();
            return "Rechteck " + width + "x" + height;
        }
    }

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) {
        // Test describePoint
        System.out.println(describePoint(new Point(0, 0)));
        System.out.println(describePoint(new Point(5, 0)));
        System.out.println(describePoint(new Point(0, 3)));
        System.out.println(describePoint(new Point(2, 7)));
        System.out.println(describePoint("not a point"));

        System.out.println();

        // Test lineLength
        System.out.println("Länge: " + lineLength(new Line(new Point(0, 0), new Point(3, 4))));
        System.out.println("Länge: " + lineLength(new Line(new Point(1, 1), new Point(4, 5))));
        System.out.println("Länge: " + lineLength("not a line"));

        System.out.println();

        // Test describeColoredPoint
        System.out.println(describeColoredPoint(new ColoredPoint(new Point(1, 2), "Rot")));
        System.out.println(describeColoredPoint(new ColoredPoint(new Point(0, 0), "Blau")));

        System.out.println();

        // Test classifyBoundingBox
        System.out.println(classifyBoundingBox(new BoundingBox(new Point(1, 1), new Point(1, 1))));
        System.out.println(classifyBoundingBox(new BoundingBox(new Point(0, 5), new Point(10, 5))));
        System.out.println(classifyBoundingBox(new BoundingBox(new Point(3, 0), new Point(3, 8))));
        System.out.println(classifyBoundingBox(new BoundingBox(new Point(0, 0), new Point(10, 5))));
    }
}
