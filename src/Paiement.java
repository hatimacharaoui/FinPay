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
        System.out.println("==== Nouveau Paiement ====");
        System.out.print("Entrez le numéro id du client : ");
        int idClient = input.nextInt();
        input.nextLine();

        Client c = client.findId(idClient);
        if (c == null) {
            System.out.println("Cet ID n’existe pas.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sqlInfo = "SELECT f.id_facture, f.montant_total, " +
                    "(SELECT COALESCE(SUM(totalPaiment), 0) FROM paiement p WHERE p.id_facture = f.id_facture) as deja_paye " +
                    "FROM facture f WHERE f.id_client = ? AND f.statut != 'PAYEE'";

            try (PreparedStatement psInfo = conn.prepareStatement(sqlInfo)) {
                psInfo.setInt(1, idClient);
                ResultSet rs = psInfo.executeQuery();

                System.out.println("==== Factures en attente pour " + c.getNome() + " ====");
                boolean hasFactures = false;
                while (rs.next()) {
                    hasFactures = true;
                    double reste = rs.getDouble("montant_total") - rs.getDouble("deja_paye");
                    System.out.println("ID Facture: " + rs.getInt("id_facture") + " | Total: " + reste + " DH");
                }

                if (!hasFactures) {
                    System.out.println("Ce client n'a aucune facture à payer.");
                    return;
                }
            }

            System.out.print("Entrez l'ID de la facture à régler : ");
            int idF = input.nextInt();

            double montantTotal = 0;
            double dejaPayeAncien = 0;
            String sqlDetail = "SELECT montant_total, (SELECT COALESCE(SUM(totalPaiment), 0) FROM paiement WHERE id_facture = ?) as deja_paye FROM facture WHERE id_facture = ?";
            try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail)) {
                psDetail.setInt(1, idF);
                psDetail.setInt(2, idF);
                ResultSet rsD = psDetail.executeQuery();
                if (rsD.next()) {
                    montantTotal = rsD.getDouble("montant_total");
                    dejaPayeAncien = rsD.getDouble("deja_paye");
                } else {
                    System.out.println("Facture introuvable.");
                    return;
                }
            }

            double resteAPayerActuel = montantTotal - dejaPayeAncien;

            System.out.print("S'agit-il d'un paiement partiel ? (oui/non) : ");
            input.nextLine();
            String choiceP = input.nextLine().trim().toLowerCase();

            System.out.print("Entrez le montant à verser : ");
            double montantVerse = input.nextDouble();

            if (choiceP.equals("non")) {
                if (montantVerse < resteAPayerActuel) {
                    System.out.println("Erreur: Montant insuffisant pour un paiement complet ! (" + resteAPayerActuel + " DH requis)");
                    return;
                }
            } else if (choiceP.equals("oui")) {
                if (montantVerse >= resteAPayerActuel) {
                    System.out.println("Erreur: Le montant partiel doit être inférieur au reste à payer.");
                    return;
                }
            }

            gererPaiementsPartiels(conn, idClient, idF, montantVerse, resteAPayerActuel);

        } catch (SQLException e) {
            System.out.println("Erreur base de données : " + e.getMessage());
        }
    }


    public void gererPaiementsPartiels(Connection conn, int idClient, int idFacture, double montantVerse, double resteAPayerAvant) throws SQLException {
        double commission = (montantVerse * 2.0) / 100;
        double resteFinal = resteAPayerAvant - montantVerse;

        String sqlInsert = "INSERT INTO paiement (id_client, id_facture, totalPaiment, date_paiment, commission) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
            psInsert.setInt(1, idClient);
            psInsert.setInt(2, idFacture);
            psInsert.setDouble(3, montantVerse);
            psInsert.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            psInsert.setDouble(5, commission);
            psInsert.executeUpdate();
        }

        String nouveauStatut = (resteFinal <= 0.01) ? "PAYEE" : "PARTIEL";

        String sqlUpdate = "UPDATE facture SET statut = ? WHERE id_facture = ?";
        try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
            psUpdate.setString(1, nouveauStatut);
            psUpdate.setInt(2, idFacture);
            psUpdate.executeUpdate();
        }

        System.out.println("==== Résumé de la transaction ====");
        System.out.println("Montant versé : " + montantVerse + " DH");
        System.out.println("Commission (2%) : " + commission + " DH");
        System.out.println("Nouveau statut de la facture : " + nouveauStatut);
        System.out.println("Reste à payer : " + (resteFinal < 0 ? 0 : resteFinal) + " DH");
        System.out.println("==========================================================");
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
                System.out.println("========================================");
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
