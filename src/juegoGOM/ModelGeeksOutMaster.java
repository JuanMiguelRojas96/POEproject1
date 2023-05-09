package juegoGOM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;


/**
 *ModelGeeksOutMaster aplica las reglas
 */
public class ModelGeeksOutMaster {
  private ArrayList<Dado> dados;
  private ArrayList<String> caras;
  private String dadoPoder;
  private int flag,puntaje,ronda;
  private boolean cambioDeRonda;




  public ModelGeeksOutMaster(){
    puntaje = 0;
    flag = 0;
    ronda = 1;
    cambioDeRonda = false;
    dadoPoder="";
    dados = new ArrayList<Dado>();
    for (int i = 0;i<10;i++){
      dados.add(new Dado());
    }
    caras = new ArrayList<String>();
    for (int i = 0;i<10;i++){
      caras.add("");
    }
  }

  /**
   * flag=
   * 0 = No se han tirado los dados
   * 1 = Tiro escoge poder
   * 2 = Activar poder
   */


  public void determinarJuego(MouseEvent e,JPanel panelDadosActivos,JPanel panelDadosUsados,JPanel panelDadosInactivos,JPanel panelTarjetaPuntuacion){
    if(e.getComponent().getName()!="42" && e.getComponent().getName()!="Dragon" && flag==1) {
      if (e.getComponent().getName() == "Meeple") {
        moverDados(e, panelDadosActivos, panelDadosUsados);
        dadoPoder = e.getComponent().getName();
        flag = 2;
      }

      if (e.getComponent().getName() == "Nave") {
        moverDados(e, panelDadosActivos, panelDadosUsados);
        dadoPoder = e.getComponent().getName();
        flag = 2;
      }
      if (e.getComponent().getName() == "SuperHeroe") {
        moverDados(e, panelDadosActivos, panelDadosUsados);
        dadoPoder = e.getComponent().getName();
        flag = 2;
      }
      if (e.getComponent().getName() == "Corazon") {
        if (panelDadosInactivos.getComponentCount() == 0) {
          JOptionPane.showMessageDialog(null, "Como escogiste un Corazón y el Panel de Dados Inactivos " +
              "está vacio, no tiene ningun efecto", "¡Está Vacio", JOptionPane.INFORMATION_MESSAGE);
          moverDados(e, panelDadosActivos, panelDadosUsados);
        }else {
          moverDados(e,panelDadosActivos,panelDadosUsados);

          Component[] components = panelDadosInactivos.getComponents();
          ArrayList<JLabel> dadosEnPanel = new ArrayList<>();
          for (Component component : components) {
            if (component instanceof JLabel) {
              dadosEnPanel.add((JLabel) component);
            }
          }
          Random random = new Random();
          int indice = random.nextInt(dadosEnPanel.size());
          JLabel labelSeleccionado = dadosEnPanel.get(indice);
          panelDadosInactivos.remove(labelSeleccionado);
          panelDadosActivos.add(labelSeleccionado);
          panelDadosActivos.repaint();
          panelDadosActivos.revalidate();
          panelDadosInactivos.repaint();
          panelDadosInactivos.revalidate();
        }
        flag = 1;
      }
    }
  }


  public void estadoPoder(MouseEvent e,JPanel panelDadosActivos,JPanel panelDadosUsados,JPanel panelDadosInactivos,JPanel panelTarjetaPuntuacion){
    if (dadoPoder=="Meeple" && flag==2 ){
      Dado dadoMeeple = new Dado();
      String caraDado = dadoMeeple.getCara();
      e.getComponent().setName(caraDado);
      ImageIcon imageDado = new ImageIcon(getClass().getResource("/resources/" + caraDado + ".png"));
      ((JLabel) e.getSource()).setIcon(imageDado);
      flag = 1;
    }

    if (dadoPoder=="Nave" && flag==2 ){
      moverDados(e,panelDadosActivos,panelDadosInactivos);
      flag = 1;

    }
    if (dadoPoder=="SuperHeroe" && flag==2 ){
      switch (e.getComponent().getName()){
        case "Meeple":setContraparte(e,"Nave");
          break;
        case "Nave":setContraparte(e,"Meeple");
          break;
        case "SuperHeroe":setContraparte(e,"Dragon");
          break;
        case "Dragon":setContraparte(e,"SuperHeroe");
          break;
        case "Corazon":setContraparte(e,"42");
          break;
        case "42":setContraparte(e,"Corazon");
          break;
      }
      flag = 1;

    }
  }

