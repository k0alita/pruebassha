package controllers;

import dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.SecurityUtils;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void onLoginClick() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error", "Por favor, rellene todos los campos.", Alert.AlertType.ERROR);
            return;
        }

        String hashedPassword = SecurityUtils.hashSHA256(password);

        // --- LÍNEAS DE DEPURACIÓN AÑADIDAS ---
        System.out.println("Intentando login con usuario: " + username);
        System.out.println("Contraseña plana introducida: " + password);
        System.out.println("Hash generado por la app:   " + hashedPassword);
        // -------------------------------------

        if (usuarioDAO.validarLogin(username, hashedPassword)) {
            mostrarAlerta("Éxito", "¡Login correcto! Bienvenido " + username, Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
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