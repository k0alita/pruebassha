package dao;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.InputStream;

public class ConexionDB {

    private static Firestore firestore = null;

    public static Firestore getFirestore() {
        try {
            // Evitamos inicializar Firebase múltiples veces en la misma ejecución
            if (firestore == null) {
                InputStream is = ConexionDB.class.getClassLoader().getResourceAsStream("firebase-service-account.json");

                if (is == null) {
                    throw new RuntimeException("No se encontró el archivo firebase-service-account.json");
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(is))
                        .setDatabaseUrl("https://tu-proyecto.firebaseio.com") // Descomenta esta línea solo si usas Realtime Database en vez de Firestore
                        .build();

                // Verificamos que no haya aplicaciones inicializadas previamente
                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                }

                firestore = FirestoreClient.getFirestore();
            }

            return firestore;

        } catch (Exception e) {
            System.out.println("Error de conexión a Firebase: " + e.getMessage());
            throw new RuntimeException("No se pudo establecer la conexión", e);
        }
    }
}