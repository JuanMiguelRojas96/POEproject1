package juegoGOM;

import javax.swing.*;
import java.awt.*;

public class Header extends JLabel {
  public Header(String title, Color backgroundColor) {
    this.setText(title);
    this.setBackground(backgroundColor);
    this.setForeground(Color.WHITE);
    this.setFont(new Font("Stencil", Font.BOLD, 30));
    this.setHorizontalAlignment(JLabel.CENTER);
    this.setOpaque(true);
  }

}
