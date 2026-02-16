package exercises;

/**
 * Übung 1: Records
 *
 * Aufgabe: Refaktoriere die folgenden klassischen Datenklassen zu Records.
 *          Achte auf kompakte Konstruktoren für Validierung.
 *
 * Hinweise:
 * - Records generieren automatisch Konstruktor, Getter, equals(), hashCode() und toString()
 * - Getter heissen wie die Komponenten (z.B. name() statt getName())
 * - Kompakte Konstruktoren können für Validierung verwendet werden
 */
public class E01_Records {

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
            return Double.compare(product.price, price) == 0 &&
                    name.equals(product.name);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(name, price);
        }

        @Override
        public String toString() {
            return "Product[name=" + name + ", price=" + price + "]";
        }
    }

    // ========================================================================
    // TODO 2: Refaktoriere diese Klasse zu einem Record mit Validierung
    //         (kompakter Konstruktor). Email darf nicht leer sein,
    //         Alter muss zwischen 0 und 150 liegen.
    // ========================================================================
    static class Customer {
        private final String name;
        private final String email;
        private final int age;

        public Customer(String name, String email, int age) {
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Email darf nicht leer sein");
            }
            if (age < 0 || age > 150) {
                throw new IllegalArgumentException("Alter muss zwischen 0 und 150 liegen");
            }
            this.name = name;
            this.email = email;
            this.age = age;
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
        public int getAge() { return age; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Customer customer = (Customer) o;
            return age == customer.age &&
                    name.equals(customer.name) &&
                    email.equals(customer.email);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(name, email, age);
        }

        @Override
        public String toString() {
            return "Customer[name=" + name + ", email=" + email + ", age=" + age + "]";
        }
    }

    // ========================================================================
    // TODO 3: Refaktoriere diese Klasse zu einem Record.
    //         Erstelle zusaetzlich eine Methode fullAddress(), die die
    //         vollstaendige Adresse als String zurueckgibt.
    // ========================================================================
    static class Address {
        private final String street;
        private final String city;
        private final String zipCode;
        private final String country;

        public Address(String street, String city, String zipCode, String country) {
            this.street = street;
            this.city = city;
            this.zipCode = zipCode;
            this.country = country;
        }

        public String getStreet() { return street; }
        public String getCity() { return city; }
        public String getZipCode() { return zipCode; }
        public String getCountry() { return country; }

        public String fullAddress() {
            return street + ", " + zipCode + " " + city + ", " + country;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Address address = (Address) o;
            return street.equals(address.street) &&
                    city.equals(address.city) &&
                    zipCode.equals(address.zipCode) &&
                    country.equals(address.country);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(street, city, zipCode, country);
        }

        @Override
        public String toString() {
            return "Address[street=" + street + ", city=" + city +
                    ", zipCode=" + zipCode + ", country=" + country + "]";
        }
    }

    // ========================================================================
    // Testcode - sollte nach dem Refactoring weiterhin funktionieren
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
    }
}
