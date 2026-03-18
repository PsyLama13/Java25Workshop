package exercises.e02;

import java.util.List;

public class PatternMatchingSwitch {

    public static String formatObject(Object obj) {
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

    public static String categorizeInput(Object input) {
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
}
