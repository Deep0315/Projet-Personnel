import java.util.ArrayList;
import java.util.List;

//Représente le croupier dans une partie de Blackjack.

public class Croupier {
    private List<Carte> main; // Les cartes du croupier

    //Constructeur : initialise la main vide.
    public Croupier() {
        this.main = new ArrayList<>();
    }

    //Vide la main du croupier (début de nouvelle manche).
    public void viderMain() {
        main.clear();
    }

    //Ajoute une carte à la main du croupier.
    public void ajouterCarte(Carte carte) {
        main.add(carte);
    }
    
    //Calcule et retourne le total de points de la main (gère l'As comme 1 ou 11).
    public int calculerPoints() {
        int total = 0;
        int nbAs = 0;

        for (Carte carte : main) {
            int valeur = carte.getValeurBlackjack();
            total += valeur;
            if (carte.getValeur().equals("As")) {
                nbAs++;
            }
        }

        // Gère le cas des As : si total > 21, on transforme les As (11) en 1
        while (total > 21 && nbAs > 0) {
            total -= 10;
            nbAs--;
        }
        return total;
    }

    //Affiche la main du croupier en console.
    public void afficherMain() {
        System.out.println("le croupier a les cartes suivantes :");
        for (Carte carte : main) {
            System.out.println(" - " + carte.toString());
        }
        System.out.println("Total de points : " + calculerPoints());
    }

    public List<Carte> getMain() {
        return main;
    }

    public void jouerTour(Paquet paquet) {
        while (calculerPoints() < 17) {
            ajouterCarte(paquet.tirerCarte());
        }
    }

    // Vérifie si le croupier a un Blackjack naturel (As + 10 dès les 2 premières cartes)
    public boolean aBlackjack() {
        if (main.size() == 2) {
            int points = calculerPoints();
            return points == 21;
        }
        return false;
    }
    
}