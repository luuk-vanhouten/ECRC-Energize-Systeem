package nl.saxion.re.ecrcenergizesysteem;

public class Offer {

    private String firstname;

    private String lastname;

    private String postalCode;

    private String placeNumber;

    private String streetName;

    private String phoneNumber;

    private String email;

    public Offer(String firstname, String lastname, String postalCode, String placeNumber, String streetName, String phoneNumber, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.postalCode = postalCode;
        this.placeNumber = placeNumber;
        this.streetName = streetName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", placeNumber='" + placeNumber + '\'' +
                ", streetName='" + streetName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
