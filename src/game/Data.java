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
				message.getBody().addField("Player " + key + " Tokens", players.get(key).getTokens().toString());
				message.getBody().addField("Player " + key + " Hand", players.get(key).getHand().getSize());
				message.getBody().addField("Player " + key + " Status", players.get(key).getDisplayer().getStatus());
				message.getBody().addField("Player " + key + " Display", players.get(key).getDisplayer().toString());
				message.getBody().addField("Player " + key + " Total", players.get(key).getDisplayer().getTotal());
				playersID += key + ",";
			}
			tournamentInfo += key + ":" + players.get(key).getDisplayer().getStatus() + ":" + players.get(key).getDisplayer().getTotal() + ";"; 
		}
		message.getBody().addField("PlayersID", playersID.substring(0, playersID.length()-1));
		message.getBody().addField("TournamentInfo", tournamentInfo.substring(0, tournamentInfo.length()-1));
		return message;
	}
	
	public static Message setup(HashMap<Integer, Player> players, int ID){
		Message message = new Message();
		message.getHeader().setType(GAMEConfig.TYPE_SET_UP);
		
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
				message.getBody().addField("Player " + key + " Tokens", players.get(key).getTokens().toString());
				message.getBody().addField("Player " + key + " Hand", players.get(key).getHand().getSize());
				message.getBody().addField("Player " + key + " Status", players.get(key).getDisplayer().getStatus());
				message.getBody().addField("Player " + key + " Display", players.get(key).getDisplayer().toString());
				message.getBody().addField("Player " + key + " Total", players.get(key).getDisplayer().getTotal());
				playersID += key + ",";
			}
			tournamentInfo += key + ":" + players.get(key).getDisplayer().getStatus() + ":" + players.get(key).getDisplayer().getTotal() + ";"; 
		}
		message.getBody().addField("PlayersID", playersID.substring(0, playersID.length()-1));
		message.getBody().addField("TournamentInfo", tournamentInfo.substring(0, tournamentInfo.length()-1));
		return message;
	}
	
	public static Message startTournament(HashMap<Integer, Player> players, int currID, int ID){
		Message message = new Message();
		message.getHeader().setType(GAMEConfig.TYPE_START_TOURNAMENT);
		message.getBody().addField("Current ID", currID);
		return message;
	}
	
	public static Message gameOver(HashMap<Integer, Player> players, int ID){
		Message message = new Message();
		message.getHeader().setType(GAMEConfig.TYPE_GAME_OVER);
		return message;
	}
	
	public static Message selectColor(HashMap<Integer, Player> players, int ID){
		Message message = new Message();
		message.getHeader().setType(GAMEConfig.TYPE_SELECT_COLOR);
		message.getBody().addField("ID", ID);
		
		String colors = "";		
		for (String tokenColor : GAMEConfig.TOKEN_COLORS_FIVE){
			colors += tokenColor + ","; 
		}		
		message.getBody().addField("Token Colors", colors.substring(0, colors.length()-1));
		
		return message;
	}
	
	public static Message confirmTournament(HashMap<Integer, Player> players, int ID){
		Message message = new Message();
		message.getHeader().setType(GAMEConfig.TYPE_CONFIRM_TOURNAMENT);
		return message;
	}
	
	public static Message playOrWithdraw(HashMap<Integer, Player> players, int ID){
		Message message = new Message();
		message.getHeader().setType(GAMEConfig.TYPE_PLAY_OR_WITHDRAW);
		return message;
	}
	
	public static Message dealCard(HashMap<Integer, Player> players, int ID){
		Message message = new Message();
		message.getHeader().setType(GAMEConfig.TYPE_DEAL_CARD);
		
		Player user = players.get(ID);
		message.getBody().addField("UserHand", user.getHand().toString());
		return message;
	}
	
	public static Message confirmRequest(HashMap<Integer, Player> players, int ID){
		Message message = new Message();
		message.getHeader().setType(GAMEConfig.TYPE_CONFIRM_REQUEST);
		return message;
	}
	
	public static Message playCard(HashMap<Integer, Player> players, int ID){
		Message message = new Message();
		message.getHeader().setType(GAMEConfig.TYPE_PLAY_CARD);
		return message;
	}
	
	public static Message withdraw(HashMap<Integer, Player> players, int ID){
		Message message = new Message();
		message.getHeader().setType(GAMEConfig.TYPE_WITHDRAW);
		return message;
	}
	
	public static Message nextPlayer(HashMap<Integer, Player> players, int ID){
		Message message = new Message();
		message.getHeader().setType(GAMEConfig.TYPE_NEXT_PLAYER);
		return message;
	}
	
	public static Message winTournament(HashMap<Integer, Player> players, int ID){
		Message message = new Message();
		message.getHeader().setType(GAMEConfig.TYPE_WIN_TOURNAMENT);
		return message;
	}
	
	public static Message nextTournament(HashMap<Integer, Player> players, int ID){
		Message message = new Message();
		message.getHeader().setType(GAMEConfig.TYPE_NEXT_TOURNAMENT);
		return message;
	}
}
