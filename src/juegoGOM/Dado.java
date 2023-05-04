package juegoGOM;


import java.util.Random;

public class Dado {

  private String[]  cara = {"Meeple","Nave","SuperHeroe","Corazon","Dragon","42"};

  /**
   *getCara crea un aleatorio que devuelve un numero entre 0 y 5, y despues lo pasa como indice del Arreglo cara.
   * @return retorna la cara del dado como String.
   */

  public String getCara(){
    Random aleatorio = new Random();
    int indice = aleatorio.nextInt(cara.length);
    String caraDelDado = cara[indice];

    return caraDelDado;
  }

}



