import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Paiement {
    private int id ;
    private Date date;
    private Client client;
    private Facture facture;
    private boolean paiementPartiel;
    private double totalPaiment;


    public Paiement(int id, Date date, Client client, Facture facture, boolean paiementPartiel, double totalPaiment) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.facture = facture;
        this.paiementPartiel = paiementPartiel;
        this.totalPaiment = totalPaiment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public boolean isPaiementPartiel() {
        return paiementPartiel;
    }

    public void setPaiementPartiel(boolean paiementPartiel) {
        this.paiementPartiel = paiementPartiel;
    }

    public double getTotalPaiment() {
        return totalPaiment;
    }

    public void setTotalPaiment(double totalPaiment) {
        this.totalPaiment = totalPaiment;
    }

    public void enregistrerPaiment(Scanner input) {
        System.out.println("--- Nouveau Paiement ---");
        System.out.print("Entrez le numéro id du client : ");
        int id = input.nextInt();
        input.nextLine();

        Client c = client.findId(id);
        if (c == null) {
            System.out.println("Cet ID n’existe pas.");
            return;
        }
        try (Connection conn = DBConnection.getConnection()) {
            String sqlSelectFactures = "SELECT id_facture, montant_total, statut FROM facture WHERE id_client = ? AND statut != 'PAYEE'";

            try (PreparedStatement psFactures = conn.prepareStatement(sqlSelectFactures)) {
                psFactures.setInt(1, id);
                ResultSet rs = psFactures.executeQuery();

                System.out.println("\n--- Factures en attente pour " + c.getNome() + " ---");
                boolean hasFactures = false;
                while (rs.next()) {
                    hasFactures = true;
                    System.out.println("ID Facture: " + rs.getInt("id_facture") +
                            " | Montant: " + rs.getDouble("montant_total") +
                            " | Statut: " + rs.getString("statut"));
                }

                if (!hasFactures) {
                    System.out.println("Ce client n'a aucune facture à payer.");
                    return;
                }
            }

            System.out.print("Entrez l'ID de la facture à régler : ");
            int idF = input.nextInt();
            System.out.print("Entrez le montant du paiement : ");
            double montant = input.nextDouble();

            double commission = (montant * 2.0) / 100;

            String sqlInsert = "INSERT INTO paiement (id_client, totalPaiment, date_paiment, commission) VALUES (?, ?, ?, ?)";
            try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                psInsert.setInt(1, id);
                psInsert.setDouble(2, montant);
                psInsert.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                psInsert.setDouble(4, commission);
                psInsert.executeUpdate();

                String sqlUpdateFacture = "UPDATE facture SET statut = (CASE WHEN montant_total <= ? THEN 'PAYEE' ELSE 'PARTIEL' END) WHERE id_facture = ?";
                try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateFacture)) {
                    psUpdate.setDouble(1, montant);
                    psUpdate.setInt(2, idF);
                    psUpdate.executeUpdate();
                }

                System.out.println("Paiement enregistré avec succès !");
                System.out.println("Commission (2%) : " + commission + " DH");
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    public void listerPaiement(){
        System.out.println("========lister paiement=============");
        String sql = "SELECT * FROM paiement";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_client");
                String totalpayment = rs.getString("totalPaiment");
                String commission = rs.getString("commission");
                String date_paiment = rs.getString("date_paiment");

                System.out.println("id : " + id);
                System.out.println("Name: " + totalpayment);
                System.out.println("commission: " + commission);
                System.out.println("date paiment: " + date_paiment);
                System.out.println("-----------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des clients : " + e.getMessage());
        }
    }


    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", date=" + date +
                ", client=" + client +
                ", facture=" + facture +
                ", paiementPartiel=" + paiementPartiel +
                ", totalPaiment=" + totalPaiment +
                '}';
    }
}
