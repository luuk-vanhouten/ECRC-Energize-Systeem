package nl.saxion.re.ecrcenergizesysteem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuOption {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    @FXML
    Parent root;

    @FXML public void switchToScenePlanning(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("planning-page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML public void switchToSceneCustomerInformation(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("customer-information.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML public void switchToSceneStockPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("stockpile-page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML public void switchToSceneLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
