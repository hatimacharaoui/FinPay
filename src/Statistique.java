import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Statistique {
    private double commissions;
    private double totalPaiements;
    private List<Facture> factures = new ArrayList<>();

    public Statistique () {}

    public Statistique (double commissions, double totalPaiements, List<Facture> factures) {
        this.commissions = commissions;
        this.totalPaiements = totalPaiements;
        this.factures = factures;
    }

    public void calculateTotalPaidAmount() {
        String query = "SELECT SUM(totalPaiment) FROM paiement";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet res = stmt.executeQuery()) {

            if (res.next()) {
                this.totalPaiements = res.getDouble(1);
                System.out.println("=== Total des paiements enregistrés ===");
                System.out.printf("Montant total payé : %.2f DH%n", this.totalPaiements);
                System.out.println("=======================================");
            } else {
                System.out.println("Aucun paiement enregistré dans la base.");
                this.totalPaiements = 0;
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors du calcul du total des paiements : " + e.getMessage());
            e.printStackTrace();
        }
    }


    public double calculateTotalCommissions() {
        String query = "SELECT SUM(commission) FROM paiement";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet res = stmt.executeQuery()) {
            if (res.next()) {
                this.commissions = res.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commissions;
    }

    public void getPaidInvoices() {

        this.factures.clear();

        String query = "SELECT id_facture, montant_total, date_facture, statut " +
                "FROM facture WHERE statut = 'PAYEE'";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("---- FACTURES PAYEES ----");

            while (resultSet.next()) {

                int id = resultSet.getInt("id_facture");
                double montant = resultSet.getDouble("montant_total");

                java.sql.Date dbDate = resultSet.getDate("date_facture");
                java.util.Date date = (dbDate != null) ? new java.util.Date(dbDate.getTime()) : null;

                String statut = resultSet.getString("statut");

                Client client = new Client();
                Prestataire prestataire = new Prestataire();


                Facture f = new Facture(id, montant, statut, date, client, prestataire);

                this.factures.add(f);

                System.out.println("Facture ID : "
                        + f.getId()
                        + " | Statut : "
                        + f.getStatut());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void getUnpaidInvoices() {

        this.factures.clear();

        String query = "SELECT id_facture, montant_total, date_facture, statut " +
                "FROM facture WHERE statut = 'NON_PAYEE'";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("---- FACTURES NON PAYEES ----");

            while (resultSet.next()) {

                int id = resultSet.getInt("id_facture");
                double montant = resultSet.getDouble("montant_total");

                java.sql.Date dbDate = resultSet.getDate("date_facture");
                java.util.Date date = (dbDate != null)
                        ? new java.util.Date(dbDate.getTime())
                        : null;

                String statut = resultSet.getString("statut");

                Client client = new Client();
                Prestataire prestataire = new Prestataire();

                // Constructeur correct
                Facture f = new Facture(id, montant, statut, date, client, prestataire);

                this.factures.add(f);

                System.out.println("Facture id : "
                        + f.getId()
                        + " | Statut : "
                        + f.getStatut());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void getTotalGainByCommissions() {
        String query = "SELECT SUM(commission) FROM paiement";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                this.commissions = resultSet.getDouble(1);
                System.out.println("GAIN TOTAL DES COMMISSIONS : " + this.commissions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}