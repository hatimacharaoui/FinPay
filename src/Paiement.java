import java.util.Date;
import java.util.Scanner;

public class Paiement {
    private int id ;
    private Date date;
    private Client client;
    private Facture facture;
    private boolean paiementPartiel;
    private double totalPaiment;


    public Paiement(int id, Date date, Client client, Facture facture, boolean paiementPartiel, double totalPaiment) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.facture = facture;
        this.paiementPartiel = paiementPartiel;
        this.totalPaiment = totalPaiment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public boolean isPaiementPartiel() {
        return paiementPartiel;
    }

    public void setPaiementPartiel(boolean paiementPartiel) {
        this.paiementPartiel = paiementPartiel;
    }

    public double getTotalPaiment() {
        return totalPaiment;
    }

    public void setTotalPaiment(double totalPaiment) {
        this.totalPaiment = totalPaiment;
    }





    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", date=" + date +
                ", client=" + client +
                ", facture=" + facture +
                ", paiementPartiel=" + paiementPartiel +
                ", totalPaiment=" + totalPaiment +
                '}';
    }
}
