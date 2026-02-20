import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FacturesExcel {

    public void exporterFacturesImpayees() {

        String sql = "SELECT f.id_facture, c.nom, f.date_facture, f.montant_total " +
                "FROM facture f " +
                "JOIN client c ON f.id_client = c.id_client " +
                "WHERE f.statut = 'PENDING'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Factures Impayées");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Client");
            header.createCell(2).setCellValue("Date");
            header.createCell(3).setCellValue("Montant");
            header.createCell(4).setCellValue("Jours de Retard");

            int rowNum = 1;

            while (rs.next()) {

                int id = rs.getInt("id_facture");
                String client = rs.getString("nom");
                Date dateFacture = rs.getDate("date_facture");
                double montant = rs.getDouble("montant_total");


                LocalDate dateF = dateFacture.toLocalDate();
                long joursRetard = ChronoUnit.DAYS.between(dateF, LocalDate.now());

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(id);
                row.createCell(1).setCellValue(client);
                row.createCell(2).setCellValue(dateFacture.toString());
                row.createCell(3).setCellValue(montant);
                row.createCell(4).setCellValue(joursRetard);
            }


            FileOutputStream fileOut = new FileOutputStream("facturesimpayeesmois.xls");
            workbook.write(fileOut);
            fileOut.close();

            System.out.println("Export Excel réussi ");

        } catch (Exception e) {
            System.out.println("Erreur export : " + e.getMessage());
        }
    }
}
