package nl.saxion.re.ecrcenergizesysteem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Postgres {

    public class ConnectionUtil {
        Connection conn = null;

        public static Connection connectdb() {
            Connection conn = null;
            try {
                String url = "jdbc:postgresql://localhost:5432/Energize ECRC";
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

