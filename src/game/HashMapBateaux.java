package game;

import java.util.HashMap;
import java.util.Map;

public class HashMapBateaux
{
  private HashMap<Integer, Integer> hashmap;

  /**
  Constructeur de la classe HashMapBateaux
  */
  public HashMapBateaux()
  {
    hashmap = new HashMap<Integer, Integer>();
  }

  public HashMap<Integer, Integer> GetHashMap(){return this.hashmap;}
  /**
  Ajoute une valeur au HashMap : hashmap
  @param numero le numero
  @param quantite la quantite
  */
  public void AjouteValeur(Integer numero, Integer quantite){this.hashmap.put(numero, quantite);}
  public Integer GetValeur(Integer numero){return this.hashmap.get(numero);}
  /**
  Renvoie si le dico est vide ou non
  @return boolean
  */
  public boolean EstVide()
  {
    for (Map.Entry dico : this.hashmap.entrySet())
    {
      Integer valeur = (Integer) dico.getValue();
      if (valeur != 0)
      {
        return false;
      }
    }
    return true;
  }
}
