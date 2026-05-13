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
import models.Usuario;

import java.util.List;

public class UsuariosController {

    @FXML
    private Label lblWelcome;

    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario, String> colUsuario;
    @FXML
    private TableColumn<Usuario, String> colContrasena;

    private String username;

    @FXML
    public void initialize() {
        // 1. Configuramos las columnas
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colContrasena.setCellValueFactory(new PropertyValueFactory<>("contraseña"));

        // 2. Cargamos los datos automáticamente en cuanto se dibuja la pantalla
        cargarDatosDeFirestore();
    }

    public void setUsuario(String username) {
        this.username = username;
        lblWelcome.setText("Gestión de Usuarios - Sesión de: " + username);
    }

    private void cargarDatosDeFirestore() {
        new Thread(() -> {
            try {
                Firestore db = ConexionDB.getFirestore();
                ApiFuture<QuerySnapshot> query = db.collection("empleado").get();
                QuerySnapshot querySnapshot = query.get();
                List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

                ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

                for (QueryDocumentSnapshot document : documents) {
                    String user = document.getString("usuario");
                    String pass = document.getString("contraseña");
                    listaUsuarios.add(new Usuario(user, pass));
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