import javax.swing.*;

public class InfoApp extends JFrame {
  public InfoApp(String name) {
    super(name);
    
    // Choose to lay out components manually
    getContentPane().setLayout(null);
    
    // Now add an AddressPanel
    PersonPanel myPanel = new PersonPanel("PERSONAL INFORMATION");
    myPanel.setLocation(5,5);
    getContentPane().add(myPanel);
    
    // Add the ADD button     
    JButton clearButton = new JButton("Clear");     
    clearButton.setLocation(268,260);     
    clearButton.setSize(75,30);      
    getContentPane().add(clearButton);  
    // Add the REMOVE button         
    JButton submitButton = new JButton("Submit");     
    submitButton.setLocation(368,260);        
    submitButton.setSize(75,30);         
    getContentPane().add(submitButton);
    // Add the REMOVE button         
    JButton quitButton = new JButton("Quit");     
    quitButton.setLocation(468,260);        
    quitButton.setSize(75,30);         
    getContentPane().add(quitButton);
    
    // Set program to stop when window closed
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(560, 330); // manually computed sizes
    setResizable(false);
  }
  
  public static void main(String[] args) {
    JFrame frame = new InfoApp("App1");
    frame.setVisible(true);
  }
}

