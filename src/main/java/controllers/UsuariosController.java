package controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import dao.ConexionDB;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Empleado;
import models.Usuario;

import java.util.List;

public class UsuariosController {

    @FXML
    private Label lblWelcome;

    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, String> colApellido;
    @FXML
    private TableColumn<Usuario, String> colDni;

    private String username;

    @FXML
    public void initialize() {
        // 1. Configuramos las columnas
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));

        // 2. Cargamos los datos automáticamente en cuanto se dibuja la pantalla
        cargarDatosDeFirestore();
    }

    public void setUsuario(String username) {
        this.username = username;
        // ✅ Evita el NullPointerException si el label no existe en el FXML
        if (lblWelcome != null) {
            lblWelcome.setText("Gestión de Usuarios - Sesión de: " + username);
        }
    }

    private void cargarDatosDeFirestore() {
        new Thread(() -> {
            try {
                Firestore db = ConexionDB.getFirestore();
                ApiFuture<QuerySnapshot> query = db.collection("usuarios").get();
                QuerySnapshot querySnapshot = query.get();
                List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

                ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

                for (QueryDocumentSnapshot document : documents) {
                    String nombre = document.getString("nombre");
                    String apellido = document.getString("apellido");
                    String dni = document.getString("dni");
                    listaUsuarios.add(new Usuario(nombre, apellido, dni));
                }

                Platform.runLater(() -> tablaUsuarios.setItems(listaUsuarios));

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error de base de datos");
                    alert.setContentText("No se pudieron cargar los usuarios de Firestore.");
                    alert.showAndWait();
                });
            }
        }).start();
    }

    @FXML
    public void onCategoria1Click(ActionEvent event) {
        // En esta vista, el botón 1 ahora funciona como "Refrescar"
        cargarDatosDeFirestore();
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