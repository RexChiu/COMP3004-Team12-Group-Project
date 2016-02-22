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
	public boolean 	isBlue()			{ return this.color.equalsIgnoreCase(GAMEConfig.COLOR_BLUE);	}
	public boolean 	isYellow()			{ return this.color.equalsIgnoreCase(GAMEConfig.COLOR_YELLOW);	}
	public boolean 	isGreen()			{ return this.color.equalsIgnoreCase(GAMEConfig.COLOR_GREEN);	}
	
	// Simple Card of Supporter (White Card)
	public boolean 	isMaiden() 			{ return this.name.equalsIgnoreCase(GAMEConfig.MAIDEN);			}
	public boolean 	isSquire() 			{ return this.name.equalsIgnoreCase(GAMEConfig.SQUIRE);			}
	
	// Special Card of Action Card
	public boolean 	isIvanhoe()			{ return this.name.equalsIgnoreCase(GAMEConfig.IVANHOE);		}
	public boolean 	isShield()			{ return this.name.equalsIgnoreCase(GAMEConfig.SHIELD);			}
	public boolean 	isStunned()			{ return this.name.equalsIgnoreCase(GAMEConfig.STUNNED);		}
	
	// Affect Display of Action Card
	public boolean 	isBreakLance()		{ return this.name.equalsIgnoreCase(GAMEConfig.BREAK_LANCE);	}
	public boolean 	isRiposte()			{ return this.name.equalsIgnoreCase(GAMEConfig.RIPOSTE);		}
	public boolean 	isDodge()			{ return this.name.equalsIgnoreCase(GAMEConfig.DODGE);			}
	public boolean 	isRetreat()			{ return this.name.equalsIgnoreCase(GAMEConfig.RETREAT);		}
	public boolean 	isKnockDown()		{ return this.name.equalsIgnoreCase(GAMEConfig.KNOCK_DOWN);		}
	public boolean 	isOutmaneuver()		{ return this.name.equalsIgnoreCase(GAMEConfig.OUTMANEUVER);	}
}