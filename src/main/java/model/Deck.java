package model;

import java.util.ArrayList;

public class Deck {
	
	private int id;
	private String name;
	private ArrayList<Card> cards;
	
	public Deck(int id, String name, ArrayList<Card> cards) {
		this.id = id;
		this.name = name;
		this.cards = cards;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

}
