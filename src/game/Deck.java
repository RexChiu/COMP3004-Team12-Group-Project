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
	public boolean 	removeCard(Card card)	{ return this.deck.remove(card); 	}
	public Card 	getCard(int index)		{ return this.deck.get(index);		}
	public int 		getSize()				{ return this.deck.size();			}
	public void 	shuffleDeck()			{ Collections.shuffle(this.deck);	}
	public boolean 	isEmpty()				{ return this.deck.isEmpty(); 		}
	public void 	cleanDeck()				{ this.deck.clear(); 				}

	// Clone deep copy the deck
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Deck cloned = (Deck)super.clone();
		return cloned;
	}

	public boolean equals (Deck other)
	{
		if (this.getSize() != other.getSize())
		{
			return false;
		}
		for (int i = 0; i < this.deck.size(); i++)
		{
			if (this.getCard(i).getName() != other.getCard(i).getName())
			{
				return false;
			}
		}

		return true;
	}
}