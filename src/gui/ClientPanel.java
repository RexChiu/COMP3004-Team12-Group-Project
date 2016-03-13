package gui;

import config.GAMEConfig;
import config.GUIConfig;
import config.LANConfig;
import network.AppClient;
import message.Message;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
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
	public HashMap<Integer, PlayerPanel> playerPanel = new HashMap<Integer, PlayerPanel>();
	public UserPanel userPanel;
	public PoolPanel poolPanel;
	public TournamentPanel tournamentPanel;

	public boolean applyEnable = Boolean.FALSE;
	public boolean submitEnable = Boolean.FALSE;

	public HashMap<Integer, Integer> selected = new HashMap<>();	

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
		poolPanel = new PoolPanel();
		getContentPane().add(poolPanel);			
	}

	public void setup_tournamentPanel(){
		tournamentPanel = new TournamentPanel();		
		getContentPane().add(tournamentPanel);	
	}

	public void setup_userPanel(){
		userPanel = new UserPanel(this);
		getContentPane().add(userPanel);
	}

	public void setup_playerPanel(){
		playerPanel.put(GUIConfig.SECOND_PLAYER_ID, new PlayerPanel(this, GUIConfig.SECOND_PLAYER_ID));
		getContentPane().add(playerPanel.get(GUIConfig.SECOND_PLAYER_ID));
		playerPanel.put(GUIConfig.THIRD_PLAYER_ID, new PlayerPanel(this, GUIConfig.THIRD_PLAYER_ID));
		getContentPane().add(playerPanel.get(GUIConfig.THIRD_PLAYER_ID));	
		playerPanel.put(GUIConfig.FOURTH_PLAYER_ID, new PlayerPanel(this, GUIConfig.FOURTH_PLAYER_ID));
		getContentPane().add(playerPanel.get(GUIConfig.FOURTH_PLAYER_ID));	
		playerPanel.put(GUIConfig.FIFTH_PLAYER_ID, new PlayerPanel(this, GUIConfig.FIFTH_PLAYER_ID));
		getContentPane().add(playerPanel.get(GUIConfig.FIFTH_PLAYER_ID));	
	}

	public void setup_menuBar(){
		JMenuBar myMenuBar = new JMenuBar();
		this.setJMenuBar(myMenuBar);

		listMENU.put("Client", GUIConfig.CLIENT_TEXT);
		listMENU.put("Setting", GUIConfig.SETTING_TEXT);
		listMENU.put("About", GUIConfig.ABOUT_TEXT);

		myMenuBar.add(newMenu("Client"));
		myMenuBar.add(newMenu("Setting"));
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

				// Reset
				userPanel.infoButton.setText("INFO");
				for (int i = GUIConfig.SECOND_PLAYER_ID; i <= GUIConfig.FIFTH_PLAYER_ID; i++) { playerPanel.get(i).infoButton.setText("INFO");}
				for (int i = GUIConfig.USER_PLAYER_ID; i <= tournamentPanel.infoLabel.size(); i++){ tournamentPanel.infoLabel.get(i).setText("INFO"); }

				listJMI.get(GUIConfig.CLIENT_JOIN).setEnabled(Boolean.TRUE);
				JOptionPane.showMessageDialog(new JFrame(), LANConfig.QUIT_SERVER, LANConfig.DISCONNECT_SERVER, JOptionPane.INFORMATION_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(new JFrame(), LANConfig.CLIENT_NOT_JOINED, LANConfig.CLIENT_ERROR, JOptionPane.ERROR_MESSAGE);					
			}
			break;
		case GUIConfig.EDIT_NETWORK:
			new NetworkSetting("Server Status").setVisible(Boolean.TRUE);
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

	public void updateUI(Message message){			
		String ID 				= message.getBody().getField("UserID").toString();
		String tokens 			= message.getBody().getField("UserTokens").toString();
		String hand 			= message.getBody().getField("UserHand").toString();
		String total 			= message.getBody().getField("UserTotal").toString();
		String status 			= message.getBody().getField("UserStatus").toString();
		String display			= message.getBody().getField("UserDisplay").toString();
		String playersID		= message.getBody().getField("PlayersID").toString();
		String tournamentInfo	= message.getBody().getField("TournamentInfo").toString();

		int index = 1;
		for (String playerInfo : tournamentInfo.split(";")){
			tournamentPanel.infoLabel.get(index).setText(playerInfo.split(":")[0]);
			tournamentPanel.statusLabel.get(index).setText(playerInfo.split(":")[1]);
			tournamentPanel.totalLabel.get(index++).setText(playerInfo.split(":")[2]);		
		}

		userPanel.infoButton.setText("User: " + ID);
		userPanel.tokenButton.setText(tokens);	
		userPanel.statusOneButton.setText((status.contains(GAMEConfig.STUNNED)) ? GAMEConfig.STUNNED : "None");
		userPanel.statusTwoButton.setText((status.contains(GAMEConfig.SHIELD)) ? GAMEConfig.SHIELD : "None");
		userPanel.updateUI(hand, total, display);

		index = GUIConfig.SECOND_PLAYER_ID;
		for (String id : playersID.split(",")){
			String playersDisplays 	= message.getBody().getField("Player " + id + " Display").toString();
			String playersHand 		= message.getBody().getField("Player " + id + " Hand").toString();
			String playersTotal 	= message.getBody().getField("Player " + id + " Total").toString();
			String playersTokens 	= message.getBody().getField("Player " + id + " Tokens").toString();
			String playersStatus 	= message.getBody().getField("Player " + id + " Status").toString();

			playerPanel.get(index).infoButton.setText(id);
			playerPanel.get(index).tokenButton.setText(playersTokens);	
			playerPanel.get(index).statusOneButton.setText((playersStatus.contains(GAMEConfig.STUNNED)) ? GAMEConfig.STUNNED : "None");
			playerPanel.get(index).statusTwoButton.setText((playersStatus.contains(GAMEConfig.SHIELD)) ? GAMEConfig.SHIELD : "None");
			playerPanel.get(index++).updateUI(playersHand, playersTotal, playersDisplays);			
		}


		if (message.getBody().hasField("Choose Colour")){
			String chooseColours = message.getBody().getField("Choose Colour").toString();
			String selectedColour = "";
			if (Integer.parseInt(chooseColours) == 5){
				int result = JOptionPane.showOptionDialog(null,
						"Please choose the tournament color", "Pick a Colour", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, GAMEConfig.TOKEN_COLORS_FIVE, GAMEConfig.TOKEN_COLORS_FIVE[0]);
				selectedColour = GAMEConfig.TOKEN_COLORS_FIVE[result];
			}else{
				int result = JOptionPane.showOptionDialog(null,
						"Please choose the tournament color", "Pick a Colour", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, GAMEConfig.TOKEN_COLORS_FOUR, GAMEConfig.TOKEN_COLORS_FOUR[0]);

				selectedColour = GAMEConfig.TOKEN_COLORS_FOUR[result];
			}
			message.getBody().addField("Selected Colour", selectedColour);
		}

		if (message.getBody().hasField("Tournament Colour")){
			String tounamentColours = message.getBody().getField("Tournament Colour").toString();
			poolPanel.tokenButton.setText(tounamentColours);
		}

		if (message.getBody().hasField("POW Confirm")){				
			int result = JOptionPane.showConfirmDialog(null, ID + ": Do you want withdraw?",
					"Play or Withdraw", JOptionPane.YES_NO_OPTION);
			if (result == 0) {
				message.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
				applyEnable = Boolean.FALSE; 
			} else {
				message.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
				applyEnable = Boolean.TRUE; 
			}
		}
		
		if (message.getBody().hasField("Legal Apply")){
			String choice = message.getBody().getField("Legal Apply").toString();
			if (choice.equals(GAMEConfig.APPLY_LEGAL)){
				System.out.println("Confirm Resuquest is legal");
				submitEnable = Boolean.TRUE;
			}else{
				System.out.println("Confirm Resuquest is illegal");
				selected.clear();
				this.userPanel.handPanel.selected = new boolean[GUIConfig.HANDPANEL_MAX_CARD];
				submitEnable = Boolean.FALSE;
			}	
		}
		
		if (message.getBody().hasField("Winner")){
			System.out.println("Winnter: " + message.getBody().getField("Winner").toString());
		}			
	}

	public AppClient getClient(){ return this.client; }
	public void setClientJoined(boolean status) { this.clientJoined = status; }
}