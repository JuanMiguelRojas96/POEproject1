package juegoGOM;


import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class GeeksOutMasterGUI extends JFrame{

  public static final String MENSAJE_AYUDA = "El juego ¡Geek Out Masters consiste! en conseguir la mayor cantidad de puntos juntando dados cuya cara visible es la cara 42.\n"
      + "El juego no depende solo de la suerte, también es importante tener una estrategia ya que una vez que se lanzan los dados, todas las caras deben ser ejecutadas.\n\n"
      + "Los dados tienen seis caras, cada una con un dibujo que permite al jugador realizar una acción especial:\n\n"
      + "*El Meeple permite relanzar otro dado en juego de la sección de dados activos.\n"
      + "*La Nave Espacial envía un dado no usado de la sección de dados activos a la sección de dados inactivos.\n"
      + "*El Superhéroe permite que cualquier dado no usado de la sección de dados activos sea volteado y colocado en su cara opuesta.\n"
      + "*El Corazón permite tomar un dado de la sección de dados inactivos y lanzarlo para que sea un nuevo dado activo.\n"
      + "*El Dragón es la cara que se quiere evitar, ya que si al final de la ronda es el último dado activo que queda, se perderán todos los puntos ganados y acumulados.\n"
      + "*La Cara 42 es la que permite sumar puntos al final de la ronda.\n\n"
      + "Además, cada cara del dado tiene una cara opuesta que corresponde a su color. Por ejemplo, la cara opuesta al Corazón es el 42, ya que tienen el mismo color (rojo);\n"
      + "la cara opuesta al Meeple es la Nave Espacial, y la cara opuesta al Superhéroe es el Dragón.\n\n"
      + "El juego está compuesto por 10 dados de Geek Out!, 1 ayuda memoria y 1 tarjeta de puntuación. El área de juego está compuesta por 4 sectores:\n"
      + "los dados activos, los dados inactivos, los dados utilizados y la tarjeta de puntuación.\n"
      + "El jugador debe usar estratégicamente los dados para obtener la mayor cantidad de puntos y evitar perderlos si el último dado activo es un Dragón.";
  private Header headerProject;
  private JLabel puntaje;
  private ArrayList<JLabel> dados;
  private JButton lanzar,ayuda,salir,reiniciar;
  private Escucha escucha;
  private JPanel panelDadosActivos,panelDadosInactivos,panelDadosUsados,panelTarjetaPuntuacion;
  private ImageIcon imageDado, background;
  private Image image;

  private ModelGeeksOutMaster modelGeeksOutMaster;



  public GeeksOutMasterGUI(){
    initGUI();

    this.setTitle("Geeks Out Master Game");
    this.setUndecorated(true);
    this.pack();
    this.setResizable(true);
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }


  private void setGridConstraint(Component component  ,int gridx,int gridy,int gridwidth,int fill,int anchor){
    GridBagConstraints constraints= new GridBagConstraints();
    constraints.gridx=gridx;
    constraints.gridy=gridy;
    constraints.gridwidth=gridwidth;
    constraints.fill=fill;
    constraints.anchor=anchor;
    this.add(component,constraints);
  }

  private void initGUI(){
    escucha = new Escucha();
    modelGeeksOutMaster = new ModelGeeksOutMaster();

    // Set up JFrame Container's Layout
    this.getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints constraints= new GridBagConstraints();
    headerProject = new Header("Mesa Geeks Out Master", Color.BLACK);
    setGridConstraint(headerProject,0,0,2,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);

    puntaje = new JLabel();
    puntaje.setText("PUNTAJE :"+modelGeeksOutMaster.getPuntaje()+"     RONDA: "+modelGeeksOutMaster.getRonda());
    puntaje.setBackground(Color.BLACK);
    puntaje.setForeground(Color.WHITE);
    puntaje.setFont(new Font("Stencil", Font.BOLD, 30));
    puntaje.setHorizontalAlignment(JLabel.CENTER);
    puntaje.setOpaque(true);
    setGridConstraint(puntaje,0,4,2,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);



    lanzar = new JButton("¡LANZAR DADOS!");
    lanzar.setFont(new Font("Stencil",Font.PLAIN,18));
    lanzar.setForeground(Color.BLACK);
    lanzar.setBackground(Color.WHITE);
    lanzar.addActionListener(escucha);
    setGridConstraint(lanzar,0,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);

    ayuda = new JButton("?");
    ayuda.setFont(new Font("Stencil",Font.PLAIN,18));
    ayuda.setForeground(Color.BLACK);
    ayuda.setBackground(Color.WHITE);
    ayuda.addActionListener(escucha);
    setGridConstraint(ayuda,0,1,1,GridBagConstraints.NONE,GridBagConstraints.LINE_START);
    JPanel panelBotones = (JPanel) ayuda.getParent();
    panelBotones.setBackground(Color.BLACK);

    salir = new JButton("¡SALIR!");
    salir.setFont(new Font("Stencil",Font.PLAIN,18));
    salir.setForeground(Color.BLACK);
    salir.setBackground(Color.WHITE);
    salir.addActionListener(escucha);
    setGridConstraint(salir,1,1,1,GridBagConstraints.NONE,GridBagConstraints.LINE_END);

    reiniciar = new JButton("¡REINICIAR!");
    reiniciar.setFont(new Font("Stencil",Font.PLAIN,18));
    reiniciar.setForeground(Color.BLACK);
    reiniciar.setBackground(Color.WHITE);
    reiniciar.addActionListener(escucha);
    setGridConstraint(reiniciar,1,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);



    panelDadosActivos = new PanelImageFondo(setImageBackground("/resources/mesa.jpg"));
    panelDadosActivos.setName("panelDadosActivos");
    panelDadosActivos.addMouseListener(escucha);
    panelDadosActivos.setPreferredSize(new Dimension(600,300));
    panelDadosActivos.setBorder(BorderFactory.createTitledBorder(null ,"DADOS ACTIVOS", TitledBorder.CENTER,
        TitledBorder.DEFAULT_JUSTIFICATION , new Font("Stencil",Font.PLAIN+Font.BOLD,20),Color.WHITE));
    setGridConstraint(panelDadosActivos,0,2,1,GridBagConstraints.BOTH,GridBagConstraints.CENTER);
    panelDadosActivos.setLayout(new FlowLayout(FlowLayout.CENTER,10,35));

    dados = new ArrayList<JLabel>();
    imageDado = new ImageIcon(getClass().getResource("/resources/SuperHeroe.PNG"));
    for (int i = 0;i<7;i++){
      dados.add(new JLabel(imageDado));
      panelDadosActivos.add(dados.get(i));
    }

    panelTarjetaPuntuacion = new PanelImageFondo(setImageBackground("/resources/tarjetonPuntaje.jpeg"));
    panelTarjetaPuntuacion.setName("panelTarjetaPuntuacion");
    panelTarjetaPuntuacion.setPreferredSize(new Dimension(600,300));
    panelTarjetaPuntuacion.setBorder(new LineBorder(Color.BLACK, 10));
    setGridConstraint(panelTarjetaPuntuacion,1,2,1,GridBagConstraints.BOTH,GridBagConstraints.CENTER);
    panelTarjetaPuntuacion.setLayout(new FlowLayout(FlowLayout.LEFT,50,10));

    panelDadosInactivos = new PanelImageFondo(setImageBackground("/resources/dadosInactivos.jpg"));
    panelDadosInactivos.setName("panelDadosInactivos");
    panelDadosInactivos.setPreferredSize(new Dimension(600,300));
    panelDadosInactivos.setBorder(new LineBorder(Color.BLACK, 10));
    setGridConstraint(panelDadosInactivos,0,3,1,GridBagConstraints.BOTH,GridBagConstraints.CENTER);
    panelDadosInactivos.setLayout(new FlowLayout(FlowLayout.CENTER,10,60));


    for (int i = 7;i<10;i++){
      dados.add(new JLabel(imageDado));
      panelDadosInactivos.add(dados.get(i));
      dados.get(i).setName("SuperHeroe");
      panelDadosInactivos.add(dados.get(i));
    }
    panelDadosUsados = new PanelImageFondo(setImageBackground("/resources/dadosUsados.jpg"));
    panelDadosUsados.setName("panelDadosUsados");
    panelDadosUsados.setPreferredSize(new Dimension(600,300));
    panelDadosUsados.setBorder(new LineBorder(Color.BLACK,10));
    setGridConstraint(panelDadosUsados,1,3,1,GridBagConstraints.BOTH,GridBagConstraints.CENTER);
    panelDadosUsados.setLayout(new FlowLayout(FlowLayout.CENTER,10,55));

    for(int i=0;i<dados.size();i++){
      dados.get(i).addMouseListener(escucha);
    }


  //probando
  }



  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        GeeksOutMasterGUI myGUI = new GeeksOutMasterGUI();
      }
    });
  }

  public Image setImageBackground(String url){
    background = new ImageIcon(getClass().getResource(url));
    image = background.getImage();
    return image;
  }

  private void pasarRonda() {
    if (modelGeeksOutMaster.getRonda()==5){
      if (modelGeeksOutMaster.getPuntaje()>=30){
        JOptionPane.showMessageDialog(null,"¡GANASTE!\n"+"\n¡Obtuviste mas de 30 Puntos!"+
            "\nTu Puntaje Final Es: "+modelGeeksOutMaster.getPuntaje()+" Puntos." ,"¡Juego Terminado!",JOptionPane.INFORMATION_MESSAGE);
      }else{
        JOptionPane.showMessageDialog(null,"El Juego Terminó.\n"+
            "Tu Puntaje Final Es: "+modelGeeksOutMaster.getPuntaje()+" Puntos." ,"¡Juego Terminado!",JOptionPane.INFORMATION_MESSAGE);
      }
    }else{
      Component[] componentes = panelDadosUsados.getComponents();
      modelGeeksOutMaster.cambiarRonda();
      modelGeeksOutMaster.setCambioDeRonda(false);
      lanzar.setVisible(true);
      modelGeeksOutMaster.setFlag(0);
      puntaje.setText("PUNTAJE :"+modelGeeksOutMaster.getPuntaje()+"     RONDA: "+modelGeeksOutMaster.getRonda());
      for (Component component:componentes){
        panelDadosUsados.remove(component);
        panelDadosActivos.add(component);
        panelDadosActivos.revalidate();
        panelDadosActivos.repaint();
        panelDadosUsados.revalidate();
        panelDadosUsados.repaint();
      }
    }
  }
  private void resetPaneles(JPanel panelReset, Component[] components){
    for (Component component:components){
      panelReset.remove(component);
      panelDadosActivos.add(component);
      panelDadosActivos.revalidate();
      panelDadosActivos.repaint();
      panelReset.revalidate();
      panelReset.repaint();
    }

  }

  private void resetGame(){
    Component[] componentesDadosUsados = panelDadosUsados.getComponents();
    resetPaneles(panelDadosUsados,componentesDadosUsados);
    Component[] componentesTarjetaPuntuacion = panelTarjetaPuntuacion.getComponents();
    resetPaneles(panelTarjetaPuntuacion,componentesTarjetaPuntuacion);
    Component[] componentesDadosInactivos = panelDadosInactivos.getComponents();
    resetPaneles(panelDadosInactivos,componentesDadosInactivos);
    Component[] componentesDadosActivos = panelDadosActivos.getComponents();
    for (int i=0;i<3;i++){
      panelDadosActivos.remove(componentesDadosActivos[i]);
      panelDadosInactivos.add(componentesDadosActivos[i]);
    }
    lanzar.setVisible(true);
    modelGeeksOutMaster.resetRonda();
    modelGeeksOutMaster.resetPuntaje();
    modelGeeksOutMaster.setFlag(0);
    puntaje.setText("PUNTAJE :"+modelGeeksOutMaster.getPuntaje()+"     RONDA: "+modelGeeksOutMaster.getRonda());
  }



  public void lanzarDadosActivos(){
    modelGeeksOutMaster.lanzarDados();
    ArrayList<String> caras = modelGeeksOutMaster.getCaras();
    for(int i=0;i< dados.size();i++){
      if (getPanelDado(dados.get(i))=="panelDadosActivos"){
        imageDado = new ImageIcon(getClass().getResource("/resources/"+caras.get(i)+".png"));
        dados.get(i).setIcon(imageDado);
        dados.get(i).setName(caras.get(i));
      }
    }
  }


  public String getPanelDado(Component dado) {
    Container contenedor = this.getContentPane();
    for (Component componente : contenedor.getComponents()) {
      if (componente instanceof JPanel) {
        if (((JPanel) componente).isAncestorOf(dado)) {
          return componente.getName();
        }
      }
    }
    return null;
  }




  private class Escucha implements ActionListener, MouseListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource()==lanzar){
        lanzarDadosActivos();
        lanzar.setVisible(false);
      }
      if (e.getSource()==ayuda){
        JOptionPane.showMessageDialog(null,MENSAJE_AYUDA,"¡AYUDA!",JOptionPane.INFORMATION_MESSAGE);
      }
      if (e.getSource()==salir){
        System.exit(0);
      }
      if (e.getSource()==reiniciar){
        resetGame();
      }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      modelGeeksOutMaster.verificarPanel(e,panelDadosActivos,panelDadosUsados,panelDadosInactivos,panelTarjetaPuntuacion);
      if(modelGeeksOutMaster.getCambioDeRonda()==true){
        pasarRonda();
      }

      if(modelGeeksOutMaster.getFlag()==0){
        JOptionPane.showMessageDialog(null,"Debes de Lanzar Los Dados Primero","¡A Lanzar Los Dados",JOptionPane.INFORMATION_MESSAGE);
      }
      if(getPanelDado(e.getComponent())=="panelDadosActivos" && modelGeeksOutMaster.getFlag()==1){
        modelGeeksOutMaster.determinarJuego(e,panelDadosActivos,panelDadosUsados,panelDadosInactivos);
        if(e.getComponent().getName()=="42" || e.getComponent().getName()=="Dragon"){
          JOptionPane.showMessageDialog(null,"Esta Cara Del Dado No Tiene Ninguna Función.\n"+
                "Por Favor Selecciona Otro.","¡Cara Sin Habilidad!",JOptionPane.INFORMATION_MESSAGE);
        }
      }
      if(getPanelDado(e.getComponent())=="panelDadosActivos" && modelGeeksOutMaster.getFlag()==2){
        modelGeeksOutMaster.estadoPoder(e,panelDadosActivos,panelDadosInactivos);
      }
      if(getPanelDado(e.getComponent())=="panelDadosInactivos" && modelGeeksOutMaster.getFlag()==3) {
        modelGeeksOutMaster.estadoPoder(e, panelDadosActivos, panelDadosInactivos);
      }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
  }
}