package nl.saxion.re.ecrcenergizesysteem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OfferTableView {
    @FXML
    private TableView<OfferTableData> offerTableView;
    @FXML
    private TableColumn<OfferTableData, String> firstNameColumn;
    @FXML
    private TableColumn<OfferTableData, String> lastNameColumn;
    @FXML
    private TableColumn<OfferTableData, String> postalCodeColumn;
    @FXML
    private TableColumn<OfferTableData, String> phoneNumberColumn;
    @FXML
    private TableColumn<OfferTableData, String> solarPanelNameColumn;
    @FXML
    private TableColumn<OfferTableData, String> inverterNameColumn;
    @FXML
    private TableColumn<OfferTableData, Double> totalPriceColumn;
    @FXML
    private TableColumn<OfferTableData, Integer> quantity_zonnepaneel;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;


    public void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        solarPanelNameColumn.setCellValueFactory(new PropertyValueFactory<>("solarPanelName"));
        quantity_zonnepaneel.setCellValueFactory(new PropertyValueFactory<>("quantity_zonnepaneel"));
        inverterNameColumn.setCellValueFactory(new PropertyValueFactory<>("inverterName"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        loadOfferTableData();

    }

    private void loadOfferTableData() {
        String query = "SELECT c.firstname, c.lastname, c.postalcode, c.phonenumber, z.product_name, o.quantity_zonnepaneel, o.omvormer_id, o.total_price, om.name " +
                "FROM offer o " +
                "JOIN customer c ON c.phonenumber = o.phonenumber " +
                "JOIN zonnepaneel z ON z.zonnepaneel_id = o.zonnepaneel_id " + "JOIN omvormer om ON om.omvormer_id = o.omvormer_id";
        ObservableList<OfferTableData> offerTableDataList = FXCollections.observableArrayList();

        try (Connection connection = Postgres.ConnectionUtil.connectdb();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String postalCode = resultSet.getString("postalcode");
                String phoneNumber = resultSet.getString("phonenumber");
                String solarPanelName = resultSet.getString("product_name");
                String inverterName = resultSet.getString("name");
                int quantity_zonnepaneel = resultSet.getInt("quantity_zonnepaneel");
                double totalPrice = resultSet.getDouble("total_price");

                OfferTableData offerTableData = new OfferTableData(firstName, lastName, postalCode, phoneNumber, solarPanelName, inverterName, totalPrice, quantity_zonnepaneel);
                offerTableDataList.add(offerTableData);
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Postgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        offerTableView.setItems
                (offerTableDataList);
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
