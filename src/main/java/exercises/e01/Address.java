package exercises.e01;

public class Address {
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
