package exercises;

/**
 * Übung 2: Sealed Classes
 *
 * Aufgabe: Refaktoriere die offene Klassenhierarchie zu Sealed Classes (und Records).
 *          Die Hierarchie soll geschlossen sein - nur die definierten Formen sind erlaubt.
 *
 * Hinweise:
 * - Verwende "sealed" und "permits" bei der Basisklasse
 * - Unterklassen muessen final, sealed oder non-sealed sein
 * - Kombiniere sealed classes mit Records wo sinnvoll
 */
public class E02_SealedClasses {

    // ========================================================================
    // TODO 1: Refaktoriere diese Hierarchie zu einer sealed class/interface.
    //         Verwende Records fuer die konkreten Formen.
    // ========================================================================
    static abstract class Shape {
        abstract double area();
        abstract String describe();
    }

    static class Circle extends Shape {
        private final double radius;

        Circle(double radius) { this.radius = radius; }

        double getRadius() { return radius; }

        @Override
        double area() { return Math.PI * radius * radius; }

        @Override
        String describe() { return "Kreis mit Radius " + radius; }
    }

    static class Rectangle extends Shape {
        private final double width;
        private final double height;

        Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        double getWidth() { return width; }
        double getHeight() { return height; }

        @Override
        double area() { return width * height; }

        @Override
        String describe() { return "Rechteck " + width + "x" + height; }
    }

    static class Triangle extends Shape {
        private final double base;
        private final double height;

        Triangle(double base, double height) {
            this.base = base;
            this.height = height;
        }

        double getBase() { return base; }
        double getHeight() { return height; }

        @Override
        double area() { return 0.5 * base * height; }

        @Override
        String describe() { return "Dreieck mit Basis " + base + " und Höhe " + height; }
    }

    // ========================================================================
    // TODO 2: Refaktoriere diese Hierarchie zu einem sealed interface
    //         mit Records. Definiere eine Methode execute(), die den
    //         Befehlstyp als String zurueckgibt.
    // ========================================================================
    static abstract class Command {
        abstract String execute();
    }

    static class SaveCommand extends Command {
        private final String filename;

        SaveCommand(String filename) { this.filename = filename; }

        String getFilename() { return filename; }

        @Override
        String execute() { return "Saving to " + filename; }
    }

    static class DeleteCommand extends Command {
        private final String filename;

        DeleteCommand(String filename) { this.filename = filename; }

        String getFilename() { return filename; }

        @Override
        String execute() { return "Deleting " + filename; }
    }

    static class UndoCommand extends Command {
        @Override
        String execute() { return "Undoing last action"; }
    }

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle(5),
            new Rectangle(3, 4),
            new Triangle(6, 3)
        };

        for (Shape shape : shapes) {
            System.out.println(shape.describe() + " -> Fläche: " + shape.area());
        }

        Command[] commands = {
            new SaveCommand("document.txt"),
            new DeleteCommand("temp.log"),
            new UndoCommand()
        };

        for (Command cmd : commands) {
            System.out.println(cmd.execute());
        }
    }
}
