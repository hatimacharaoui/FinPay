import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NommageFichierTest {
    public static String genererDernierRecu(int idPaiement) {

        try (Connection conn = DBConnection.getConnection()) {

            String sql = """
                    SELECT id_paiement, id_facture, totalPaiment
                    FROM paiement
                    ORDER BY id_paiement DESC
                    LIMIT 1
                    """;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {

                int idFacture = rs.getInt("id_facture");
                double montant = rs.getDouble("totalPaiment");

                String filename = "recupaiement_" + idPaiement + ".pdf";
                Document document = new Document(PageSize.A6.rotate());
                PdfWriter.getInstance(document, new FileOutputStream(filename));
                document.open();

                document.setMargins(15, 15, 15, 15);

                Font titleFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                Paragraph title = new Paragraph("RECU DE PAIEMENT", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(10);
                document.add(title);

                Paragraph line = new Paragraph("----------------------------------");
                line.setAlignment(Element.ALIGN_CENTER);
                document.add(line);


                Paragraph p1 = new Paragraph("Numero Paiement :  " + idPaiement);
                p1.setAlignment(Element.ALIGN_CENTER);
                document.add(p1);
                Paragraph p2 = new Paragraph("Numero Facture:   " + idFacture);
                p2.setAlignment(Element.ALIGN_CENTER);
                document.add(p2);
                Paragraph p3 = new Paragraph("Montant Paye :    " + montant + " DH");
                p3.setAlignment(Element.ALIGN_CENTER);
                document.add(p3);
                Paragraph p4 = new Paragraph("Date :     "+ new Date());
                p4.setAlignment(Element.ALIGN_CENTER);
                document.add(p4);

                Paragraph line2 = new Paragraph("----------------------------------");
                line2.setAlignment(Element.ALIGN_CENTER);
                document.add(line2);


                document.close();

                System.out.println("Reçu généré NOW: " + filename);
                return filename;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    @Test
    void RecuGeneratorTest() {
       ;
       String result = genererDernierRecu(10);
       assertEquals("recupaiement_10.pdf", result, "!");
    }
}
