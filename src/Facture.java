import java.time.LocalDate;

public class Facture {
    private int id ;
    private LocalDate date;
    private String statut;
    Prestataire prestataire;
    Paiement paiement;

    public int getId() {return id;}
    public LocalDate getDate() {return date;}
    public String getStatut() {return statut;}
    public Prestataire getPrestataire() {return prestataire;}
    public Paiement getPaiement() {return paiement;}

    public Facture(int id, LocalDate date, String statut, Prestataire prestataire, Paiement paiement) {
        this.id = id;
        this.date = date;
        this.statut = statut;
        this.prestataire = prestataire;
        this.paiement = paiement;
    }

}
