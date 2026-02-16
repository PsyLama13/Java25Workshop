package exercises;

import java.util.List;

/**
 * Übung 5: Pattern Matching for switch
 *
 * Aufgabe: Refaktoriere die if-else-Ketten zu switch mit Pattern Matching.
 *          Nutze Type Patterns, Guarded Patterns (when) und null-Handling.
 *
 * Hinweise:
 * - switch kann auf Typen matchen: case Integer i ->
 * - Guarded Patterns: case String s when s.isEmpty() ->
 * - null kann als eigener Case behandelt werden
 */
public class E05_PatternMatchingSwitch {

    // ========================================================================
    // TODO 1: Refaktoriere die if-else-Kette zu switch mit Type Patterns
    // ========================================================================
    static String formatObject(Object obj) {
        if (obj == null) {
            return "null";
        } else if (obj instanceof Integer) {
            Integer i = (Integer) obj;
            return "Ganzzahl: " + i;
        } else if (obj instanceof Long) {
            Long l = (Long) obj;
            return "Long: " + l;
        } else if (obj instanceof Double) {
            Double d = (Double) obj;
            return "Dezimalzahl: " + String.format("%.2f", d);
        } else if (obj instanceof String) {
            String s = (String) obj;
            return "Text: \"" + s + "\"";
        } else if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            return "Liste[" + list.size() + "]";
        } else {
            return "Objekt: " + obj.toString();
        }
    }

    // ========================================================================
    // TODO 2: Refaktoriere zu switch mit Guarded Patterns (when)
    // ========================================================================
    static String categorizeInput(Object input) {
        if (input == null) {
            return "Keine Eingabe";
        }

        if (input instanceof String) {
            String s = (String) input;
            if (s.isEmpty()) {
                return "Leerer Text";
            } else if (s.length() > 100) {
                return "Sehr langer Text (" + s.length() + " Zeichen)";
            } else {
                return "Text: " + s;
            }
        }

        if (input instanceof Integer) {
            Integer i = (Integer) input;
            if (i < 0) {
                return "Negative Zahl";
            } else if (i == 0) {
                return "Null";
            } else if (i <= 100) {
                return "Kleine Zahl: " + i;
            } else {
                return "Grosse Zahl: " + i;
            }
        }

        if (input instanceof List<?>) {
            List<?> list = (List<?>) input;
            if (list.isEmpty()) {
                return "Leere Liste";
            } else {
                return "Liste mit " + list.size() + " Elementen";
            }
        }

        return "Unbekannter Typ: " + input.getClass().getSimpleName();
    }

    // ========================================================================
    // TODO 3: Refaktoriere zu switch mit Pattern Matching.
    //         Berechne die Flaeche verschiedener Formen.
    //         (Nutze die Shape-Klassen aus Uebung 2 oder definiere eigene)
    // ========================================================================
    static abstract class Shape {}
    static class Circle extends Shape {
        final double radius;
        Circle(double radius) { this.radius = radius; }
    }
    static class Rectangle extends Shape {
        final double width, height;
        Rectangle(double width, double height) { this.width = width; this.height = height; }
    }
    static class Triangle extends Shape {
        final double base, height;
        Triangle(double base, double height) { this.base = base; this.height = height; }
    }

    static double calculateArea(Shape shape) {
        if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            return Math.PI * c.radius * c.radius;
        } else if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            return r.width * r.height;
        } else if (shape instanceof Triangle) {
            Triangle t = (Triangle) shape;
            return 0.5 * t.base * t.height;
        } else {
            throw new IllegalArgumentException("Unbekannte Form: " + shape);
        }
    }

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) {
        // Test formatObject
        System.out.println(formatObject(null));
        System.out.println(formatObject(42));
        System.out.println(formatObject(100L));
        System.out.println(formatObject(3.14159));
        System.out.println(formatObject("Hallo Welt"));
        System.out.println(formatObject(List.of(1, 2, 3)));

        System.out.println();

        // Test categorizeInput
        System.out.println(categorizeInput(null));
        System.out.println(categorizeInput(""));
        System.out.println(categorizeInput("Hallo"));
        System.out.println(categorizeInput("A".repeat(200)));
        System.out.println(categorizeInput(-5));
        System.out.println(categorizeInput(0));
        System.out.println(categorizeInput(42));
        System.out.println(categorizeInput(999));
        System.out.println(categorizeInput(List.of()));
        System.out.println(categorizeInput(List.of(1, 2)));

        System.out.println();

        // Test calculateArea
        System.out.println("Kreis: " + calculateArea(new Circle(5)));
        System.out.println("Rechteck: " + calculateArea(new Rectangle(3, 4)));
        System.out.println("Dreieck: " + calculateArea(new Triangle(6, 3)));
    }
}
