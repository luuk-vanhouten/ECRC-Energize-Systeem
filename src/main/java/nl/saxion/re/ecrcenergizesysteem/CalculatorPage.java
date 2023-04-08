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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    @FXML
    Label totalCost;
    private double totalPriceWithoutBTW;
    private int totalNumberOfSolarPanels;
    @FXML
    private ChoiceBox<Customer> customerEmailSelector;
    @FXML
    ChoiceBox<Omvormer> omvormer;
    @FXML
    ChoiceBox<SolarPanel> zonnepaneelselector;
    @FXML
    TextField totalNrOfPanels;
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

    @FXML
    private CheckBox getFase3;


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

    public ObservableList<Customer> observableListCustomerEmails() {
        String sql = "SELECT email,phonenumber FROM customer";
        ObservableList<Customer> data = FXCollections.observableArrayList();

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                int phoneNumber = resultSet.getInt("phonenumber"); // Get the phone number from the result set
                Customer customer = new Customer(email, phoneNumber);
                data.add(customer);
            }

            customerEmailSelector.setItems(data);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Postgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return data;
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
            }
            omvormer.setItems(observableListOmvormer);

        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(Postgres.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return observableListOmvormer;
    }


    @FXML
    public void initialize() {
        ObservableList<Customer> customerObservableList = observableListCustomerEmails();
        customerEmailSelector.setItems(customerObservableList);
        ObservableList<SolarPanel> solarPanelList = observableListSolarpanel();
        zonnepaneelselector.setItems(solarPanelList);

        ObservableList<Omvormer> omvormerList = getObservableListOmvormer();
        omvormer.setItems(omvormerList);
    }

    @FXML
    void onCalculateButtonPressed() throws SQLException {
        SolarPanel selectedPanel = zonnepaneelselector.getSelectionModel().getSelectedItem();

        if (selectedPanel == null) {
            showAlert("Selecteer een type zonnepaneel.");
            return;
        }

        String widthInput = widthCalculator.getText();
        String lengthInput = lengthCalculator.getText();

        if (widthInput == null || widthInput.isEmpty()) {
            showAlert("Vul de breedte in.");
            return;
        }

        if (lengthInput == null || lengthInput.isEmpty()) {
            showAlert("Vul de lengte in.");
            return;
        }

        double length = selectedPanel.getLength();
        double width = selectedPanel.getWidth();

        double i = (Math.floor(Double.parseDouble(widthInput) / width) * Math.floor(Double.parseDouble(lengthInput) / length));
        double j = (Math.floor(Double.parseDouble(widthInput) / length) * Math.floor(Double.parseDouble(lengthInput) / width));

        if (i > j) {
            totalNumberOfSolarPanels = (int) i;
            answer.setText((int) i + " in landscape.");
        } else {
            totalNumberOfSolarPanels = (int) j;
            answer.setText((int) j + " in portrait.");
        }
    }

    @FXML
    void onOpbrengstButtonPressed() throws SQLException {
        SolarPanel selectedPanel = zonnepaneelselector.getSelectionModel().getSelectedItem();
        int opbrengst = selectedPanel.getOpbrengst();
        double verliesfactor = Double.parseDouble(opbrengstverlies.getText());
        System.out.println(totalNumberOfSolarPanels);

//        if (totalNumberOfSolarPanels != null) {
        Double totalYieldValue = opbrengst * verliesfactor * 0.85 * totalNumberOfSolarPanels;
        totalYield.setText(totalYieldValue.toString());
        getFase3.setSelected(totalYieldValue > 6000);


    }

    public void switchToSceneCustomerInformation(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("customer-information.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onFinishButtonPressed(ActionEvent event) {
        Customer customer = customerEmailSelector.getSelectionModel().getSelectedItem();
        SolarPanel selectedPanel = zonnepaneelselector.getSelectionModel().getSelectedItem();
        Omvormer selectedOmvormer = omvormer.getSelectionModel().getSelectedItem();
        String totalPanelsText = totalNrOfPanels.getText();
        Boolean fase3 = getFase3.isSelected();

        if (customer == null) {
            showAlert("Selecteer een klant.");
            return;
        }

        if (selectedPanel == null) {
            showAlert("Selecteer het type zonnepaneel.");
            return;
        }

        if (selectedOmvormer == null) {
            showAlert("Selecteer het type omvormer.");
            return;
        }

        if (totalPanelsText == null || totalPanelsText.isEmpty()) {
            showAlert("Selecteer het aantal zonnepanelen.");
            return;
        }
        if (totalCost.getText() == null || totalCost.getText().isEmpty()) {
            showAlert("Bereken de totale prijs voordat u verder gaat.");
            return;
        }

        int total = Integer.parseInt(totalPanelsText);

        String insertOfferSql = "INSERT INTO offer(phonenumber, zonnepaneel_id, total_price, quantity_zonnepaneel, omvormer_id, fase3) VALUES(?, ?, ?, ?, ?,?)";
        String updateStockSql = "UPDATE zonnepaneel SET stock = stock - ? WHERE zonnepaneel_id = ?";

        try {
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(insertOfferSql);
            preparedStatement.setInt(1, customer.getPhoneNumber());
            preparedStatement.setInt(2, selectedPanel.getId());
            preparedStatement.setDouble(3, totalPriceWithoutBTW);
            preparedStatement.setInt(4, total);
            preparedStatement.setInt(5, selectedOmvormer.getId());
            preparedStatement.setBoolean(6, fase3);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                preparedStatement = connection.prepareStatement(updateStockSql);
                preparedStatement.setInt(1, total);
                preparedStatement.setInt(2, selectedPanel.getId());

                rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    connection.commit();
                    showAlert("Offerte gelukt en voorraad bijgewerkt.");

                    try {
                        createOfferPDF("offer.pdf", customer, selectedPanel, selectedOmvormer, total, totalPriceWithoutBTW, fase3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Parent root = FXMLLoader.load(getClass().getResource("offer-page.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    connection.rollback();
                    showAlert("mislukt om de offerte op te slaan.");
                }
            } else {
                connection.rollback();
                showAlert("mislukt om de offerte op te slaan.");
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Logger.getLogger(CalculatorPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    @FXML
    public void switchToSceneMenuPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu-option.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void onTotalPriceButtonPressed(ActionEvent event) {
        SolarPanel selectedPanel = zonnepaneelselector.getSelectionModel().getSelectedItem();
        Omvormer selectedOmvormer = omvormer.getSelectionModel().getSelectedItem();
        String totalPanelsText = totalNrOfPanels.getText();
        boolean fase3Aansluiting = getFase3.isSelected();

        if (selectedPanel == null) {
            showAlert("Selecteer het type zonnepaneel.");
            return;
        }

        if (selectedOmvormer == null) {
            showAlert("Selecteer het type omvormer.");
            return;
        }

        if (totalPanelsText == null || totalPanelsText.isEmpty()) {
            showAlert("Vul het aantal zonnepanelen in.");
            return;
        }
        int inputTotalPanels = Integer.parseInt(totalPanelsText);
        if (inputTotalPanels > totalNumberOfSolarPanels) {
            showAlert("Kan niet hoger zijn dan de maximale hoeveelheid op een dak.");
            return;
        }
        int fase3 = 0;
        if (fase3Aansluiting) {
            fase3 = 800;
        }

        double totalPriceInclBTW = fase3 + (selectedPanel.getPrice() * Double.parseDouble(totalPanelsText)) + selectedOmvormer.getPrice() + 1000
                + (Double.parseDouble(totalPanelsText) * 50);
        totalPriceWithoutBTW = totalPriceInclBTW;
        totalCost.setText("€" + totalPriceWithoutBTW);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("offerte");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void createOfferPDF(String outputFile, Customer customer, SolarPanel solarPanel, Omvormer omvormer, int quantity, double totalCost, boolean fase3) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
            contentStream.setLeading(20);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Offer");
            contentStream.newLine();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.showText("Customer: " + customer.getEmailadress());
            contentStream.newLine();
            contentStream.showText(quantity + " Zonnepaneel: " + solarPanel.getName() + "  €" + (quantity * solarPanel.getPrice()));
            contentStream.newLine();
            contentStream.showText("1 omvormer:" + omvormer.getName() + "  €" + omvormer.getPrice());
            contentStream.newLine();
            contentStream.showText("Installatie- en materiaalkosten:" + "  €" + (1000 + (quantity * 50)));
            contentStream.newLine();
            contentStream.showText("Fase 3: " + (fase3 ? "  €" + 800 : "Niet nodig"));
            contentStream.newLine();
            contentStream.showText("Totale kosten: " + "  €" + totalPriceWithoutBTW);
            contentStream.newLine();
            contentStream.showText("BTW: " + "  €" + (totalCost * 0.21));
            contentStream.newLine();
            contentStream.showText("Totaal inclusief BTW: " + (totalPriceWithoutBTW * 1.21));
            contentStream.endText();


            contentStream.close();

            document.save(outputFile);
        }
    }
}



