package nl.saxion.re.ecrcenergizesysteem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlanningPage {

    public void setParentController(Fullplanning parentController) {
        this.parentController = parentController;
    }


    private ObservableList<Offer> data = FXCollections.observableArrayList();
    private ObservableList<Integer> teams=FXCollections.observableArrayList(1,2,3);

    private Fullplanning parentController;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private ComboBox<Offer> offerSelector;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<Integer> teamSelector;


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
    @FXML
    public void initialize() {
        ObservableList<Offer> offers = fetchOffers();
        offerSelector.setItems(offers);
        teamSelector.setItems(teams);
          }



    private ObservableList<Offer> fetchOffers() {
        String sql = "SELECT * FROM offer WHERE installation_date IS NULL AND team IS NULL";

        ObservableList<Offer> offers = FXCollections.observableArrayList();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int offerId = resultSet.getInt("offer_id");
                // Fetch other columns, if needed
                // ...

                Offer offer = new Offer(offerId);
                offers.add(offer);
            }
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(PlanningPage.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return offers;
    }
    private void refreshOfferSelector() {
        ObservableList<Offer> offers = fetchOffers();
        offerSelector.setItems(offers);
    }
    @FXML
    void onSaveButtonPressed(ActionEvent event) {
        Offer selectedOffer = offerSelector.getSelectionModel().getSelectedItem();

        if (selectedOffer == null) {
            System.out.println("No offer selected.");
            return;
        }

        Date selectedDate = Date.valueOf(datePicker.getValue());
        int team = teamSelector.getValue();

        String checkTeamAvailabilitySql = "SELECT COUNT(*) FROM offer WHERE installation_date = ? AND team = ?";
        try (PreparedStatement checkTeamAvailabilityStmt = connection.prepareStatement(checkTeamAvailabilitySql)) {
            checkTeamAvailabilityStmt.setObject(1, selectedDate);
            checkTeamAvailabilityStmt.setInt(2, team);

            ResultSet resultSet = checkTeamAvailabilityStmt.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("The selected team is already assigned to an offer on this date.");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanningPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Update the existing offer with the selected date and team
        String updateOfferSql = "UPDATE offer SET installation_date = ?, team = ? WHERE offer_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateOfferSql)) {
            preparedStatement.setObject(1, selectedDate);
            preparedStatement.setInt(2, team);
            preparedStatement.setInt(3, selectedOffer.getOffer_id());

            int rowsAffected = preparedStatement.executeUpdate();


            if (rowsAffected > 0) {
                System.out.println("Offer updated successfully.");
                refreshOfferSelector(); // Refresh the offer selector after updating the offer
                ((Node) event.getSource()).getScene().getWindow().hide(); // Close the window
            } else {
                System.out.println("Failed to update the offer.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanningPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



        @FXML
    public void switchToSceneLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-page.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}