import java.util.ArrayList;
import java.util.List;

// Représente le joueur dans une partie de Blackjack.

public class Joueur {
    private List<Carte> main; // Les cartes en main
    private int argent;       // L'argent du joueur (sert de score)
    private String nom;

    // Constructeur du joueur avec un nom et un montant d'argent initial.
    public Joueur(String nom, int argentInitial) {
        this.nom = nom;
        this.argent = argentInitial;
        this.main = new ArrayList<>();
    }

    //Ajoute une carte à la main du joueur.
    public void ajouterCarte(Carte carte) {
        main.add(carte);
    }

    //Vide la main du joueur (début de nouvelle manche).
    public void viderMain() {
        main.clear();
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

    //Affiche la main du joueur en console.
    public void afficherMain() {
        System.out.println(nom + " a les cartes suivantes :");
        for (Carte carte : main) {
            System.out.println(" - " + carte.toString());
        }
        System.out.println("Total de points : " + calculerPoints());
    }

    //Retourne l'argent du joueur.
    public int getArgent() {
        return argent;
    }

    //Modifie l'argent du joueur (positif = gain, négatif = perte).
    public void modifierArgent(int montant) {
        argent += montant;
    }

    //Retourne le nom du joueur.
    public String getNom() {
        return nom;
    }

    //Retourne la main du joueur (utile pour certaines règles).
    public List<Carte> getMain() {
        return main;
    }

    // Vérifie si le joueur a un Blackjack naturel (As + 10 dès les 2 premières cartes)
    public boolean aBlackjack() {
        if (main.size() == 2) {
            int points = calculerPoints();
            return points == 21;
        }
        return false;
    }

    
}
