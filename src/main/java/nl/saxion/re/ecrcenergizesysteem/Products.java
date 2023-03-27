package nl.saxion.re.ecrcenergizesysteem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Products {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public Products() {
        connection = Postgres.ConnectionUtil.connectdb();
    }

    @FXML
    public void saveCustomerInDatabase(ActionEvent event) throws IOException {


        String sql = "SELECT * FROM products INNER JOIN product_attributes ON products.product_id=product_attributes.product_id INNER JOIN  attributes ON attributes.attribute_id=product_attributes.attribute_id";

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ComboBox comboBox = new ComboBox<>();
            while (resultSet.next()) {
                comboBox.getItems().add(resultSet.getString("product_name"));
                comboBox.getItems().add(resultSet.getDouble("attribute_value"));
                comboBox.getItems().add( resultSet.getString("attribute_type"));

                comboBox.getItems().add(resultSet.getString("attribute_name"));
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Postgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }


    }
}
