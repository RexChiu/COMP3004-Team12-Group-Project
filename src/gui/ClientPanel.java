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
import java.util.ArrayList;
import java.util.HashMap;

public class ClientPanel extends JFrame implements ActionListener{

	/**
	 * Client Panel for the Client Ivanhoe
	 */
	private static final long serialVersionUID = -4161260668908177011L;

	private static final String GAME_TITLE = "Ivanhoe";
	private AppClient client;
	private boolean clientJoined = Boolean.FALSE;
	private boolean playerUpdated = Boolean.FALSE;

	public HashMap<String, JMenuItem> 		listJMI 	= new HashMap<String, JMenuItem>();
	public HashMap<String, String[]> 		listMENU 	= new HashMap<String, String[]>();
	public HashMap<String, PlayerPanel> 	playerPanel	= new HashMap<String, PlayerPanel>();
	public HashMap<String, String> 			dataPacket 	=  new HashMap<String, String>();
	
	public UserPanel userPanel;
	public PoolPanel poolPanel;
	public TournamentPanel tournamentPanel;
	
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
		playerPanel.put(""+GUIConfig.SECOND_PLAYER_ID, new PlayerPanel(this, GUIConfig.SECOND_PLAYER_ID));
		getContentPane().add(playerPanel.get(""+GUIConfig.SECOND_PLAYER_ID));
		playerPanel.put(""+GUIConfig.THIRD_PLAYER_ID, new PlayerPanel(this, GUIConfig.THIRD_PLAYER_ID));
		getContentPane().add(playerPanel.get(""+GUIConfig.THIRD_PLAYER_ID));	
		playerPanel.put(""+GUIConfig.FOURTH_PLAYER_ID, new PlayerPanel(this, GUIConfig.FOURTH_PLAYER_ID));
		getContentPane().add(playerPanel.get(""+GUIConfig.FOURTH_PLAYER_ID));	
		playerPanel.put(""+GUIConfig.FIFTH_PLAYER_ID, new PlayerPanel(this, GUIConfig.FIFTH_PLAYER_ID));
		getContentPane().add(playerPanel.get(""+GUIConfig.FIFTH_PLAYER_ID));
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
				Message message = new Message();
				message.getHeader().state = LANConfig.PLAYER_LOSS;
				client.send(message);
				//JOptionPane.showMessageDialog(new JFrame(), LANConfig.QUIT_SERVER, LANConfig.DISCONNECT_SERVER, JOptionPane.INFORMATION_MESSAGE);
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

