package game;

import java.util.*;

public class ExecutableConsole {
  static Plateau plateau1;
  static Plateau plateau2;
  static Plateau plateauCourant;
  static Scanner scanner;
  static Random rand;

  public static void main(String[] args) {
    System.out.print("\n-----------------Lancement du jeu de la bataille navale--------------------\n");

    //scanner
    Scanner scanner = new Scanner(System.in);
    //System.out.print("\nAppelez un joueur 'BOT' pour que ses tours soient joués automatiquements");
    System.out.print("\nEntrez le nom du joueur 1 : \n-> ");
    String nom1 = (String) scanner.nextLine();
    System.out.print("\nEntrez le nom du joueur 2 : \n-> ");
    String nom2 = (String) scanner.nextLine();
    //setup classes
    plateau1 = new Plateau(nom1, false);
    plateau2 = new Plateau(nom2, true);
    //System.out.print("\nBOT 1 : " + (nom1 == "BOT"));
    //System.out.print("\nBOT 2 : " + (nom2 == "BOT"));
    //System.out.print("\nNom 2 : '" + plateau2.GetNom() + "'");
    plateauCourant = plateau1;
    //plateau 1
    plateau1.AjouteBateau(plateau1.CreerBateau(5, 5));
    plateau1.AjouteBateau(plateau1.CreerBateau(4, 4));
    plateau1.AjouteBateau(plateau1.CreerBateau(3, 3));
    plateau1.AjouteBateau(plateau1.CreerBateau(3, 2));
    plateau1.AjouteBateau(plateau1.CreerBateau(2, 1));
    //plateau 2
    plateau2.AjouteBateau(plateau2.CreerBateau(5, 5));
    plateau2.AjouteBateau(plateau2.CreerBateau(4, 4));
    plateau2.AjouteBateau(plateau2.CreerBateau(3, 3));
    plateau2.AjouteBateau(plateau2.CreerBateau(3, 2));
    plateau2.AjouteBateau(plateau2.CreerBateau(2, 1));
    //hashmap 1
    plateau1.GetHashMap().AjouteValeur(5, 5);
    plateau1.GetHashMap().AjouteValeur(4, 4);
    plateau1.GetHashMap().AjouteValeur(3, 3);
    plateau1.GetHashMap().AjouteValeur(2, 3);
    plateau1.GetHashMap().AjouteValeur(1, 2);
    //hashmap 2
    plateau2.GetHashMap().AjouteValeur(5, 5);
    plateau2.GetHashMap().AjouteValeur(4, 4);
    plateau2.GetHashMap().AjouteValeur(3, 3);
    plateau2.GetHashMap().AjouteValeur(2, 3);
    plateau2.GetHashMap().AjouteValeur(1, 2);

    //new VueBatailleNavale(plateau1, plateau2);

    //affichage
    //System.out.print("" + plateau1.toString());
    //System.out.print("\n" + plateau2.toString());

    //tour
    NouveauTour(plateauCourant);
  }

