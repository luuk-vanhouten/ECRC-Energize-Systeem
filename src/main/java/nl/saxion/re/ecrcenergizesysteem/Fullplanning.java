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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fullplanning {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private TableView<Offer> plannedOffersFullscreenTable;
    @FXML
    private TableColumn<Offer, Integer> plannedOffersFullscreenIdColumn;
    @FXML
    private TableColumn<Offer, Date> plannedOffersFullscreenDateColumn;
    @FXML
    private TableColumn<Offer, Integer> plannedOffersFullscreenTeamColumn;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button logoutButton;
    @FXML
    private Button addOfferButton;
    @FXML
    private Button menuButton;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public Fullplanning() {
            connection = Postgres.ConnectionUtil.connectdb();
        }



    @FXML
    public void initialize() {
        plannedOffersFullscreenIdColumn.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
        plannedOffersFullscreenDateColumn.setCellValueFactory(new PropertyValueFactory<>("installation_date"));
        plannedOffersFullscreenTeamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));

        loadPlannedOffers();
    }
    @FXML
    public void onAddOfferButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("planning-page.fxml"));
            Parent root = loader.load();

            PlanningPage controller = loader.getController();
            controller.setParentController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Installation Offer");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadPlannedOffers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadPlannedOffers() {
        plannedOffersFullscreenTable.setItems(getPlannedOffers());
    }

    private ObservableList<Offer> getPlannedOffers() {
        String sql = "SELECT * FROM offer WHERE installation_date IS NOT NULL AND team IS NOT NULL AND installation_date > CURRENT_DATE";

        ObservableList<Offer> offers = FXCollections.observableArrayList();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int offerId = resultSet.getInt("offer_id");
                Date installation_date = resultSet.getDate("installation_date");
                int team = resultSet.getInt("team");

                Offer offer = new Offer(offerId, installation_date, team);
                offers.add(offer);
            }
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(Fullplanning.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return offers;
    }
    @FXML
    public void switchToSceneLoginPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login-page.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToSceneMenuPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("menu-option.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
