package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;

import javax.swing.*;

import config.GUIConfig;
import config.IMGConfig;
import game.Card;
import game.Hand;
import game.Player;
import network.AppClient;
import message.Message;

public class HandPanel extends JPanel implements MouseListener{

	/**
	 * Hand Panel for the Client Ivanhoe
	 */

	
	public JButton applyButton, submitButton;
	
	private ClientPanel client;
	private JLabel view = new JLabel();	

	private URL[] urlSmall = new URL[GUIConfig.HANDPANEL_MAX_CARD];
	private URL[] urlLarge = new URL[GUIConfig.HANDPANEL_MAX_CARD];
	private JLabel[] card = new JLabel[GUIConfig.HANDPANEL_MAX_CARD];
	private boolean[] selected = new boolean[GUIConfig.HANDPANEL_MAX_CARD];
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
		for (int i = 0; i < numCard; i++){
			selected[i] = Boolean.FALSE;
			urlSmall[i] = this.getClass().getResource(hand.getCard(i).getIMG(IMGConfig.IMAGE_SIZE_SMALL));
			urlLarge[i] = this.getClass().getResource(hand.getCard(i).getIMG(IMGConfig.IMAGE_SIZE_LARGE));
			card[i] = new JLabel(new ImageIcon(urlSmall[i]));
			card[i].setLocation(GUIConfig.HANDPANEL_USER_CARD_LOCATION_X+i*GUIConfig.HANDPANEL_USER_CARD_SIZE, GUIConfig.HANDPANEL_USER_CARD_LOCATION_Y);
			card[i].setSize(GUIConfig.HANDPANEL_USER_CARD_WIDTH, GUIConfig.HANDPANEL_USER_CARD_HEIGHT);
			card[i].setBounds(GUIConfig.HANDPANEL_USER_CARD_LOCATION_X+i*GUIConfig.HANDPANEL_USER_CARD_SIZE, 
					GUIConfig.HANDPANEL_USER_CARD_LOCATION_Y, GUIConfig.HANDPANEL_USER_CARD_WIDTH, GUIConfig.HANDPANEL_USER_CARD_HEIGHT);
			
			layeredPane.add(card[i]);
			layeredPane.setLayer(card[i], i);
		}
	}
	
	public void updateUI(boolean isUser, String size){		
		applyButton.setVisible(Boolean.FALSE);
		submitButton.setVisible(Boolean.FALSE);
		
		this.isUser = isUser;	
		numCard = Integer.parseInt(size);
		for (int i = 0; i < numCard; i++){
			selected[i] = Boolean.FALSE;
			URL url = this.getClass().getResource(IMGConfig.DECK_IVANHOE_TINY);
			card[i] = new JLabel(new ImageIcon(url));
			card[i].setLocation(GUIConfig.HANDPANEL_PLAYER_CARD_LOCATION_X+i*GUIConfig.HANDPANEL_PLAYER_CARD_SIZE, GUIConfig.HANDPANEL_PLAYER_CARD_LOCATION_Y);
			card[i].setSize(GUIConfig.HANDPANEL_PLAYER_CARD_WIDTH, GUIConfig.HANDPANEL_PLAYER_CARD_HEIGHT);
			card[i].setBounds(GUIConfig.HANDPANEL_PLAYER_CARD_LOCATION_X+i*GUIConfig.HANDPANEL_PLAYER_CARD_SIZE, 
					GUIConfig.HANDPANEL_PLAYER_CARD_LOCATION_Y, GUIConfig.HANDPANEL_PLAYER_CARD_WIDTH, GUIConfig.HANDPANEL_PLAYER_CARD_HEIGHT);
			
			layeredPane.add(card[i]);
			layeredPane.setLayer(card[i], i);
		}
		
	}
	
	public void mouseClicked(MouseEvent e) {
		if (isUser){
			if (e.getSource() == applyButton){
				if (client.getClient() != null){
					Message message = new Message();
					client.getClient().send(message);
				}
			}else if (e.getSource() == submitButton){
				if (client.getClient() != null){
					Message message = new Message();
					client.getClient().send(message);
				}
			} else if (e.getButton() == MouseEvent.BUTTON1){
				if (e.getY() < 190 && e.getY() > 10){
					int index = (e.getX()-10)/GUIConfig.HANDPANEL_USER_CARD_SIZE;	
					if (index < numCard){
						selected[index] = !selected[index];
						if (selected[index]){ card[index].setLocation(card[index].getX()-10, card[index].getY()-10); }
						else{ card[index].setLocation(card[index].getX()+10, card[index].getY()+10); }
					}else if ((e.getX()-10) < ((numCard-1)*GUIConfig.HANDPANEL_USER_CARD_SIZE+GUIConfig.HANDPANEL_USER_CARD_WIDTH)){
						System.out.println(numCard-1);
						selected[numCard-1] = !selected[numCard-1];
						if (selected[numCard-1]){ card[numCard-1].setLocation(card[numCard-1].getX()-10, card[numCard-1].getY()-10); }
						else{ card[numCard-1].setLocation(card[numCard-1].getX()+10, card[numCard-1].getY()+10); }				
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
