package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import config.GAMEConfig;
import config.GUIConfig;
import config.IMGConfig;
import game.Hand;

public class DisplayPanel extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 5174646146646913340L;
	
	JLabel view = new JLabel();	
	ClientPanel client;

	String[] listLarge = {IMGConfig.ADAPT_LARGE, IMGConfig.AXEFIVE_LARGE, IMGConfig.BREAKLANCE_LARGE, IMGConfig.CHANGEWEAPON_LARGE, 
			IMGConfig.CHARGE_LARGE, IMGConfig.COUNTERCHARGE_LARGE, IMGConfig.DISGRACE_LARGE, IMGConfig.DODGE_LARGE, IMGConfig.DROPWEAPON_LARGE,
			IMGConfig.IVANHOE_LARGE, IMGConfig.JOUSTINGFIVE_LARGE, IMGConfig.KNOCKDOWN_LARGE, IMGConfig.MAIDENSIX_LARGE, IMGConfig.SHIELD_LARGE};	
	String[] listSmall = {IMGConfig.ADAPT_SMALL, IMGConfig.AXEFIVE_SMALL, IMGConfig.BREAKLANCE_SMALL, IMGConfig.CHANGEWEAPON_SMALL, 
					IMGConfig.CHARGE_SMALL, IMGConfig.COUNTERCHARGE_SMALL, IMGConfig.DISGRACE_SMALL, IMGConfig.DODGE_SMALL, IMGConfig.DROPWEAPON_SMALL,
					IMGConfig.IVANHOE_SMALL, IMGConfig.JOUSTINGFIVE_SMALL, IMGConfig.KNOCKDOWN_SMALL, IMGConfig.MAIDENSIX_SMALL, IMGConfig.SHIELD_SMALL};
	String[] listTiny = {IMGConfig.ADAPT_TINY, IMGConfig.AXEFIVE_TINY, IMGConfig.BREAKLANCE_TINY, IMGConfig.CHANGEWEAPON_TINY, 
					IMGConfig.CHARGE_TINY, IMGConfig.COUNTERCHARGE_TINY, IMGConfig.DISGRACE_TINY, IMGConfig.DODGE_TINY, IMGConfig.DROPWEAPON_TINY,
					IMGConfig.IVANHOE_TINY, IMGConfig.JOUSTINGFIVE_TINY, IMGConfig.KNOCKDOWN_TINY, IMGConfig.MAIDENSIX_TINY, IMGConfig.SHIELD_TINY};

	URL[] urlTiny = new URL[13];
	URL[] urlLarge = new URL[13];
	boolean[] selected = new boolean[13];
	private int numCard = 0;
	private JLayeredPane layeredPane;
	private ArrayList<JLabel> cards = new ArrayList<>();
	public String ID = "";
	
	public DisplayPanel(ClientPanel client, int x, int y, int width, int height) { 
		setLayout(null);
		this.client = client;	
		
		layeredPane = new JLayeredPane();
		layeredPane.setSize(new Dimension(width, height));
		layeredPane.setBorder(BorderFactory.createTitledBorder(""));
		layeredPane.addMouseListener(this);	
		add(layeredPane);
		
		setLocation(x, y);
		setSize(width, height);
		setBackground(Color.WHITE);
	}
	
	public void updateUI(String display){
		if (this.cards.size() != 0)
			this.cards.get(this.cards.size()-1).setVisible(Boolean.FALSE);
		cards.clear();
		layeredPane.removeAll();
		
		Hand hand = new Hand(display);	
		numCard = hand.getSize();
		
		for (int i = 0; i < numCard; i++){
			selected[i] = Boolean.FALSE;
			urlTiny[i] = this.getClass().getResource(hand.getCard(i).getIMG(IMGConfig.IMAGE_SIZE_TINY));
			urlLarge[i] = this.getClass().getResource(hand.getCard(i).getIMG(IMGConfig.IMAGE_SIZE_LARGE));
			cards.add(new JLabel(new ImageIcon(urlTiny[i])));
			cards.get(i).setLocation(i*15+5, 5);
			cards.get(i).setSize(60, 90);
			cards.get(i).setBounds(i*15+5, 5, 60, 90);
			
			layeredPane.add(cards.get(i));
			layeredPane.setLayer(cards.get(i), i);
		}
	}

	public void mousePressed(MouseEvent e) {			
		if (e.getButton() == MouseEvent.BUTTON3){
			if (e.getY() < 190 && e.getY() > 10){
				int index = (e.getX()-10)/15;
				if (index < numCard){
					view = new JLabel();
					view.setVisible(Boolean.TRUE);
					view.setIcon(new ImageIcon(urlLarge[index]));
					view.setLocation(400, 120);
					view.setSize(240,360);
					client.add(view);
					client.setComponentZOrder(view,0);	
					client.repaint();
				}else if ((e.getX()-10) < ((numCard-1)*GUIConfig.HANDPANEL_PLAYER_CARD_SIZE+GUIConfig.HANDPANEL_PLAYER_CARD_WIDTH)){
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
	
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1){
			if (e.getY() < 190 && e.getY() > 10){
				int index = (e.getX()-10)/GUIConfig.HANDPANEL_PLAYER_CARD_SIZE;	
				
				int selectedDisplayIndex = -1;
				String key = "";
				
				
				if (this.client.userPanel.ID.equalsIgnoreCase(ID)){
					key = GAMEConfig.SELECTED_DISPLAY_INDEX;					
					if (this.client.dataPacket.containsKey(key)){
						selectedDisplayIndex = Integer.parseInt(this.client.dataPacket.get(key));
					}
				}else{
					this.client.dataPacket.put(GAMEConfig.SELECTED_TARGET_ID, ID);
					key = GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX;
					if (this.client.dataPacket.containsKey(key)){
						selectedDisplayIndex = Integer.parseInt(this.client.dataPacket.get(key));
					}					
				}
						
				if (index < numCard){
					if (selectedDisplayIndex != -1 && selectedDisplayIndex != index){
						selected[selectedDisplayIndex] = !selected[selectedDisplayIndex];
						cards.get(selectedDisplayIndex).setLocation(cards.get(selectedDisplayIndex).getX()+5, cards.get(selectedDisplayIndex).getY()+5); 
					}
					
					this.client.dataPacket.put(key, Integer.toString(selectedDisplayIndex != index ? index : -1)); 						
			
					selected[index] = !selected[index];
					if (selected[index]){ 
						cards.get(index).setLocation(cards.get(index).getX()-5, cards.get(index).getY()-5); 
					} else { 
						cards.get(index).setLocation(cards.get(index).getX()+5, cards.get(index).getY()+5); 
					}
				}else if ((e.getX()-10) < ((numCard-1)*GUIConfig.HANDPANEL_USER_CARD_SIZE+GUIConfig.HANDPANEL_USER_CARD_WIDTH)){
					if (selectedDisplayIndex != -1 && selectedDisplayIndex != numCard-1){
						selected[selectedDisplayIndex] = !selected[selectedDisplayIndex];
						cards.get(selectedDisplayIndex).setLocation(cards.get(selectedDisplayIndex).getX()+5, cards.get(selectedDisplayIndex).getY()+5); 
					}
						
					this.client.dataPacket.put(key, Integer.toString(selectedDisplayIndex != numCard-1 ? numCard-1 : -1));						
					
					selected[numCard-1] = !selected[numCard-1];
					if (selected[numCard-1]){ 
						cards.get(numCard-1).setLocation(cards.get(numCard-1).getX()-5, cards.get(numCard-1).getY()-5);
					} else { 
						cards.get(numCard-1).setLocation(cards.get(numCard-1).getX()+5, cards.get(numCard-1).getY()+5); 
					}				
				}
			}
		}
			
	}	

	public void mouseReleased(MouseEvent e){
		view.setVisible(Boolean.FALSE);
		view.removeAll();
		client.remove(view);
		client.repaint();	
	}	
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
}
