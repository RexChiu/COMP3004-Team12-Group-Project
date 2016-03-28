package game;

public class Player {
	
	private Hand hand;
	private Display display;
	private Tokens tokens;
	private boolean withdrawn;
	private int ID;
	
	public Player(int ID){
		this.hand = new Hand();
		this.display = new Display();
		this.tokens = new Tokens();
		this.ID = ID;
	}

	public void 	setWithdrawn(boolean status){ this.withdrawn = status;				}
	public void 	cleanDisplay()				{ this.display = new Display();			}
	public boolean 	checkToken(String token) 	{ return this.tokens.checkToken(token); }
	public void		addToken(String token)		{ this.tokens.addToken(token); 			}
	public void		addDisplay(Card card)		{ this.display.addCard(card); 			}
	public void 	addCard(Card card)			{ this.hand.drawCard(card); 			}
	public Tokens 	getTokens() 				{ return this.tokens; 					}
	public int		getID() 					{ return this.ID; 						}
	public Hand 	getHand() 					{ return this.hand; 					}
	public Display 	getDisplayer() 				{ return this.display; 					}
	public boolean  isWithdrawn()				{ return this.withdrawn;				}
	
	public String 	toString(){
		return this.ID + ":" + this.tokens + ":" + this.hand +  ":" + this.display;
	}
}
