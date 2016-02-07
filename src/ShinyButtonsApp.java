import javax.swing.*;

public class ShinyButtonsApp extends JFrame {

  public ShinyButtonsApp(String name) { 
    super(name);
    
    // Choose to lay out components manually
    getContentPane().setLayout(null);
    
    ButtonsPanel myPanel = new ButtonsPanel();
    myPanel.setLocation(10,10);
    getContentPane().add(myPanel);
        
    JButton submitButton = new JButton("New Game");     
    submitButton.setLocation(375,570);        
    submitButton.setSize(100,25);         
    add(submitButton);
    
    JButton quitButton = new JButton("Quit");     
    quitButton.setLocation(485,570);        
    quitButton.setSize(75,25);         
    add(quitButton);
    
    JLabel aLabel = new JLabel("Score:");
    aLabel.setLocation(10, 570);
    aLabel.setSize(100,25);
    add(aLabel);
    
    JTextField nameField = new JTextField("0");
    nameField.setHorizontalAlignment(JTextField.RIGHT);
    nameField.setLocation(75, 570);
    nameField.setSize(100,25);
    add(nameField);
    
    // Set program to stop when window closed
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(578, 634); // manually computed sizes
    setResizable(false);
  }
  
  public static void main(String[] args) { 
    JFrame frame = new ShinyButtonsApp("Shiny Buttons");
    frame.setVisible(true);
  }  
}
