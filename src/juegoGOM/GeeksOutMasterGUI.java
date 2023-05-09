package juegoGOM;


import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class GeeksOutMasterGUI extends JFrame{
  private Header headerProject;
  private JLabel puntaje;
  private ArrayList<JLabel> dados;
  private JButton lanzar;
  private Escucha escucha;
  private JPanel panelDadosActivos,panelDadosInactivos,panelDadosUsados,panelTarjetaPuntuacion;
  private ImageIcon imageDado, background;
  private Image image;

  private ModelGeeksOutMaster modelGeeksOutMaster;



  public GeeksOutMasterGUI(){
    initGUI();

    this.setTitle("Geeks Out Master Game");
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



    lanzar = new JButton("¡Lanzar Dados!");
    lanzar.addActionListener(escucha);
    setGridConstraint(lanzar,0,1,2,GridBagConstraints.NONE,GridBagConstraints.CENTER);



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

    panelTarjetaPuntuacion = new PanelImageFondo(setImageBackground("/resources/tarjetonPuntaje.jpg"));
    panelTarjetaPuntuacion.setName("panelTarjetaPuntuacion");
    panelTarjetaPuntuacion.setPreferredSize(new Dimension(600,300));
    panelTarjetaPuntuacion.setBorder(new LineBorder(Color.WHITE, 3));
    setGridConstraint(panelTarjetaPuntuacion,1,2,1,GridBagConstraints.BOTH,GridBagConstraints.CENTER);

    panelDadosInactivos = new PanelImageFondo(setImageBackground("/resources/dadosInactivos.jpg"));
    panelDadosInactivos.setName("panelDadosInactivos");
    panelDadosInactivos.setPreferredSize(new Dimension(600,300));
    panelDadosInactivos.setBorder(new LineBorder(Color.WHITE, 3));
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
    panelDadosUsados.setBorder(new LineBorder(Color.WHITE,3));
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

  private void resetGame() {
    if (modelGeeksOutMaster.getRonda()==5){
      JOptionPane.showMessageDialog(null,"El Juego Terminó.\n"+
          "Tu Puntaje Final Es: "+modelGeeksOutMaster.getPuntaje()+" Puntos." ,"¡Juego Terminado!",JOptionPane.INFORMATION_MESSAGE);
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
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      modelGeeksOutMaster.verificarPanel(e,panelDadosActivos,panelDadosUsados,panelDadosInactivos,panelTarjetaPuntuacion);
      if(modelGeeksOutMaster.getCambioDeRonda()==true){
        resetGame();
      }

      if(modelGeeksOutMaster.getFlag()==0){
        JOptionPane.showMessageDialog(null,"Debes de Lanzar Los Dados Primero","¡A Lanzar Los Dados",JOptionPane.INFORMATION_MESSAGE);
      }
      if(getPanelDado(e.getComponent())=="panelDadosActivos" && modelGeeksOutMaster.getFlag()==1){
        modelGeeksOutMaster.determinarJuego(e,panelDadosActivos,panelDadosUsados,panelDadosInactivos,panelTarjetaPuntuacion);
        if(e.getComponent().getName()=="42" || e.getComponent().getName()=="Dragon"){
          JOptionPane.showMessageDialog(null,"Esta Cara Del Dado No Tiene Ninguna Función.\n"+
                "Por Favor Selecciona Otro.","¡Cara Sin Habilidad!",JOptionPane.INFORMATION_MESSAGE);
        }
      }
      if(getPanelDado(e.getComponent())=="panelDadosActivos" && modelGeeksOutMaster.getFlag()==2){
        modelGeeksOutMaster.estadoPoder(e,panelDadosActivos,panelDadosUsados,panelDadosInactivos,panelTarjetaPuntuacion);
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