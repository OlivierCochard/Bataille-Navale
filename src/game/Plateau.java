package game;

import java.util.ArrayList;
import java.util.Random;

public class Plateau
{
  private String nom;
  private int[][] grille_bateaux;
  private GrilleDesTirs grille_tirs;
  private ArrayList<Bateau> listeBateaux;
  private HashMapBateaux hashmap;
  private boolean bot;

  /**
  Constructeur de la classe Plateau
  @param nom le nom du plateau
  @param bot le fait que le plateau soit un bot ou non
  */
  public Plateau(String nom, boolean bot)
  {
    this.bot = bot;
    this.nom = nom;
    this.listeBateaux = new ArrayList<Bateau>();
    this.grille_bateaux = new int[10][];
    for (int i = 0; i < 10; i++)
    {
      this.grille_bateaux[i] = new int[10];
    }
    this.grille_tirs = new GrilleDesTirs();
    this.hashmap = new HashMapBateaux();
  }

  public String GetNom(){return this.nom;}
  public int[][] GetGrilleBateaux(){return this.grille_bateaux;}
  public GrilleDesTirs GetGrilleTirs(){return this.grille_tirs;}
  public HashMapBateaux GetHashMap(){return this.hashmap;}
  public int GetValeurGrilleBateaux(int x, int y){return this.grille_bateaux[x][y];}
  public ArrayList<Bateau> GetListeBateaux(){return this.listeBateaux;}
  public boolean GetBot(){return this.bot;}

  /**
  Renvoie un bateau créer de facon reflechi pour qu'il passe dans la grille sans chevaucher les autres bateaux, de facon aléatoire
  @param taille la taille
  @param valeurAffichage le numero
  @return Bateau
  */
  public Bateau CreerBateau(int taille, int valeurAffichage)
  {
    Random rand = new Random();
    int posX = rand.nextInt(10 - taille);
    int posY = rand.nextInt(10 - taille);
    int orientation = rand.nextInt(2);

    if (orientation == 0)
    {
      for (int i = posX; i < posX + taille; i++)
      {
        if (this.grille_bateaux[i][posY] != 0)
        {
          return CreerBateau(taille, valeurAffichage);
        }
      }
      Bateau garde = new Bateau(taille, posX, posY, orientation, valeurAffichage);
      this.listeBateaux.add(garde);
      return garde;
    }
    else
    {
      for (int i = posY; i < posY + taille; i++)
      {
        if (this.grille_bateaux[posX][i] != 0)
        {
          return CreerBateau(taille, valeurAffichage);
        }
      }
      Bateau garde = new Bateau(taille, posX, posY, orientation, valeurAffichage);
      this.listeBateaux.add(garde);
      return garde;
    }
  }
  /**
  Ajoute un bateau au plateau
  @param bateau le bateau
  */
  public void AjouteBateau(Bateau bateau)
  {
    int taille = bateau.GetTaille();
    int posX = bateau.GetPosX();
    int posY = bateau.GetPosY();
    int orientation = bateau.GetOrientation();
    int valeurAffichage = bateau.GetValeur();

    if (orientation == 0)
    {
      for (int i = posX; i < posX + taille; i++)
      {
        this.grille_bateaux[i][posY] = valeurAffichage;
      }
    }
    else
    {
      for (int i = posY; i < posY + taille; i++)
      {
        this.grille_bateaux[posX][i] = valeurAffichage;
      }
    }
  }

  /**
  Renvoie l'affichage du plateau
  @return string
  */
  @Override
  public String toString()
  {
    String res = "\nBateaux d'" + GetNom() + " :";
    res += "\n    1 2 3 4 5 6 7 8 9 10";
    res += "\n    - - - - - - - - - -";
    for (int i = 0; i < 10; i++)
    {
      if (i != 9)
      {
        res += "\n" + (i + 1) + " - ";
      }
      else
      {
        res += "\n" + (i + 1) + "- ";
      }

      for (int j = 0; j < 10; j++)
      {
        res += this.grille_bateaux[i][j] + " ";
      }
    }
    return res;
  }
  /**
  Renvoie l'affichage du plateau
  @return string
  */
  public String affichagePlateau()
  {
    String res = "\n   1 2 3 4 5 6 7 8 9 10";
    res += "\n   ___________________";
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
        Integer valeurBateau = this.grille_bateaux[i][j];
        Integer valeurTir = this.grille_tirs.GetValeurGrille(i, j);
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
              res += "X|";
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
              res += valeurBateau + "|";
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
            res += "~|";

            if (i == 2)
            {
              res += "  Les differents bateaux :";
            }
            else if (i == 3)
            {
              res += "  5 -> taille 5";
            }
            else if (i == 4)
            {
              res += "  4 -> taille 4";
            }
            else if (i == 5)
            {
              res += "  3 -> taille 3";
            }
            else if (i == 6)
            {
              res += "  2 -> taille 3";
            }
            else if (i == 7)
            {
              res += "  1 -> taille 2";
            }
          }
        }
      }
    }
    return res;
  }
  /**
  Renvoie si le bateau est possible
  @param x la coordonnée x
  @param y la coordonnée y
  @param orientation l'orientation
  @param taille la taille
  @return bool
  */
  public boolean PlacementPossible(int x, int y, int orientation, int taille) {
    if (orientation == 0) {
      if (x + taille > 10) {
        return false;
      }
      for (int i = 0; i < taille; i++) {
        if (grille_bateaux[x + i][y] != 0) {
          return false;
        }
      }
    } else {
      if (y + taille > 10) {
        return false;
      }
      for (int i = 0; i < taille; i++) {
        if (grille_bateaux[x][y + i] != 0) {
          return false;
        }
      }
    }
    return true;
  }
  /**
  Renvoie si a coordonnee est tirable ou non
  @param x la coordonnée x
  @param y la coordonnée y
  @return bool
  */
  public boolean Tirer(int x, int y) {
    if (grille_tirs.GetGrille()[x][y] != 0) {
      return false;
    }
    grille_tirs.SetValeurGrille(x, y, 1);
    return true;
  }

  /**
  Renvoie si la coordonnnee est coulé ou non
  @param x la coordonnée x
  @param y la coordonnée y
  @return bool
  */
  public boolean EstCoule(int x, int y) {
    // Recherche du bateau touché à la position (x,y)
    Bateau bateau = null;
    for (Bateau b : this.GetListeBateaux()) {
        if (this.GetValeurGrilleBateaux(x, y) == b.GetValeur()) {
            bateau = b;
            break;
        }
    }

    if (bateau == null) {
        return false;
    }

    // Vérification si toutes les parties du bateau ont été touchées
    for (int i = 0; i < bateau.GetTaille(); i++) {
        int posX = bateau.GetOrientation() == 0 ? bateau.GetPosX() + i : bateau.GetPosX();
        int posY = bateau.GetOrientation() == 1 ? bateau.GetPosY() + i : bateau.GetPosY();
        if (grille_tirs.GetGrille()[posX][posY] == 0) {
            return false;
        }
    }
    bateau.SetIsCoule();
    return true;
  }

  /**
  Renvoie si le plateau n'a plus de bateaux
  @return bool
  */
  public boolean EstFini() {
    for (Bateau bateau : this.GetListeBateaux()) {
      if (!bateau.GetIsCoule()) {
        return false;
      }
    }
    return true;
  }

}
