package model;

import java.util.ArrayList;

public class Deck {
	
	private String name;
	private int deckValue;
	private ArrayList<Card> cards;
	
	public Deck(String name, int deckValue, ArrayList<Card> cards) {
		this.name = name;
		this.deckValue = deckValue;
		this.cards = cards;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDeckValue() {
		return deckValue;
	}

	public void setDeckValue(int deckValue) {
		this.deckValue = deckValue;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	@Override
	public String toString() {
		return "Deck [name=" + name + ", deckValue=" + deckValue + ", cards=" + cards + "]";
	}

}
