package exercises;

/**
 * Übung 1: Records & Sealed Classes
 *
 * Teil 1 – Records: Refaktoriere klassische Datenklassen zu Records.
 *          Achte auf kompakte Konstruktoren für Validierung.
 *
 * Teil 2 – Sealed Classes: Refaktoriere offene Klassenhierarchien zu
 *          Sealed Interfaces. Kombiniere sealed interfaces mit Records.
 *
 * Hinweise:
 * - Records generieren automatisch Konstruktor, Getter, equals(), hashCode() und toString()
 * - Getter heissen wie die Komponenten (z.B. name() statt getName())
 * - Kompakte Konstruktoren können für Validierung verwendet werden
 * - Verwende "sealed" und "permits" bei der Basisklasse/-interface
 * - Unterklassen müssen final, sealed oder non-sealed sein
 */
public class E01_RecordsUndSealedClasses {

    // ========================================================================
    // TEIL 1: RECORDS
    // ========================================================================

    // TODO 1: Refaktoriere diese Klasse zu einem Record
    // ========================================================================
    static class Product {
        private final String name;
        private final double price;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() { return name; }
        public double getPrice() { return price; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            return Double.compare(product.price, price) == 0 && name.equals(product.name);
        }

        @Override public int hashCode() { return java.util.Objects.hash(name, price); }
        @Override public String toString() { return "Product[name=" + name + ", price=" + price + "]"; }
    }

    // TODO 2: Refaktoriere zu einem Record mit Validierung (kompakter Konstruktor).
    //         Email darf nicht leer sein, Alter muss zwischen 0 und 150 liegen.
    // ========================================================================
    static class Customer {
        private final String name;
        private final String email;
        private final int age;

        public Customer(String name, String email, int age) {
            if (email == null || email.isBlank())
                throw new IllegalArgumentException("Email darf nicht leer sein");
            if (age < 0 || age > 150)
                throw new IllegalArgumentException("Alter muss zwischen 0 und 150 liegen");
            this.name = name;
            this.email = email;
            this.age = age;
        }

        public String getName()  { return name; }
        public String getEmail() { return email; }
        public int getAge()      { return age; }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Customer c = (Customer) o;
            return age == c.age && name.equals(c.name) && email.equals(c.email);
        }

        @Override public int hashCode() { return java.util.Objects.hash(name, email, age); }
        @Override public String toString() { return "Customer[name=" + name + ", email=" + email + ", age=" + age + "]"; }
    }

    // TODO 3: Refaktoriere zu einem Record. Behalte die Methode fullAddress().
    // ========================================================================
    static class Address {
        private final String street;
        private final String city;
        private final String zipCode;
        private final String country;

        public Address(String street, String city, String zipCode, String country) {
            this.street = street; this.city = city;
            this.zipCode = zipCode; this.country = country;
        }

        public String getStreet()  { return street; }
        public String getCity()    { return city; }
        public String getZipCode() { return zipCode; }
        public String getCountry() { return country; }

        public String fullAddress() {
            return street + ", " + zipCode + " " + city + ", " + country;
        }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Address a = (Address) o;
            return street.equals(a.street) && city.equals(a.city)
                    && zipCode.equals(a.zipCode) && country.equals(a.country);
        }

        @Override public int hashCode() { return java.util.Objects.hash(street, city, zipCode, country); }
        @Override public String toString() {
            return "Address[street=" + street + ", city=" + city + ", zipCode=" + zipCode + ", country=" + country + "]";
        }
    }

    // ========================================================================
    // TEIL 2: SEALED CLASSES
    // ========================================================================

    // TODO 4: Refaktoriere diese Klassenhierarchie zu einem sealed interface
    //         mit Records für die konkreten Formen. Entferne die describe()-
    //         und area()-Methoden – die werden in Übung 2 via switch berechnet.
    // ========================================================================
    static abstract class Shape {
        abstract double area();
    }

    static class Circle extends Shape {
        private final double radius;
        Circle(double radius) { this.radius = radius; }
        double getRadius() { return radius; }
        @Override double area() { return Math.PI * radius * radius; }
    }

    static class Rectangle extends Shape {
        private final double width, height;
        Rectangle(double width, double height) { this.width = width; this.height = height; }
        double getWidth()  { return width; }
        double getHeight() { return height; }
        @Override double area() { return width * height; }
    }

    static class Triangle extends Shape {
        private final double base, height;
        Triangle(double base, double height) { this.base = base; this.height = height; }
        double getBase()   { return base; }
        double getHeight() { return height; }
        @Override double area() { return 0.5 * base * height; }
    }

    // TODO 5: Refaktoriere zu einem sealed interface mit Records.
    //         Behalte die Methode execute() als Interface-Default oder in den Records.
    // ========================================================================
    static abstract class Command {
        abstract String execute();
    }

    static class SaveCommand extends Command {
        private final String filename;
        SaveCommand(String filename) { this.filename = filename; }
        String getFilename() { return filename; }
        @Override String execute() { return "Saving to " + filename; }
    }

    static class DeleteCommand extends Command {
        private final String filename;
        DeleteCommand(String filename) { this.filename = filename; }
        String getFilename() { return filename; }
        @Override String execute() { return "Deleting " + filename; }
    }

    static class UndoCommand extends Command {
        @Override String execute() { return "Undoing last action"; }
    }

    // ========================================================================
    // Testcode – sollte nach dem Refactoring weiterhin funktionieren
    // ========================================================================
    public static void main(String[] args) {
        // Test Product
        var p1 = new Product("Laptop", 999.99);
        var p2 = new Product("Laptop", 999.99);
        System.out.println("Product: " + p1);
        System.out.println("Equals: " + p1.equals(p2));  // true

        // Test Customer
        var c = new Customer("Anna", "anna@example.com", 30);
        System.out.println("Customer: " + c);
        try {
            new Customer("Bob", "", 25);
        } catch (IllegalArgumentException e) {
            System.out.println("Validierung OK: " + e.getMessage());
        }

        // Test Address
        var a = new Address("Bahnhofstr. 1", "Zürich", "8001", "Schweiz");
        System.out.println("Address: " + a.fullAddress());

        System.out.println();

        // Test Shapes
        Shape[] shapes = { new Circle(5), new Rectangle(3, 4), new Triangle(6, 3) };
        for (Shape shape : shapes) {
            System.out.printf("Fläche: %.2f%n", shape.area());
        }

        System.out.println();

        // Test Commands
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
