package nl.saxion.re.ecrcenergizesysteem;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.floor;

public class CalculatorPage {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ObservableList<String> data;

    ObservableList<String> omvormers;

    @FXML
    private Label answer;
    @FXML
    private Label totalYield;

    private ObservableList<String> customerEmailData;
    private Double totalNumberOfSolarPanels;
    @FXML
    private ChoiceBox<String> customerEmailSelector;
    @FXML
    ChoiceBox<String> omvormer;
    @FXML
    ChoiceBox<SolarPanel> zonnepaneelselector;
    @FXML
    TextField lengthCalculator;
    @FXML
    TextField widthCalculator;
    @FXML
    TextField opbrengstverlies;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;


    public CalculatorPage() {
        connection = Postgres.ConnectionUtil.connectdb();
    }


    public ObservableList<SolarPanel> observableListSolarpanel() {
        String sql = "SELECT * FROM zonnepaneel";
        ObservableList<SolarPanel> data = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);

            while (resultSet.next()) {
                String name = resultSet.getString("product_name");
                double price = resultSet.getDouble("price");
                int lengte = resultSet.getInt("lengte");
                int breedte = resultSet.getInt("breedte");
                int zonnepanelen_id = resultSet.getInt("zonnepaneel_id");
                int opbrengst = resultSet.getInt("opbrengst");
                SolarPanel solarPanel = new SolarPanel(name, price, lengte, breedte, zonnepanelen_id, opbrengst);
                data.add(solarPanel);
            }
            zonnepaneelselector.setItems(data);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Postgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return data;
    }

    public ObservableList<String> observableListCustomerEmails() {
        String sql = "SELECT email FROM customer";
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                data.add(email);
            }
            customerEmailSelector.setItems(data);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Postgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return data;
    }

    public ObservableList<String> getObservableListOmvormer() {
        String sql = "SELECT name, omvormer_max_capacity FROM omvormer";
        ObservableList<String> observableListOmvormer = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int maxCapacity = resultSet.getInt("omvormer_max_capacity");
                Omvormer omvormer = new Omvormer(name, maxCapacity);
                observableListOmvormer.add(omvormer.getName());
            }
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(Postgres.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return observableListOmvormer;
    }


    @FXML
    public void initialize() {
        customerEmailData = observableListCustomerEmails();

        ObservableList<SolarPanel> solarPanelList = observableListSolarpanel();
        zonnepaneelselector.setItems(solarPanelList);

        ObservableList<String> omvormerList = getObservableListOmvormer();
        omvormer.setItems(omvormerList);
        omvormer.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    // Handle selection change here
                });
    }

    @FXML
    private void onCalculateButtonPressed() throws SQLException {
        SolarPanel selectedPanel = zonnepaneelselector.getSelectionModel().getSelectedItem();
        if (selectedPanel != null) {
            int length = selectedPanel.getLength();
            int width = selectedPanel.getWidth();

            Double i = (floor(Double.parseDouble(widthCalculator.getText()) / width) * floor(Double.parseDouble(lengthCalculator.getText()) / length));
            Double j = (floor(Double.parseDouble(widthCalculator.getText()) / length) * floor(Double.parseDouble(lengthCalculator.getText()) / width));

            if (i > j) {
                totalNumberOfSolarPanels = i;
                answer.setText(i + " landscape");
            } else if (i < j) {
                totalNumberOfSolarPanels = j;
                answer.setText(j + "portrait");
            }
        }
    }

    @FXML
    private void onOpbrengstButtonPressed() throws SQLException {
        SolarPanel selectedPanel = zonnepaneelselector.getSelectionModel().getSelectedItem();
        int opbrengst = selectedPanel.getOpbrengst();
        double verliesfactor = Double.parseDouble(opbrengstverlies.getText());
        int aantalPanels = Integer.parseInt(answer.getText());
        Double totaYield = opbrengst * verliesfactor * 0.85 * totalNumberOfSolarPanels;
        totalYield.setText(totaYield.toString());
    }

    public void switchToSceneCustomerInformation(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("customer-information.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onFinishButtonPressed(ActionEvent actionEvent) {
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

