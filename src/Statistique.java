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

    public Statistique (double commissions, double totalPaiements) {
        this.commissions = commissions;
        this.totalPaiements = totalPaiements;
    }

    public void calculateTotalPaidAmount() {
        String query = "SELECT SUM(paid_amount) FROM Paiement";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet res = stmt.executeQuery()) {
            if (res.next()) {
                this.totalPaiements = res.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double calculateTotalCommissions() {
        String query = "SELECT SUM(commission_fee) FROM Paiement";
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
        String query = "SELECT id, date, statut FROM facture WHERE statut = 'PAYEE'";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("---- FACTURES PAYEES ----");
            while(resultSet.next()) {
                int id = resultSet.getInt("id");

                java.sql.Date dbDate = resultSet.getDate("date");
                LocalDate date = (dbDate != null) ? dbDate.toLocalDate() : null;
                String statut = resultSet.getString("statut");

                Paiement paiement = new Paiement();
                Client client = new Client();
                Prestataire prestataire = new Prestataire();

                Facture f = new Facture(id, paiement, statut, date, client, prestataire);
                this.factures.add(f);

                System.out.println("Facture ID : " + f.getId() + " | Statut : " + f.getStatut();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getUnpaidInvoices() {
        this.factures.clear();
        String query = "SELECT id, date, statut FROM facture WHERE statut = 'NON_PAYEE'";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("---- FACTURES NON PAYEES ----");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                java.sql.Date dbDate = resultSet.getDate("date");
                LocalDate date = (dbDate != null) ? dbDate.toLocalDate() : null;
                String statut = resultSet.getString("statut");

                Paiement paiement = new Paiement();
                Client client = new Client();
                Prestataire prestataire = new Prestataire();

                Facture f = new Facture(id, paiement, statut, date, client, prestataire);

                this.factures.add(f);
                System.out.println("Facture id : " + f.getId() + " | Statut : " + f.getStatut());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getTotalGainByCommissions() {
        String query = "SELECT SUM(montant_commission) FROM paiement";

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
