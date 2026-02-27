import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, FileNotFoundException {

        Scanner sc = new Scanner(System.in);

        Client client = new Client();
        Prestataire prestataire = new Prestataire();
        Paiement paiement = new Paiement();
        Statistique statistique = new Statistique();
        FacturePDF facturePDF = new FacturePDF();
        RapportExcel rapportExcel = new RapportExcel();
        FacturePrestataireMois facturesprestatairemois = new FacturePrestataireMois();
        FacturesExcel facturesExcel = new FacturesExcel();
        RecuGenerator recuGenerator = new RecuGenerator();

        int choix1;

        do {
            System.out.println("\n========== FINPAY ==========");
            System.out.println("1. Gestion Clients");
            System.out.println("2. Gestion Prestataires");
            System.out.println("3. Gestion Factures");
            System.out.println("4. Gestion Paiements");
            System.out.println("5. Statistiques");
            System.out.println("6. Génerer une Facture (PDF)");
            System.out.println("7. Générer un Reçu de Paiement (PDF) ");
            System.out.println("8. Générer une Facture d'un Prestataire (Excel) ");
            System.out.println("9. Générer un Rapport Global Mensuel (Excel) ");
            System.out.println("10. Export des Factures Impayées (Excel) ");
            System.out.println("0. Quitter");

            System.out.print("Enter votre Choix : ");
            choix1 = sc.nextInt();

            switch (choix1) {

                case 1 :
                    int choixClient;
                    do {
                        System.out.println("\n--- Section Client ---");
                        System.out.println("1. Ajouter");
                        System.out.println("2. Modifier");
                        System.out.println("3. Supprimer");
                        System.out.println("4. Lister");
                        System.out.println("5. Rechercher");
                        System.out.println("0. Retour");

                        choixClient = sc.nextInt();

                        switch (choixClient) {
                            case 1 :
                                client.AjouterClient(sc);
                                break;
                            case 2 :
                                client.ModifierClient(sc);
                                break;
                            case 3 :
                                client.supprimeClient(sc);
                                break;
                            case 4 :
                                client.listerClient();
                                break;
                            case 5 :
                                client.RechercherClient(sc);
                                break;
                        }

                    } while (choixClient != 0);
                    break;


                case 2 :
                    int choixPrestataire;
                    do {
                        System.out.println("\n--- Section Prestataire ---");
                        System.out.println("1. Ajouter");
                        System.out.println("2. Modifier");
                        System.out.println("3. Supprimer");
                        System.out.println("4. Lister");
                        System.out.println("5. Rechercher");
                        System.out.println("0. Retour");

                        choixPrestataire = sc.nextInt();

                        switch (choixPrestataire) {
                            case 1 :
                                prestataire.AjouterP();
                                break;
                        }

                    } while (choixPrestataire != 0);
                    break;

                case 3 :
                    int choixFacture;
                    do {
                        System.out.println("\n--- Section Facture ---");
                        System.out.println("1. Créer");
                        System.out.println("2. Modifier");
                        System.out.println("3. Supprimer");
                        System.out.println("4. Lister");
                        System.out.println("5. Filtrer par statut");
                        System.out.println("6. Filtrer par prestataire");
                        System.out.println("0. Retour");

                        choixFacture = sc.nextInt();

                        switch (choixFacture) {
                            case 1 :
                                prestataire.AjouterFacture();
                                break;
                            case 2 :
                                prestataire.modifieFacture();
                                break;
                            case 3 :
                                prestataire.DeleteFature();
                                break;
                            case 4 :
                                prestataire.afficherFacture();
                                break;
                        }

                    } while (choixFacture != 0);
                    break;

                case 4 :
                    int choixPaiement;
                    do {
                        System.out.println("\n--- Section paiement ---");
                        System.out.println("1. Enregistrer");
                        System.out.println("2. Lister");
                        System.out.println("0. Retour");

                        choixPaiement = sc.nextInt();

                        switch (choixPaiement) {
                            case 1 :
                                paiement.enregistrerPaiement(sc);
                                break;
                            case 2 :
                                paiement.listerPaiements();
                                break;
                        }

                    } while (choixPaiement != 0);
                    break;

                case 5 :
                    int choixStatistique;
                    do {
                        System.out.println("\n--- Section Statistiques ---");
                        System.out.println("1. Total Paiements");
                        System.out.println("2. Total Commissions");
                        System.out.println("3. Factures payées");
                        System.out.println("4. Factures non payées");
                        System.out.println("0. Retour");

                        choixStatistique = sc.nextInt();

                        switch (choixStatistique) {
                            case 1 :
                                statistique.calculateTotalPaidAmount();
                                break;
                            case 2 :
                                statistique.getTotalGainByCommissions();
                                break;
                            case 3 :
                                statistique.getPaidInvoices();
                                break;
                            case 4 :
                                statistique.getUnpaidInvoices();
                                break;
                        }

                    } while (choixStatistique != 0);
                    break;

                case 6 :
//                    FacturePDF.genererFacture();
                    break;
                case 7 :
                    RecuGenerator.genererDernierRecu();
                    break;
                case 8 :
                    FacturePrestataireMois.execute();
                    break;
                case 9 :
//                    RapportExcel.genererRapportGlobalMensuel();
                    break;
                case 10 :
//                    facturesExcel.exporterFacturesImpayees();
                    break;

                default : System.out.println("Choix invalide ");
            }

        } while (choix1 != 0);
    }
}