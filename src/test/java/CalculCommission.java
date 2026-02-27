import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculCommission {
    private final Paiement p = new Paiement();

    @Test
    public void montantNormal() {
        double entre = 1000d;
        double attendu = 20d;
        assertEquals(attendu, p.calculeCommission(entre));
    }

    @Test
    public void montantZero(){
        assertEquals(0d, p.calculeCommission(0d));
    }

    @Test
    public void MontantEleve(){
        double entre = 100000d;
        double attendu = 2000d;
        assertEquals(attendu, p.calculeCommission(entre));
    }


}