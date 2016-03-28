package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;

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

		Hand hand = new Hand(data);		
		numCard = hand.getSize();

		for (int i = numCard; i < cards.size(); i++)
			cards.get(i).setVisible(Boolean.FALSE);
		
		layeredPane.removeAll();
		cards.clear();
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
		this.isUser = isUser;		
		this.playCardButton.setVisible(Boolean.FALSE);
		this.endTurnButton.setVisible(Boolean.FALSE);
		
		numCard = Integer.parseInt(size);
		
		for (int i = numCard; i < cards.size(); i++)
			cards.get(i).setVisible(Boolean.FALSE);
		
		layeredPane.removeAll();
		cards.clear();
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
				//System.out.println("*******************Apply Button is clicked*******************");
				if (client.getClient() != null){
					client.userPanel.updateHand(client.userPanel.hand);

					Message message = new Message();
					message.getHeader().sender = this.client.userPanel.infoButton.getText().trim();
					message.getHeader().receiver = "Ivanhoe";
					message.getHeader().setType(GAMEConfig.TYPE_PLAY_CARD);
					message.getHeader().setState(GAMEConfig.PLAY_CARD);
					message.getBody().addField("Selected Card Index", 			this.client.selectedHandIndex+"");
					message.getBody().addField("Selected Target", 				this.client.selectedTargetID+"");
					message.getBody().addField("Selected Own Display Index",	this.client.selectedDisplayIndex+"");
					message.getBody().addField("Selected Target Display ID",	this.client.targetDisplayID+"");
					message.getBody().addField("Selected Target Display Index", this.client.targetDisplayIndex+"");
					
					client.getClient().send(message);
					this.client.selectedHandIndex = -1;
					this.client.selectedTargetID = "";
					this.client.selectedDisplayIndex = -1;
					this.client.targetDisplayID = "";
					this.client.targetDisplayIndex = -1;
					//System.out.print(message.toString());
				}
				//System.out.println("*************************************************************");
				
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
					int selectedHandIndex = this.client.selectedHandIndex;
					if (index < numCard){
						if (selectedHandIndex != -1 && selectedHandIndex != index){
							selected[selectedHandIndex] = !selected[selectedHandIndex];
							cards.get(selectedHandIndex).setLocation(cards.get(selectedHandIndex).getX()+10, cards.get(selectedHandIndex).getY()+10); 
						}
						this.client.selectedHandIndex = (selectedHandIndex != index ? index : -1);
						System.out.println("Selected Index: " + this.client.selectedHandIndex);
						
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
						this.client.selectedHandIndex = (selectedHandIndex != numCard-1 ? numCard-1 : -1);

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
