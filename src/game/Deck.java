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
	
	public void 	addCard(Card card)		{ this.deck.add(card); 				}
	public Card 	getCard(int index)		{ return this.deck.get(index);		}
	public boolean 	removeCard(Card card)	{ return this.deck.remove(card); 	}
	public boolean 	isEmpty()				{ return this.deck.isEmpty(); 		}
	public int 		getSize()				{ return this.deck.size();			}
}