package nl.saxion.re.ecrcenergizesysteem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPage implements Initializable {
    @FXML
    private TextField userName;
    @FXML
    private PasswordField textPassword;
    Stage dialogStage = new Stage();
    Scene scene;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public LoginPage() {
        connection = Postgres.ConnectionUtil.connectdb();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: Add initialization code here
    }

    public void loginAction(ActionEvent event){
        String username = userName.getText().toString();
        String password = textPassword.getText().toString();

        String sql = "SELECT * FROM employees WHERE username = ? and password = ?";

        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                infoBox("Mislukt, probeer opnieuw in te loggen", null, "Fout");
            }else{
                infoBox("Login gelukt",null,"Gelukt" );
                Node node = (Node)event.getSource();
                dialogStage = (Stage) node.getScene().getWindow();
                dialogStage.close();
                scene = new Scene(FXMLLoader.load(getClass().getResource("menu-option.fxml")));
                dialogStage.setScene(scene);
                dialogStage.show();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


    public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}




//package nl.saxion.re.ecrcenergizesysteem;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ResourceBundle;
//
//public class LoginPage implements Initializable {
//    @FXML
//    private TextField userName;
//
//    @FXML
//    private PasswordField textPassword;
//
//    Stage dialogStage = new Stage();
//    Scene scene;
//
//    Connection connection = null;
//    PreparedStatement preparedStatement = null;
//    ResultSet resultSet = null;
//
//    public LoginPage() {
//        connection = Postgres.ConnectionUtil.connectdb();
//    }
//
//
//
//    public void loginAction(ActionEvent event){
//        String username = (userName.getText().toString());
//        String password = textPassword.getText().toString();
//
//        String sql = "SELECT * FROM employees WHERE username = ? and password = ?";
//
//        try{
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, password);
//            resultSet = preparedStatement.executeQuery();
//            if(!resultSet.next()){
//                infoBox("mislukt, probeer opnieuw in te loggen", null, "Fout");
//            }else{
//                infoBox("Login gelukt",null,"Gelukt" );
//                Node node = (Node)event.getSource();
//                dialogStage = (Stage) node.getScene().getWindow();
//                dialogStage.close();
//                scene = new Scene(FXMLLoader.load(getClass().getResource("menu-option.fxml")));
//                dialogStage.setScene(scene);
//                dialogStage.show();
//            }
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//
//    public static void infoBox(String infoMessage, String headerText, String title){
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setContentText(infoMessage);
//        alert.setTitle(title);
//        alert.setHeaderText(headerText);
//        alert.showAndWait();
//    }
//
//
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//
//
//    }
//
//}


