package nl.saxion.re.ecrcenergizesysteem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlanningPage implements Initializable {

     @FXML
    private ChoiceBox<String> teamChoice;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    @FXML
    Parent root;

    private String[] teams = { "Team 1","Team 2", "Team 3","Team 4"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        teamChoice.getItems().addAll(teams);
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