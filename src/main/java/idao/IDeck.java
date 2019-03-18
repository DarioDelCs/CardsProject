package idao;

import model.Deck;

public interface IDeck {

	public Deck getDeckFromName(String name);
	public boolean saveDeck(Deck deck);

}
