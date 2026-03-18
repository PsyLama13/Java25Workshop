package exercises.e04;

import java.util.Objects;

public class NormalizedChild extends Base {
    private static String normalize(String value) {
        Objects.requireNonNull(value, "Wert darf nicht null sein");
        return value.strip().toLowerCase();
    }

    public NormalizedChild(String value) {
        super(normalize(value));
    }
}
