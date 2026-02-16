package exercises;

import java.util.List;

/**
 * Übung 3: Pattern Matching for instanceof
 *
 * Aufgabe: Eliminiere die redundanten Casts nach instanceof-Pruefungen
 *          und nutze Pattern Matching mit Flow Scoping.
 *
 * Hinweise:
 * - "if (obj instanceof String s)" bindet direkt eine Variable
 * - Pattern-Variablen koennen mit && kombiniert werden
 * - Flow Scoping: Variable ist verfuegbar wo der Compiler den Match garantiert
 */
public class E03_PatternMatchingInstanceof {

    // ========================================================================
    // TODO 1: Refaktoriere mit Pattern Matching fuer instanceof
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

    // ========================================================================
    // TODO 2: Refaktoriere mit Pattern Matching und kombiniere mit Bedingungen
    // ========================================================================
    static String classifyNumber(Object obj) {
        if (obj instanceof Integer) {
            Integer i = (Integer) obj;
            if (i > 0) {
                return "Positive Ganzzahl: " + i;
            } else if (i == 0) {
                return "Null";
            } else {
                return "Negative Ganzzahl: " + i;
            }
        } else if (obj instanceof Double) {
            Double d = (Double) obj;
            if (Double.isNaN(d)) {
                return "NaN";
            } else if (Double.isInfinite(d)) {
                return "Unendlich";
            } else {
                return "Dezimalzahl: " + d;
            }
        } else if (obj instanceof String) {
            String s = (String) obj;
            try {
                double parsed = Double.parseDouble(s);
                return "Zahl als String: " + parsed;
            } catch (NumberFormatException e) {
                return "Keine Zahl: " + s;
            }
        }
        return "Kein numerischer Typ";
    }

    // ========================================================================
    // TODO 3: Refaktoriere mit negiertem Pattern Matching (Flow Scoping)
    //         Tipp: !(obj instanceof Type t) fuer Early Return
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
    // TODO 4: Refaktoriere - Nutze Pattern Matching mit &&
    // ========================================================================
    static String describeCollection(Object obj) {
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            if (list.isEmpty()) {
                return "Leere Liste";
            } else if (list.size() == 1) {
                return "Singleton-Liste: " + list.getFirst();
            } else {
                return "Liste mit " + list.size() + " Elementen";
            }
        }
        return "Keine Liste";
    }

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) {
        // Test formatValue
        System.out.println(formatValue("Hello"));
        System.out.println(formatValue(42));
        System.out.println(formatValue(3.14));
        System.out.println(formatValue(List.of(1, 2, 3)));

        System.out.println();

        // Test classifyNumber
        System.out.println(classifyNumber(42));
        System.out.println(classifyNumber(-7));
        System.out.println(classifyNumber(3.14));
        System.out.println(classifyNumber(Double.NaN));
        System.out.println(classifyNumber("123.45"));
        System.out.println(classifyNumber("abc"));

        System.out.println();

        // Test safeLengthSum
        System.out.println("Length sum: " + safeLengthSum("Hello", "World"));
        System.out.println("Length sum: " + safeLengthSum("Hello", 42));

        System.out.println();

        // Test describeCollection
        System.out.println(describeCollection(List.of()));
        System.out.println(describeCollection(List.of("single")));
        System.out.println(describeCollection(List.of(1, 2, 3)));
        System.out.println(describeCollection("not a list"));
    }
}
