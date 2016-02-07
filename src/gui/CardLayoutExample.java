package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
public class CardLayoutExample extends JFrame { 
	JPanel slides;
	CardLayout layoutManager;
	public CardLayoutExample(String title) { 
		super(title);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        // Create a JPanel with a CardLayout manager for the slides
		slides = new JPanel();
		slides.setBackground(Color.WHITE); 
		slides.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
		slides.setLayout(layoutManager = new CardLayout(1,5)); 
		slides.add("1", new JLabel("Monday")); 
		slides.add("2", new JLabel("Thuesday")); 
		slides.add("3", new JLabel("Wednesday")); 
		slides.add("4", new JLabel("Thursday")); 
		slides.add("5", new JLabel("Friday")); 
		getContentPane().add(slides);
		

        // Now add some slide show buttons for forward and reverse
		JButton rev = new BasicArrowButton(JButton.WEST); getContentPane().add(rev);
		JButton fwd = new BasicArrowButton(JButton.EAST); getContentPane().add(fwd);
        // Set up the listeners using anonymous classes
		rev.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
                layoutManager.previous(slides);
            } });

		fwd.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
                layoutManager.next(slides);
            }});


		
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);
    }

	public static void main(String[] args) {
		new CardLayoutExample("Simple Slide Show").setVisible(true);
	} 
}