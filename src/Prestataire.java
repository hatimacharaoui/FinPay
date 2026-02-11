import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Prestataire {
    private int id;
    private  String secteur;

    public Prestataire(int id, String secteur) {
        this.id = id;
        this.secteur = secteur;
    }
    public int getId() {return id;}
    public String getSecteur() {return secteur;}

public  void AjouterFacture(Facture f) throws SQLException {
    String sql = "INSERT INTO Spectator (secteur) VALUES ( ?)";
    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    ps.setString(1, this.secteur);}
}
