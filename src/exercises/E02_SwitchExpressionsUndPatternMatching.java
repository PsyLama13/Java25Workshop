package exercises;

import java.util.List;

/**
 * Übung 2: Switch Expressions & Pattern Matching
 *
 * Teil 1 – Switch Expressions: Refaktoriere switch-Statements zu modernen
 *          Switch Expressions mit Arrow-Syntax und yield.
 *
 * Teil 2 – Pattern Matching (instanceof): Eliminiere redundante Casts
 *          nach instanceof-Prüfungen. Nutze Flow Scoping.
 *
 * Teil 3 – Pattern Matching im switch: Kombiniere Switch Expressions mit
 *          Type Patterns und Guarded Patterns (when).
 *
 * Hinweise:
 * - Switch Expression gibt einen Wert zurück (kein break nötig)
 * - Arrow-Syntax (->) verhindert Fall-Through; yield für Blöcke
 * - "if (obj instanceof String s)" bindet direkt eine Variable
 * - Guarded Patterns: case String s when s.isEmpty() ->
 * - null kann als eigener Case behandelt werden
 */
public class E02_SwitchExpressionsUndPatternMatching {

    enum Day    { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
    enum Season { SPRING, SUMMER, AUTUMN, WINTER }

    // ========================================================================
    // TEIL 1: SWITCH EXPRESSIONS
    // ========================================================================

    // TODO 1: Refaktoriere zu einer Switch Expression
    // ========================================================================
    static String dayType(Day day) {
        String result;
        switch (day) {
            case MONDAY: case TUESDAY: case WEDNESDAY: case THURSDAY: case FRIDAY:
                result = "Werktag"; break;
            case SATURDAY: case SUNDAY:
                result = "Wochenende"; break;
            default:
                result = "Unbekannt";
        }
        return result;
    }

    // TODO 2: Refaktoriere zu einer Switch Expression mit yield
    //         (die Seiteneffekte System.out.println(...) sollen erhalten bleiben)
    // ========================================================================
    static String seasonGreeting(Season season) {
        String result;
        switch (season) {
            case SPRING:
                System.out.println("Die Blumen blühen!"); result = "Frühlingsgefühle"; break;
            case SUMMER:
                System.out.println("Ab ins Schwimmbad!"); result = "Sommerfreude"; break;
            case AUTUMN:
                System.out.println("Die Blätter fallen."); result = "Herbststimmung"; break;
            case WINTER:
                System.out.println("Schnee!"); result = "Winterzauber"; break;
            default:
                result = "Unbekannt";
        }
        return result;
    }

    // TODO 3: Refaktoriere zu einer Switch Expression
    // ========================================================================
    static int daysInMonth(int month, boolean leapYear) {
        int days;
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                days = 31; break;
            case 4: case 6: case 9: case 11:
                days = 30; break;
            case 2:
                if (leapYear) { days = 29; } else { days = 28; } break;
            default:
                throw new IllegalArgumentException("Ungültiger Monat: " + month);
        }
        return days;
    }

    // ========================================================================
    // TEIL 2: PATTERN MATCHING (instanceof)
    // ========================================================================

