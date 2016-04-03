package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config.GAMEConfig;

public class Deck {

	private List<Card> deck;

	public Deck() {
		// TODO Auto-generated constructor stub
		this.deck = new ArrayList<Card>();
	}

	public void init1(){ // All Action first player
		addCards(new Card(GAMEConfig.DROP_WEAPON, GAMEConfig.ACTION_CARD, 0), 7);
		addCards(new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, 2), 1);
		addCards(new Card(GAMEConfig.DROP_WEAPON, GAMEConfig.ACTION_CARD, 0), 100);	
	}

	public void init2(){ // Green Tournament
		addCards(new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, 2), 10);
		addCards(new Card(GAMEConfig.NO_WEAPON, 	GAMEConfig.COLOR_GREEN, 	GAMEConfig.VALUE_NO_WEAPON_ONE), 		4);
		addCards(new Card(GAMEConfig.JOUSTING,		GAMEConfig.COLOR_PURPLE, 	GAMEConfig.VALUE_JOUSTING_THREE), 		GAMEConfig.NUMBER_PURPLE_THREE	);
		addCards(new Card(GAMEConfig.SWORD, 		GAMEConfig.COLOR_RED, 		GAMEConfig.VALUE_SWORD_THREE), 			GAMEConfig.NUMBER_RED_THREE		);
		addCards(new Card(GAMEConfig.AXE, 			GAMEConfig.COLOR_BLUE, 		GAMEConfig.VALUE_AXE_TWO), 				GAMEConfig.NUMBER_BLUE_TWO		);
		addCards(new Card(GAMEConfig.MORNINGSTAR, 	GAMEConfig.COLOR_YELLOW, 	GAMEConfig.VALUE_MORNINGSTART_TWO), 	GAMEConfig.NUMBER_YELLOW_TWO	);
		addCards(new Card(GAMEConfig.DROP_WEAPON, GAMEConfig.ACTION_CARD, 0), 100);	
	}

	public void init3(){ // one Action Cards/Shield
		addCards(new Card(GAMEConfig.DROP_WEAPON, GAMEConfig.ACTION_CARD, 0), 5);
		addCards(new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, 2), 5);
		addCards(new Card(GAMEConfig.SHIELD, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_SHIELD		);
		addCards(new Card(GAMEConfig.STUNNED,		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_STUNNED		);
		addCards(new Card(GAMEConfig.NO_WEAPON, 	GAMEConfig.COLOR_GREEN, 	GAMEConfig.VALUE_NO_WEAPON_ONE), 		4);
		addCards(new Card(GAMEConfig.JOUSTING,		GAMEConfig.COLOR_PURPLE, 	GAMEConfig.VALUE_JOUSTING_THREE), 		GAMEConfig.NUMBER_PURPLE_THREE	);
		addCards(new Card(GAMEConfig.SWORD, 		GAMEConfig.COLOR_RED, 		GAMEConfig.VALUE_SWORD_THREE), 			GAMEConfig.NUMBER_RED_THREE		);
		addCards(new Card(GAMEConfig.AXE, 			GAMEConfig.COLOR_BLUE, 		GAMEConfig.VALUE_AXE_TWO), 				GAMEConfig.NUMBER_BLUE_TWO		);
		addCards(new Card(GAMEConfig.MORNINGSTAR, 	GAMEConfig.COLOR_YELLOW, 	GAMEConfig.VALUE_MORNINGSTART_TWO), 	GAMEConfig.NUMBER_YELLOW_TWO	);
		addCards(new Card(GAMEConfig.DROP_WEAPON, GAMEConfig.ACTION_CARD, 0), 100);	
	}

	public void init4(){ // one Action Cards/Shield
		addCards(new Card(GAMEConfig.DISGRACE, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_DISGRACE		);
		addCards(new Card(GAMEConfig.DROP_WEAPON, 	GAMEConfig.ACTION_CARD, 0), 5);
		addCards(new Card(GAMEConfig.SQUIRE, 		GAMEConfig.SUPPORTERS_WHITE, 2), 5);
		addCards(new Card(GAMEConfig.SHIELD, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_SHIELD		);
		addCards(new Card(GAMEConfig.STUNNED,		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_STUNNED		);
		addCards(new Card(GAMEConfig.NO_WEAPON, 	GAMEConfig.COLOR_GREEN, 	GAMEConfig.VALUE_NO_WEAPON_ONE), 		4);
		addCards(new Card(GAMEConfig.JOUSTING,		GAMEConfig.COLOR_PURPLE, 	GAMEConfig.VALUE_JOUSTING_THREE), 		GAMEConfig.NUMBER_PURPLE_THREE	);
		addCards(new Card(GAMEConfig.SWORD, 		GAMEConfig.COLOR_RED, 		GAMEConfig.VALUE_SWORD_THREE), 			GAMEConfig.NUMBER_RED_THREE		);
		addCards(new Card(GAMEConfig.AXE, 			GAMEConfig.COLOR_BLUE, 		GAMEConfig.VALUE_AXE_TWO), 				GAMEConfig.NUMBER_BLUE_TWO		);
		addCards(new Card(GAMEConfig.MORNINGSTAR, 	GAMEConfig.COLOR_YELLOW, 	GAMEConfig.VALUE_MORNINGSTART_TWO), 	GAMEConfig.NUMBER_YELLOW_TWO	);
		addCards(new Card(GAMEConfig.DROP_WEAPON, GAMEConfig.ACTION_CARD, 0), 100);	
	}
	
	public void initTestCase() { //all white cards
		addCards(new Card(GAMEConfig.SQUIRE, 		GAMEConfig.SUPPORTERS_WHITE, 2), 110);
	}

	public void init(){
		addCards(new Card(GAMEConfig.JOUSTING,		GAMEConfig.COLOR_PURPLE, 	GAMEConfig.VALUE_JOUSTING_THREE), 		GAMEConfig.NUMBER_PURPLE_THREE	);
		addCards(new Card(GAMEConfig.JOUSTING, 		GAMEConfig.COLOR_PURPLE, 	GAMEConfig.VALUE_JOUSTING_FOUR), 		GAMEConfig.NUMBER_PURPLE_FOUR	);
		addCards(new Card(GAMEConfig.JOUSTING,		GAMEConfig.COLOR_PURPLE, 	GAMEConfig.VALUE_JOUSTING_FIVE), 		GAMEConfig.NUMBER_PURPLE_FIVE	);
		addCards(new Card(GAMEConfig.JOUSTING, 		GAMEConfig.COLOR_PURPLE, 	GAMEConfig.VALUE_JOUSTING_SEVEN), 		GAMEConfig.NUMBER_PURPLE_SEVER	);

		addCards(new Card(GAMEConfig.SWORD, 		GAMEConfig.COLOR_RED, 		GAMEConfig.VALUE_SWORD_THREE), 			GAMEConfig.NUMBER_RED_THREE		);
		addCards(new Card(GAMEConfig.SWORD, 		GAMEConfig.COLOR_RED, 		GAMEConfig.VALUE_SWORD_FOUR), 			GAMEConfig.NUMBER_RED_FOUR		);
		addCards(new Card(GAMEConfig.SWORD, 		GAMEConfig.COLOR_RED, 		GAMEConfig.VALUE_SWORD_FIVE), 			GAMEConfig.NUMBER_RED_FIVE		);

		addCards(new Card(GAMEConfig.AXE, 			GAMEConfig.COLOR_BLUE, 		GAMEConfig.VALUE_AXE_TWO), 				GAMEConfig.NUMBER_BLUE_TWO		);
		addCards(new Card(GAMEConfig.AXE, 			GAMEConfig.COLOR_BLUE, 		GAMEConfig.VALUE_AXE_THREE), 			GAMEConfig.NUMBER_BLUE_THREE	);
		addCards(new Card(GAMEConfig.AXE, 			GAMEConfig.COLOR_BLUE, 		GAMEConfig.VALUE_AXE_FOUR), 			GAMEConfig.NUMBER_BLUE_FOUR		);
		addCards(new Card(GAMEConfig.AXE, 			GAMEConfig.COLOR_BLUE, 		GAMEConfig.VALUE_AXE_FIVE), 			GAMEConfig.NUMBER_BLUE_FIVE		);

		addCards(new Card(GAMEConfig.MORNINGSTAR, 	GAMEConfig.COLOR_YELLOW, 	GAMEConfig.VALUE_MORNINGSTART_TWO), 	GAMEConfig.NUMBER_YELLOW_TWO	);
		addCards(new Card(GAMEConfig.MORNINGSTAR, 	GAMEConfig.COLOR_YELLOW, 	GAMEConfig.VALUE_MORNINGSTART_THREE), 	GAMEConfig.NUMBER_YELLOW_THREE	);
		addCards(new Card(GAMEConfig.MORNINGSTAR, 	GAMEConfig.COLOR_YELLOW, 	GAMEConfig.VALUE_MORNINGSTART_FOUR), 	GAMEConfig.NUMBER_YELLOW_FOUR	);

		addCards(new Card(GAMEConfig.NO_WEAPON, 	GAMEConfig.COLOR_GREEN, 	GAMEConfig.VALUE_NO_WEAPON_ONE), 		GAMEConfig.NUMBER_GREEN_ONE		);

		addCards(new Card(GAMEConfig.SQUIRE, 		GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO), 			GAMEConfig.NUMBER_SQUIRES_TWO	);
		addCards(new Card(GAMEConfig.SQUIRE,		GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_THREE),		GAMEConfig.NUMBER_SQUIRES_THREE	);		
		addCards(new Card(GAMEConfig.MAIDEN, 		GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_MAIDEN_SIX), 			GAMEConfig.NUMBER_MAIDENS_SIX	);

		addCards(new Card(GAMEConfig.UNHORSE, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_UNHORSE		);
		addCards(new Card(GAMEConfig.CHANGE_WEAPON, GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_CHANGE_WEAPON	);
		addCards(new Card(GAMEConfig.DROP_WEAPON, 	GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_DROP_WEAPON	);
		addCards(new Card(GAMEConfig.SHIELD, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_SHIELD		);
		addCards(new Card(GAMEConfig.STUNNED,		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_STUNNED		);
		addCards(new Card(GAMEConfig.IVANHOE, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_IVANHOE		);
		addCards(new Card(GAMEConfig.BREAK_LANCE, 	GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_BREAK_LANCE	);
		addCards(new Card(GAMEConfig.RIPOSTE, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_RIPOSTE		);
		addCards(new Card(GAMEConfig.DODGE, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_DODGE			);
		addCards(new Card(GAMEConfig.RETREAT, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_RETREAT		);
		addCards(new Card(GAMEConfig.KNOCK_DOWN, 	GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_KNOCK_DOWN	);
		addCards(new Card(GAMEConfig.OUTMANEUVER, 	GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_OUTMANEUVER	);
		addCards(new Card(GAMEConfig.CHARGE, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_CHARGE		);
		addCards(new Card(GAMEConfig.COUNTERCHARGE, GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_COUNTERCHARGE	);
		addCards(new Card(GAMEConfig.DISGRACE, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_DISGRACE		);
		addCards(new Card(GAMEConfig.ADAPT, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_ADAPT			);
		addCards(new Card(GAMEConfig.OUTWIT, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_OUTFIT		);	
	}

	// Help function to add many same card
	public  void addCards(Card card, int count){
		for (int i = 0; i < count; i++){
			addCard(card);
		}
	}

	public void 	addCard(Card card)		{ this.deck.add(card); 				}
	public boolean 	removeCard(Card card)	{ return this.deck.remove(card); 	}
	public Card 	getCard(int index)		{ return this.deck.get(index);		}
	public int 		getSize()				{ return this.deck.size();			}
	public void 	shuffleDeck()			{ Collections.shuffle(this.deck);	}
	public boolean 	isEmpty()				{ return this.deck.isEmpty(); 		}
	public void 	cleanDeck()				{ this.deck.clear(); 				}
	
	public boolean  hasCard(Card card){
		for (Card cards : deck) {
			if (cards.getName() == card.getName())
				return true;
		}
		return false;
	}

	// Clone deep copy the deck
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Deck cloned = (Deck)super.clone();
		return cloned;
	}

	public boolean equals (Deck deck){
		if (this == deck) return Boolean.TRUE;

		for (int i = 0; i < this.deck.size(); i++){
			if (!getCard(i).equals(deck.getCard(i)))
				return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}
}