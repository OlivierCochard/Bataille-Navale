package game;

public class Bateau
{
  private int taille;
  private int posX;
  private int posY;
  private int orientation;
  private int valeur;
  private boolean isCoule;

  /**
  Constructeur de la classe Bateau.
  @param taille la taille du bateau
  @param posX la position x du bateau
  @param posY la position y du bateau
  @param orientation l'orientation de bateau
  @param valeur le numero du bateau
  */
  public Bateau(int taille, int posX, int posY, int orientation, int valeur)
  {
    this.taille = taille;
    this.posX = posX;
    this.posY = posY;
    this.orientation = orientation;
    this.valeur = valeur;
    this.isCoule = false;
  }

  public int GetTaille(){return this.taille;}
  public int GetPosX(){return this.posX;}
  public int GetPosY(){return this.posY;}
  public int GetOrientation(){return this.orientation;}
  public int GetValeur(){return this.valeur;}
  public boolean GetIsCoule(){return this.isCoule;}

  /**
  l'attribut posX prend la valeur en parametres
  @param val la valeur
  */
  public void SetPosX(int val){this.posX = val;}
  /**
  l'attribut posY prend la valeur en parametres
  @param val la valeur
  */
  public void SetPosY(int val){this.posY = val;}
  /**
  l'attribut orientation prend la valeur en parametres
  @param val la valeur
  */
  public void SetOrientation(int val){this.orientation = val;}
  /**
  l'attribut isCoule prend la valeur false
  */
  public void SetIsCoule(){this.isCoule = true;}
}
