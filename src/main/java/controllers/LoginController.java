package controllers;

import dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.SecurityUtils;

import javafx.event.ActionEvent;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void onLoginClick(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error", "Por favor, rellene todos los campos.", Alert.AlertType.ERROR);
            return;
        }

        String hashedPassword = SecurityUtils.hashSHA256(password);

        if (usuarioDAO.validarLogin(username, hashedPassword)) {
            // Login correcto: Cargamos el dashboard
            cargarDashboard(event, username);
        } else {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
        }
    }

    private void cargarDashboard(ActionEvent event, String username) {
        try {
            // Cargar el archivo FXML del dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            Parent root = loader.load();

            // Opcional: Pasar el nombre de usuario al nuevo controlador
            DashboardController controller = loader.getController();
            controller.setUsuario(username);

            // Obtener el Stage (ventana) actual desde el evento del botón
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cambiar la escena
            Scene scene = new Scene(root, 1200, 900); // Tamaño más grande para el dashboard
            stage.setTitle("Aplicación Punto Limpio - Dashboard");
            stage.setScene(scene);
            stage.centerOnScreen(); // Centrar en la pantalla

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error Crítico", "No se pudo cargar la pantalla principal.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}