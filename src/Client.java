import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class Client extends Person {
    private String email;
    private String phone;

    public Client(int id, String nome, String email, String phone) {
        super(id, nome);
        this.email = email;
        this.phone = phone;
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


    public Client findId(int id) {
        String sql = "SELECT * FROM client WHERE id_client = ?";
        Client client = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    client = new Client(id,getNome(), getEmail(),getPhone());
                    client.setId(rs.getInt("id_client"));
                    client.setNome(rs.getString("nom"));
                    client.setEmail(rs.getString("email"));
                    client.setPhone(rs.getString("telephone"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche : " + e.getMessage());
        }

        return client;
    }



    public void AjouterClient(Scanner input) {
        System.out.println("=== Inscription Nouveau Client ===");

        System.out.print("Entrez le nom : ");
        String name = input.nextLine();

        System.out.print("Entrez l'email: ");
        String email = input.nextLine();

        System.out.print("Entrez le numéro de téléphone: ");
        String phone = input.nextLine();

        String sql = "INSERT INTO client (nom, email, telephone) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Le client a été enregistré avec succès!");
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.out.println("L'ID, l'email ou le téléphone existe déjà.");
            } else {
                System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
            }
        }
    }

    public void ModifierClient(Scanner input) {
        System.out.print("Entrez le numéro id du client à modifier: ");
        int id = input.nextInt();
        input.nextLine();

        Client c = findId(id);

        if (c == null) {
            System.out.println("Cet ID n’existe pas.");
            return;
        }

        System.out.println("Qu’allez-vous changer ?");
        System.out.println("1. Nom");
        System.out.println("2. Email");
        System.out.println("3. Téléphone");
        System.out.print("Entrez un choix: ");
        int choice = input.nextInt();
        input.nextLine();

        String nomColonne = "";
        String nouvelleValeur = "";

        switch (choice) {
            case 1:
                nomColonne = "nom";
                System.out.print("Saisissez le nouveau nom: ");
                nouvelleValeur = input.nextLine();
                break;
            case 2:
                nomColonne = "email";
                System.out.print("Saisissez le nouvel email: ");
                nouvelleValeur = input.nextLine();
                break;
            case 3:
                nomColonne = "telephone";
                System.out.print("Saisissez le nouveau téléphone: ");
                nouvelleValeur = input.nextLine();
                break;
            default:
                System.out.println("Choix invalide.");
                return;
        }

        String sql = "UPDATE client SET " + nomColonne + " = ? WHERE id_client = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nouvelleValeur);
            pstmt.setInt(2, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Modification réussie dans la base de données !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }


    public void supprimeClient(Scanner input) {
        System.out.print("Entrez le numéro id du client à supprimer : ");
        int id = input.nextInt();
        input.nextLine();

        String sql = "DELETE FROM client WHERE id_client = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Client supprimé avec succès de la base de données.");
            } else {
                System.out.println("Aucun client trouvé avec l'ID : " + id);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    public void listerClient() {
        System.out.println("======= Données clients===========");

        String sql = "SELECT * FROM client";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_client");
                String name = rs.getString("nom");
                String email = rs.getString("email");
                String phone = rs.getString("telephone");

                System.out.println("id : " + id);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Phone: " + phone);
                System.out.println("===============================================");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des clients : " + e.getMessage());
        }

        System.out.println("===========================================");
    }

    public void RechercherClient(Scanner input) {
        System.out.println("===== Rechercher Client =====");
        System.out.print("Entrez le numéro id: ");
        int id = input.nextInt();
        input.nextLine();

        String sql = "SELECT * FROM client WHERE id_client = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("==========ID trouvé===============");
                    System.out.println("id    : " + rs.getInt("id_client"));
                    System.out.println("Nom   : " + rs.getString("nom"));
                    System.out.println("Email : " + rs.getString("email"));
                    System.out.println("Phone : " + rs.getString("telephone"));
                    System.out.println("==================================================");
                } else {
                    System.out.println("Cet ID n’existe pas dans la base de données.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche : " + e.getMessage());
        }
    }






    @Override
    public String toString() {
        return "Client{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
