import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RapportExcel {
    public static void genererRapportGlobalMensuel() {
        String query = "SELECT " +
                       "p.nom AS prestataire, " +
                       "COUNT(f.id_facture) AS nombre_factures, " +
                       "SUM(f.montant_total) AS total_genere, " +
                       "SUM(pa.commission) AS total_commissions" +
                       "FROM prestataire p" +
                       "JOIN facture f ON p.id_prestataire = f.id_prestataire " +
                       "JOIN paiement pa ON f.id_facture = pa.id_facture " +
                       "GROUP BY p.id_prestataire, p.nom";

        LocalDate now =  LocalDate.now();
        String monthYear = now.format(DateTimeFormatter.ofPattern("MM_yyyy"));
        String filename = "RapportGlobal_ " + monthYear + " .xls";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();
             Workbook workbook = new HSSFWorkbook()
        ){
            Sheet sheet = workbook.createSheet("Rapport Mensuel");
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Prestataire", "Nombre Factures","Total Généré ", "Total Commissions"};

            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(resultSet.getString("prestataire"));
                row.createCell(1).setCellValue(resultSet.getInt("nombre_factures"));
                row.createCell(2).setCellValue(resultSet.getDouble("total_genere"));
                row.createCell(3).setCellValue(resultSet.getDouble("total_commissions"));
            }

            for (int i = 0; i <= columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(filename);) {
                workbook.write(fileOut);
            }

            System.out.println("Rapport Excel généré avec succés : " + filename);
        } catch (Exception e) {
            System.out.println("Erreur lors de la génération du rapport Excel : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
