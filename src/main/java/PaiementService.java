public class PaiementService {
    private FactureDAO factureDAO;

    public PaiementService(FactureDAO factureDAO) {
        this.factureDAO = factureDAO;
    }

    public boolean verifierMettreAJourStatut(int idFacture) {
        double total = factureDAO.getMontantTotal(idFacture);
        double paye = factureDAO.getMontantDejaPaye(idFacture);

        if (paye >= total) {
            factureDAO.updateStatut(idFacture, "PAYEE");
            return true;
        } else {
            factureDAO.updateStatut(idFacture, "PARTIEL");
            return false;
        }
    }
}
