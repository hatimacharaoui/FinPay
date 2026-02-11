import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Client client = new Client(1,"zakaria", "zakaria@enaa.ma", "+212 607523048");

        int choiceMenu = 0;

        do {
            System.out.println("app FinPay");
            System.out.println("1. Ajoute Client");
            System.out.println("2. Modifier Client");
            System.out.println("3. supprime Client");
            System.out.println("4. desply Client");
            System.out.println("5. Rechercher Client");
            System.out.println("0. se déconnecter");

            System.out.println("Entrez le choice: ");
            choiceMenu = input.nextInt();
            input.nextLine();

            switch (choiceMenu){
                case 1:
                    client.AjouterClient(input);
                    break;
                case 2:
                    client.ModifierClient(input);
                    break;
                case 3:
                    client.supprimeClient(input);
                    break;
                case 4:
                    client.listerClient();
                    break;
                case 5:
                    client.RechercherClient(input);
                    break;
                case 0:
                    System.out.println("À bientôt");
                    break;
                default:
                    System.out.println("Invalid input");
            }

        }while (choiceMenu!=0);


    }
}