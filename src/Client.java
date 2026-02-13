import java.sql.*;

public class Client extends Person {
    private String email;
    private String phone;

    public Client( String nome, String email, String phone) {
        super(nome);
        this.email = email;
        this.phone = phone;
    }
    public Client(int id) {
        this.setId(id);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void ajouterClient(Client c){
        try{
            String sql = "INSERT INTO client (nom, email, telephone) VALUES (?,?,?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,c.getNome());
            ps.setString(2,c.getEmail());
            ps.setString(3,c.getPhone());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                c.setId(rs.getInt(1));
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
}