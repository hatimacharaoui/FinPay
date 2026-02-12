import java.time.LocalDate;

public class Facture {
    private int id ;
    private LocalDate date;
    private String statut;
    Prestataire prestataire;
    Paiement paiement;
    Client client;

    public Client getClient() {return client;}
    public int getId() {return id;}
    public LocalDate getDate() {return date;}
    public String getStatut() {return statut;}
    public Prestataire getPrestataire() {return prestataire;}
    public Paiement getPaiement() {return paiement;}

    public Facture(int id, Paiement paiement, String statut, LocalDate date, Client client, Prestataire prestataire) {
        this.id = id;
        this.paiement = paiement;
        this.statut = statut;
        this.date = date;
        this.client= client;
        this.prestataire = prestataire;
    }
    public void setDate(LocalDate date) {this.date = date;}
    public void setStatut(String statut) {this.statut = statut;}
    public void setPrestataire(Prestataire prestataire) {this.prestataire = prestataire;}
    public void setPaiement(Paiement paiement) {this.paiement = paiement;}
    public void setClient(Client client) {this.client = client;}
    public void setId(int id) {this.id = id;}
}