  /**
  Fait jouer un nouveau tour au plateau en parametres
  @param plateau le plateau qui joue
  */
  static void NouveauTour(Plateau plateau)
  {
    //initialisation
    plateauCourant = plateau;
    System.out.print("\n\n-----------------" + plateau.GetNom() + " c'est à toi de jouer--------------------");
    Plateau plateauAdverse = plateau1;
    if (plateau == plateau1)
    {
      plateauAdverse = plateau2;
    }
    //System.out.print("\n" + plateauAdverse.affichagePlateau());
    System.out.print("\n" + affichagePlateaux());
    //bot
    Random rand = new Random();
    int x = rand.nextInt(9) + 1;
    int y = rand.nextInt(9) + 1;
    //joueur
    if (plateau.GetBot() == false)
    {
      scanner = new Scanner(System.in);
      System.out.print("\n\n\nEntrez la coordonnée X de votre tir : \n-> ");
      x = scanner.nextInt();
      System.out.print("\n\nEntrez la coordonnée Y de votre tir : \n-> ");
      y = scanner.nextInt();
    }
    //effets
    if (VerifCoordonnee(y, x, plateauAdverse) == false)
    {
      System.out.print("\n\nTir impossible en [" + x + "," + y + "], une erreur est survenue...");
      NouveauTour(plateau);
    }
    else
    {
      System.out.print("\n\nTir effectué en [" + x + "," + y + "]");
      int valeurCible = plateauAdverse.GetValeurGrilleBateaux(y-1, x-1);
      plateauAdverse.GetGrilleTirs().SetValeurGrille(y-1, x-1, 1);
      System.out.print("\n" + affichagePlateaux());
      if (valeurCible != 0)//bateau touché
      {
        int partiesRestantesSurBateauTouche = plateauAdverse.GetHashMap().GetValeur(valeurCible);
        plateauAdverse.GetHashMap().AjouteValeur(valeurCible, partiesRestantesSurBateauTouche - 1);

        if (partiesRestantesSurBateauTouche <= 1)
        {
          System.out.print("\n\nBateau adverse numero " + valeurCible + " coulé !!");
        }

        if (plateauAdverse.GetHashMap().EstVide())
        {
          System.out.print("\n\n\nGGs : " + plateau.GetNom() + " !!!");
          return;
        }
        else
        {
          System.out.print("\n\nTir reussi !!!");
          NouveauTour(plateau);
          return;
        }
      }
      else//tir loupé
      {
        System.out.print("\n\nTir loupé !!!");
        NouveauTour(plateauAdverse);
        return;
      }
    }
  }

  /**
  Verif si la coordonnéeest possible
  @param x la coordonnée x du bateau
  @param y la coordonnée y du bateau
  @param plateau le plateau à verifier
  @return bool
  */
  static boolean VerifCoordonnee(int x, int y, Plateau plateau)
  {
    if (x <= 0 || x >= 11)
    {
      return false;
    }
    if (y <= 0 || y >= 11)
    {
      return false;
    }

    System.out.print("\nvaleur grille tirs : " + plateau.GetGrilleTirs().GetValeurGrille(x-1, y-1));
    if (plateau.GetGrilleTirs().GetValeurGrille(x-1, y-1) == 1)
    {
      return false;
    }

    return true;
  }

  /**
  Méthode affichagePlateaux().
  @return string du plateau
  */
  static String affichagePlateaux()
  {
    String res = "\n   1 2 3 4 5 6 7 8 9 10           1 2 3 4 5 6 7 8 9 10";
    res += "\n   ___________________            ___________________";
    for (int i = 0; i < 10; i++)
    {
      if (i != 9)
      {
        res += "\n" + (i + 1) + " |";
      }
      else
      {
        res += "\n" + (i + 1) + "|";
      }

      for (int j = 0; j < 10; j++)
      {
        Integer valeurBateau = plateau1.GetValeurGrilleBateaux(i, j);
        Integer valeurTir = plateau1.GetGrilleTirs().GetValeurGrille(i, j);
        if (valeurTir != 0)
        {
          if (valeurBateau == 0)
          {
            if (j != 9)
            {
              res += "X ";
            }
            else
            {
              res += "X|          |";
            }
          }
          else
          {
            if (j != 9)
            {
              res += valeurBateau + " ";
            }
            else
            {
              res += valeurBateau + "|          |";
            }
          }
        }
        else
        {
          if (j != 9)
          {
            res += "~ ";
          }
          else
          {
            res += "~|          |";
            for (int w = 0; w < 10; w++)
            {
              valeurBateau = plateau2.GetValeurGrilleBateaux(i, w);
              valeurTir = plateau2.GetGrilleTirs().GetValeurGrille(i, w);
              if (valeurTir != 0)
              {
                if (valeurBateau == 0)
                {
                  if (w != 9)
                  {
                    res += "X ";
                  }
                  else
                  {
                    res += "X|";
                  }
                }
                else
                {
                  if (w != 9)
                  {
                    res += valeurBateau + " ";
                  }
                  else
                  {
                    res += valeurBateau + "|";
                  }
                }
              }
              else
              {
                if (w != 9)
                {
                  res += "~ ";
                }
                else
                {
                  res += "~|";
                }
              }
            }
          }
        }
      }
    }
    res += "\n         " + plateau1.GetNom() + "                         " + plateau2.GetNom();
    return res;
  }
}
