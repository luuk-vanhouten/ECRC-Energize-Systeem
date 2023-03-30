package nl.saxion.re.ecrcenergizesysteem;

public class Customer {

    private String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;
    private int houseNumber;
    private String postalCode;
    private String streetName;

    public Customer(String email, int phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String firstName, String lastName, String email, int phoneNumber, int houseNumber, String postalCode, String streetName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.streetName = streetName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStreetName() {
        return streetName;
    }
}