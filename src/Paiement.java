
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


    public Paiement () {};
    public Paiement(int id, Date date, Client client, Facture facture, boolean paiementPartiel, double totalPaiment) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.facture = facture;
        this.paiementPartiel = paiementPartiel;
        this.totalPaiment = totalPaiment;
    }

    public Paiement(int id, double totalPaiment) {
        this.id = id;
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


    public void enregistrerPaiement(Scanner input) {

        System.out.println("==== Nouveau Paiement ====");

        System.out.print("Entrez l'ID du client : ");
        int idClient = input.nextInt();

        try (Connection conn = DBConnection.getConnection()) {

            // Afficher factures non payées
            String sqlFactures =
                    "SELECT f.id_facture, f.montant_total, " +
                            "COALESCE(SUM(p.totalPaiment),0) as deja_paye " +
                            "FROM facture f " +
                            "LEFT JOIN paiement p ON f.id_facture = p.id_facture " +
                            "WHERE f.id_client = ? AND f.statut != 'PAYEE' " +
                            "GROUP BY f.id_facture";

            PreparedStatement psFacture = conn.prepareStatement(sqlFactures);
            psFacture.setInt(1, idClient);

            ResultSet rs = psFacture.executeQuery();

            boolean existe = false;

            while (rs.next()) {
                existe = true;
                double reste = rs.getDouble("montant_total") - rs.getDouble("deja_paye");

                System.out.println("ID Facture: " + rs.getInt("id_facture")
                        + " | Reste à payer: " + reste + " DH");
            }

            if (!existe) {
                System.out.println("Aucune facture à payer.");
                return;
            }

            // Choisir facture
            System.out.print("Entrez l'ID de la facture : ");
            int idFacture = input.nextInt();

            // Récupérer montant total et déjà payé
            String sqlDetail =
                    "SELECT montant_total, " +
                            "COALESCE((SELECT SUM(totalPaiment) FROM paiement WHERE id_facture = ?),0) as deja_paye " +
                            "FROM facture WHERE id_facture = ?";

            PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
            psDetail.setInt(1, idFacture);
            psDetail.setInt(2, idFacture);

            ResultSet rsDetail = psDetail.executeQuery();

            if (!rsDetail.next()) {
                System.out.println("Facture introuvable.");
                return;
            }

            double montantTotal = rsDetail.getDouble("montant_total");
            double dejaPaye = rsDetail.getDouble("deja_paye");
            double resteAvant = montantTotal - dejaPaye;

            System.out.println("Reste actuel : " + resteAvant + " DH");

            //  Montant versé
            System.out.print("Montant à verser : ");
            double montantVerse = input.nextDouble();

            if (montantVerse > resteAvant) {
                System.out.println("Erreur : montant supérieur au reste !");
                return;
            }

            //  Calcul commission
            double commission = montantVerse * 0.02;
            double resteFinal = resteAvant - montantVerse;

            //  Insérer paiement
            String sqlInsert =
                    "INSERT INTO paiement(id_client, id_facture, totalPaiment, date_paiment, commission) " +
                            "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setInt(1, idClient);
            psInsert.setInt(2, idFacture);
            psInsert.setDouble(3, montantVerse);
            psInsert.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            psInsert.setDouble(5, commission);

            psInsert.executeUpdate();

            // 7Mise à jour statut facture
            String nouveauStatut = (resteFinal <= 0.01) ? "PAYEE" : "PARTIEL";

            String sqlUpdate = "UPDATE facture SET statut = ? WHERE id_facture = ?";
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setString(1, nouveauStatut);
            psUpdate.setInt(2, idFacture);
            psUpdate.executeUpdate();

            // Résumé
            System.out.println("==== Transaction réussie ====");
            System.out.println("Montant versé : " + montantVerse + " DH");
            System.out.println("Commission (2%) : " + commission + " DH");
            System.out.println("Nouveau statut : " + nouveauStatut);
            System.out.println("Reste à payer : " + (resteFinal < 0 ? 0 : resteFinal) + " DH");

        } catch (SQLException e) {
            System.out.println("Erreur base de données : " + e.getMessage());
        }
    }


    public void listerPaiements() {

        String sql = "SELECT * FROM paiement";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                System.out.println("ID Paiement : " + rs.getInt("id_paiement"));
                System.out.println("Client ID : " + rs.getInt("id_client"));
                System.out.println("Facture ID : " + rs.getInt("id_facture"));
                System.out.println("Montant : " + rs.getDouble("totalPaiment") + " DH");
                System.out.println("Commission : " + rs.getDouble("commission") + " DH");
                System.out.println("Date : " + rs.getString("date_paiment"));
                System.out.println("-----------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
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