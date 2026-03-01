import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculFactureTest {
    public double calculerTotalFacture(List<Double> montantsFactures) {
        if (montantsFactures == null || montantsFactures.isEmpty()) {
            return 0.0;
        }
        return montantsFactures.stream().mapToDouble(Double::doubleValue).sum();
    }

    @Test
    @DisplayName("Somme correcte des montants")
    void testSommeCorrecteMontants() {
        List<Double> factures = Arrays.asList(1000.0);
        double total = calculerTotalFacture(factures);
        assertEquals(1000, total, "La somme doit correspondre au montant de la facture unique");
    }

    @Test
    @DisplayName("Plusieurs factures")
    void testPlusieursFactures() {
        List<Double> factures = Arrays.asList(1500.0, 250.50, 300.0, 50.0);
        double total = calculerTotalFacture(factures);
        assertEquals(2100.50, total, "La somme de plusieurs factures doit etre exacte");
    }

    @Test
    @DisplayName("Cas liste vide")
    void testCasListeVide() {
        List<Double> factures = Collections.emptyList();
        double total = calculerTotalFacture(factures);
        assertEquals(0.0, total, "une liste vide doit donner un total de 0");
    }
}