package exercises.e02;

import java.util.List;

public class PatternMatchingInstanceof {

    public static String formatValue(Object obj) {
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

    public static int safeLengthSum(Object first, Object second) {
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
}