    // TODO 4: Refaktoriere mit Pattern Matching für instanceof
    // ========================================================================
    static String formatValue(Object obj) {
        if (obj instanceof String) {
            String s = (String) obj;
            return "String mit Länge " + s.length() + ": " + s.toUpperCase();
        } else if (obj instanceof Integer) {
            Integer i = (Integer) obj;
            return "Integer: " + (i * 2);
        } else if (obj instanceof Double) {
            Double d = (Double) obj;
            return "Double gerundet: " + Math.round(d);
        } else if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            return "Liste mit " + list.size() + " Elementen";
        } else {
            return "Unbekannt: " + obj;
        }
    }

    // TODO 5: Refaktoriere mit negiertem Pattern Matching (Flow Scoping / Early Return)
    //         Tipp: !(obj instanceof Type t) als Guard
    // ========================================================================
    static int safeLengthSum(Object first, Object second) {
        if (!(first instanceof String)) {
            return -1;
        }
        String s1 = (String) first;

        if (!(second instanceof String)) {
            return -1;
        }
        String s2 = (String) second;

        return s1.length() + s2.length();
    }

    // ========================================================================
    // TEIL 3: PATTERN MATCHING im switch
    // ========================================================================

    // TODO 6: Refaktoriere die if-else-Kette zu switch mit Type Patterns
    //         Tipp: null kann direkt als "case null ->" behandelt werden
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

    // TODO 7: Refaktoriere zu switch mit Guarded Patterns (when)
    // ========================================================================
    static String categorizeInput(Object input) {
        if (input == null) {
            return "Keine Eingabe";
        }
        if (input instanceof String) {
            String s = (String) input;
            if (s.isEmpty()) { return "Leerer Text"; }
            else if (s.length() > 100) { return "Sehr langer Text (" + s.length() + " Zeichen)"; }
            else { return "Text: " + s; }
        }
        if (input instanceof Integer) {
            Integer i = (Integer) input;
            if (i < 0) { return "Negative Zahl"; }
            else if (i == 0) { return "Null"; }
            else if (i <= 100) { return "Kleine Zahl: " + i; }
            else { return "Grosse Zahl: " + i; }
        }
        if (input instanceof List<?>) {
            List<?> list = (List<?>) input;
            if (list.isEmpty()) { return "Leere Liste"; }
            else { return "Liste mit " + list.size() + " Elementen"; }
        }
        return "Unbekannter Typ: " + input.getClass().getSimpleName();
    }

    // TODO 8: Refaktoriere zu switch mit Pattern Matching.
    //         Berechne die Fläche mit switch – kein if-else mehr.
    //         Bonus: Mache Shape zu einem sealed interface (nutze Records aus Übung 1)
    // ========================================================================
    static abstract class Shape {}
    static class Circle    extends Shape { final double radius;        Circle(double r)        { radius = r; } }
    static class Rectangle extends Shape { final double width, height; Rectangle(double w, double h) { width = w; height = h; } }
    static class Triangle  extends Shape { final double base, height;  Triangle(double b, double h)  { base = b; this.height = h; } }

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
        System.out.println("=== Switch Expressions ===");
        System.out.println("Montag: " + dayType(Day.MONDAY));
        System.out.println("Samstag: " + dayType(Day.SATURDAY));
        System.out.println("Frühling: " + seasonGreeting(Season.SPRING));
        System.out.println("Januar: " + daysInMonth(1, false) + " Tage");
        System.out.println("Februar (Schaltjahr): " + daysInMonth(2, true) + " Tage");

        System.out.println();

        System.out.println("=== instanceof Pattern Matching ===");
        System.out.println(formatValue("Hello"));
        System.out.println(formatValue(42));
        System.out.println(formatValue(3.14));
        System.out.println(formatValue(List.of(1, 2, 3)));
        System.out.println("Length sum: " + safeLengthSum("Hello", "World"));
        System.out.println("Length sum: " + safeLengthSum("Hello", 42));

        System.out.println();

        System.out.println("=== Pattern Matching im switch ===");
        System.out.println(formatObject(null));
        System.out.println(formatObject(42));
        System.out.println(formatObject(100L));
        System.out.println(formatObject(3.14159));
        System.out.println(formatObject("Hallo Welt"));
        System.out.println(formatObject(List.of(1, 2, 3)));
        System.out.println();
        System.out.println(categorizeInput(null));
        System.out.println(categorizeInput(""));
        System.out.println(categorizeInput("Hallo"));
        System.out.println(categorizeInput(-5));
        System.out.println(categorizeInput(42));
        System.out.println(categorizeInput(999));
        System.out.println(categorizeInput(List.of()));
        System.out.println();
        System.out.printf("Kreis:    %.2f%n", calculateArea(new Circle(5)));
        System.out.printf("Rechteck: %.2f%n", calculateArea(new Rectangle(3, 4)));
        System.out.printf("Dreieck:  %.2f%n", calculateArea(new Triangle(6, 3)));
    }
}
