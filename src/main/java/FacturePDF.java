import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.Scanner;

public class FacturePDF {

    public String genererFacture(int idFacture) throws SQLException, FileNotFoundException {

        String testfacture = "facture_" + idFacture + ".pdf";
        try (Connection con = DBConnection.getConnection()) {

            String sqlFacture = "SELECT * FROM facture WHERE id_facture = ?";
            PreparedStatement psFacture = con.prepareStatement(sqlFacture);
            psFacture.setInt(1, idFacture);
            ResultSet rsFacture = psFacture.executeQuery();

            if (!rsFacture.next()) {
                System.out.println("Facture introuvable !");
            }

            double montantTotal = rsFacture.getDouble("montant_total");
            String statut = rsFacture.getString("statut");
            Date dateFacture = rsFacture.getDate("date_facture");
            int idClient = rsFacture.getInt("id_client");
            int idPrestataire = rsFacture.getInt("id_prestataire");

            String sqlClient = "SELECT * FROM client WHERE id_client = ?";
            PreparedStatement psClient = con.prepareStatement(sqlClient);
            psClient.setInt(1, idClient);
            ResultSet rsClient = psClient.executeQuery();
            rsClient.next();

            String nomClient = rsClient.getString("nom");
            String email = rsClient.getString("email");
            String telephone = rsClient.getString("telephone");

            String sqlPrestataire = "SELECT * FROM prestataire WHERE id_prestataire = ?";
            PreparedStatement psPrestataire = con.prepareStatement(sqlPrestataire);
            psPrestataire.setInt(1, idPrestataire);
            ResultSet rsPrestataire = psPrestataire.executeQuery();
            rsPrestataire.next();

            String nomPrestataire = rsPrestataire.getString("nom");
            String secteur = rsPrestataire.getString("secteur");

            String sqlCommission = "SELECT IFNULL(SUM(commission),0) FROM paiement WHERE id_facture = ?";
            PreparedStatement psCommission = con.prepareStatement(sqlCommission);
            psCommission.setInt(1, idFacture);
            ResultSet rsCommission = psCommission.executeQuery();
            rsCommission.next();

            double commission = rsCommission.getDouble(1);
            double montantNet = montantTotal - commission;

            //pdf

            Document document = new Document();
            PdfWriter.getInstance(document,
                    new FileOutputStream(testfacture));

            document.open();

            document.add(new Paragraph("========== FINPAY =========="));
            document.add(new Paragraph("Facture N° : " + idFacture));
            document.add(new Paragraph("Date : " + dateFacture));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("---- Client ----"));
            document.add(new Paragraph("Nom : " + nomClient));
            document.add(new Paragraph("Email : " + email));
            document.add(new Paragraph("Téléphone : " + telephone));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("---- Prestataire ----"));
            document.add(new Paragraph("Nom : " + nomPrestataire));
            document.add(new Paragraph("Secteur : " + secteur));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Montant Total : " + montantTotal + " MAD"));
            document.add(new Paragraph("Commission : " + commission + " MAD"));
            document.add(new Paragraph("Montant Net : " + montantNet + " MAD"));
            document.add(new Paragraph("Statut : " + statut));

            document.close();

            System.out.println("Facture PDF générée avec succès ");


        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        return testfacture;
    }

}
