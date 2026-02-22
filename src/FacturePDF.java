import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.Scanner;

public class FacturePDF {

    public static void genererFacture() throws SQLException, FileNotFoundException {

        Scanner sc = new Scanner(System.in);
        System.out.print("Entrer ID Facture : ");
        int idFacture = sc.nextInt();

        String sql = "SELECT * FROM facture WHERE id_facture=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFacture);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Document document = new Document();
                PdfWriter.getInstance(document,
                        new FileOutputStream("facture_" + idFacture + ".pdf"));

                document.open();

                document.add(new Paragraph("===== FACTURE ====="));
                document.add(new Paragraph("ID: " + idFacture));
                document.add(new Paragraph("Montant: " + rs.getDouble("montant_total")));
                document.add(new Paragraph("Statut: " + rs.getString("statut")));

                document.close();

            } else {
                System.out.println(" Facture introuvable ");
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}