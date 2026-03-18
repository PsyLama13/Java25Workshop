package exercises.e04;

public class ValidatedChild extends Base {
    public ValidatedChild(String value) {
        super(value);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Wert darf nicht leer sein");
        }
    }
}
