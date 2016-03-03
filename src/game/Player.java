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
	public boolean 	checkToken(String token) 	{ return this.token.hasPurple(); 	}
	public void		addToken(String token)		{ this.token.addToken(token); 		}
	public void		addDisplay(Card card)		{ this.display.addCard(card); 		}
	public void 	addCard(Card card)			{ this.hand.drawCard(card); 		}
	public Tokens 	getTokens() 				{ return this.token; 				}
	public int		getID() 					{ return this.ID; 					}
	public Hand 	getHand() 					{ return this.hand; 				}
	public Display 	getDisplayer() 				{ return this.display; 				}
	
	public String 	toString(){
		return this.ID + ":" + this.token + ":" + this.hand +  ":" + this.display;
	}
}
