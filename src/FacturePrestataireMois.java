import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FacturePrestataireMois {

    public FacturePrestataireMois() {
    }

    public static void execute(){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Factures d’un Prestataire");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Date", "Client", "Montant", "Statut"};
        for (int i=0; i<headers.length; i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(getCellStyle(workbook));
        }
        sheet.setColumnWidth(0,3_000);
        sheet.setColumnWidth(1,5_000);
        sheet.setColumnWidth(2,5_000);
        sheet.setColumnWidth(3,5_000);
        sheet.setColumnWidth(4,5_000);


        CellStyle bodyStyle = workbook.createCellStyle();
        bodyStyle.setAlignment(HorizontalAlignment.CENTER);
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(bodyStyle);
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));



        String sql = "SELECT f.id_facture, f.date_facture, c.nom AS nom_client, f.montant_total, f.statut " +
                "FROM facture f " +
                "JOIN client c ON f.id_client = c.id_client";

        try(    Connection conn = DBConnection.getConnection();
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {

            int row = 1;
            while (rs.next()){
                Row rowBody = sheet.createRow(row++);
                Cell cell0 = rowBody.createCell(0);
                cell0.setCellValue(rs.getInt("id_facture"));
                cell0.setCellStyle(bodyStyle);
                Cell dateCell = rowBody.createCell(1);
                dateCell.setCellValue(rs.getDate("date_facture"));
                dateCell.setCellStyle(dateStyle);
                Cell cell2 = rowBody.createCell(2);
                cell2.setCellValue(rs.getString("nom_client"));
                cell2.setCellStyle(bodyStyle);
                Cell cell3 = rowBody.createCell(3);
                cell3.setCellValue(rs.getDouble("montant_total"));
                cell3.setCellStyle(bodyStyle);
                Cell cell4 = rowBody.createCell(4);
                cell4.setCellValue(rs.getString("statut"));
                cell4.setCellStyle(bodyStyle);


            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des factures : " + e.getMessage());
        }





        try {
            FileOutputStream fileOutputStream = new FileOutputStream("facturesprestatairemois.xls");
            workbook.write(fileOutputStream);
            System.out.println("Fichier Excel généré avec succès !");
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static CellStyle getCellStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        Font boldFont =workbook.createFont();
        boldFont.setColor(IndexedColors.WHITE.getIndex());
        boldFont.setBold(true);

        headerStyle.setFont(boldFont);


        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        return headerStyle;
    };

}
