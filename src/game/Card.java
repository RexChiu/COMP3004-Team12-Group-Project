package game;

import config.GAMEConfig;
import config.IMGConfig;

public class Card implements Cloneable{
	
	private String 	name;
	private String 	color;
	private int 	value;

	public Card(String card){
		this.name = card.split(",")[0];
		this.color = card.split(",")[1];
		this.value = Integer.parseInt(card.split(",")[2]);
	}
	
	public Card(String name, String color, int value) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.color = color;
		this.value = value;
	}
	
	public String getIMG(String size) { 
		if (isAction()){
			if (size.equalsIgnoreCase(IMGConfig.IMAGE_SIZE_TINY)){
				return IMGConfig.RESOURCE_ACTION_TINY + this.name + ".png";
			} else if (size.equalsIgnoreCase(IMGConfig.IMAGE_SIZE_SMALL)){
				return IMGConfig.RESOURCE_ACTION_SMALL + this.name + ".png";				
			}else{ 
				return IMGConfig.RESOURCE_ACTION_LARGE + this.name + ".png";
			}
		}else{
			if (size.equalsIgnoreCase(IMGConfig.IMAGE_SIZE_TINY)){
				return IMGConfig.RESOURCE_SIMPLE_TINY + this.name + IMGConfig.INTEGER_TO_STRING[this.value] + ".png";
			} else if (size.equalsIgnoreCase(IMGConfig.IMAGE_SIZE_SMALL)){
				return IMGConfig.RESOURCE_SIMPLE_SMALL + this.name + IMGConfig.INTEGER_TO_STRING[this.value] + ".png";				
			}else{ 
				return IMGConfig.RESOURCE_SIMPLE_LARGE + this.name + IMGConfig.INTEGER_TO_STRING[this.value] + ".png";
			}
		}
	}
	
	// Collection of getter 
	public String 	getName() 			{ return this.name;  }
	public String 	getColor() 			{ return this.color; }
	public int 		getValue() 			{ return this.value; }
	
	// Compare two card and return true if they are equals
	public boolean	equals(Card card)	{ 
	    return (this.name.equals(card.name) && this.color.equals(card.color) && value == card.value);    
	}
	
	// Check the card is action card
	public boolean 	isAction()			{ return this.color.equalsIgnoreCase(GAMEConfig.ACTION_CARD); 		}
	public boolean	isSupporter()		{ return this.color.equalsIgnoreCase(GAMEConfig.SUPPORTERS_WHITE);	}
	
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
	
	// Affect Tournament Colour
	public boolean	isUnhorse()			{ return this.name.equalsIgnoreCase(GAMEConfig.UNHORSE);		}
	public boolean	isChangeWeapon()	{ return this.name.equalsIgnoreCase(GAMEConfig.CHANGE_WEAPON);	}
	public boolean	isDropWeapon()		{ return this.name.equalsIgnoreCase(GAMEConfig.DROP_WEAPON);	}
	
	// Affect Display of Action Card
	public boolean 	isBreakLance()		{ return this.name.equalsIgnoreCase(GAMEConfig.BREAK_LANCE);	}
	public boolean 	isRiposte()			{ return this.name.equalsIgnoreCase(GAMEConfig.RIPOSTE);		}
	public boolean 	isDodge()			{ return this.name.equalsIgnoreCase(GAMEConfig.DODGE);			}
	public boolean 	isRetreat()			{ return this.name.equalsIgnoreCase(GAMEConfig.RETREAT);		}
	public boolean 	isKnockDown()		{ return this.name.equalsIgnoreCase(GAMEConfig.KNOCK_DOWN);		}
	public boolean 	isOutmaneuver()		{ return this.name.equalsIgnoreCase(GAMEConfig.OUTMANEUVER);	}
	public boolean 	isCharge()			{ return this.name.equalsIgnoreCase(GAMEConfig.CHARGE);			}
	public boolean 	isCountercharge()	{ return this.name.equalsIgnoreCase(GAMEConfig.COUNTERCHARGE);	}
	public boolean 	isDisgrace()		{ return this.name.equalsIgnoreCase(GAMEConfig.DISGRACE);		}
	public boolean 	isAdapt()			{ return this.name.equalsIgnoreCase(GAMEConfig.ADAPT);			}
	public boolean 	isOutwit()			{ return this.name.equalsIgnoreCase(GAMEConfig.OUTWIT);			}
	public String	toString()			{ return this.name + "," + this.color + "," + this.value; 		}
}