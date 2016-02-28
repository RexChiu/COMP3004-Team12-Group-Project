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
	
	public void 	setToken(String token)		{ this.token = new Tokens(token); }
	public boolean 	checkToken(String token) 	{ return this.token.hasPurple(); }
	public int		getID() {return this.ID; }
	
	public String 	toString(){
		return this.ID + ":" + this.token + ":" + this.hand +  ":" + this.display;
	}
}
