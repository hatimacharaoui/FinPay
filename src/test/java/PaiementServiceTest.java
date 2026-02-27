import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaiementServiceTest {

    @Mock
    private FactureDAO fauxDAO;

    @InjectMocks
    private PaiementService paiementService;

    @Test
    @DisplayName("Changer la statut à PAYEE")
    void testFacturePayeeTotalement() {
        when(fauxDAO.getMontantTotal(1)).thenReturn(1000.0);
        when(fauxDAO.getMontantDejaPaye(1)).thenReturn(1000.0);

        boolean result = paiementService.verifierMettreAJourStatut(1);

        assertTrue(result, "!");
    }

    @Test
    @DisplayName("Changer la statut à PARTIEL")
    void testFacturePartiel() {
        when(fauxDAO.getMontantTotal(2)).thenReturn(1000.0);
        when(fauxDAO.getMontantDejaPaye(2)).thenReturn(400.0);

        boolean result = paiementService.verifierMettreAJourStatut(2);

        assertFalse(result, "!");
    }
}
