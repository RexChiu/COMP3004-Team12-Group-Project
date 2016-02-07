package gui;

import config.GUIConfig;
import jdk.nashorn.internal.runtime.regexp.joni.Config;

import java.awt.*; 
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class IvanhoeApp extends JFrame implements ActionListener{
	
	private static final String GAME_TITLE = "Ivanhoe";
	
	public static final String MENU_STRINGS = "Open Server Close Server Join Client Close Client"
    		+ "View Player Edit Player About Game Help Game";
	
	public HashMap<String, JMenuItem> listJMI = new HashMap<String, JMenuItem>();
	public HashMap<String, String[]> listMENU = new HashMap<String, String[]>();
		
	public IvanhoeApp(){
		super(GAME_TITLE);
		getContentPane().setLayout(null);

		JButton titleLabel = new JButton(GAME_TITLE.toUpperCase());
		titleLabel.setLocation(GUIConfig.TITLE_TEXT_LOCATION_X, GUIConfig.TITLE_TEXT_LOCATION_Y);
		titleLabel.setSize(GUIConfig.TITLE_TEXT_WIDTH, GUIConfig.TITLE_TEXT_HEIGHT);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBackground(Color.BLACK);
		getContentPane().add(titleLabel);
		
		setup_menuBar();		
		setup_poolPanel();
		setup_tournamentPanel();
		setup_playerPanel();
		setup_userPanel();		
		
	    setSize(GUIConfig.APP_WINDOW_WIDTH, GUIConfig.APP_WINDOW_HEIGHT);
	    setResizable(false);
	}
	
	public void setup_poolPanel(){
		JPanel poolPanel = new PoolPanel();
		getContentPane().add(poolPanel);			
	}

	public void setup_tournamentPanel(){
		JPanel tournamentPanel = new TournamentPanel();		
		getContentPane().add(tournamentPanel);	
	}
	
	public void setup_userPanel(){
		JPanel userPanel = new UserPanel();
		getContentPane().add(userPanel);	
	}
	
	public void setup_playerPanel(){
		JPanel playerOnePanel = new PlayerPanel(GUIConfig.SECOND_PLAYER_ID);
		getContentPane().add(playerOnePanel);	
		JPanel playerTwoPanel = new PlayerPanel(GUIConfig.THIRD_PLAYER_ID);
		getContentPane().add(playerTwoPanel);	
		JPanel playerThreePanel = new PlayerPanel(GUIConfig.FOURTH_PLAYER_ID);
		getContentPane().add(playerThreePanel);	
		JPanel playerFourPanel = new PlayerPanel(GUIConfig.FIFTH_PLAYER_ID);
		getContentPane().add(playerFourPanel);	
	}
	
	public void setup_menuBar(){
	    JMenuBar myMenuBar = new JMenuBar();
	    this.setJMenuBar(myMenuBar);

	    listMENU.put("Server", GUIConfig.SERVER_TEXT);
	    listMENU.put("Client", GUIConfig.CLIENT_TEXT);
	    listMENU.put("Player", GUIConfig.PLAYER_TEXT);
	    listMENU.put("About", GUIConfig.ABOUT_TEXT);
	    
	    myMenuBar.add(newMenu("Server"));
	    myMenuBar.add(newMenu("Client"));
	    myMenuBar.add(newMenu("Player"));
	    myMenuBar.add(newMenu("About"));		
	}
	
	public JMenu newMenu( String menuString){
	    JMenu menu = new JMenu(menuString); 
	    menu.setMnemonic(menuString.toUpperCase().charAt(0)); 
	    
	    for (String name : listMENU.get(menuString)){
	    	listJMI.put(name, new JMenuItem(name));
	    	listJMI.get(name).addActionListener(this);
			
			menu.add(listJMI.get(name));
	    }
	    
		return menu;
	}
	
	public void actionPerformed(ActionEvent e){
		String text = e.getActionCommand();
		if (MENU_STRINGS.contains(text)){
			System.out.println(listJMI.get(text).getText());
		}
		
		
		switch(text){
			case "About Game":
				new AboutDialog(this, "About Ivanhoe", "About Ivanhoe").setVisible(Boolean.TRUE);
				break;
			case "Help Game":
				break;
			default:
				break;
		}
	}
	
	public static void main(String args[]) { new IvanhoeApp().setVisible(true); }
}