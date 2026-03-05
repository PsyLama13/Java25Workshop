package exercises;

import java.util.Objects;

/**
 * Übung 4: Flexible Constructor Bodies
 *
 * Aufgabe: Refaktoriere die Konstruktoren, sodass Validierung und Transformation
 *          VOR dem super()-Aufruf stattfinden. Dies ist seit Java 22 (Preview) möglich.
 *
 * Hinweise:
 * - Statements dürfen jetzt vor super()/this() stehen
 * - Vor super() darf nicht auf "this" zugegriffen werden
 * - Instanzvariablen erst nach super() zuweisbar
 * - Kompilierung mit --enable-preview nötig
 */
public class E04_FlexibleConstructorBodies {

    // ========================================================================
    // TODO 1: Refaktoriere den Konstruktor.
    //         Die Validierung soll VOR super() passieren.
    //         Aktuell wird erst nach super() validiert – wenn super()
    //         Seiteneffekte hätte, wäre das problematisch.
    // ========================================================================
    static class Base {
        final String value;

        Base(String value) {
            this.value = value;
            System.out.println("Base initialisiert mit: " + value);
        }
    }

    static class ValidatedChild extends Base {
        ValidatedChild(String value) {
            // Aktuell: super() wird aufgerufen BEVOR validiert wird
            super(value);
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("Wert darf nicht leer sein");
            }
        }
    }

    // ========================================================================
    // TODO 2: Refaktoriere den Konstruktor.
    //         Die Transformation (Normalisierung) soll VOR super() passieren,
    //         damit super() den normalisierten Wert erhält.
    //         Aktuell wird ein Workaround mit statischer Hilfsmethode verwendet.
    // ========================================================================
    static class NormalizedChild extends Base {
        // Workaround: statische Methode, da vor super() keine Statements erlaubt waren
        private static String normalize(String value) {
            Objects.requireNonNull(value, "Wert darf nicht null sein");
            return value.strip().toLowerCase();
        }

        NormalizedChild(String value) {
            super(normalize(value));  // Workaround mit statischer Methode
        }
    }

    // ========================================================================
    // TODO 3: Refaktoriere den Konstruktor.
    //         Die Validierung soll direkt im Konstruktor stehen,
    //         nicht in einer statischen Hilfsmethode.
    // ========================================================================
    static class Dimension {
        final int width;
        final int height;

        Dimension(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    static class ValidatedDimension extends Dimension {
        private static int validatePositive(int value, String name) {
            if (value <= 0)
                throw new IllegalArgumentException(name + " muss positiv sein, war: " + value);
            return value;
        }

        ValidatedDimension(int width, int height) {
            // Workaround: Validierung in statischer Methode
            super(validatePositive(width, "Breite"), validatePositive(height, "Höhe"));
        }
    }

    // ========================================================================
    // TODO 4: Refaktoriere die this()-Delegation.
    //         Die Berechnungen (fullName, id) sollen direkt vor this() stehen,
    //         ohne statische Hilfsmethoden.
    // ========================================================================
    static class NamedEntity {
        final String fullName;
        final String id;

        NamedEntity(String fullName, String id) {
            this.fullName = fullName;
            this.id = id;
        }
    }

    static class Employee extends NamedEntity {
        private static String buildFullName(String firstName, String lastName) {
            Objects.requireNonNull(firstName, "Vorname darf nicht null sein");
            Objects.requireNonNull(lastName, "Nachname darf nicht null sein");
            return firstName.strip() + " " + lastName.strip();
        }

        private static String generateId(String firstName, String lastName) {
            return (firstName.charAt(0) + lastName).toLowerCase();
        }

        Employee(String firstName, String lastName) {
            // Workaround mit statischen Methoden
            super(buildFullName(firstName, lastName), generateId(firstName, lastName));
        }
    }

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== ValidatedChild ===");
        var vc = new ValidatedChild("Hallo");
        System.out.println("Value: " + vc.value);
        try {
            new ValidatedChild("");
        } catch (IllegalArgumentException e) {
            System.out.println("Validierung: " + e.getMessage());
        }

        System.out.println();

        System.out.println("=== NormalizedChild ===");
        var nc = new NormalizedChild("  HELLO World  ");
        System.out.println("Value: '" + nc.value + "'");

        System.out.println();

        System.out.println("=== ValidatedDimension ===");
        var vd = new ValidatedDimension(10, 20);
        System.out.println("Dimension: " + vd.width + "x" + vd.height);
        try {
            new ValidatedDimension(-1, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Validierung: " + e.getMessage());
        }

        System.out.println();

        System.out.println("=== Employee ===");
        var emp = new Employee("Max", "Mustermann");
        System.out.println("Name: " + emp.fullName);
        System.out.println("ID:   " + emp.id);
    }
}