  public void setContraparte(MouseEvent e,String caraOpuesta){
    e.getComponent().setName(caraOpuesta);
    ImageIcon imageDado = new ImageIcon(getClass().getResource("/resources/"+caraOpuesta+".png"));
    ((JLabel) e.getSource()).setIcon(imageDado);
  }
  public void moverDados(MouseEvent e,JPanel actualContenedor,JPanel nuevoContenedor){
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    actualContenedor.remove(e.getComponent());
    nuevoContenedor.add(e.getComponent(),gbc);
    actualContenedor.revalidate();
    actualContenedor.repaint();
    nuevoContenedor.revalidate();
    nuevoContenedor.repaint();
  }

  public void verificarPanel(MouseEvent e,JPanel panelDadosActivos,JPanel panelDadosUsados,JPanel panelDadosInactivos,JPanel panelTarjetaPuntuacion){
    Component[] componentes = panelDadosActivos.getComponents();
    if (componentes.length == 0){
      JOptionPane.showMessageDialog(null,"¡NO SUMAS PUNTOS!.\n"+
          "No Ha Quedado Ningún Dado","¡NO SUMAS PUNTOS!",JOptionPane.INFORMATION_MESSAGE);
      cambioDeRonda = true;
    }
    if(flag==1){
      int cantidad42 = 0;
      int cantidadDragon = 0;
      ArrayList<Component> dados42 = new ArrayList<>();
      dados42.clear();
      for (Component componente : componentes) {
        JLabel label = (JLabel) componente;
        if (label.getName().equals("42")){
          cantidad42 += 1;
          dados42.add(componente);
        }
        if (label.getName().equals("Dragon")){
          cantidadDragon += 1;
        }
      }
      if (componentes.length == cantidadDragon+cantidad42){
        if (cantidadDragon != 0){
          JOptionPane.showMessageDialog(null,"¡PERDISTE!.\n"+
              "En Los Dados Restantes Hay "+cantidadDragon+" Dragones","¡Hay Un Dragón!",JOptionPane.INFORMATION_MESSAGE);
          cambioDeRonda = true;
          puntaje = 0;
        }
        if (cantidadDragon == 0 && cantidad42 !=0 ){
          switch (cantidad42){
            case 1 :JOptionPane.showMessageDialog(null,"¡SUMAS PUNTOS!.\n"+
                "En Los Dados Restantes Hay "+cantidad42+" Caras 42.\n"+
                "Sumas 1 Punto","¡SUMAS PUNTOS!",JOptionPane.INFORMATION_MESSAGE);
              puntaje += 1;
              cambioDeRonda = true;
              moverComponentes(panelDadosActivos,panelTarjetaPuntuacion,componentes);
              break;
            case 2 :JOptionPane.showMessageDialog(null,"¡SUMAS PUNTOS!.\n"+
                "En Los Dados Restantes Hay "+cantidad42+" Caras 42.\n"+
                "Sumas 3 Punto","¡SUMAS PUNTOS!",JOptionPane.INFORMATION_MESSAGE);
              puntaje += 3;
              cambioDeRonda = true;
              moverComponentes(panelDadosActivos,panelTarjetaPuntuacion,componentes);

              break;
            case 3 :JOptionPane.showMessageDialog(null,"¡SUMAS PUNTOS!.\n"+
                "En Los Dados Restantes Hay "+cantidad42+" Caras 42.\n"+
                "Sumas 6 Punto","¡SUMAS PUNTOS!",JOptionPane.INFORMATION_MESSAGE);
              puntaje += 6;
              cambioDeRonda = true;
              moverComponentes(panelDadosActivos,panelTarjetaPuntuacion,componentes);
              break;
            case 4 :JOptionPane.showMessageDialog(null,"¡SUMAS PUNTOS!.\n"+
                "En Los Dados Restantes Hay "+cantidad42+" Caras 42.\n"+
                "Sumas 10 Punto","¡SUMAS PUNTOS!",JOptionPane.INFORMATION_MESSAGE);
              puntaje += 10;
              cambioDeRonda = true;
              moverComponentes(panelDadosActivos,panelTarjetaPuntuacion,componentes);
              break;
            case 5 :JOptionPane.showMessageDialog(null,"¡SUMAS PUNTOS!.\n"+
                "En Los Dados Restantes Hay "+cantidad42+" Caras 42.\n"+
                "Sumas 15 Punto","¡SUMAS PUNTOS!",JOptionPane.INFORMATION_MESSAGE);
              puntaje += 15;
              cambioDeRonda = true;
              moverComponentes(panelDadosActivos,panelTarjetaPuntuacion,componentes);
              break;
            case 6 :JOptionPane.showMessageDialog(null,"¡SUMAS PUNTOS!.\n"+
                "En Los Dados Restantes Hay "+cantidad42+" Caras 42.\n"+
                "Sumas 21 Punto","¡SUMAS PUNTOS!",JOptionPane.INFORMATION_MESSAGE);
              puntaje += 21;
              cambioDeRonda = true;
              moverComponentes(panelDadosActivos,panelTarjetaPuntuacion,componentes);
              break;
            case 7 :JOptionPane.showMessageDialog(null,"¡SUMAS PUNTOS!.\n"+
                "En Los Dados Restantes Hay "+cantidad42+" Caras 42.\n"+
                "Sumas 28 Punto","¡SUMAS PUNTOS!",JOptionPane.INFORMATION_MESSAGE);
              puntaje += 28;
              cambioDeRonda = true;
              moverComponentes(panelDadosActivos,panelTarjetaPuntuacion,componentes);
              break;
            case 8 :JOptionPane.showMessageDialog(null,"¡SUMAS PUNTOS!.\n"+
                "En Los Dados Restantes Hay "+cantidad42+" Caras 42.\n"+
                "Sumas 36 Punto","¡SUMAS PUNTOS!",JOptionPane.INFORMATION_MESSAGE);
              puntaje += 36;
              cambioDeRonda = true;
              moverComponentes(panelDadosActivos,panelTarjetaPuntuacion,componentes);
              break;
            case 9 :JOptionPane.showMessageDialog(null,"¡SUMAS PUNTOS!.\n"+
                "En Los Dados Restantes Hay "+cantidad42+" Caras 42.\n"+
                "Sumas 45 Punto","¡SUMAS PUNTOS!",JOptionPane.INFORMATION_MESSAGE);
              puntaje += 45;
              cambioDeRonda = true;
              moverComponentes(panelDadosActivos,panelTarjetaPuntuacion,componentes);
              break;
            case 10 :JOptionPane.showMessageDialog(null,"¡SUMAS PUNTOS!.\n"+
                "En Los Dados Restantes Hay "+cantidad42+" Caras 42.\n"+
                "Sumas 55 Punto","¡SUMAS PUNTOS!",JOptionPane.INFORMATION_MESSAGE);
              puntaje += 55;
              cambioDeRonda = true;
              moverComponentes(panelDadosActivos,panelTarjetaPuntuacion,componentes);
              break;
          }
        }
      }
    }
  }
  public void lanzarDados() {
    for(int i = 0; i<10;i++){
      caras.set(i,dados.get(i).getCara());
    }
    flag = 1;
  }
  public void moverComponentes(JPanel panelActual, JPanel panelDestino, Component[] componentes) {
    for (Component component : componentes) {
      panelActual.remove(component);
      panelDestino.add(component);
      panelActual.revalidate();
      panelActual.repaint();
      panelDestino.revalidate();
      panelDestino.repaint();
    }
  }

  public boolean getCambioDeRonda(){
    return cambioDeRonda;
  }

  public void setCambioDeRonda(boolean bool){
    cambioDeRonda = bool;
  }

  public ArrayList<String> getCaras(){
    return caras;
  }

  public int getFlag() {
    return flag;
  }

  public int getPuntaje() {
    return puntaje;
  }

  public int getRonda() {
    return ronda;
  }
  public void cambiarRonda(){
    ronda += 1;
  }

  public void setFlag(int i){
    flag = i;
  }

  public String getDadoPoder(){
    return dadoPoder;
  }
}




