import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, FileNotFoundException {

        Scanner sc = new Scanner(System.in);

        Client client = new Client();
        Prestataire prestataire = new Prestataire();
        Facture facture = new Facture();
        Paiement paiement = new Paiement();
        Statistique statistique = new Statistique();
        FacturePDF facturePDF = new FacturePDF();

        int choix1;

        do {
            System.out.println("\n========== FINPAY ==========");
            System.out.println("1. Gestion Clients");
            System.out.println("2. Gestion Prestataires");
            System.out.println("3. Gestion Factures");
            System.out.println("4. Gestion Paiements");
            System.out.println("5. Statistiques");
            System.out.println("6. generer une Facture");
            System.out.println("0. Quitter");

            System.out.print("Enter votre Choix : ");
            choix1 = sc.nextInt();

            switch (choix1) {

                case 1 -> {
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
                            case 1 -> client.AjouterClient(sc);
                            case 4 -> client.listerClient();
                        }

                    } while (choixClient != 0);
                }


                case 2 -> {
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
                            case 1 -> prestataire.AjouterP();
                        }

                    } while (choixPrestataire != 0);
                }


                case 3 -> {
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
                            case 1 -> prestataire.AjouterFacture();
                            case 2 -> prestataire.modifieFacture();
                            case 3 -> prestataire.DeleteFature();
                            case 4 -> prestataire.afficherFacture();
                        }

                    } while (choixFacture != 0);
                }


                case 4 -> {
                    int choixPaiement;
                    do {
                        System.out.println("\n--- Section paiement ---");
                        System.out.println("1. Enregistrer");
                        System.out.println("2. Modifier");
                        System.out.println("3. Lister");
                        System.out.println("4. Paiement partiel");
                        System.out.println("0. Retour");

                        choixPaiement = sc.nextInt();

                        switch (choixPaiement) {
                            case 1 -> paiement.enregistrerPaiment(sc);
                            case 3 -> paiement.listerPaiement();
                            case 4 -> paiement.gererPaiementsPartiels();
                        }

                    } while (choixPaiement != 0);
                }


                case 5 -> {
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
                            case 1 -> statistique.calculateTotalPaidAmount();
                            case 2 -> statistique.calculateTotalCommissions();
                            case 3 -> statistique.getPaidInvoices();
                            case 4 -> statistique.getUnpaidInvoices();
                        }

                    } while (choixStatistique != 0);
                }
                case 6 :
                    FacturePDF.genererFacture();

                default -> System.out.println("Choix invalide ");
            }

        } while (choix1 != 0);
    }
}