	public void shutDown(){
		// Reset
		clientJoined = !clientJoined;
		listJMI.get(GUIConfig.CLIENT_JOIN).setEnabled(Boolean.TRUE);
		JOptionPane.showMessageDialog(new JFrame(), LANConfig.SERVER_DOWN + ", " + LANConfig.CLIENT_LOSS, LANConfig.DISCONNECT_SERVER, JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}
	
	public void updateUI(Message message){				
		String 	ID 				= message.getBody().getField("UserID").toString();
		String 	tokens 			= message.getBody().getField("UserTokens").toString();
		String 	hand 			= message.getBody().getField("UserHand").toString();
		String 	total 			= message.getBody().getField("UserTotal").toString();
		String 	status 			= message.getBody().getField("UserStatus").toString();
		String 	display			= message.getBody().getField("UserDisplay").toString();
		String 	playersID		= message.getBody().getField("PlayersID").toString();
		String 	tournamentInfo	= message.getBody().getField("TournamentInfo").toString();
		String  tournamnetColor = message.getBody().getField("Tournament Color").toString();
		int 	winnerID		= Integer.parseInt(message.getBody().getField("Winner ID").toString());

		this.poolPanel.tournamentButton.setText(tournamnetColor);
		
		int index = 1;
		for (String playerInfo : tournamentInfo.split(";")){
			tournamentPanel.infoLabel.get(index).setText(playerInfo.split(":")[0]);
			tournamentPanel.statusLabel.get(index).setText(playerInfo.split(":")[1]);
			tournamentPanel.totalLabel.get(index++).setText(playerInfo.split(":")[2]);		
		}

		tokens = tokens.replaceAll(",", "\n");		
		userPanel.ID = ID;
		userPanel.infoButton.setText(ID);
		userPanel.tokenButton.setText(tokens);	
		userPanel.statusOneButton.setText((status.contains(GAMEConfig.STUNNED)) ? GAMEConfig.STUNNED : "None");
		userPanel.statusTwoButton.setText((status.contains(GAMEConfig.SHIELD)) ? GAMEConfig.SHIELD : "None");
		userPanel.updateUI(hand, total, display);

		if (!this.playerUpdated){
			ArrayList<String> oldKeys = new ArrayList<String>();
			oldKeys.addAll(playerPanel.keySet());
			String[] newKeys = playersID.split(",");
			for (int i = 0; i < newKeys.length; i++){
				playerPanel.put(newKeys[i], playerPanel.get(oldKeys.get(i)));
				playerPanel.remove(oldKeys.get(i));
			}
			this.playerUpdated = !this.playerUpdated;
		}
		
		for (String playerID : playersID.split(",")){
			String playersDisplays 	= message.getBody().getField("Player " + playerID + " Display").toString();
			String playersHand 		= message.getBody().getField("Player " + playerID + " Hand").toString();
			String playersTotal 	= message.getBody().getField("Player " + playerID + " Total").toString();
			String playersTokens 	= message.getBody().getField("Player " + playerID + " Tokens").toString();
			String playersStatus 	= message.getBody().getField("Player " + playerID + " Status").toString();

			playersTokens = playersTokens.replaceAll(",", "");
			playerPanel.get(playerID).ID = playerID;
			playerPanel.get(playerID).infoButton.setText(playerID);
			playerPanel.get(playerID).tokenButton.setText(playersTokens);	
			playerPanel.get(playerID).statusOneButton.setText((playersStatus.contains(GAMEConfig.STUNNED)) ? GAMEConfig.STUNNED : "None");
			playerPanel.get(playerID).statusTwoButton.setText((playersStatus.contains(GAMEConfig.SHIELD)) ? GAMEConfig.SHIELD : "None");
			playerPanel.get(playerID).updateUI(playersHand, playersTotal, playersDisplays);
		}

		int state = message.getHeader().getState();
		
		int result = 0;
		String colors = "";
		String selectedColor = "";
		String choices = "";
		String cardName = "";
		String fromID = "";
		String[] tokenList = null;
		Message response = null;
		switch (state){
			case GAMEConfig.SELECT_COLOR:
				colors = message.getBody().getField("Select Colors").toString();
				if (colors.split(",").length == 5){
					result = JOptionPane.showOptionDialog(null,
							"Please choose the tournament color", "Pick a Colour", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
							null, GAMEConfig.TOKEN_COLORS_FIVE, GAMEConfig.TOKEN_COLORS_FIVE[0]);
					selectedColor = GAMEConfig.TOKEN_COLORS_FIVE[result];
					
					response = new Message();
					response.getHeader().sender = ID;
					response.getHeader().state = message.getHeader().getState();
					response.getHeader().type = message.getHeader().getType();
					response.getBody().addField("Tournament Color", selectedColor);
					
					this.client.send(response);
					System.out.println("Result of MSG: \n" + response.toString());
				}else{
					result = JOptionPane.showOptionDialog(null,
							"Please choose the tournament color", "Pick a Colour", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
							null, GAMEConfig.TOKEN_COLORS_FOUR, GAMEConfig.TOKEN_COLORS_FOUR[0]);

					selectedColor = GAMEConfig.TOKEN_COLORS_FOUR[result];
					response = new Message();
					response.getHeader().sender = ID;
					response.getHeader().state = message.getHeader().getState();
					response.getHeader().type = message.getHeader().getType();
					response.getBody().addField("Tournament Color", selectedColor);
					
					this.client.send(response);
					System.out.println("Select Color MSG: \n" + response.toString());
				}
				break;
			case GAMEConfig.PLAY_OR_WITHDRAW:
				result = JOptionPane.showConfirmDialog(null, ID + ": Do you want withdraw?",
						"Play or Withdraw", JOptionPane.YES_NO_OPTION);
				
				response = new Message();
				response.getHeader().sender = ID;
				response.getHeader().state = message.getHeader().getState();
				response.getHeader().type = message.getHeader().getType();
				response.getBody().addField("POW Choice", (result == 0 ? GAMEConfig.POW_WITHDRAW : GAMEConfig.POW_PLAY));
				this.client.send(response);
				
				System.out.println("POW MSG: \n" + response.toString());
				break;
			case GAMEConfig.CHECK_IVANHOE:
				cardName = message.getBody().getField("Card Name").toString();
				fromID = message.getBody().getField("From ID").toString();				
				
				result = JOptionPane.showConfirmDialog(null, ID + ": " + fromID + " played " + cardName + ".\nDo you want play Ivanhoe?",
						"Ivanhoe Choice of " + cardName, JOptionPane.YES_NO_OPTION);
				
				response = new Message();
				response.getHeader().sender = ID;
				response.getHeader().state = message.getHeader().getState();
				response.getHeader().type = message.getHeader().getType();
				response.getBody().addField("Ivanhoe Choice", (result == 0 ? GAMEConfig.IVANHOE_YES : GAMEConfig.IVANHOE_NO));
				this.client.send(response);
				
				System.out.println("Ivanhoe Choice MSG: \n" + response.toString());
				break;
			case GAMEConfig.MAIDEN_PUNISH:
				choices = message.getBody().getField("Maiden Punish").toString();
				tokenList = choices.split(",");
				result = JOptionPane.showOptionDialog(null,
						"Please choose the tournament color", "Pick a Colour", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, tokenList, tokenList[0]);

				selectedColor = tokenList[result];
				response = new Message();
				response.getHeader().sender = ID;
				response.getHeader().state = message.getHeader().getState();
				response.getHeader().type = message.getHeader().getType();
				response.getBody().addField("Maiden Punish", selectedColor);
				
				this.client.send(response);
				System.out.println("Select Maiden Punish MSG: \n" + response.toString());				
				break;
			case GAMEConfig.CHANGE_TOURNAMENT_COLOR:
				choices = message.getBody().getField("Change Tournament Color").toString();
				tokenList = choices.split(",");
				result = JOptionPane.showOptionDialog(null,
						"Please choose new tournament color", "Pick a Colour", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, tokenList, tokenList[0]);

				selectedColor = tokenList[result];
				response = new Message();
				response.getHeader().sender = ID;
				response.getHeader().state = message.getHeader().getState();
				response.getHeader().type = message.getHeader().getType();
				response.getBody().addField("Change Tournament Color", selectedColor);
				
				this.client.send(response);
				System.out.println("Select Change Tournament Color MSG: \n" + response.toString());					
				break;
			case GAMEConfig.WIN_TOURNAMENT:
				result = JOptionPane.showOptionDialog(null,
						"Please choose the token color", "Pick a Colour", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, GAMEConfig.TOKEN_COLORS_FIVE, GAMEConfig.TOKEN_COLORS_FIVE[0]);
				selectedColor = GAMEConfig.TOKEN_COLORS_FIVE[result];
				
				response = new Message();
				response.getHeader().sender = ID;
				response.getHeader().state = message.getHeader().getState();
				response.getHeader().type = message.getHeader().getType();
				response.getBody().addField("Token Color", selectedColor);
				
				this.client.send(response);
				System.out.println("Result of MSG: \n" + response.toString());
				break;
			case GAMEConfig.GAME_OVER:
				JOptionPane.showMessageDialog(new JFrame(), GAMEConfig.TYPE_GAME_OVER + "\n" + winnerID + GAMEConfig.WINNER, GAMEConfig.TYPE_GAME_OVER, JOptionPane.INFORMATION_MESSAGE);
				break;
			default:
				break;
		}
	}

	public AppClient getClient(){ return this.client; }
	public void setClientJoined(boolean status) { this.clientJoined = status; }
}