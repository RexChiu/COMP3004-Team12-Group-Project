package config;

public class GAMEConfig {

	// Ivanhoe Configuration	
	public final static int 	GAME_READY				= 0;
	public final static int 	GAME_SETUP				= 1;
	public final static int 	DEAL_CARD				= 2;
	public final static int		SELECT_COLOR			= 3;
	public final static int 	PLAY_OR_WITHDRAW		= 4;
	public final static int 	PLAY_CARD				= 5;
	public final static int 	WIN_TOURNAMENT			= 6;
	public final static int 	GAME_OVER				= 7;
	public final static int 	END_TURN				= 8;
	public final static int 	MAIDEN_PUNISH			= 9;
	public final static int 	CHANGE_TOURNAMENT_COLOR	= 10;
	public final static int 	CHECK_IVANHOE			= 11;

	public static final String[] STATE = {"Game Ready", "Game Setup", "Deal Card","Select Colour", 
			"Play or Withdraw", "Play Card", "Win Tournament", "Game Over" , "End Turn", 
			"Maiden Punish", "Change Tournament Color", "Check Ivanhoe"};
	public static final String 	WINNER = " win the game of Ivanhoe";
	
	public static final String 	TYPE_GAME_OVER					= "Game Over";
	public static final String 	TYPE_SET_UP						= "Game Setup";
	public static final String	TYPE_SELECT_COLOR	 			= "Select Color";
	public static final String 	TYPE_PLAY_OR_WITHDRAW			= "Play or Withdraw";
	public static final String 	TYPE_DEAL_CARD					= "Deal Card";
	public static final String 	TYPE_PLAY_CARD					= "Play Card";
	public static final String 	TYPE_WITHDRAW 					= "Withdraw";
	public static final String 	TYPE_WIN_TOURNAMENT				= "Win Tournament";
	public final static String 	TYPE_END_TURN					= "End Turn";
	public final static String 	TYPE_MAIDEN_PUNISH				= "Maiden Punish";
	public final static String 	TYPE_CHANGE_TOURNAMENT_COLOR	= "Change Tournament Color";
	public final static String 	TYPE_CHECK_IVANHOE				= "CHECK Ivanhoe";

	public static final String	POW_PLAY				= "Play";
	public static final String  POW_WITHDRAW			= "Withdraw";
	public static final String	IVANHOE_YES				= "Yes";
	public static final String  IVANHOE_NO				= "No";
	public static final String  APPLY_LEGAL				= "Legal";
	public static final String  APPLY_ILLEGAL			= "Illegal";
	
	// Player State Config
	public final static int		PLAYING					= 0;
	public final static int		WITHDRAW				= 1;
	public final static int		KEY_INDEX				= 0;
	public final static int		STATE_INDEX				= 1;
	
	// Tokens Configuration
	public static final int 	ANY_FOUR_TOKENS 		= 4;
	public static final int 	ALL_FIVE_TOKENS 		= 5;
	public static final int		FIVE_TOKEN_GAME			= 4;
	public static final int  	NUMBER_COLOR_FOUR		= 4;
	public static final int  	NUMBER_COLOR_FIVE		= 5;
	
	// Token Configurat8ion
	public static final String	IMAGE_PATH 				= "PATH";
	public static final String[]TOKEN_COLORS_FIVE		= {"Purple", "Red", "Blue", "Yellow", "Green"};
	public static final String[]TOKEN_COLORS_FOUR		= {"Red", "Blue", "Yellow", "Green"};
	public static final String[]TOKEN_COLORS_THREE		= {"Red", "Blue", "Yellow"};
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
	
	// Data Packet Key Feature
	public static final String	SELECTED_HAND_INDEX				= "Selected Card Index";
	public static final String	SELECTED_DISPLAY_INDEX			= "Selected Display Index";
	public static final String	SELECTED_TARGET_ID				= "Selected Target ID";
	public static final String	SELECTED_TARGET_DISPLAY_INDEX	= "Selected Target Display Index";
	
	public static final boolean compareObject(String objOne, String objTwo) { 
		return objOne.toUpperCase().equals(objTwo.toUpperCase());
	}
	
	public static final boolean containsKey(String objOne, String objTwo) { 
		return objOne.toUpperCase().contains(objTwo.toUpperCase());
	}
}
