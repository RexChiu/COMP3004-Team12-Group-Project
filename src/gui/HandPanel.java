package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import config.GAMEConfig;
import config.GUIConfig;
import config.IMGConfig;
import game.Hand;
import message.Message;

public class HandPanel extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5036118621399228672L;

	/**
	 * Hand Panel for the Client Ivanhoe
	 */	
	public JButton playCardButton, endTurnButton;
	
	private ClientPanel client;
	private JLabel view = new JLabel();	

	private URL[] urlSmall = new URL[GUIConfig.HANDPANEL_MAX_CARD];
	private URL[] urlLarge = new URL[GUIConfig.HANDPANEL_MAX_CARD];
	//private JLabel[] card = new JLabel[GUIConfig.HANDPANEL_MAX_CARD];
	private ArrayList<JLabel> cards = new ArrayList<>();
	public boolean[] selected = new boolean[GUIConfig.HANDPANEL_MAX_CARD];
	private int numCard = 0;
	private boolean isUser = Boolean.TRUE;
	private JLayeredPane layeredPane;
	public String ID = "";
	
	public HandPanel(ClientPanel client, int x, int y, int width, int height) { 
		setLayout(null);
		this.client = client;	
		layeredPane = new JLayeredPane();
		layeredPane.setSize(new Dimension(width, height));
		layeredPane.setBorder(BorderFactory.createTitledBorder(""));
		layeredPane.addMouseListener(this);		
		add(layeredPane);
		
		playCardButton = new JButton("Play Card");
		playCardButton.setLocation(GUIConfig.HANDPANEL_PLAYCARD_BUTTON_LOCATION_X, GUIConfig.HANDPANEL_PLAYCARD_BUTTON_LOCATION_Y);
		playCardButton.setSize(GUIConfig.HANDPANEL_BUTTON_WIDTH, GUIConfig.HANDPANEL_BUTTON_HEIGHT);
		playCardButton.setEnabled(Boolean.TRUE);
		playCardButton.addMouseListener(this);
		client.add(playCardButton);
		
		endTurnButton = new JButton("End Turn");
		endTurnButton.setLocation(GUIConfig.HANDPANEL_ENDTURN_BUTTON_LOCATION_X, GUIConfig.HANDPANEL_ENDTURN_BUTTON_LOCATION_Y);
		endTurnButton.setSize(GUIConfig.HANDPANEL_BUTTON_WIDTH, GUIConfig.HANDPANEL_BUTTON_HEIGHT);
		endTurnButton.setEnabled(Boolean.TRUE);
		endTurnButton.addMouseListener(this);
		client.add(endTurnButton);
		
		setLocation(x, y);
		setSize(width, height);
		setBackground(Color.WHITE);
	}	

	public void updateUI(String data){
		if (this.cards.size() != 0)
			this.cards.get(this.cards.size()-1).setVisible(Boolean.FALSE);
		this.cards.clear();
		this.layeredPane.removeAll();
		
		Hand hand = new Hand(data);		
		this.numCard = hand.getSize();
		
		for (int i = 0; i < numCard; i++){
			selected[i] = Boolean.FALSE;
			urlSmall[i] = this.getClass().getResource(hand.getCard(i).getIMG(IMGConfig.IMAGE_SIZE_SMALL));
			urlLarge[i] = this.getClass().getResource(hand.getCard(i).getIMG(IMGConfig.IMAGE_SIZE_LARGE));
			cards.add(new JLabel(new ImageIcon(urlSmall[i])));
			cards.get(i).setVisible(Boolean.TRUE);
			cards.get(i).setLocation(GUIConfig.HANDPANEL_USER_CARD_LOCATION_X+i*GUIConfig.HANDPANEL_USER_CARD_SIZE, GUIConfig.HANDPANEL_USER_CARD_LOCATION_Y);
			cards.get(i).setSize(GUIConfig.HANDPANEL_USER_CARD_WIDTH, GUIConfig.HANDPANEL_USER_CARD_HEIGHT);
			cards.get(i).setBounds(GUIConfig.HANDPANEL_USER_CARD_LOCATION_X+i*GUIConfig.HANDPANEL_USER_CARD_SIZE, 
					GUIConfig.HANDPANEL_USER_CARD_LOCATION_Y, GUIConfig.HANDPANEL_USER_CARD_WIDTH, GUIConfig.HANDPANEL_USER_CARD_HEIGHT);
			
			layeredPane.add(cards.get(i));
			layeredPane.setLayer(cards.get(i), i);
		}
	}
	
	public void updateUI(boolean isUser, String size){
		if (this.cards.size() != 0)
			this.cards.get(this.cards.size()-1).setVisible(Boolean.FALSE);
		this.cards.clear();
		this.layeredPane.removeAll();
		
		this.isUser = isUser;		
		this.playCardButton.setVisible(Boolean.FALSE);
		this.endTurnButton.setVisible(Boolean.FALSE);
		
		this.numCard = Integer.parseInt(size);
		
		for (int i = 0; i < numCard; i++){
			selected[i] = Boolean.FALSE;
			URL url = this.getClass().getResource(IMGConfig.DECK_IVANHOE_TINY);
			cards.add(new JLabel(new ImageIcon(url)));
			cards.get(i).setLocation(GUIConfig.HANDPANEL_PLAYER_CARD_LOCATION_X+i*GUIConfig.HANDPANEL_PLAYER_CARD_SIZE, GUIConfig.HANDPANEL_PLAYER_CARD_LOCATION_Y);
			cards.get(i).setSize(GUIConfig.HANDPANEL_PLAYER_CARD_WIDTH, GUIConfig.HANDPANEL_PLAYER_CARD_HEIGHT);
			cards.get(i).setBounds(GUIConfig.HANDPANEL_PLAYER_CARD_LOCATION_X+i*GUIConfig.HANDPANEL_PLAYER_CARD_SIZE, 
					GUIConfig.HANDPANEL_PLAYER_CARD_LOCATION_Y, GUIConfig.HANDPANEL_PLAYER_CARD_WIDTH, GUIConfig.HANDPANEL_PLAYER_CARD_HEIGHT);
			
			layeredPane.add(cards.get(i));
			layeredPane.setLayer(cards.get(i), i);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if (isUser){
			if (e.getSource() == playCardButton){
				if (client.getClient() != null){
					client.userPanel.updateHand(client.userPanel.hand);
					if(!client.userPanel.display.equals(""))
						client.userPanel.updateDisplay(client.userPanel.display);
					for (String playerID : client.playerPanel.keySet()){
						String display = client.playerPanel.get(playerID).display;
						if (!display.equals(""))
							client.playerPanel.get(playerID).displayPanel.updateUI(display);
					}

					Message message = new Message();
					message.getHeader().sender = this.client.userPanel.infoButton.getText().trim();
					message.getHeader().receiver = "Ivanhoe";
					message.getHeader().setType(GAMEConfig.TYPE_PLAY_CARD);
					message.getHeader().setState(GAMEConfig.PLAY_CARD);
					
					if (this.client.dataPacket.containsKey(GAMEConfig.SELECTED_HAND_INDEX))
						message.getBody().addField(GAMEConfig.SELECTED_HAND_INDEX, 				this.client.dataPacket.get(GAMEConfig.SELECTED_HAND_INDEX));
					if (this.client.dataPacket.containsKey(GAMEConfig.SELECTED_DISPLAY_INDEX))
						message.getBody().addField(GAMEConfig.SELECTED_DISPLAY_INDEX,			this.client.dataPacket.get(GAMEConfig.SELECTED_DISPLAY_INDEX));
					if (this.client.dataPacket.containsKey(GAMEConfig.SELECTED_TARGET_ID))
						message.getBody().addField(GAMEConfig.SELECTED_TARGET_ID, 			this.client.dataPacket.get(GAMEConfig.SELECTED_TARGET_ID));
					if (this.client.dataPacket.containsKey(GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX))
						message.getBody().addField(GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX, 	this.client.dataPacket.get(GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX));	
					this.client.dataPacket = new HashMap<String, String>();
					if (message.getBody().hasField(GAMEConfig.SELECTED_HAND_INDEX))
						client.getClient().send(message);
					System.out.print(message.toString());
				}			
			}else if (e.getSource() == endTurnButton){
				if (client.getClient() != null){
					Message message = new Message();
					message.getHeader().sender = this.client.userPanel.ID;
					message.getHeader().setType(GAMEConfig.TYPE_END_TURN);
					message.getHeader().setState(GAMEConfig.END_TURN);
					client.getClient().send(message);
				}
			} else if (e.getButton() == MouseEvent.BUTTON1){
				if (e.getY() < 190 && e.getY() > 10){
					int index = (e.getX()-10)/GUIConfig.HANDPANEL_USER_CARD_SIZE;	
					int selectedHandIndex = -1;
					String key = GAMEConfig.SELECTED_HAND_INDEX;
					
					if (this.client.dataPacket.containsKey(key)){
						selectedHandIndex = Integer.parseInt(this.client.dataPacket.get(key));
					}
					
					if (index < numCard){
						if (selectedHandIndex != -1 && selectedHandIndex != index){
							selected[selectedHandIndex] = !selected[selectedHandIndex];
							cards.get(selectedHandIndex).setLocation(cards.get(selectedHandIndex).getX()+10, cards.get(selectedHandIndex).getY()+10); 
						}
						
						this.client.dataPacket.put(key, Integer.toString(selectedHandIndex != index ? index : -1));
												
						selected[index] = !selected[index];
						if (selected[index]){ 
							cards.get(index).setLocation(cards.get(index).getX()-10, cards.get(index).getY()-10); 
						} else { 
							cards.get(index).setLocation(cards.get(index).getX()+10, cards.get(index).getY()+10); 
						}
					}else if ((e.getX()-10) < ((numCard-1)*GUIConfig.HANDPANEL_USER_CARD_SIZE+GUIConfig.HANDPANEL_USER_CARD_WIDTH)){
						if (selectedHandIndex != -1 && selectedHandIndex != numCard-1){
							selected[selectedHandIndex] = !selected[selectedHandIndex];
							cards.get(selectedHandIndex).setLocation(cards.get(selectedHandIndex).getX()+10, cards.get(selectedHandIndex).getY()+10); 
						}
						
						this.client.dataPacket.put(key, Integer.toString(selectedHandIndex != numCard-1 ? numCard-1 : -1));

						selected[numCard-1] = !selected[numCard-1];
						if (selected[numCard-1]){ 
							cards.get(numCard-1).setLocation(cards.get(numCard-1).getX()-10, cards.get(numCard-1).getY()-10); 
							
						} else { 
							cards.get(numCard-1).setLocation(cards.get(numCard-1).getX()+10, cards.get(numCard-1).getY()+10); 
						}				
					}
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {	
		if (isUser){	
			if (e.getButton() == MouseEvent.BUTTON3 && e.getSource() != playCardButton && e.getSource() != endTurnButton){
				if (e.getY() < 190 && e.getY() > 10){
					int index = (e.getX()-10)/GUIConfig.HANDPANEL_USER_CARD_SIZE;
					if (index < numCard){
						view = new JLabel();
						view.setVisible(Boolean.TRUE);
						view.setIcon(new ImageIcon(urlLarge[index]));
						view.setLocation(GUIConfig.HANDPANEL_LOCATION_X, GUIConfig.HANDPANEL_LOCATION_Y);
						view.setSize(GUIConfig.HANDPANEL_VIEW_WIDTH, GUIConfig.HANDPANEL_VIEW_HEIGHT);
						client.add(view);
						client.setComponentZOrder(view,0);	
						client.repaint();
					} else if ((e.getX()-10) < ((numCard-1)*GUIConfig.HANDPANEL_USER_CARD_SIZE+GUIConfig.HANDPANEL_USER_CARD_WIDTH)){
						view = new JLabel();
						view.setVisible(Boolean.TRUE);
						view.setIcon(new ImageIcon(urlLarge[numCard-1]));
						view.setLocation(GUIConfig.HANDPANEL_LOCATION_X, GUIConfig.HANDPANEL_LOCATION_Y);
						view.setSize(GUIConfig.HANDPANEL_VIEW_WIDTH, GUIConfig.HANDPANEL_VIEW_HEIGHT);
						client.add(view);
						client.setComponentZOrder(view,0);	
						client.repaint();		
					}
				}	
			} 		
		}
	}	
	
	public void mouseReleased(MouseEvent e){
		if (isUser){
			view.setVisible(Boolean.FALSE);
			view.removeAll();
			client.remove(view);
			client.repaint();	
		}
	}	
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
}
