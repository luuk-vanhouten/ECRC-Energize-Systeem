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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockpilePage {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ObservableList<String> data;
    @FXML
    ComboBox<Omvormer> omvormer;
    @FXML
    ComboBox<SolarPanel> zonnepaneelselector;
    @FXML
    public Label voorraadPaneel;
    @FXML
    public Label voorraadOmvormer;
    @FXML
    public Label voorraadOmvormer5000;
    @FXML
    public Label voorraadOmvormer6000;
    @FXML
    public Label voorraadOmvormer8000;
    @FXML
    public Label voorraadOmvormer12000;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField quantityFieldForOmvormer;
    @FXML
    private Button buyButton;
    @FXML
    private Button buyButtonOmvormer;
    @FXML
    private Label statusLabel;
    @FXML
    private Label statusOmvormer;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;


    public StockpilePage() {
        connection = Postgres.ConnectionUtil.connectdb();
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

    @FXML
    private void buyZonnepanelen(ActionEvent event) {
        try {
            int quantity = Integer.parseInt(quantityField.getText());

            if (quantity <= 0) {
                statusLabel.setText("Ongeldig. Kies een getal boven 0.");
                return;
            }

            SolarPanel selectedPanel = zonnepaneelselector.getSelectionModel().getSelectedItem();

            if (selectedPanel == null) {
                statusLabel.setText("Selecteer eerst een paneel.");
                return;
            }

            try (Connection connection = Postgres.ConnectionUtil.connectdb()) {
                String updateSql = "UPDATE zonnepaneel SET stock = stock + ? WHERE zonnepaneel_id = ?";
                String sql = "SELECT * FROM zonnepaneel";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                    preparedStatement.setInt(1, quantity);
                    preparedStatement.setInt(2, selectedPanel.getId());
                    int rowsAffected = preparedStatement.executeUpdate();
                    while (resultSet.next()) {
                        if (rowsAffected > 0) {
                            statusLabel.setText("Zonnepanelen gekocht en voorraad aangepast!");
                            voorraadOmvormer.setText(String.valueOf(resultSet.getInt("stock")));
                        } else {
                            statusLabel.setText("Failed to update zonnepanelen stock.");
                        }
                    }
                }
                try {
                    preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    System.out.println(resultSet);

                    while (resultSet.next()) {
                        voorraadPaneel.setText(String.valueOf(resultSet.getInt("stock")));
                    }
                } catch (SQLException e) {
                    statusLabel.setText("EEEEEEEEEEEEEE");
                }
            } catch (SQLException e) {
                statusLabel.setText("Error updating stock: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Ongeldige input. Voer een getal in.");
        }
    }

    @FXML
    private void buyOmvormer(ActionEvent event) {
        try {
            int quantity = Integer.parseInt(quantityFieldForOmvormer.getText());

            if (quantity <= 0) {
                statusOmvormer.setText("Ongeldig. Kies een getal boven 0.");
                return;
            }

            Omvormer selectedOmvormer = omvormer.getSelectionModel().getSelectedItem();

            if (selectedOmvormer == null) {
                statusOmvormer.setText("Selecteer eerst een omvormer.");
                return;
            }

            try (Connection connection = Postgres.ConnectionUtil.connectdb()) {
                String updateSql = "UPDATE omvormer SET stock = stock + ? WHERE omvormer_id = ?";
                String sql = "SELECT * FROM omvormer";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                    preparedStatement.setInt(1, quantity);
                    preparedStatement.setInt(2, selectedOmvormer.getId());
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        statusOmvormer.setText("Omvermers gekocht en voorraad aangepast!");
                    } else {
                        statusOmvormer.setText("Failed to update omvormer stock.");
                    }
                }
                try {
                    preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    System.out.println(resultSet);

                    while (resultSet.next()) {
                        if (resultSet.getInt("omvormer_id") == 600) {
                            voorraadOmvormer.setText(String.valueOf(resultSet.getInt("stock")));
                        } else if (resultSet.getInt("omvormer_id") == 601) {
                            voorraadOmvormer5000.setText(String.valueOf(resultSet.getInt("stock")));
                        } else if (resultSet.getInt("omvormer_id") == 602) {
                            voorraadOmvormer6000.setText(String.valueOf(resultSet.getInt("stock")));
                        } else if (resultSet.getInt("omvormer_id") == 603) {
                            voorraadOmvormer8000.setText(String.valueOf(resultSet.getInt("stock")));
                        } else if (resultSet.getInt("omvormer_id") == 604) {
                            voorraadOmvormer12000.setText(String.valueOf(resultSet.getInt("stock")));
                        }
                    }
                } catch (SQLException e) {
                    statusOmvormer.setText("EEEEEEEEEEEEEE");
                }
            } catch (SQLException e) {
                statusOmvormer.setText("Error updating stock: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            statusOmvormer.setText("Ongeldige input. Voer een getal in.");
        }
    }

    public ObservableList<Omvormer> getObservableListOmvormer() {
        String sql = "SELECT* FROM omvormer";
        ObservableList<Omvormer> observableListOmvormer = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int maxCapacity = resultSet.getInt("omvormer_max_capacity");
                double price = resultSet.getDouble("price");
                int id = resultSet.getInt("omvormer_id");
                Omvormer omvormer = new Omvormer(name, maxCapacity, price, id);
                observableListOmvormer.add(omvormer);
                if (resultSet.getInt("omvormer_id") == 600) {
                    voorraadOmvormer.setText(String.valueOf(resultSet.getInt("stock")));
                } else if (resultSet.getInt("omvormer_id") == 601) {
                    voorraadOmvormer5000.setText(String.valueOf(resultSet.getInt("stock")));
                } else if (resultSet.getInt("omvormer_id") == 602) {
                    voorraadOmvormer6000.setText(String.valueOf(resultSet.getInt("stock")));
                } else if (resultSet.getInt("omvormer_id") == 603) {
                    voorraadOmvormer8000.setText(String.valueOf(resultSet.getInt("stock")));
                } else if (resultSet.getInt("omvormer_id") == 604) {
                    voorraadOmvormer12000.setText(String.valueOf(resultSet.getInt("stock")));
                }
            }
            omvormer.setItems(observableListOmvormer);

        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(Postgres.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return observableListOmvormer;
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
                voorraadPaneel.setText(String.valueOf(resultSet.getInt("stock")));
                data.add(solarPanel);
            }
            zonnepaneelselector.setItems(data);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Postgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return data;
    }

    public void initialize() {
        ObservableList<SolarPanel> solarPanelList = observableListSolarpanel();
        zonnepaneelselector.setItems(solarPanelList);
        ObservableList<Omvormer> omvormerList = getObservableListOmvormer();
        omvormer.setItems(omvormerList);
    }
}
