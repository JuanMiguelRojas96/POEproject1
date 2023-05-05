package juegoGOM;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GeeksOutMasterGUI extends JFrame{

  private PanelImageFondo panelImageFondo;
  private Header headerProject;
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



    lanzar = new JButton("¡Lanzar Dados!");
    lanzar.addActionListener(escucha);
    setGridConstraint(lanzar,0,1,2,GridBagConstraints.NONE,GridBagConstraints.CENTER);



    panelDadosActivos = new PanelImageFondo(setImageBackground("/resources/mesa.jpg"));
    panelDadosActivos.setName("panelDadosActivos");
    panelDadosActivos.setPreferredSize(new Dimension(600,300));
    panelDadosActivos.setBorder(BorderFactory.createTitledBorder(null ,"Dados Activos", TitledBorder.CENTER,
        TitledBorder.DEFAULT_JUSTIFICATION , new Font("Arial",Font.PLAIN,14),Color.BLACK));
    setGridConstraint(panelDadosActivos,0,2,1,GridBagConstraints.BOTH,GridBagConstraints.CENTER);
    ImageIcon background = new ImageIcon("/resources/SuperHeroe.PNG");
    JLabel backgroundLabel = new JLabel(background);
    backgroundLabel.setPreferredSize(new Dimension(panelDadosActivos.getWidth(),panelDadosActivos.getHeight()));
    backgroundLabel.setMaximumSize(new Dimension(panelDadosActivos.getWidth(),panelDadosActivos.getHeight()));
    panelDadosActivos.add(backgroundLabel);

    dados = new ArrayList<JLabel>();
    imageDado = new ImageIcon(getClass().getResource("/resources/SuperHeroe.PNG"));
    for (int i = 0;i<7;i++){
      dados.add(new JLabel(imageDado));
      panelDadosActivos.add(dados.get(i));
    }

    panelTarjetaPuntuacion = new PanelImageFondo(setImageBackground("/resources/tarjetonPuntaje.jpg"));
    panelTarjetaPuntuacion.setName("panelTarjetaPuntuacion");
    panelTarjetaPuntuacion.setPreferredSize(new Dimension(600,300));
    panelTarjetaPuntuacion.setBorder(BorderFactory.createTitledBorder(null ,"Tarjeta de Puntuación", TitledBorder.CENTER,
        TitledBorder.DEFAULT_JUSTIFICATION , new Font("Arial",Font.PLAIN,14),Color.BLACK));
    setGridConstraint(panelTarjetaPuntuacion,1,2,1,GridBagConstraints.BOTH,GridBagConstraints.CENTER);

    panelDadosInactivos = new PanelImageFondo(setImageBackground("/resources/dadosInactivos.jpg"));
    panelDadosInactivos.setName("panelDadosInactivos");
    panelDadosInactivos.setPreferredSize(new Dimension(600,300));
    panelDadosInactivos.setBorder(BorderFactory.createTitledBorder(null ,"Dados Inactivos", TitledBorder.CENTER,
        TitledBorder.DEFAULT_JUSTIFICATION , new Font("Arial",Font.PLAIN,14),Color.BLACK));
    setGridConstraint(panelDadosInactivos,0,3,1,GridBagConstraints.BOTH,GridBagConstraints.CENTER);
    for (int i = 7;i<10;i++){
      dados.add(new JLabel(imageDado));
      panelDadosInactivos.add(dados.get(i));
    }
    panelDadosUsados = new PanelImageFondo(setImageBackground("/resources/dadosUsados.jpg"));
    //panelDadosUsados = new JPanel();
    panelDadosUsados.setName("panelDadosUsados");
    panelDadosUsados.setPreferredSize(new Dimension(600,300));
    panelDadosUsados.setBorder(BorderFactory.createTitledBorder(null ,"Dados Usados", TitledBorder.CENTER,
        TitledBorder.DEFAULT_JUSTIFICATION , new Font("Arial",Font.PLAIN,14),Color.BLACK));
    setGridConstraint(panelDadosUsados,1,3,1,GridBagConstraints.BOTH,GridBagConstraints.CENTER);


    for(int i=0;i<dados.size();i++){
      dados.get(i).addMouseListener(escucha);
    }

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
        //lanzar.setVisible(false);
      }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
      if(getPanelDado(e.getComponent())=="panelActivos" && modelGeeksOutMaster.getFlag()==2){
        modelGeeksOutMaster.estadoPoder(e,panelDadosActivos,panelDadosUsados,panelDadosInactivos,panelTarjetaPuntuacion);
      }
      if(getPanelDado(e.getComponent())=="panelDadosActivos" && modelGeeksOutMaster.getFlag()==3){
        modelGeeksOutMaster.estadoPoder(e,panelDadosActivos,panelDadosUsados,panelDadosInactivos,panelTarjetaPuntuacion);
      }
      if (getPanelDado(e.getComponent())=="panelDadosInactivos" && modelGeeksOutMaster.getFlag()==5){
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