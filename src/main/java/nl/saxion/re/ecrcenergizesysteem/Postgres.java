package nl.saxion.re.ecrcenergizesysteem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Postgres {

    public static void writeToDatabases(String firstName, String lastName, String eMail, int phoneNumber, String streetName, int houseNumber, String postalCode) {
        String url = "jdbc:postgresql://localhost:5432/energizer";
        String user = "postgres";
        String password = "482148";

        String cfirstname = firstName;
        String clastname = lastName;
        String cemail = eMail;
        int cphonenumber = phoneNumber;
        String cstreetname = streetName;
        int chousenumber = houseNumber;
        String cpostalcode = postalCode;

        String query = "INSERT INTO customer(firstname, lastname, email, phonenumber, streetname, housenumber, postalcode) VALUES(?, ?, ?,?,?,?,?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setString(3, eMail);
            pst.setInt(4, phoneNumber);
            pst.setString(5, streetName);
            pst.setInt(6, houseNumber);
            pst.setString(7, postalCode);
            pst.executeUpdate();
            System.out.println("Sucessfully created.");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Postgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }


    }

    public class ConnectionUtil {
        Connection conn = null;

        public static Connection connectdb() {
            Connection conn = null;
            try {
                String url = "jdbc:postgresql://localhost:5432/energizer";
                String user = "postgres";
                String password = "482148";
                conn = DriverManager.getConnection(url, user, password);
                return conn;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}

