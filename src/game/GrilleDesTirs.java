package game;

public class GrilleDesTirs
{
  private int[][] grille;

  /**
  Constructeur de la classe GrilleDesTirs
  */
  public GrilleDesTirs()
  {
    this.grille = new int[10][];
    for (int i = 0; i < 10; i++)
    {
      this.grille[i] = new int[10];
    }
  }

  public int[][] GetGrille(){return this.grille;}
  /**
  l'attribut grille prend la valeur en parametres suivant les coordonnnes aussi en paramètres
  @param x la coordonnnee x
  @param y la coordonnnee y
  @param val la valeur
  */
  public void SetValeurGrille(int x, int y, int valeur){this.grille[x][y] = valeur;}
  public int GetValeurGrille(int x, int y){return this.grille[x][y];}

  /**
  Renvoie l'affichage de la grille de tir
  @return string
  */
  @Override
  public String toString()
  {
    String res = "\n";
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
        res += this.grille[i][j] + " ";
      }
    }
    return res;
  }
}
