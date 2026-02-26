import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FactureTest {

    Prestataire p = new Prestataire();

    @Test
    void paiementTotal_devientPaid() {

        assertEquals("PAID", p.calculerStatut(100, 1000));
    }

    @Test
    void paiementPartiel_devientPending() {
        assertEquals("PENDING", p.calculerStatut(1000, 500));
    }

    @Test
    void aucunPaiement_restePending() {
        assertEquals("PENDING", p.calculerStatut(1000, 0));
    }
}