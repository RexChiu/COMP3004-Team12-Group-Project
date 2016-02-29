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

public class HandPanel extends JPanel implements MouseListener{

	/**
	 * Hand Panel for the Client Ivanhoe
	 */
	private static final long serialVersionUID = 2634805864040454133L;
	JLabel view;	
	boolean display = false;
	ClientPanel client;
	
	public HandPanel(ClientPanel client, int x, int y, int width, int height) { 
		setLayout(null);
		
		this.client = client;
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(new Dimension(width, height));
		layeredPane.setBorder(BorderFactory.createTitledBorder(""));
		layeredPane.addMouseListener(this);	
				
		URL url1 = this.getClass().getResource(IMGConfig.ADAPT_SMALL);
		JLabel card1 = new JLabel(new ImageIcon(url1));
		card1.setLocation(10, 10);
		card1.setSize(120, 180);
		card1.setBounds(10, 10, 120, 180);
		
		layeredPane.add(card1);
		layeredPane.setLayer(card1, 0);
		
		URL url2 = this.getClass().getResource(IMGConfig.AXETHREE_SMALL);
		JLabel card2 = new JLabel(new ImageIcon(url2));
		card2.setLocation(60, 10);
		card2.setSize(120, 180);
		card2.setBounds(60, 10, 120, 180);
		layeredPane.add(card2);
		layeredPane.setLayer(card2, 1);
		
		URL url3 = this.getClass().getResource(IMGConfig.DISGRACE_SMALL);
		JLabel card3 = new JLabel(new ImageIcon(url3));
		card3.setLocation(110, 10);
		card3.setSize(120, 180);
		card3.setBounds(110, 10, 120, 180);
		layeredPane.add(card3);
		layeredPane.setLayer(card3, 2);
		
		URL url4 = this.getClass().getResource(IMGConfig.BREAKLANCE_SMALL);
		JLabel card4 = new JLabel(new ImageIcon(url4));
		card4.setLocation(160, 10);
		card4.setSize(120, 180);
		card4.setBounds(160, 10, 120, 180);
		layeredPane.add(card4);
		layeredPane.setLayer(card4, 3);
		
		URL url5 = this.getClass().getResource(IMGConfig.AXETWO_SMALL);
		JLabel card5 = new JLabel(new ImageIcon(url5));
		card5.setLocation(210, 10);
		card5.setSize(120, 180);
		card5.setBounds(210, 10, 120, 180);
		layeredPane.add(card5);
		layeredPane.setLayer(card5, 4);
		
		URL url6 = this.getClass().getResource(IMGConfig.CHANGEWEAPON_SMALL);
		JLabel card6 = new JLabel(new ImageIcon(url6));
		card6.setLocation(260, 10);
		card6.setSize(120, 180);
		card6.setBounds(260, 10, 120, 180);
		layeredPane.add(card6);
		layeredPane.setLayer(card6, 5);
		
		URL url7 = this.getClass().getResource(IMGConfig.MAIDENSIX_SMALL);
		JLabel card7 = new JLabel(new ImageIcon(url7));
		card7.setLocation(310, 10);
		card7.setSize(120, 180);
		card7.setBounds(310, 10, 120, 180);
		card7.addMouseListener(this);
		layeredPane.add(card7);
		layeredPane.setLayer(card7, 6);
		
		URL url8 = this.getClass().getResource(IMGConfig.IVANHOE_SMALL);
		JLabel card8 = new JLabel(new ImageIcon(url8));
		card8.setLocation(360, 10);
		card8.setSize(120, 180);
		card8.setBounds(360, 10, 120, 180);
		layeredPane.add(card8);
		layeredPane.setLayer(card8, 7);
		
		add(layeredPane);
		
		setLocation(x, y);
		setSize(width, height);
		setBackground(Color.WHITE);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3){
			if (!display){
				URL url = this.getClass().getResource(IMGConfig.MAIDENSIX_LARGE);
				view = new JLabel();
				view.setIcon(new ImageIcon(url));
				view.setLocation(400, 200);
				view.setSize(240,360);
				client.add(view);
				client.setComponentZOrder(view,0);	
				display = !display;
				client.repaint();
			}else{
				display = !display;
				view.setIcon(null);
				view.removeAll();
				client.remove(view);	
				client.repaint();						
			}
		}
	}

	public void mousePressed(MouseEvent e) {
	}
	
	public void mouseReleased(MouseEvent e){
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	public void mouseExited(MouseEvent e) {
		
	}
	
}
