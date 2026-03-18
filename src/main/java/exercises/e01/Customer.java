package exercises.e01;

public class Customer {
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
