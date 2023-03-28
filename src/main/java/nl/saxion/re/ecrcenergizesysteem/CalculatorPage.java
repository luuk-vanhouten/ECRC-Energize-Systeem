package nl.saxion.re.ecrcenergizesysteem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorPage  {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ObservableList<String> data;
    private ObservableList<String> customerEmailData;

    @FXML
    private ChoiceBox<String> customerEmailSelector;
    @FXML
    ChoiceBox<String> zonnepaneelselector;

    @FXML
    TextField lenght;


    public CalculatorPage() {
        connection = Postgres.ConnectionUtil.connectdb();
    }

//    @Override
//    public void start(Stage primarystage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("calculator-page.fxml"));
//        Scene scene= new Scene(root,800,600);
//        primarystage.setScene(scene);
//        primarystage.show();
//    }


    public ObservableList<String> observableListSolarpanel() {
        String sql = "SELECT * FROM zonnepaneel";
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);

            while (resultSet.next()) {
                String name = resultSet.getString("product_name");
                double price = resultSet.getDouble("price");
                int length = resultSet.getInt("lengte");
                int width = resultSet.getInt("breedte");
                int zonnepanelen_id = resultSet.getInt("zonnepaneel_id");
                int opbrengst = resultSet.getInt("opbrengst");
                String total = String.format("%s - $%.2f-%dx%d", name, price, length, width, zonnepanelen_id, opbrengst);
                data.add(total);
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
                String email= resultSet.getString("email");
                data.add(email);
            }
            customerEmailSelector.setItems(data);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Postgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return data;
    }


    @FXML
    public void initialize() {
        customerEmailData=observableListCustomerEmails();
        data = FXCollections.observableArrayList();
        zonnepaneelselector.setItems(data);
        observableListSolarpanel();
    }
}
