import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Prestataire extends Person {

    private String secteur;
    Scanner sc = new Scanner(System.in);

    public Prestataire() {}

    public Prestataire(int id, String nom) {
        super(id, nom);
    }

    public Prestataire(int id){
        setId(id);
    }


    public void AjouterP() {

        System.out.println("Enter Prestataire Nom:");
        String prestataireNom = sc.nextLine();

        System.out.println("Enter Prestataire Secteur:");
        String prestataireSecteur = sc.nextLine();

        String sql = "INSERT INTO prestataire (nom, secteur) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, prestataireNom);
            ps.setString(2, prestataireSecteur);
            ps.executeUpdate();

            System.out.println("Prestataire inserted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void AjouterFacture() {

        LocalDate date = LocalDate.now();

        try (Connection conn = DBConnection.getConnection()) {

            System.out.println("Entre Montant total :");
            double montant = sc.nextDouble();
            sc.nextLine();


            System.out.println("Entre client ID :");
            int client_id = sc.nextInt();

            System.out.println("Entre prestataire ID :");
            int prestataire_id = sc.nextInt();
            sc.nextLine();

            // Vérifier client
            String checkClient = "SELECT id_client FROM client WHERE id_client = ?";
            try (PreparedStatement ps = conn.prepareStatement(checkClient)) {
                ps.setInt(1, client_id);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    System.out.println("Client not found");
                    return;
                }
            }

            // Vérifier prestataire
            String checkPrestataire = "SELECT id_prestataire FROM prestataire WHERE id_prestataire = ?";
            try (PreparedStatement ps = conn.prepareStatement(checkPrestataire)) {
                ps.setInt(1, prestataire_id);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    System.out.println("Prestataire not found");
                    return;
                }
            }

            String sql = "INSERT INTO facture (montant_total,  date_facture, id_client, id_prestataire) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDouble(1, montant);
                ps.setDate(2, Date.valueOf(date));
                ps.setInt(3, client_id);
                ps.setInt(4, prestataire_id);
                ps.executeUpdate();
            }

            System.out.println("Facture inserted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void modifieFacture() {

        System.out.println("Enter Facture ID:");
        int factureId = sc.nextInt();
        sc.nextLine();

        String sql = "UPDATE facture SET statut = ? WHERE id_facture = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.println("Enter new statut:");
            String updatedStatut = sc.nextLine();

            ps.setString(1, updatedStatut);
            ps.setInt(2, factureId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Facture updated successfully");
            } else {
                System.out.println("Facture not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void DeleteFature() {

        System.out.println("Enter Facture ID to delete:");
        int factureId = sc.nextInt();

        String sql = "DELETE FROM facture WHERE id_facture = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, factureId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Facture deleted successfully");
            } else {
                System.out.println("Facture not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void afficherFacture() {

        System.out.println("Choose status:");
        System.out.println("1. PAYEE");
        System.out.println("2. NON_PAYEE");
        System.out.println("3. EN_ATTENTE");

        int choix = sc.nextInt();

        String statusFilter = switch (choix) {
            case 1 -> "PAYEE";
            case 2 -> "NON_PAYEE";
            case 3 -> "EN_ATTENTE";
            default -> null;
        };

        if (statusFilter == null) {
            System.out.println("Invalid choice");
            return;
        }

        String sql = "SELECT * FROM facture WHERE statut = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, statusFilter);
            ResultSet result = ps.executeQuery();

            while (result.next()) {

                System.out.println("Facture ID: " + result.getInt("id_facture")
                        + ", Montant: " + result.getDouble("montant_total")
                        + ", Statut: " + result.getString("statut")
                        + ", Client ID: " + result.getInt("id_client")
                        + ", Prestataire ID: " + result.getInt("id_prestataire"));

                System.out.println("------------------------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}