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
}