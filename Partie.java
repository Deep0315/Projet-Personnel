import java.util.Scanner;

// Gère le déroulement complet d'une partie de Blackjack.
public class Partie {
    private Joueur joueur;
    private Croupier croupier;
    private Paquet paquet;
    private Scanner scanner;

    private int victoires = 0;
    private int defaites = 0;
    private int egalites = 0;

    // Constructeur : initialise les éléments de la partie.
    public Partie() {
        scanner = new Scanner(System.in);
        System.out.print("Entrez votre nom : ");
        String nom = scanner.nextLine();
        joueur = new Joueur(nom, 100); // 100 jetons de départ
        croupier = new Croupier();
        paquet = new Paquet();
    }

    // Lance une boucle de jeu avec plusieurs manches.
    public void demarrer() {
        System.out.println("\nBienvenue au Blackjack, " + joueur.getNom() + " !");

        boolean continuer = true;

        while (continuer && joueur.getArgent() > 0) {
            System.out.println("\n====================== NOUVELLE MANCHE ======================");
            System.out.println("Jetons actuels : " + joueur.getArgent());
            System.out.println("-------------------------------------------------------------");

            System.out.print("Entrez votre mise : ");
            int mise = scanner.nextInt();
            scanner.nextLine(); // Nettoyer la ligne

            if (mise <= 0 || mise > joueur.getArgent()) {
                System.out.println("Mise invalide. Manche annulée.");
                continue;
            }

            // Préparation de la manche
            joueur.viderMain();
            croupier.viderMain();
            paquet.melanger();

            // Distribution initiale
            joueur.ajouterCarte(paquet.tirerCarte());
            joueur.ajouterCarte(paquet.tirerCarte());
            croupier.ajouterCarte(paquet.tirerCarte());
            croupier.ajouterCarte(paquet.tirerCarte());

            // Affichage
            joueur.afficherMain();
            System.out.println("Carte visible du croupier : " + croupier.getMain().get(0));
            System.out.println("-------------------------------------------------------------");

            // Vérifie les Blackjacks naturels
            boolean joueurBJ = joueur.aBlackjack();
            boolean croupierBJ = croupier.aBlackjack();

            if (joueurBJ || croupierBJ) {
                System.out.println("\nVérification des Blackjacks naturels...");

                if (joueurBJ && croupierBJ) {
                    System.out.println("Égalité ! Vous et le croupier avez un Blackjack naturel.");
                    System.out.println("Votre mise vous est rendue.");
                    egalites++;
                } else if (joueurBJ) {
                    int gain = (int) (mise * 1.5);
                    System.out.println("Blackjack naturel ! Vous gagnez 1,5x votre mise : +" + gain + " jetons.");
                    joueur.modifierArgent(gain);
                    victoires++;
                } else {
                    System.out.println("Le croupier a un Blackjack naturel. Vous perdez votre mise.");
                    joueur.modifierArgent(-mise);
                    defaites++;
                }

                System.out.println("=============================================================");
                System.out.print("Voulez-vous rejouer ? (o/n) : ");
                String reponse = scanner.nextLine().toLowerCase();
                continuer = reponse.equals("o");
                continue;
            }

            // Tour du joueur
            boolean joueurAReste = false;
            while (!joueurAReste && joueur.calculerPoints() < 21) {
                System.out.print("Voulez-vous (t)irer, (r)ester ou (d)oubler ? ");
                String choix = scanner.nextLine().toLowerCase();

                if (choix.equals("t")) {
                    Carte nouvelleCarte = paquet.tirerCarte();
                    joueur.ajouterCarte(nouvelleCarte);
                    System.out.println("Vous avez tiré : " + nouvelleCarte);
                    joueur.afficherMain();
                    System.out.println("-------------------------------------------------------------");
                } else if (choix.equals("r")) {
                    joueurAReste = true;
                } else if (choix.equals("d")) {
                    if (joueur.getArgent() >= mise * 2) {
                        mise *= 2;
                        Carte nouvelleCarte = paquet.tirerCarte();
                        joueur.ajouterCarte(nouvelleCarte);
                        System.out.println("Vous doublez votre mise et tirez une seule carte : " + nouvelleCarte);
                        joueur.afficherMain();
                        joueurAReste = true;
                        System.out.println("-------------------------------------------------------------");
                    } else {
                        System.out.println("Vous n'avez pas assez de jetons pour doubler.");
                    }
                } else {
                    System.out.println("Choix invalide.");
                }
            }


            int pointsJoueur = joueur.calculerPoints();

            if (pointsJoueur > 21) {
                System.out.println("Vous avez dépassé 21 ! Vous perdez " + mise + " jetons.");
                joueur.modifierArgent(-mise);
                defaites++;
            } else {
                // Tour du croupier
                System.out.println("\n====================== TOUR DU CROUPIER ======================");
                croupier.jouerTour(paquet);
                croupier.afficherMain();
                System.out.println("-------------------------------------------------------------");

                int pointsCroupier = croupier.calculerPoints();

                if (pointsCroupier > 21 || pointsJoueur > pointsCroupier) {
                    System.out.println("Vous gagnez ! + " + mise + " jetons");
                    joueur.modifierArgent(mise);
                    victoires++;
                } else if (pointsCroupier > pointsJoueur) {
                    System.out.println("Le croupier gagne. - " + mise + " jetons");
                    joueur.modifierArgent(-mise);
                    defaites++;
                } else {
                    System.out.println("Égalité ! Vous récupérez votre mise.");
                    egalites++;
                }
            }

            System.out.println("=============================================================");
            System.out.print("Voulez-vous rejouer ? (o/n) : ");
            String reponse = scanner.nextLine().toLowerCase();
            continuer = reponse.equals("o");
        }

        System.out.println("\n====================== FIN DE LA PARTIE ======================");
        System.out.println("Bilan de la session :");
        System.out.println("- VICTOIRES : " + victoires);
        System.out.println("- DÉFAITES  : " + defaites);
        System.out.println("- ÉGALITÉS  : " + egalites);
        System.out.println("-------------------------------------------------------------");
        System.out.println("Jetons finaux : " + joueur.getArgent());
        System.out.println("Merci d'avoir joué, " + joueur.getNom() + " !");
    }
}
