package game;

import config.GAMEConfig;

public class Card implements Cloneable{
	
	private String 	name;
	private String 	color;
	private int 	value;

	public Card(String name, String color, int value) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.color = color;
		this.value = value;
	}
	
	// Collection of getter 
	public String 	getName() 			{ return this.name;  }
	public String 	getColor() 			{ return this.color; }
	public int 		getValue() 			{ return this.value; }

	// Check the card is action card
	public boolean 	isAction()			{ return this.color.equalsIgnoreCase(GAMEConfig.ACTION_CARD); }
	
	// Simple Card of Color Card
	public boolean 	isPurple()			{ return this.color.equalsIgnoreCase(GAMEConfig.COLOR_PURPLE);	}
	public boolean 	isRed()				{ return this.color.equalsIgnoreCase(GAMEConfig.COLOR_RED);		}
}