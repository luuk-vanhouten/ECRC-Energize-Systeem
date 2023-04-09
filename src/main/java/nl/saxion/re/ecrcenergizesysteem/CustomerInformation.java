package nl.saxion.re.ecrcenergizesysteem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerInformation {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField houseNumber;
    @FXML
    private TextField streetName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField emailadress;
    @FXML
    private Button exit;
    @FXML
    private Button next;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    Parent root;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public CustomerInformation() {
        connection = Postgres.ConnectionUtil.connectdb();
    }
    @FXML
    public void switchToSceneCalculatorPageNoCus(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("calculator-page.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToSceneCalculatorPage(ActionEvent event) throws IOException {
        if (saveCustomerInDatabase(event)) {
            Parent root = FXMLLoader.load(getClass().getResource("calculator-page.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    //
    @FXML
    public boolean saveCustomerInDatabase(ActionEvent event) throws IOException {
        String firstname = firstName.getText();
        String lastname = lastName.getText();
        String emailadres = emailadress.getText();
        String phonenumber = phoneNumber.getText();
        String streetname = streetName.getText();
        String housenumber = houseNumber.getText();
        String postalcode = postalCode.getText();

        if (!validateInput(firstname, lastname, emailadres, phonenumber, housenumber, postalcode, streetname)) {
            return false;
        }

        int phoneNumberInt = Integer.parseInt(phonenumber);
        int houseNumberInt = Integer.parseInt(housenumber);

        String sql = "INSERT INTO customer(firstname, lastname, email, phonenumber, housenumber, postalcode, streetname) VALUES(?, ?, ?, ?, ?, ?, ?) RETURNING *";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, emailadres);
            preparedStatement.setInt(4, phoneNumberInt);
            preparedStatement.setInt(5, houseNumberInt);
            preparedStatement.setString(6, postalcode);
            preparedStatement.setString(7, streetname);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(Customer.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }
    private boolean validateInput(String firstname, String lastname, String emailadres, String phonenumber, String housenumber, String postalcode, String streetname) {
        String nameRegex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
        String emailRegex = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$";
        String phoneRegex = "^\\d+$";
        String houseNumberRegex = "^\\d+$";
        String postalCodeRegex = "^\\d{4}[a-zA-Z]{2}$";
        String streetNameRegex = "^[a-zA-Z ]+$";

        if (!firstname.matches(nameRegex) || !lastname.matches(nameRegex)) {
            showAlert("Verkeerde naam", "Voornaam en achternaam kunnen alleen letters bevatten.");
            return false;
        } else if (!postalcode.matches(postalCodeRegex)) {
            showAlert("verkeerde postcode", "Postcode moet er als volgt uitzien '1234AB'.");
            return false;
        } else if (!housenumber.matches(houseNumberRegex)) {
            showAlert("Verkeerd huisnummer", "Een huisnummer kan  alleen cijfers bevatten");
            return false;
        } else if (!streetname.matches(streetNameRegex)) {
            showAlert("verkeerde straatnaam", "Een  straatnaam bevat alleen letters.");
            return false;
        } else if (!phonenumber.matches(phoneRegex)) {
            showAlert("Verkeerd telefoonnummer", "Een telefoonnummer kan alleen cijfers bevatten.");
            return false;
        } else if (!emailadres.matches(emailRegex)) {
            showAlert("Verkeerde email", "Vul een geldend e-mailadres in.");
            return false;
        }
        return true;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void switchToSceneLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-page.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToSceneMenuPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu-option.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

