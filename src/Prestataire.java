import java.sql.*;

public class Prestataire {
    private int id;
    private String nom;
    private  String secteur;

    public Prestataire( String nom, String secteur) {
        this.nom= nom;
        this.secteur = secteur;
    }
    public int getId() {return id;}
    public String getSecteur() {return secteur;}

    public void setId(int id) {
        this.id = id;
    }

    public void AjouterP(Prestataire prestataire){
        try{
            String sqll = "INSERT INTO prestataire (nom,secteur) VALUES (?,?)";
            PreparedStatement pss =DBConnection.getConnection().prepareStatement(sqll,Statement.RETURN_GENERATED_KEYS);
            pss.setString(1,prestataire.nom);
            pss.setString(2,prestataire.secteur);
            pss.executeUpdate();
            ResultSet rss = pss.getGeneratedKeys();
            if (rss.next()) {
                prestataire.setId(rss.getInt(1));
            }
            System.out.println("A facture inserted ");
            rss.close();
            pss.close();
        }
        catch(SQLException e){
        e.printStackTrace();
        throw new RuntimeException(e);
        }}

public void AjouterFacture(Facture f) throws SQLException {
        try{
            String sql = "INSERT INTO facture (montant_total,statut,date_facture,id_client,id_prestataire) VALUES (?,?,?,?,?)";
            PreparedStatement   ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1,f.getPaiement().getMontant());
            ps.setString(2,f.getStatut());
            ps.setDate(3, Date.valueOf(f.getDate()));
            ps.setInt(4,f.getClient().getId());
            ps.setInt(5,this.id);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                f.setId(rs.getInt(1));
            }
            System.out.println("A facture inserted ");
            rs.close();
            ps.close();
            }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
}
    public void modifieFacture(Facture f) throws SQLException {
        String sql = "UPDATE facture SET statut  = ? WHERE id_facture = ?";
        PreparedStatement  ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1,f.getStatut());
        ps.setInt(2,f.getId());
        ps.executeUpdate();
        System.out.println("Facture updated successfully");
        ps.close();
    }



}
