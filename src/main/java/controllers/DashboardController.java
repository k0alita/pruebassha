package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Label lblWelcome;

    // Método opcional para recibir datos de la ventana anterior
    public void setUsuario(String username) {
        lblWelcome.setText("Bienvenido/a, " + username);
    }

    @FXML
    public void onCategoria1Click() {
        System.out.println("Categoria 1 seleccionada (Pendiente de implementar)");
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