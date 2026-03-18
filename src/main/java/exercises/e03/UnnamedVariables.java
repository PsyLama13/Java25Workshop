package exercises.e03;

import java.util.List;
import java.util.Map;

public class UnnamedVariables {

    public static int countElements(List<?> list) {
        int count = 0;
        for (var element : list) {
            count++;
        }
        return count;
    }

    public static boolean isValidNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static int sumValues(Map<String, Integer> map) {
        int[] sum = {0};
        map.forEach((key, value) -> sum[0] += value);
        return sum[0];
    }

    public static String describeX(Object obj) {
        if (obj instanceof Point(int x, int y)) {
            return "x = " + x;
        }
        return "Kein Punkt";
    }

    public static String describeFirst(Object obj) {
        if (obj instanceof Pair(var first, var second)) {
            return "Erstes Element: " + first;
        }
        return "Kein Paar";
    }

    public static String classifyEmployee(Object obj) {
        if (obj instanceof Employee) {
            Employee emp = (Employee) obj;
            String name       = emp.name();
            String department = emp.department();
            int salary        = emp.salary();
            if (salary > 100_000) { return name + " ist Topverdiener"; }
            else                  { return name + " hat ein normales Gehalt"; }
        }
        return "Kein Mitarbeiter";
    }
}
