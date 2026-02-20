
import java.time.LocalDate;
import java.util.Date;

public class Facture {
    private int id ;
    private LocalDate date;
    private String statut;
    private Prestataire prestataire;
    private Client client;
    private double  montant;

    public Facture() {}
    public Facture(int id, double montant, String statut, LocalDate date) {
        this.id = id;
        this.montant=montant;
        this.statut = statut;
        this.date = date;
    }
    public Facture(int id, double montant, String statut, LocalDate date, Client client, Prestataire prestataire) {
        this.id = id;
        this.montant=montant;
        this.statut = statut;
        this.date = date;
        this.client= client;
        this.prestataire = prestataire;
    }


    public Client getClient() {return client;}
    public int getId() {return id;}
    public LocalDate getDate() {return date;}
    public String getStatut() {return statut;}
    public Prestataire getPrestataire() {return prestataire;}
    public double getMontant() {return montant;}




    public void setDate(LocalDate date) {this.date = date;}
    public void setStatut(String statut) {this.statut = statut;}
    public void setPrestataire(Prestataire prestataire) {this.prestataire = prestataire;}
    public void setClient(Client client) {this.client = client;}
    public void setId(int id) {this.id = id;}
}