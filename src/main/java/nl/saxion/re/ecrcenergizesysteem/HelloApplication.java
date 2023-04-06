
package nl.saxion.re.ecrcenergizesysteem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Connection conn = Postgres.ConnectionUtil.connectdb();

        Path configFile = Path.of("src/main/resources/dbconfig.properties");
        if (!Files.exists(configFile)) {
            System.out.println("Maak een bestand aan met de naam config.txt met het wachtwoord van de database.");
            System.exit(-1);
        }
        String configContents = Files.readString(configFile);
        System.out.println("Config contents: " + configContents);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-option.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

