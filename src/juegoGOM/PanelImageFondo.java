package juegoGOM;

import javax.swing.*;
import java.awt.*;

public class PanelImageFondo extends JPanel {
    private Image image;

    public PanelImageFondo(Image image) {

        this.image = image;
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0,0,getWidth(),getHeight(),this);
    }


}
