import java.sql.SQLException;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        LocalDate date = LocalDate.now();

        if (DBConnection.getConnection() != null) {
            System.out.println("✔ Connected successfully!");
        } else {
            System.out.println("❌ Connection failed!");
        }
        Client c = new Client("zddSDFzpoiejrzd","AyoubHaziejridsdsSFDSFGedzedail.com","067489SDF73947");

        Prestataire p = new Prestataire("Ayoub","Clinique");

        Paiement  paiment = new Paiement(233.43,date,12);
        Facture f = new Facture(10,paiment,"PAYEE",date,c,p);
        p.modifieFacture(f);
    }
}