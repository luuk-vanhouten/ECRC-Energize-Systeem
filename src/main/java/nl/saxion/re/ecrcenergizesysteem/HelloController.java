package nl.saxion.re.ecrcenergizesysteem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        String componentToOpen = "my-own-component";
        String windowTitle = "My own component";
        boolean openInNewWindow = true;
        int width = 800;
        int height = 600;

        Stage stage;
        if (openInNewWindow) {
            stage = new Stage();
        } else {
            stage = (Stage) welcomeText.getScene().getWindow();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(componentToOpen + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.show();
    }
}