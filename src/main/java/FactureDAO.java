public interface FactureDAO {
    double getMontantTotal(int idFacture);
    double getMontantDejaPaye(int idFacture);
    void updateStatut(int idFacture, String statut);
}
