package game;

public class Player {
	
	private Hand hand;
	private Display display;
	private Tokens token;
	private int ID;
	
	public Player(int ID){
		this.hand = new Hand();
		this.display = new Display();
		this.ID = ID;
	}
	
	public void 	setToken(String token)		{ this.token = new Tokens(token);	}
	public void		addToken(String token)		{ this.token.addToken(token); 		}
	public boolean 	checkToken(String token) 	{ return this.token.hasPurple(); 	}
	public int		getID() {return this.ID; }
	
	public void 	addCard(Card card)			{ this.hand.drawCard(card); 		}
	
	public static String getTokenKey(String data){
		String tokens = getToken(data);
		String[] key = tokens.split(",");
		String result = "";
		for (String k : key){
			result += k.substring(0, 1);
		}
		return result;
	}
	
	public static String getID(String data){
		return data.substring(0, 5);
	}
	
	public static String getToken(String data){
		if (!data.contains("[Token]")) return "";
		int beginIndex = data.indexOf("[Token]")+7;
		int endIndex = data.indexOf(":[Hand]");
		return data.substring(beginIndex, endIndex);
	}
	
	public static String getHand(String data){
		if (!data.contains("[Hand]")) return "";
		int beginIndex = data.indexOf("[Hand]")+6;
		int endIndex = data.indexOf(":[Display]");
		return data.substring(beginIndex, endIndex);
	}
	
	public static String getTotal(String data){
		if (!data.contains("Total")) return "";
		int beginIndex = data.indexOf("[Total]")+7;
		int endIndex = data.indexOf("[Status]");
		return data.substring(beginIndex, endIndex);
	}
	
	public static String getStatus(String data){
		if (!data.contains("[Card]")) return "";
		int beginIndex = data.indexOf("[Status]")+6;
		int endIndex = data.indexOf("[Card]");
		return data.substring(beginIndex, endIndex);
	}
	
	public static String getCard(String data){
		if (!data.contains("[Card]")) return "";
		int beginIndex = data.indexOf("[Card]")+6;
		return data.substring(beginIndex);
	}
	
	public String 	toString(){
		return this.ID + ":" + this.token + ":" + this.hand +  ":" + this.display;
	}
}
