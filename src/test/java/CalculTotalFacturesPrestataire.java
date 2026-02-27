import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculTotalFacturesPrestataire {
    private final Prestataire p = new Prestataire();

    @Test
    public void SommeCorrecteMontantsTest(){
        List<Double> montans = List.of(123.23,900.89,200.90);
        double attend = 1225.02;
        assertEquals(attend, p.calculerTotalFactures(montans));
    }

    @Test
    public void casListeVide(){
        List<Double> montant = new ArrayList<>();
        assertEquals(0.0, p.calculerTotalFactures(montant));
    }

    @Test
    public void plusieursFactures(){
        List<Double> montant = List.of(700.0);
        double attend = 700.0;
        assertEquals(attend, p.calculerTotalFactures(montant));
    }

}