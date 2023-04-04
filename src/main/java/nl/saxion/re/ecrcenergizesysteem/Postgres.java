package nl.saxion.re.ecrcenergizesysteem;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Postgres {

    public static class ConnectionUtil {
        Connection conn = null;

        public static Connection connectdb() {
            Connection conn = null;
            try {
                if (conn == null || conn.isClosed()) {
                    try (InputStream inputStream = Postgres.class.getResourceAsStream("/dbconfig.properties")) {
                        Properties properties = new Properties();
                        properties.load(inputStream);

                        String url = properties.getProperty("db.url");
                        String user = properties.getProperty("db.username");
                        String password = properties.getProperty("db.password");
                        conn = DriverManager.getConnection(url, user, password);
                        return conn;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return conn;
        }
    }
}