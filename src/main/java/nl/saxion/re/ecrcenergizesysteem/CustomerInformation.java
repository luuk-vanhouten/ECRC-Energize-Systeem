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
        Offer customer = new Offer(firstName.getText(), lastName.getText(), postalCode.getText(), houseNumber.getText(), streetName.getText(), phoneNumber.getText(), email.getText());
//        Alert gegevens = new Alert(Alert.AlertType.CONFIRMATION);
//        gegevens.setHeaderText("gegevens");
//        gegevens.show();
//        gegevens.setContentText(customer.toString());

      Postgres.writeToDatabases(firstName.getText(),lastName.getText(),email.getText(),Integer.parseInt(phoneNumber.getText()),streetName.getText(),Integer.parseInt(houseNumber.getText()), postalCode.getText());
        Parent root = FXMLLoader.load(getClass().getResource("my-own-component.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
