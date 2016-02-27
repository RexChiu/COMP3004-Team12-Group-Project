package gui;

import config.GUIConfig;
import config.LANConfig;
import network.AppClient;

import java.awt.*;

import javax.imageio.IIOException;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.ConnectException;
import java.util.HashMap;

public class ClientPanel extends JFrame implements ActionListener{
	
	/**
	 * Client Panel for the Client Ivanhoe
	 */
	private static final long serialVersionUID = -4161260668908177011L;

	private static final String GAME_TITLE = "Ivanhoe";
	private AppClient client;
	private boolean clientJoined = Boolean.FALSE;
	
	public HashMap<String, JMenuItem> listJMI = new HashMap<String, JMenuItem>();
	public HashMap<String, String[]> listMENU = new HashMap<String, String[]>();
	
	public ClientPanel(){
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
		
	    setSize(GUIConfig.CLIENT_WINDOW_WIDTH, GUIConfig.CLIENT_WINDOW_HEIGHT);
	    setResizable(Boolean.FALSE);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	    listMENU.put("Client", GUIConfig.CLIENT_TEXT);
	    listMENU.put("Player", GUIConfig.PLAYER_TEXT);
	    listMENU.put("About", GUIConfig.ABOUT_TEXT);
	    
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
		switch(text){
			case GUIConfig.CLIENT_JOIN:
				System.out.println();
				if (!clientJoined){
					try{
						client = new AppClient(LANConfig.DEFAULT_HOST, LANConfig.DEFAULT_PORT, this);
						clientJoined = !clientJoined;
						listJMI.get(GUIConfig.CLIENT_JOIN).setEnabled(Boolean.FALSE);	
						JOptionPane.showMessageDialog(new JFrame(), LANConfig.JOIN_SEREVR, LANConfig.CONNNECT_SERVER, JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException ioe) {
						JOptionPane.showMessageDialog(new JFrame(), LANConfig.SERVER_NOT_RUNNING, LANConfig.SERVER_ERROR, JOptionPane.ERROR_MESSAGE);
					}					
				}
				break;
			case GUIConfig.CLIENT_QUIT:
				if (clientJoined){
					client.stop();
					client = null;
					clientJoined = !clientJoined;
					listJMI.get(GUIConfig.CLIENT_JOIN).setEnabled(Boolean.TRUE);
					JOptionPane.showMessageDialog(new JFrame(), LANConfig.QUIT_SERVER, LANConfig.DISCONNECT_SERVER, JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(new JFrame(), LANConfig.CLIENT_NOT_JOINED, LANConfig.CLIENT_ERROR, JOptionPane.ERROR_MESSAGE);					
				}
				break;
			case GUIConfig.VIEW_PLAYER:
				break;
			case GUIConfig.EDIT_PLAYER:
				break;
			case GUIConfig.ABOUT_GAME:
				new AboutDialog(this, "About Ivanhoe", "About Ivanhoe").setVisible(Boolean.TRUE);
				break;
			case GUIConfig.HELP_GAME:
				break;
			default:
				break;
		}
	}
	

	public void setClientJoined(boolean status) { this.clientJoined = status; }
}