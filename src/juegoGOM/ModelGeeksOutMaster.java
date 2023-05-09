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


  public void determinarJuego(MouseEvent e,JPanel panelDadosActivos,JPanel panelDadosUsados,JPanel panelDadosInactivos){
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
          flag = 1;
        }else {
          moverDados(e, panelDadosActivos, panelDadosUsados);
          dadoPoder = e.getComponent().getName();
          flag = 3;
          /*moverDados(e,panelDadosActivos,panelDadosUsados);

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
          panelDadosInactivos.revalidate();*/
        }
      }
    }
  }


  public void estadoPoder(MouseEvent e,JPanel panelDadosActivos,JPanel panelDadosInactivos){
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
    if (dadoPoder=="Corazon" && flag==3){
      Dado dadoCorazon = new Dado();
      String caraDado = dadoCorazon.getCara();
      e.getComponent().setName(caraDado);
      ImageIcon imageDado = new ImageIcon(getClass().getResource("/resources/" + caraDado + ".png"));
      ((JLabel) e.getSource()).setIcon(imageDado);
      moverDados(e,panelDadosInactivos,panelDadosActivos);
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
          Component[] componentesPuntuacion = panelTarjetaPuntuacion.getComponents();
          for (Component component : componentesPuntuacion) {
            panelTarjetaPuntuacion.remove(component);
            panelDadosActivos.add(component);
            panelTarjetaPuntuacion.revalidate();
            panelTarjetaPuntuacion.repaint();
            panelDadosActivos.revalidate();
            panelDadosActivos.repaint();
          }
          cambioDeRonda = true;
          puntaje = 0;
        }
        if (cantidadDragon == 0 && cantidad42 !=0 ){
          moverComponentes(panelDadosActivos,panelTarjetaPuntuacion,componentes);
          cambioDeRonda = true;
          verificarPuntaje(panelTarjetaPuntuacion);
          JOptionPane.showMessageDialog(null,"¡SUMAS PUNTOS!.\n"+
              "En Los Dados Restantes Hay "+cantidad42+" Caras 42.\n"+
              "Ahora tienes "+puntaje+" Puntos","¡SUMAS PUNTOS!",JOptionPane.INFORMATION_MESSAGE);
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

  public void verificarPuntaje(JPanel panelTarjetaPuntuacion){
    Component[] componentes = panelTarjetaPuntuacion.getComponents();
    switch (componentes.length){
      case 1: puntaje = 1;
        break;
      case 2:puntaje = 3;
        break;
      case 3:puntaje = 6;
        break;
      case 4:puntaje = 10;
        break;
      case 5:puntaje = 15;
        break;
      case 6:puntaje = 21;
        break;
      case 7:puntaje = 28;
        break;
      case 8:puntaje = 36;
        break;
      case 9:puntaje = 45;
        break;
      case 10:puntaje = 55;
        break;
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

}




