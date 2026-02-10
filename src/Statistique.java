import javax.print.attribute.standard.MediaSize;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Statistique {
    private double totalCommissions;
    private double totalPayments;
    private List<Facture> factures;

    public Statistique (double totalCommissions, double totalPayments, List<Facture> factures) {
        this.totalCommissions = totalCommissions;
        this.totalPayments = totalPayments;
        this.factures = factures;
    }

    public void calculateTotalPaidAmount() {
        String query = "SELECT SUM(paid_amount) FROM Paiement";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet res = stmt.executeQuery()) {
            if (res.next()) {
                this.totalPayments = res.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double calculateTotalCommissions() {
        String query = "SELECT SUM(commission_fee) FROM Paiement";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet res = stmt.executeQuery()) {
            if (res.next()) {
                this.totalCommissions = res.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCommissions;
    }

    public void getPaidInvoices() {
    }

    public void getUnpaidInvoices() {
    }

    public void getTotalGainByCommissions() {
    }
}
