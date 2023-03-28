package nl.saxion.re.ecrcenergizesysteem;

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
    private TextField email;
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

    @FXML
    public void saveCustomerInDatabase(ActionEvent event) throws IOException{

        String fistname = this.firstName.getText().toString();
        String lastname = this.lastName.getText().toString();
        String email = this.email.getText().toString();
        int phonenumber = Integer.parseInt((this.phoneNumber.getText().toString()));
        String streetname = this.streetName.getText().toString();
        int housenumber = Integer.parseInt((this.houseNumber.getText().toString()));
        String postalcode = this.postalCode.getText().toString();
        int customer_id = 0;

        String sql = "INSERT INTO customer(firstname,lastname,email,phonenumber,housenumber,postalcode,streetname) VALUES(?, ?, ?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1,customer_id);
            preparedStatement.setString(1, fistname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, phonenumber);
            preparedStatement.setInt(5, housenumber);
            preparedStatement.setString(6, postalcode);
            preparedStatement.setString(7, streetname);
            preparedStatement.executeUpdate();
            System.out.println("Sucessfully created.");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Postgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
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

