package nl.saxion.re.ecrcenergizesysteem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class PlanningPage {

    @FXML
    private TextField inputField;
    @FXML
    private Button addDatumButton;
    @FXML
    private TableView<Offer> dataTable;
    @FXML
    private TableColumn<Offer, Integer> idColumn;
    @FXML
    private TableColumn<Offer, String> groupColumn;
    @FXML
    private TableColumn<Offer, String> datumColumn;
    @FXML
    private TableColumn<Offer, LocalDate> dateColumn;

    private ObservableList<Offer> data = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> teamChoice;
    @FXML
    private ComboBox<Integer> groupSelector;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    @FXML
    Parent root;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public PlanningPage() {
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
//    @FXML
//    private void handleAddDatum(ActionEvent event) {
//        Integer group = groupSelector.getSelectionModel().getSelectedItem();
//        String datum = inputField.getText().trim();
//        LocalDate today = LocalDate.now();
//        String query = "INSERT INTO data (group_name, datum, date) VALUES (?, ?, ?)";
//
//        if (group != null && !datum.isEmpty()) {
//            if (canAddDatum(group, today)) {
//                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                    preparedStatement.setInt(1, group);
//                    preparedStatement.setString(2, datum);
//                    preparedStatement.setObject(3, today);
//                    preparedStatement.executeUpdate();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                loadData();
//            } else {
//                System.out.println("You have already added a datum for this group today.");
//            }
//        }
//        inputField.clear();
//    }
//    private boolean canAddDatum(String group, LocalDate date) {
//        String query = "SELECT COUNT(*) FROM data WHERE group_name = ? AND date = ?";
//        try (Connection conn = DatabaseConnection.connect()) {
//            PreparedStatement stmt = conn.prepareStatement(query);
//            stmt.setString(1, group);
//            stmt.setObject(2, date);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                int count = rs.getInt(1);
//                return count == 0;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

//    private void loadData() {
//        data.clear();
//        String query = "SELECT * FROM data";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            ResultSet rs = preparedStatement.executeQuery(query);
//            while (rs.next()) {
//                int id = rs.getInt("offerid");
//                int team = rs.getInt("group_name");
//                String datum = rs.getString("datum");
//                LocalDate date = rs.getObject("date", LocalDate.class);
//                data.add(new Offer(id, team, datum, date));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    @FXML
//    public void switchToSceneLoginPage(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("login-page.fxml"));
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
}