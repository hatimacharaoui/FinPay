import java.math.BigDecimal;
import java.time.LocalDate;

public class Paiement {
    private int idPaiement;
    private double montant;
    private LocalDate datePaiement;
    private BigDecimal tauxCommission = new BigDecimal("2.00");
    private BigDecimal montantCommission;
    private int idFacture;

    public Paiement(double montant, LocalDate datePaiement, int idFacture) {
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.idFacture = idFacture;
    }

    public int getIdPaiement() {return idPaiement;}
    public void setIdPaiement(int idPaiement) {this.idPaiement = idPaiement;}
    public double getMontant() {return montant;}
    public void setMontant(double montant) {this.montant = montant;}
    public LocalDate getDatePaiement() {return datePaiement;}
    public void setDatePaiement(LocalDate datePaiement) {this.datePaiement = datePaiement;}
    public BigDecimal getTauxCommission() {return tauxCommission;}
    public void setTauxCommission(BigDecimal tauxCommission) {this.tauxCommission = tauxCommission;}
    public BigDecimal getMontantCommission() {return montantCommission;}
    public void setMontantCommission(BigDecimal montantCommission) {this.montantCommission = montantCommission;}
    public int getIdFacture() {return idFacture;}
    public void setIdFacture(int idFacture) {this.idFacture = idFacture;}

    @Override
    public String toString() {
        return "Paiement{" +
                "idPaiement=" + idPaiement +
                ", montant=" + montant +
                ", datePaiement=" + datePaiement +
                ", tauxCommission=" + tauxCommission +
                ", montantCommission=" + montantCommission +
                ", idFacture=" + idFacture +
                '}';
    }
}
