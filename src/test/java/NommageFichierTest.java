import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NommageFichierTest {
    public String genererNomFacture(int idFacture) {
        return "facture_" + idFacture + ".pdf";
    }

    public String genererNomRecu(int idPaiement) {
        return "recu_" + idPaiement + ".pdf";
    }

    public String genererNomRapportMensuel(LocalDate date) {
        String monthYear = date.format(DateTimeFormatter.ofPattern("MMyyyy"));
        return "rapport" + monthYear + ".xls";
    }

    @Test
    @DisplayName("Nom de fichier PDF de la facture")
    void testNomFacture() {
        String nomFichier = genererNomFacture(123);
        assertEquals("facture_123.pdf", nomFichier, "!");
    }

    @Test
    @DisplayName("Nom de fichier PDF de recu")
    void testNomRecu() {
        String nomFichier = genererNomRecu(456);
        assertEquals("recu_456.pdf", nomFichier, "!");
    }

    @Test
    @DisplayName("Nom de fichier XLS de rapport mensuel")
    void testNomRapport() {
        LocalDate dateTest = LocalDate.of(2026, 2, 24);
        String nomFichier = genererNomRapportMensuel(dateTest);
        assertEquals("rapport022026.xls", nomFichier, "!");
    }
}
