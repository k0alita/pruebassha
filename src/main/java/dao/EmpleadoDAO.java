package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import java.util.List;
import java.util.concurrent.ExecutionException;
import utils.SecurityUtils; // Añade este import arriba

public class EmpleadoDAO {

    public boolean validarLogin(String usuario, String contraseña) {
        try {
            // Obtenemos la instancia de Firestore desde la clase adaptada anteriormente
            Firestore db = ConexionDB.getFirestore();

            String contraseñaHasheada = SecurityUtils.hashSHA256(contraseña);

            // Hacemos el equivalente al "SELECT * FROM empleado WHERE usuario = ? AND contraseña = ?"
            // Buscamos en la colección "empleado" donde coincidan usuario y contraseña
            ApiFuture<QuerySnapshot> query = db.collection("empleado")
                    .whereEqualTo("usuario", usuario)
                    .whereEqualTo("contraseña", contraseñaHasheada) // ← usamos el hash
                    .get();
            // Bloqueamos la ejecución temporalmente hasta que la consulta termine (.get())
            QuerySnapshot querySnapshot = query.get();

            // Obtenemos los documentos resultantes
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

            // Si la lista de documentos no está vacía, el usuario y contraseña son correctos
            return !documents.isEmpty();

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error en login con Firebase: " + e.getMessage());
            // Si hubo interrupción, se recomienda restaurar el estado de interrupción
            Thread.currentThread().interrupt();
            return false;
        } catch (Exception e) {
            System.out.println("Error inesperado en login: " + e.getMessage());
            return false;
        }
    }
}