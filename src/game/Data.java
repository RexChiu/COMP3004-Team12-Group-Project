package game;

import java.util.HashMap;

import config.GAMEConfig;
import message.Message;

public class Data {

	public static Message getMessage(HashMap<Integer, Player> players, int ID){
		Message message = new Message();
		
		Player user = players.get(ID);
		message.getBody().addField("UserID",ID);
		message.getBody().addField("UserHand", user.getHand().toString());
		message.getBody().addField("UserTokens", user.getTokens().toString());
		message.getBody().addField("UserDisplay", user.getDisplayer().toString());
		message.getBody().addField("UserStatus", user.getDisplayer().getStatus());
		message.getBody().addField("UserTotal", user.getDisplayer().getTotal());
		
		String playersID = "";
		String tournamentInfo = "";
		for (Integer key: players.keySet()){
			if (key != ID){
				message.getBody().addField("Player " + key + " Tokens", 	players.get(key).getTokens().toString());
				message.getBody().addField("Player " + key + " Hand", 		players.get(key).getHand().getSize());
				message.getBody().addField("Player " + key + " Status", 	players.get(key).getDisplayer().getStatus());
				message.getBody().addField("Player " + key + " Display",	players.get(key).getDisplayer().toString());
				message.getBody().addField("Player " + key + " Total", 		players.get(key).getDisplayer().getTotal());
				playersID += key + ",";
			}
			tournamentInfo += key + ":" + players.get(key).getDisplayer().getStatus() + ":" + players.get(key).getDisplayer().getTotal() + ";"; 
		}
		message.getBody().addField("PlayersID", playersID.substring(0, playersID.length()-1));
		message.getBody().addField("TournamentInfo", tournamentInfo.substring(0, tournamentInfo.length()-1));
		message.getBody().addField("Tournament Color", user.getDisplayer().getTournament());
		return message;
	}
	
	public static Message setup(HashMap<Integer, Player> players, int ID){
		Message message = getMessage(players, ID);
		message.getHeader().setType(GAMEConfig.TYPE_SET_UP);
		message.getHeader().setState(GAMEConfig.GAME_SETUP);
				
		return message;
	}
	
	public static Message startTournament(HashMap<Integer, Player> players, int currID, int ID){
		Message message = getMessage(players, ID);
		message.getHeader().setType(GAMEConfig.TYPE_START_TOURNAMENT);
		message.getHeader().setState(GAMEConfig.START_TOURNAMENT);
		
		message.getBody().addField("Current ID", currID);
		return message;
	}
	
	public static Message selectColor(HashMap<Integer, Player> players, int ID, int numColor){
		Message message = getMessage(players, ID);
		message.getHeader().setType(GAMEConfig.TYPE_SELECT_COLOR);
		message.getHeader().setState(GAMEConfig.SELECT_COLOUR);
				
		String colors = "";		
		for (String tokenColor : (numColor == 5 ? GAMEConfig.TOKEN_COLORS_FIVE : GAMEConfig.TOKEN_COLORS_FOUR)){
			colors += tokenColor + ","; 
		}		
		message.getBody().addField("Select Colors", colors.substring(0, colors.length()-1));
		
		return message;
	}
	
	public static Message playOrWithdraw(HashMap<Integer, Player> players, int ID){
		Message message = getMessage(players, ID);
		message.getHeader().setType(GAMEConfig.TYPE_PLAY_OR_WITHDRAW);
		message.getHeader().setState(GAMEConfig.PLAY_OR_WITHDRAW);
				
		return message;
	}
	
	public static Message dealCard(HashMap<Integer, Player> players, int ID){
		Message message = getMessage(players, ID);
		message.getHeader().setType(GAMEConfig.TYPE_DEAL_CARD);
		message.getHeader().setState(GAMEConfig.DEAL_CARD);
		
		return message;
	}
	
	public static Message playCard(HashMap<Integer, Player> players, int ID){
		Message message = getMessage(players, ID);
		message.getHeader().setType(GAMEConfig.TYPE_PLAY_CARD);
		message.getHeader().setState(GAMEConfig.PLAY_CARD);
		
		return message;
	}
	
	public static Message withdraw(HashMap<Integer, Player> players, int ID){
		Message message = getMessage(players, ID);
		message.getHeader().setType(GAMEConfig.TYPE_WITHDRAW);
		message.getHeader().setState(GAMEConfig.WITHDRAW);
		
		return message;
	}
	
	public static Message maidenPunish(HashMap<Integer, Player> players, int ID, String choices){
		Message message = getMessage(players, ID);
		message.getHeader().setType(GAMEConfig.TYPE_MAIDEN_PUNISH);
		message.getHeader().setState(GAMEConfig.MAIDEN_PUNISH);
		message.getBody().addField("Maiden Punish", choices);
					
		return message;
	}
	
	public static Message winTournament(HashMap<Integer, Player> players, int ID){
		Message message = getMessage(players, ID);
		message.getHeader().setType(GAMEConfig.TYPE_WIN_TOURNAMENT);
		message.getHeader().setState(GAMEConfig.WIN_TOURNAMENT);
		
		return message;
	}
	
	public static Message gameOver(HashMap<Integer, Player> players, int ID){
		Message message = getMessage(players, ID);
		message.getHeader().setType(GAMEConfig.TYPE_GAME_OVER);
		message.getHeader().setState(GAMEConfig.GAME_OVER);
		
		return message;
	}
}
