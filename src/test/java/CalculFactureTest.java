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
    @DisplayName("Doit retourner la somme correcte pour plusieurs factures")
    void testCalculTotalPlusieursFactures() {
        List<Double> factures = Arrays.asList(1500.0, 250.50, 300.0);
        double total = calculerTotalFacture(factures);
        assertEquals(2050.50, total, "une liste vide doit donner un total de 0");
    }

    @Test
    @DisplayName("doit retourner 0 si la liste des factures est vide")
    void testCalculTotalListeVide() {
        List<Double> factures = Collections.emptyList();
        double total = calculerTotalFacture(factures);
        assertEquals(0.0, total, "une liste vide doit donner un total de 0");
    }
}