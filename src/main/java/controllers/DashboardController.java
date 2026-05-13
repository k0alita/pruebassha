package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Label lblWelcome;

    // Guardamos el usuario para poder pasarlo a otras pantallas
    private String username;

    public void setUsuario(String username) {
        this.username = username;
        lblWelcome.setText("Bienvenido/a, " + username);
    }

    @FXML
    public void onCategoria1Click(ActionEvent event) {
        // Al hacer clic en Categoría 1, cargamos la pantalla de Usuarios
        try {
            // Asegúrate de que el archivo FXML se llame usuarios.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/usuarios.fxml"));
            Parent root = loader.load();

            UsuariosController controller = loader.getController();
            // Le pasamos el usuario a la siguiente pantalla
            controller.setUsuario(this.username);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 900);
            stage.setTitle("Aplicación Punto Limpio - Usuarios");
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo cargar la pantalla de usuarios.");
            alert.showAndWait();
        }
    }

    @FXML
    public void onCategoria2Click() {
        System.out.println("Categoria 2 seleccionada (Pendiente de implementar)");
    }

    @FXML
    public void onCategoria3Click() {
        System.out.println("Categoria 3 seleccionada (Pendiente de implementar)");
    }
}