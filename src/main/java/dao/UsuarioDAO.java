package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public boolean validarLogin(String usuario, String contraseña) {
        // Asegúrate de que tu tabla en la BD se llame 'usuarios' y tenga estas columnas
        String sql = "SELECT * FROM empleado WHERE usuario = ? AND contraseña = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            pstmt.setString(2, contraseña);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Retorna true si encuentra el usuario con esa clave
            }

        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
            return false;
        }
    }
}