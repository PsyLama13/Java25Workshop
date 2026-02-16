package exercises;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Übung 7: Unnamed Variables & Patterns
 *
 * Aufgabe: Ersetze unbenutzte Variablen durch _ (Unnamed Variables).
 *          Dies verbessert die Lesbarkeit und signalisiert bewusst,
 *          dass der Wert nicht benoetigt wird.
 *
 * Hinweise:
 * - _ kann fuer unbenutzte Variablen in for-each, try-catch, Lambdas verwendet werden
 * - _ kann in Record Patterns fuer nicht benoetigte Komponenten stehen
 * - _ kann mehrfach im selben Scope verwendet werden
 */
public class E07_UnnamedVariables {

    record Point(int x, int y) {}
    record Pair<A, B>(A first, B second) {}
    record Employee(String name, String department, int salary) {}

    // ========================================================================
    // TODO 1: Nutze Unnamed Variables in der for-each-Schleife.
    //         Wir brauchen nur die Anzahl der Elemente.
    // ========================================================================
    static int countElements(List<?> list) {
        int count = 0;
        for (var element : list) {
            count++;
        }
        return count;
    }

    // ========================================================================
    // TODO 2: Nutze Unnamed Variables im try-catch.
    //         Die Exception-Variable wird nicht verwendet.
    // ========================================================================
    static boolean isValidNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    // ========================================================================
    // TODO 3: Nutze Unnamed Variables in der Lambda.
    //         Beim Iterieren ueber eine Map brauchen wir nur die Values.
    // ========================================================================
    static int sumValues(Map<String, Integer> map) {
        int[] sum = {0};
        map.forEach((key, value) -> sum[0] += value);
        return sum[0];
    }

    // ========================================================================
    // TODO 4: Nutze Unnamed Patterns in Record Patterns.
    //         Bei Point interessiert uns nur die x-Koordinate.
    //         Bei Pair nur das erste Element.
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

    // ========================================================================
    // TODO 5: Nutze Unnamed Variables und Patterns in switch.
    //         Wir interessieren uns nur fuer den Namen des Employees
    //         und ob das Gehalt ueber 100000 liegt.
    // ========================================================================
    static String classifyEmployee(Object obj) {
        if (obj instanceof Employee) {
            Employee emp = (Employee) obj;
            String name = emp.name();
            String department = emp.department();
            int salary = emp.salary();
            if (salary > 100000) {
                return name + " ist Topverdiener";
            } else {
                return name + " hat ein normales Gehalt";
            }
        }
        return "Kein Mitarbeiter";
    }

    // ========================================================================
    // TODO 6: Nutze Unnamed Variables fuer mehrfache nicht-benoetigte Werte.
    //         Wir brauchen aus der verschachtelten Struktur nur bestimmte
    //         Teile.
    // ========================================================================
    record Address(String street, String city) {}
    record Person(String name, Address home, Address work) {}

    static String getWorkCity(Object obj) {
        if (obj instanceof Person(var name, var home, var work)) {
            if (work instanceof Address(var street, var city)) {
                return city;
            }
        }
        return "Unbekannt";
    }

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) {
        // Test countElements
        System.out.println("Count: " + countElements(List.of("a", "b", "c")));

        // Test isValidNumber
        System.out.println("'42' valid: " + isValidNumber("42"));
        System.out.println("'abc' valid: " + isValidNumber("abc"));

        // Test sumValues
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 10);
        map.put("b", 20);
        map.put("c", 30);
        System.out.println("Sum: " + sumValues(map));

        // Test describeX
        System.out.println(describeX(new Point(5, 10)));

        // Test describeFirst
        System.out.println(describeFirst(new Pair<>("Hello", 42)));

        // Test classifyEmployee
        System.out.println(classifyEmployee(new Employee("Anna", "IT", 120000)));
        System.out.println(classifyEmployee(new Employee("Bob", "HR", 80000)));

        // Test getWorkCity
        var person = new Person("Max",
            new Address("Homestr. 1", "Bern"),
            new Address("Workstr. 5", "Zürich"));
        System.out.println("Work city: " + getWorkCity(person));
    }
}
