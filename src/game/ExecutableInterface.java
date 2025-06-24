package game;

public class ExecutableInterface {

  public static void main(String[] args) {

    // Initialisation du plateau du joueur
    Plateau plateauJoueur = new Plateau("Joueur", false);
    plateauJoueur.AjouteBateau(plateauJoueur.CreerBateau(5, 5));
    plateauJoueur.AjouteBateau(plateauJoueur.CreerBateau(4, 4));
    plateauJoueur.AjouteBateau(plateauJoueur.CreerBateau(3, 3));
    plateauJoueur.AjouteBateau(plateauJoueur.CreerBateau(3, 2));
    plateauJoueur.AjouteBateau(plateauJoueur.CreerBateau(2, 1));

    plateauJoueur.GetHashMap().AjouteValeur(5, 5);
    plateauJoueur.GetHashMap().AjouteValeur(4, 4);
    plateauJoueur.GetHashMap().AjouteValeur(3, 3);
    plateauJoueur.GetHashMap().AjouteValeur(2, 3);
    plateauJoueur.GetHashMap().AjouteValeur(1, 2);

    // Initialisation du plateau de l'adversaire
    Plateau plateauAdversaire = new Plateau("Bot", true);
    plateauAdversaire.AjouteBateau(plateauAdversaire.CreerBateau(5, 5));
    plateauAdversaire.AjouteBateau(plateauAdversaire.CreerBateau(4, 4));
    plateauAdversaire.AjouteBateau(plateauAdversaire.CreerBateau(3, 3));
    plateauAdversaire.AjouteBateau(plateauAdversaire.CreerBateau(3, 2));
    plateauAdversaire.AjouteBateau(plateauAdversaire.CreerBateau(2, 1));

    plateauAdversaire.GetHashMap().AjouteValeur(5, 5);
    plateauAdversaire.GetHashMap().AjouteValeur(4, 4);
    plateauAdversaire.GetHashMap().AjouteValeur(3, 3);
    plateauAdversaire.GetHashMap().AjouteValeur(2, 3);
    plateauAdversaire.GetHashMap().AjouteValeur(1, 2);

    VueBatailleNavale batailleNavale = new VueBatailleNavale(plateauJoueur, plateauAdversaire);
  }
}
