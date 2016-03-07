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

	
	public JButton applyButton, submitButton;
	
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
	
	public HandPanel(ClientPanel client, int x, int y, int width, int height) { 
		setLayout(null);
		this.client = client;	
		layeredPane = new JLayeredPane();
		layeredPane.setSize(new Dimension(width, height));
		layeredPane.setBorder(BorderFactory.createTitledBorder(""));
		layeredPane.addMouseListener(this);		
		add(layeredPane);
		
		applyButton = new JButton("Apply");
		applyButton.setLocation(GUIConfig.HANDPANEL_APPLY_BUTTON_LOCATION_X, GUIConfig.HANDPANEL_APPLY_BUTTON_LOCATION_Y);
		applyButton.setSize(GUIConfig.HANDPANEL_BUTTON_WIDTH, GUIConfig.HANDPANEL_BUTTON_HEIGHT);
		applyButton.setEnabled(Boolean.TRUE);
		applyButton.addMouseListener(this);
		client.add(applyButton);
		
		submitButton = new JButton("Submit");
		submitButton.setLocation(GUIConfig.HANDPANEL_SUBMIT_BUTTON_LOCATION_X, GUIConfig.HANDPANEL_SUBMIT_BUTTON_LOCATION_Y);
		submitButton.setSize(GUIConfig.HANDPANEL_BUTTON_WIDTH, GUIConfig.HANDPANEL_BUTTON_HEIGHT);
		submitButton.setEnabled(Boolean.TRUE);
		submitButton.addMouseListener(this);
		client.add(submitButton);
		
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
		applyButton.setVisible(Boolean.FALSE);
		submitButton.setVisible(Boolean.FALSE);
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
			if (e.getSource() == applyButton){
				if (client.getClient() != null){
					if (client.applyEnable){
						String selectedCard = client.selected.keySet().toString();
						client.userPanel.updateHand(client.userPanel.hand);
												
						Message message = new Message();
						message.getHeader().setType(GAMEConfig.TYPE_CONFIRM_REQUEST);
						message.getHeader().setState(GAMEConfig.CONFIRM_REQUEST);
						message.getBody().addField("Selected Card", selectedCard.substring(1, selectedCard.length()-1));
						client.getClient().send(message);
					}
				}
			}else if (e.getSource() == submitButton){
				if (client.getClient() != null){
					if (client.submitEnable){
						String selectedCard = client.selected.keySet().toString();
						Message message = new Message();
						message.getHeader().setType(GAMEConfig.TYPE_PLAY_CARD);
						message.getHeader().setState(GAMEConfig.PLAY_CARD);
						message.getBody().addField("Selected Card", selectedCard.substring(1, selectedCard.length()-1));
						message.getBody().addField("Submission", "OK");
						client.getClient().send(message);

						client.selected.clear();
						client.submitEnable = Boolean.FALSE;
						client.applyEnable = Boolean.FALSE;
					}
				}
			} else if (e.getButton() == MouseEvent.BUTTON1){
				if (e.getY() < 190 && e.getY() > 10 && !client.submitEnable){
					int index = (e.getX()-10)/GUIConfig.HANDPANEL_USER_CARD_SIZE;						
					if (index < numCard){
						selected[index] = !selected[index];
						if (selected[index]){ 
							cards.get(index).setLocation(cards.get(index).getX()-10, cards.get(index).getY()-10); 
							client.selected.put(index, index);
						} else { 
							cards.get(index).setLocation(cards.get(index).getX()+10, cards.get(index).getY()+10); 
							client.selected.remove(index);
						}
					}else if ((e.getX()-10) < ((numCard-1)*GUIConfig.HANDPANEL_USER_CARD_SIZE+GUIConfig.HANDPANEL_USER_CARD_WIDTH)){
						selected[numCard-1] = !selected[numCard-1];
						if (selected[numCard-1]){ 
							cards.get(numCard-1).setLocation(cards.get(numCard-1).getX()-10, cards.get(numCard-1).getY()-10); 
							client.selected.put(numCard-1, numCard-1);
						} else { 
							cards.get(numCard-1).setLocation(cards.get(numCard-1).getX()+10, cards.get(numCard-1).getY()+10); 
							client.selected.remove(numCard-1);
						}				
					}
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {	
		if (isUser){	
			if (e.getButton() == MouseEvent.BUTTON3 && e.getSource() != applyButton && e.getSource() != submitButton){
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
