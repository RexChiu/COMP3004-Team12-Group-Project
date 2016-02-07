import javax.swing.*;

public class ButtonsPanel extends JPanel {
  JButton[][] buttons; 
  
  public static ImageIcon[] icons = {new ImageIcon("RedButton.png"),  new ImageIcon("OrangeButton.png"), 
    new ImageIcon("YellowButton.png"), new ImageIcon("GreenButton.png"), new ImageIcon("BlueButton.png"), 
    new ImageIcon("LightGrayButton.png"), new ImageIcon("DarkGrayButton.png")}; 
  
  public ButtonsPanel() {
    // Choose to lay out components manually
    setLayout(null);
    
    // Make a border around the outside with the given title  
    buttons = new JButton[8][8];     
    ShinyButtons images = new ShinyButtons ();
    
    for(int row=0; row < 8; row++) { 
      for (int col=0; col < 8; col++) { 
        buttons[row][col] = new JButton(icons[images.getButton(row,col)]);
        buttons[row][col].setLocation(col * 69, row * 69); 
        buttons[row][col].setSize(69,69); 
        add(buttons[row][col]); 
      } 
    } 

    setSize(552, 552);
  }
}

