package nl.saxion.re.ecrcenergizesysteem;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.Date;

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

    private final ObjectProperty<Date> installationDate;
    private final IntegerProperty team;

    public OfferTableData(int offerId, String firstName, String lastName, String postalCode, String phoneNumber,
                          String solarPanelName, String inverterName, double totalPrice, int quantity_zonnepaneel, Date installationDate, int team) {
        this.offerId = offerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.solarPanelName = solarPanelName;
        this.inverterName = inverterName;
        this.totalPrice = totalPrice;
        this.quantity_zonnepaneel = quantity_zonnepaneel;
        this.installationDate = new SimpleObjectProperty<>(installationDate);
        this.team = new SimpleIntegerProperty(team);
    }

    // Getter and setter methods for all properties

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

    public Date getInstallationDate() {
        return installationDate.get();
    }

    public ObjectProperty<Date> installationDateProperty() {
        return installationDate;
    }

    public int getTeam() {
        return team.get();
    }

    public IntegerProperty teamProperty() {
        return team;
    }
}