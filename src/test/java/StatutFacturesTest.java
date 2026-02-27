import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

public class StatutFacturesTest {

    Scanner sc = new Scanner(System.in);
    @Test
    void testPaiementTotal() {

        // Appeler la méthode
        Paiement p = new Paiement();
        String statut = p.testPaiement(sc,1, 7, 1000);

        // Vérifier que le statut est PAID
        assertEquals("PAID", statut);
    }

    @Test
    void testPaiementPartiel() {
        String input = "1\n7\n500\n"; // client 1, facture 102, montant partiel

        Paiement p = new Paiement();
        String statut = p.testPaiement(sc,1, 7, 500);

        // Vérifier que le statut est PENDING
        assertEquals("PENDING", statut);
    }

    @Test
    void testAucunPaiement() {

        Paiement p = new Paiement();
        String statut = p.testPaiement(sc,1, 7, 0);

        assertEquals("PENDING", statut);
    }
}