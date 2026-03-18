package exercises.e07;

import java.util.*;

public class SequencedCollectionsExercise {

    public static String getFirstAndLast(List<String> list) {
        if (list.isEmpty()) { return "Liste ist leer"; }
        String first = list.get(0);
        String last  = list.get(list.size() - 1);
        return "Erstes: " + first + ", Letztes: " + last;
    }

    public static List<String> reverseList(List<String> list) {
        List<String> reversed = new ArrayList<>(list);
        Collections.reverse(reversed);
        return reversed;
    }

    public static String getFirstAndLastEntry(LinkedHashMap<String, Integer> map) {
        if (map.isEmpty()) { return "Map ist leer"; }

        Map.Entry<String, Integer> first = map.entrySet().iterator().next();

        Map.Entry<String, Integer> last = null;
        for (Map.Entry<String, Integer> entry : map.entrySet()) { last = entry; }

        return "Erstes: " + first + ", Letztes: " + last;
    }

    public static List<String> manipulateList(List<String> original) {
        LinkedList<String> list = new LinkedList<>(original);

        list.add(0, "START");
        list.add(list.size(), "END");
        list.remove(0);
        list.remove(list.size() - 1);

        return list;
    }
}
