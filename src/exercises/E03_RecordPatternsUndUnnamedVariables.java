package exercises;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Übung 3: Record Patterns & Unnamed Variables
 *
 * Teil 1 – Record Patterns: Dekonstruiere Records direkt im Pattern Matching.
 *          Eliminiere manuelle Getter-Aufrufe.
 *
 * Teil 2 – Unnamed Variables (_): Ersetze unbenutzte Variablen durch _.
 *          Signalisiert bewusst, dass ein Wert nicht benötigt wird.
 *
 * Hinweise:
 * - Record Patterns: case Point(int x, int y) ->
 * - Verschachtelte Patterns sind möglich
 * - Kombination mit when-Klauseln
 * - _ kann in for-each, try-catch, Lambdas und Record Patterns verwendet werden
 * - _ kann mehrfach im selben Scope verwendet werden
 */
public class E03_RecordPatternsUndUnnamedVariables {

    record Point(int x, int y) {}
    record Line(Point start, Point end) {}
    record ColoredPoint(Point point, String color) {}
    record BoundingBox(Point topLeft, Point bottomRight) {}

    // ========================================================================
    // TEIL 1: RECORD PATTERNS
    // ========================================================================

    // TODO 1: Refaktoriere mit Record Patterns in instanceof.
    //         Eliminiere die manuellen Getter-Aufrufe.
    // ========================================================================
    static String describePoint(Object obj) {
        if (obj instanceof Point) {
            Point p = (Point) obj;
            int x = p.x();
            int y = p.y();
            if (x == 0 && y == 0) { return "Ursprung"; }
            else if (y == 0)       { return "Auf der X-Achse bei x=" + x; }
            else if (x == 0)       { return "Auf der Y-Achse bei y=" + y; }
            else                   { return "Punkt bei (" + x + ", " + y + ")"; }
        }
        return "Kein Punkt";
    }

    // TODO 2: Refaktoriere mit verschachtelten Record Patterns in switch.
    //         Dekonstruiere Line direkt in start/end-Koordinaten.
    // ========================================================================
    static double lineLength(Object obj) {
        if (obj instanceof Line) {
            Line line = (Line) obj;
            Point start = line.start();
            Point end   = line.end();
            int dx = end.x() - start.x();
            int dy = end.y() - start.y();
            return Math.sqrt(dx * dx + dy * dy);
        }
        return -1;
    }

    // TODO 3: Refaktoriere mit verschachtelten Record Patterns.
    //         Dekonstruiere ColoredPoint und den enthaltenen Point komplett.
    // ========================================================================
    static String describeColoredPoint(Object obj) {
        if (obj instanceof ColoredPoint) {
            ColoredPoint cp = (ColoredPoint) obj;
            Point p  = cp.point();
            String color = cp.color();
            int x = p.x();
            int y = p.y();
            return color + " Punkt bei (" + x + ", " + y + ")";
        }
        return "Kein farbiger Punkt";
    }

    // TODO 4: Refaktoriere mit Record Patterns und when-Klauseln in switch.
    //         Bestimme, ob eine BoundingBox: ein Punkt, eine horizontale Linie,
    //         eine vertikale Linie oder ein reguläres Rechteck ist.
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
            return "Rechteck " + (br.x() - tl.x()) + "x" + (br.y() - tl.y());
        }
    }

    // ========================================================================
    // TEIL 2: UNNAMED VARIABLES (_)
    // ========================================================================

    record Pair<A, B>(A first, B second) {}
    record Employee(String name, String department, int salary) {}

    // TODO 5: Nutze Unnamed Variables in der for-each-Schleife.
    //         Wir brauchen nur die Anzahl der Elemente.
    // ========================================================================
    static int countElements(List<?> list) {
        int count = 0;
        for (var element : list) {
            count++;
        }
        return count;
    }

    // TODO 6: Nutze Unnamed Variables im try-catch und in der Lambda.
    //         a) Die Exception-Variable wird nicht verwendet.
    //         b) Beim Iterieren über eine Map brauchen wir nur die Values.
    // ========================================================================
    static boolean isValidNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    static int sumValues(Map<String, Integer> map) {
        int[] sum = {0};
        map.forEach((key, value) -> sum[0] += value);
        return sum[0];
    }

    // TODO 7: Nutze Unnamed Patterns in Record Patterns.
    //         a) Bei Point interessiert uns nur die x-Koordinate.
    //         b) Bei Pair nur das erste Element.
    //         c) Bei Employee nur Name und Gehalt (department ignorieren).
    // ========================================================================
    static String describeX(Object obj) {
        if (obj instanceof Point(int x, int y)) {
            return "x = " + x;
        }
        return "Kein Punkt";
    }

    static String describeFirst(Object obj) {
        if (obj instanceof Pair(var first, var second)) {
            return "Erstes Element: " + first;
        }
        return "Kein Paar";
    }

    static String classifyEmployee(Object obj) {
        if (obj instanceof Employee) {
            Employee emp = (Employee) obj;
            String name       = emp.name();
            String department = emp.department();  // nicht gebraucht → _
            int salary        = emp.salary();
            if (salary > 100_000) { return name + " ist Topverdiener"; }
            else                  { return name + " hat ein normales Gehalt"; }
        }
        return "Kein Mitarbeiter";
    }

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== Record Patterns ===");
        System.out.println(describePoint(new Point(0, 0)));
        System.out.println(describePoint(new Point(5, 0)));
        System.out.println(describePoint(new Point(0, 3)));
        System.out.println(describePoint(new Point(2, 7)));
        System.out.println(describePoint("not a point"));
        System.out.println();
        System.out.printf("Linienlänge: %.2f%n", lineLength(new Line(new Point(0, 0), new Point(3, 4))));
        System.out.println(describeColoredPoint(new ColoredPoint(new Point(1, 2), "Rot")));
        System.out.println();
        System.out.println(classifyBoundingBox(new BoundingBox(new Point(1, 1), new Point(1, 1))));
        System.out.println(classifyBoundingBox(new BoundingBox(new Point(0, 5), new Point(10, 5))));
        System.out.println(classifyBoundingBox(new BoundingBox(new Point(3, 0), new Point(3, 8))));
        System.out.println(classifyBoundingBox(new BoundingBox(new Point(0, 0), new Point(10, 5))));

        System.out.println();

        System.out.println("=== Unnamed Variables ===");
        System.out.println("Count: " + countElements(List.of("a", "b", "c")));
        System.out.println("'42' valid: " + isValidNumber("42"));
        System.out.println("'abc' valid: " + isValidNumber("abc"));
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 10); map.put("b", 20); map.put("c", 30);
        System.out.println("Sum: " + sumValues(map));
        System.out.println(describeX(new Point(5, 10)));
        System.out.println(describeFirst(new Pair<>("Hello", 42)));
        System.out.println(classifyEmployee(new Employee("Anna", "IT", 120_000)));
        System.out.println(classifyEmployee(new Employee("Bob", "HR", 80_000)));
    }
}
