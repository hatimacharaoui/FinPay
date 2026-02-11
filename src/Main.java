import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        System.out.println("--- Test de connexion à la base de données ---");

        // Utilisation de try-with-resources pour fermer la connexion automatiquement
        try (Connection conn = DBConnection.getConnection()) {

            if (conn != null) {
                System.out.println("✅ Succès : Connexion établie avec la base 'finpay_db' !");
                System.out.println("Détails de la connexion : " + conn.toString());
            } else {
                System.out.println("⚠️ Attention : La connexion a renvoyé null.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur : Impossible de se connecter.");
            System.err.println("Message d'erreur : " + e.getMessage());
            // Affiche la trace complète pour le débogage
            e.printStackTrace();
        }
    }
}