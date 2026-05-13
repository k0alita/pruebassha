package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {

    public static Connection getConnection() throws SQLException {
        try {
            Properties props = new Properties();
            InputStream is = ConexionDB.class.getClassLoader().getResourceAsStream("config.properties");

            if (is == null) {
                throw new RuntimeException("No se encontró el archivo config.properties");
            }

            props.load(is);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
            throw new SQLException("No se pudo establecer la conexión", e);
        }
    }
}