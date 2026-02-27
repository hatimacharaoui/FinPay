import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class FichierTest {

    FacturePDF p = new FacturePDF();
    FacturesExcel f = new FacturesExcel();
    RapportExcel r = new RapportExcel();

    @Test
    void testNomFacture() throws SQLException, FileNotFoundException {
        String nom = p.genererFacture(7);
        assertEquals("facture_7.pdf", nom);
    }

    @Test
    void testFactureExcel() {
        String nom = f.exporterFacturesImpayees(12);
        assertEquals("facturesimpayees_12.xls", nom);
    }

    @Test
    void testRapportExcel() {
        String nom = r.genererRapportGlobalMensuel("02_2026");
        assertEquals("rapport_02_2026.xls", nom);
    }


}
