package exercises.e04;

public class ValidatedDimension extends Dimension {
    private static int validatePositive(int value, String name) {
        if (value <= 0)
            throw new IllegalArgumentException(name + " muss positiv sein, war: " + value);
        return value;
    }

    public ValidatedDimension(int width, int height) {
        super(validatePositive(width, "Breite"), validatePositive(height, "Höhe"));
    }
}
