// Représente une carte à jouer dans un jeu de Blackjack.

public class Carte {
    private String couleur; // Ex : "Coeur", "Carreau", "Trèfle", "Pique"
    private String valeur;  // Ex : "As", "2", ..., "10", "Valet", "Dame", "Roi"
    
    // Constructeur de la carte.
    public Carte(String valeur, String couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
    }

    //Retourne la valeur de la carte (ex : "As", "10", "Dame", etc.)
    public String getValeur() {
        return valeur;
    }

    //Retourne la couleur de la carte (ex : "coeur", "Trèfle", etc.)
    public String getCouleur() {
        return couleur;
    }

    /**
     * Retourne la valeur de la carte en points pour le Blackjack.
     * - Les cartes numériques valent leur nombre (2 à 10)
     * - Les figures (Valet, Dame, Roi) valent 10 points
     * - L'As vaut 11 par défaut (on ajustera selon le contexte plus tard)
     */
    public int getValeurBlackjack() {
        switch (valeur) {
            case "As":
                return 11;
            case "Roi":
            case "Dame":
            case "Valet":
                return 10;
            default:
                return Integer.parseInt(valeur); // Pour les cartes "2" à "10"
        }
    }

    //Représentation textuelle de la carte (ex : "As de Cœur")
    @Override
    public String toString() {
        return valeur + " de " + couleur;
    }
}
