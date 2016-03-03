package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import config.GUIConfig;
import config.IMGConfig;
import game.Hand;
import game.Player;

public class DisplayPanel extends JPanel implements MouseListener{
	
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
	JLabel[] card = new JLabel[13];
	boolean[] selected = new boolean[13];
	private int numCard = 0;
	private JLayeredPane layeredPane;
	
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
		Hand hand = new Hand(display);	
		numCard = hand.getSize();
		
		for (int i = 0; i < numCard; i++){
			selected[i] = Boolean.FALSE;
			urlTiny[i] = this.getClass().getResource(hand.getCard(i).getIMG(IMGConfig.IMAGE_SIZE_TINY));
			urlLarge[i] = this.getClass().getResource(hand.getCard(i).getIMG(IMGConfig.IMAGE_SIZE_LARGE));
			card[i] = new JLabel(new ImageIcon(urlTiny[i]));
			card[i].setLocation(i*15+5, 5);
			card[i].setSize(60, 90);
			card[i].setBounds(i*15+5, 5, 60, 90);
			
			layeredPane.add(card[i]);
			layeredPane.setLayer(card[i], i);
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
	public void mouseReleased(MouseEvent e){
		view.setVisible(Boolean.FALSE);
		view.removeAll();
		client.remove(view);
		client.repaint();	
	}	
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
}
