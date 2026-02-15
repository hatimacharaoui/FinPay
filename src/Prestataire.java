
            import java.sql.*;
            import java.time.LocalDate;
            import java.util.ArrayList;
            import java.util.List;
            import java.util.Scanner;

            public class Prestataire extends Person{
                private  String secteur;
                Scanner sc = new Scanner(System.in);

                public Prestataire () {}
                public Prestataire(int id, String nom) {
                    super(id, nom);
                }
                public Prestataire(int id){
                super.setId(id);
            }
                public void AjouterP(){
                    try(Connection conn = DBConnection.getConnection()){
                        System.out.println("Enter Prestataire Nom:");
                        String prestataireNom=sc.nextLine();
                        System.out.println("Enter Prestataire Secteur");
                        String prestataireSecteur=sc.nextLine();

                        String sqll = "INSERT INTO prestataire (nom,secteur) VALUES (?,?)";
                        PreparedStatement pss =conn.prepareStatement(sqll);
                        pss.setString(1,prestataireNom);
                        pss.setString(2,prestataireSecteur);
                        pss.executeUpdate();
                        System.out.println("A Prestataire inserted ");
                    }
                    catch(SQLException e){
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }}

                public void AjouterFacture() throws SQLException {
                    LocalDate date = LocalDate.now();
                    try (Connection conn = DBConnection.getConnection()){
                        System.out.println("Entre Montant total :");
                        double montant=sc.nextDouble();
                        sc.nextLine();
                        System.out.println("Entre Statut :");
                        String statut=sc.nextLine();
                        System.out.println("Entre client ID :");
                        int client_id=sc.nextInt();
                        System.out.println("Entre prestataire ID :");
                        int prestataire_id=sc.nextInt();

                        Client client= new Client(client_id);
                        Prestataire prestataire = new Prestataire(prestataire_id);
                    
                        String checkclient = "SELECT * FROM client WHERE id_client = ?";
                        PreparedStatement ps2= conn.prepareStatement(checkclient);
                        ps2.setInt(1,client_id);
                        ResultSet rs2 = ps2.executeQuery();

                        if(!rs2.next()){
                            System.out.println("Client not found");
                            return;
                        }
                        rs2.close();
                        ps2.close();

                        String checkPrestataire = "SELECT * FROM prestataire WHERE id_prestataire  = ?";
                        PreparedStatement ps3= conn.prepareStatement(checkPrestataire);
                        ps3.setInt(1,prestataire_id);
                        ResultSet rs3 = ps3.executeQuery();
                        if(!rs3.next()){
                            System.out.println("Prestataire not found");
                            return;
                        }

                        rs3.close();
                        ps3.close();

                        Facture f = new Facture(0,montant, statut, date, client, prestataire);

                        String sql = "INSERT INTO facture (montant_total,statut,date_facture,id_client,id_prestataire) VALUES (?,?,?,?,?)";
                        PreparedStatement   ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        ps.setDouble(1,f.getMontant());
                        ps.setString(2,f.getStatut());
                        ps.setDate(3, Date.valueOf(f.getDate()));
                        ps.setInt(4,client_id);
                        ps.setInt(5,prestataire_id);
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
                public void modifieFacture() throws SQLException {
                    System.out.println("Enter Facture ID");
                    int DactureId=sc.nextInt();
                    sc.nextLine();
                    String checkFactureID="SELECT * FROM facture WHERE id_facture=?";

        try(Connection conn= DBConnection.getConnection();){
                            try (
                            PreparedStatement ps =conn.prepareStatement(checkFactureID);){
                            ps.setInt(1, DactureId);
                            ResultSet rs4 = ps.executeQuery();
                            if(!rs4.next()){
                                System.out.println("Facture not found");
                                return;
                            }
                        }

                    System.out.println("Enter new statut:");
                    String updatedStatut = sc.nextLine();

            String updateFacture = "UPDATE facture SET statut  = ? WHERE id_facture = ?";
            try(PreparedStatement psupdate =conn.prepareStatement(updateFacture);)
            {
                    psupdate.setString(1,updatedStatut);
                    psupdate.setInt(2,DactureId);
                    psupdate.executeUpdate();
                    System.out.println("Facture updated successfully");

            }} catch(SQLException e){
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
                public void DeleteFature() throws SQLException {
                    System.out.println("Saisissez la fracture si vous souhaitez la supprimer");
                    int DactureId=sc.nextInt();
                    String checkFactureID="SELECT * FROM facture WHERE id_facture=?";
                    PreparedStatement ps4= DBConnection.getConnection().prepareStatement(checkFactureID);
                    ps4.setInt(1, DactureId);
                    ResultSet rs4 = ps4.executeQuery();

                    if(!rs4.next()){
                        System.out.println("Facture ID not found");
                        return;
                    }
                    String sql ="DELETE FROM facture WHERE id_facture = ?";
                    PreparedStatement ps5 = DBConnection.getConnection().prepareStatement(sql);
                    ps5.setInt(1,DactureId);
                    ps5.executeUpdate();
                    System.out.println("La facture a été supprimée.");

                }


    public void afficherFacture() throws SQLException {

        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM facture";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet result = ps.executeQuery();
            
            System.out.println("======= LISTE DES FACTURES =======");

            while(result.next()){
                int id = result.getInt("id_facture");
                double montant = result.getDouble("montant_total");
                String statut = result.getString("statut");
                LocalDate date = result.getDate("date_facture").toLocalDate();
                int client_id = result.getInt("id_client");
                int prestataire_id = result.getInt("id_prestataire");

                Client c = new Client(client_id);
                Prestataire p = new Prestataire(prestataire_id);
                Facture f = new Facture(id, montant, statut, date, c, p);

                list.add(f);

                System.out.println("Facture ID: " + f.getId() +
                        ", Montant: " + f.getMontant() +
                        "\nStatut: " + f.getStatut() +
                        ", Date de Facture: " + f.getDate() +
                        "\nClient ID: " + f.getClient().getId() +
                        ", Prestataire ID: " + f.getPrestataire().getId());
            }
            System.out.println("===================================");

        } catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

                public void filterFactureParStatut() throws SQLException {
                    System.out.println("Chose status to filter :");
                    System.out.println("1. PAYEE");
                    System.out.println("2. NON_PAYEE");
                    System.out.println("3. EN_ATTENTE");
                    int choix=sc.nextInt();
                    List<Facture>list = new ArrayList<>();
                    String sql = "SELECT * FROM facture WHERE statut = ?";
                    try(Connection conn = DBConnection.getConnection() ; 
                    PreparedStatement ps = conn.prepareStatement(sql)){
                    String statusFilter = "";
                    if (choix == 1) {
                        statusFilter = "PAYEE";
                    } else if (choix == 2) {
                        statusFilter = "NON_PAYEE";
                    } else if (choix == 3) {
                        statusFilter = "EN_ATTENTE";
                    }else {
                        System.out.println("Choix invalide.");
                        return;
                    }
                    ps.setString(1, statusFilter);
                    ResultSet result= ps.executeQuery();
                    
            System.out.println("======= FACTURES " + statusFilter + " =======");

                    while(result.next()){
                        
                        int id =result.getInt("id_facture");
                        double montant = result.getDouble("montant_total");
                        String statut = result.getString("statut");
                        LocalDate date = result.getDate("date_facture").toLocalDate();
                        int client_id = result.getInt("id_client");
                        int prestataire_id = result.getInt("id_prestataire");
                        Client c = new Client(client_id);
                        Prestataire p = new Prestataire(prestataire_id);
                        Facture f = new Facture(id,montant,statut,date,c,p);
                        list.add(f);
                        System.out.println("Facture ID: " + f.getId() +
                                ", Montant: " + f.getMontant() +
                                ", Statut: " + f.getStatut() +
                                ", Client ID: " + f.getClient().getId() +
                                ", Prestataire ID: " + f.getPrestataire().getId());
                    }
                        System.out.println("===================================");
                    result.close();
                }catch(SQLException e){
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
            }
        }
