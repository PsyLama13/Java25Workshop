package exercises.e04;

import java.util.Objects;

public class Employee extends NamedEntity {
    private static String buildFullName(String firstName, String lastName) {
        Objects.requireNonNull(firstName, "Vorname darf nicht null sein");
        Objects.requireNonNull(lastName, "Nachname darf nicht null sein");
        return firstName.strip() + " " + lastName.strip();
    }

    private static String generateId(String firstName, String lastName) {
        return (firstName.charAt(0) + lastName).toLowerCase();
    }

    public Employee(String firstName, String lastName) {
        super(buildFullName(firstName, lastName), generateId(firstName, lastName));
    }
}
