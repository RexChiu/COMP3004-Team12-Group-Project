package config;

public class GAMEConfig {

	// Ivanhoe Configuration
	public final static int 	GAME_SETUP 				= 0;
	public final static int 	GAME_READY				= 1;
	public final static int 	SELECT_TOURNAMENT		= 2;
	public final static int 	PLAY_OR_WITHDRAWAL		= 3;
	public final static int		DEAL_A_CARD				= 4;
	public final static int		PLAY_A_CARD				= 5;
	public final static int 	WITHDAWAL				= 6;
	public final static int 	NEXT_PLAYER				= 7;
	public final static int 	NEXT_TOURNAMENT			= 8;
	public final static int 	GAME_OVER				= 9;
	
	// Tokens Configuration
	public static final int 	ANY_FOUR_TOKENS 		= 4;
	public static final int 	ALL_FIVE_TOKENS 		= 5;
	
	// Token Configurat8ion
	public static final String	IMAGE_PATH 				= "PATH";
	public static final String[]TOKEN_COLORS			= {"Purple", "Red", "Blue", "Yellow", "Green"};
	// Hand Configuration
	public static final int 	MIN_CARD = 1;
	
	// Display Configuration
	public static final String 	GREEN_CARD				= "Green";
	public static final String 	STATUS_SHIELD			= "Shield";
	public static final String 	STATUS_STUNNED			= "Stunned";
	
	// Deck Configuration
	public static final int 	NUMBER_PURPLE_THREE		= 4;
	public static final int 	NUMBER_PURPLE_FOUR		= 4;
	public static final int 	NUMBER_PURPLE_FIVE 		= 4;
	public static final int 	NUMBER_PURPLE_SEVER 	= 2;
	
	public static final int 	NUMBER_RED_THREE		= 6;
	public static final int 	NUMBER_RED_FOUR			= 6;
	public static final int 	NUMBER_RED_FIVE 		= 2;

	public static final int 	NUMBER_BLUE_TWO			= 4;
	public static final int 	NUMBER_BLUE_THREE		= 4;
	public static final int 	NUMBER_BLUE_FOUR		= 4;
	public static final int 	NUMBER_BLUE_FIVE 		= 2;

	public static final int 	NUMBER_YELLOW_TWO 		= 4;
	public static final int 	NUMBER_YELLOW_THREE		= 8;
	public static final int 	NUMBER_YELLOW_FOUR		= 2;
	
	public static final int 	NUMBER_GREEN_ONE		= 14;
	
	public static final int 	NUMBER_SQUIRES_TWO		= 8;
	public static final int 	NUMBER_SQUIRES_THREE	= 8;
	
	public static final int 	NUMBER_MAIDENS_SIX		= 4;

	public static final int	 	NUMBER_UNHORSE			= 1;
	public static final int	 	NUMBER_CHANGE_WEAPON	= 1;
	public static final int 	NUMBER_DROP_WEAPON		= 1;
	public static final int 	NUMBER_SHIELD			= 1;
	public static final int 	NUMBER_STUNNED			= 1;
	public static final int 	NUMBER_IVANHOE			= 1;
	public static final int 	NUMBER_BREAK_LANCE		= 1;
	public static final int 	NUMBER_RIPOSTE			= 3;
	public static final int 	NUMBER_DODGE			= 1;
	public static final int 	NUMBER_RETREAT			= 1;
	public static final int 	NUMBER_KNOCK_DOWN		= 2;
	public static final int 	NUMBER_OUTMANEUVER		= 1;
	public static final int 	NUMBER_CHARGE			= 1;
	public static final int 	NUMBER_COUNTERCHARGE	= 1;
	public static final int 	NUMBER_DISGRACE			= 1;
	public static final int 	NUMBER_ADAPT			= 1;
	public static final int 	NUMBER_OUTFIT			= 1;

	
	// Card Configuration
	public static final String 	COLOR_PURPLE 			= "Purple";
	public static final String 	COLOR_RED 				= "Red";
	public static final String 	COLOR_BLUE 				= "Blue";
	public static final String 	COLOR_YELLOW 			= "Yellow";
	public static final String 	COLOR_GREEN 			= "Green";
	public static final String 	SUPPORTERS_WHITE		= "White";
	public static final String 	ACTION_CARD				= "Action Card";
	
	public static final String 	JOUSTING				= "Jousting";
	public static final String 	SWORD					= "Sword";
	public static final String 	AXE						= "Axe";
	public static final String 	MORNINGSTAR				= "Morningstar";
	public static final String 	NO_WEAPON				= "NoWeapon";
	
	public static final String 	MAIDEN 					= "Maiden";
	public static final String 	SQUIRE 					= "Squire";
	public static final String 	UNHORSE					= "Unhorse";
	public static final String 	CHANGE_WEAPON			= "ChangeWeapon";
	public static final String	DROP_WEAPON				= "DropWeapon";
	public static final String 	SHIELD					= "Shield";
	public static final String 	STUNNED					= "Stunned";
	public static final String 	IVANHOE					= "Ivanhoe";
	public static final String 	BREAK_LANCE				= "BreakLance";
	public static final String 	RIPOSTE					= "Riposte";
	public static final String 	DODGE					= "Dodge";
	public static final String 	RETREAT					= "Retreat";
	public static final String 	KNOCK_DOWN				= "KnockDown";
	public static final String 	OUTMANEUVER				= "Outmaneuver";
	public static final String 	CHARGE					= "Charge";
	public static final String 	COUNTERCHARGE			= "Countercharge";
	public static final String 	DISGRACE				= "Disgrace";
	public static final String 	ADAPT					= "Adapt";
	public static final String 	OUTWIT					= "Outwit";

	public static final int		VALUE_JOUSTING_THREE	= 3;
	public static final int		VALUE_JOUSTING_FOUR		= 4;
	public static final int		VALUE_JOUSTING_FIVE		= 5;
	public static final int		VALUE_JOUSTING_SEVEN	= 7;	
	public static final int		VALUE_SWORD_THREE		= 3;
	public static final int		VALUE_SWORD_FOUR		= 4;
	public static final int		VALUE_SWORD_FIVE		= 5;
	public static final int		VALUE_AXE_TWO			= 2;
	public static final int		VALUE_AXE_THREE			= 3;
	public static final int		VALUE_AXE_FOUR			= 4;
	public static final int		VALUE_AXE_FIVE			= 5;	
	public static final int		VALUE_MORNINGSTART_TWO	= 2;
	public static final int		VALUE_MORNINGSTART_THREE= 3;
	public static final int		VALUE_MORNINGSTART_FOUR = 4;	
	public static final int		VALUE_NO_WEAPON_ONE 	= 1;
	
	public static final int		VALUE_SQUIRE_TWO 		= 2;
	public static final int		VALUE_SQUIRE_THREE 		= 3;	
	public static final int		VALUE_MAIDEN_SIX 		= 6;
	
	public static final int		VALUE_ACTION_CARD_ZERO	= 0;
	
	public static final boolean compareObject(String objOne, String objTwo) { 
		return objOne.toUpperCase().equals(objTwo.toUpperCase());
	}
	
	public static final boolean containsKey(String objOne, String objTwo) { 
		return objOne.toUpperCase().contains(objTwo.toUpperCase());
	}
}
