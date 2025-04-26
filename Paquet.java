import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Représente un paquet de cartes utilisé dans une partie de Blackjack.

public class Paquet {
    private List<Carte> cartes; // Liste de toutes les cartes du paquet

    //Constructeur : crée un paquet complet de 52 cartes et le mélange.
    public Paquet() {
        cartes = new ArrayList<>();
        initialiser();
        melanger();
    }

    //Initialise le paquet avec 52 cartes (sans joker).
    private void initialiser() {
        String[] couleurs = {"Coeur", "Carreau", "Trèfle", "Pique"};
        String[] valeurs = {"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valet", "Dame", "Roi"};
        for (String couleur : couleurs) {
            for (String valeur : valeurs) {
                cartes.add(new Carte(valeur, couleur));
            }
        }
    }

    //Mélange les cartes du paquet.
    public void melanger() {
        Collections.shuffle(cartes);
    }

    //Tire une carte du dessus du paquet (et la retire du paquet).
    public Carte tirerCarte() {
        if (cartes.isEmpty()) {
            System.out.println("Le paquet est vide. Réinitialisation automatique.");
            initialiser();
            melanger();
        }
        return cartes.remove(0); // Retire et retourne la première carte
    }

    //Renvoie le nombre de cartes restantes dans le paquet.
    public int taille() {
        return cartes.size();
    }
}
