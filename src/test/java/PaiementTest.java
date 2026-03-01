import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaiementTest {

    Paiement paiement = new Paiement();

    @Test
    void commissionMontantNormal() {
        assertEquals(20.0, paiement.calculerCommission(1000));
    }
    @Test
    void commissionMontantZero() {
        assertEquals(0.0, paiement.calculerCommission(0));
    }
    @Test
    void commissionMontantEleve() {
        assertEquals(87.0, paiement.calculerCommission(500));
    }
}