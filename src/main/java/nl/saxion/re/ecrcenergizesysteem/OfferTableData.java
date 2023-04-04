package nl.saxion.re.ecrcenergizesysteem;

public class OfferTableData {
    private int offerId;
    private String firstName;
    private String lastName;
    private String postalCode;
    private String phoneNumber;
    private String solarPanelName;
    private String inverterName;
    private double totalPrice;
    private int quantity_zonnepaneel;

    public OfferTableData(int offerId, String firstName, String lastName, String postalCode, String phoneNumber,
                          String solarPanelName, String inverterName, double totalPrice,int quantity_zonnepaneel ) {
        this.offerId = offerId;
        this.firstName = firstName;
        this.quantity_zonnepaneel=quantity_zonnepaneel;
        this.lastName = lastName;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.solarPanelName = solarPanelName;
        this.inverterName = inverterName;
        this.totalPrice = totalPrice;
    }

    public int getOfferId() {
        return offerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getQuantity_zonnepaneel() {
        return quantity_zonnepaneel;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSolarPanelName() {
        return solarPanelName;
    }

    public String getInverterName() {
        return inverterName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
