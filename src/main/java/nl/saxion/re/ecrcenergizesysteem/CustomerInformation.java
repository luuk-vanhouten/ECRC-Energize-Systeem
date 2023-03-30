package nl.saxion.re.ecrcenergizesysteem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private TextField emailAdress;
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
    public void switchToSceneMenuPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu-option.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToSceneCalculatorPage(ActionEvent event) throws IOException {
        saveCustomerInDatabase(event); // save the customer before switching to calculator page
        Parent root = FXMLLoader.load(getClass().getResource("calculator-page.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//
@FXML
public ObservableList<Customer> saveCustomerInDatabase(ActionEvent event) throws IOException {
    String firstname = firstName.getText();
    String lastname = lastName.getText();
    String emailadress = emailAdress.getText();
    int phonenumber = Integer.parseInt(phoneNumber.getText());
    String streetname = streetName.getText();
    int housenumber = Integer.parseInt(houseNumber.getText());
    String postalcode = postalCode.getText();

    String sql = "INSERT INTO customer(firstname, lastname, email, phonenumber, housenumber, postalcode, streetname) VALUES(?, ?, ?, ?, ?, ?, ?) RETURNING *";

    ObservableList<Customer> customerList = FXCollections.observableArrayList();

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, firstname);
        preparedStatement.setString(2, lastname);
        preparedStatement.setString(3, emailadress);
        preparedStatement.setInt(4, phonenumber);
        preparedStatement.setInt(5, housenumber);
        preparedStatement.setString(6, postalcode);
        preparedStatement.setString(7, streetname);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            String email = resultSet.getString("email");
            int phoneNumber = resultSet.getInt("phonenumber");
            int houseNumber = resultSet.getInt("housenumber");
            String postalCode = resultSet.getString("postalcode");
            String streetName = resultSet.getString("streetname");
            Customer customer = new Customer(firstName, lastName, email, phoneNumber, houseNumber, postalCode, streetName);
            customerList.add(customer);
        }

    } catch (SQLException ex) {
        Logger logger = Logger.getLogger(Customer.class.getName());
        logger.log(Level.SEVERE, ex.getMessage(), ex);
    }

    return customerList;
}


    @FXML
    public void switchToSceneLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-page.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//    @FXML
//    public void initialize() {
//        // add action listener to save button
//        next.setOnAction(e -> {
//            try {
//                saveCustomerInDatabase();
//            } catch (IOException ex) {
//                Logger.getLogger(CustomerInformation.class.getName()).log(Level.SEVERE, null, ex);
////            }
////        });
//    }

}

