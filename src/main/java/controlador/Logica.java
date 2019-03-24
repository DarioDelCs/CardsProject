package controlador;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import daoImpl.ExistCardImpl;
import daoImpl.MongoDeckImpl;
import model.Card;
import model.Deck;

public class Logica {
	
	private int valTotal=0;
	
	private MongoDeckImpl mongoDeckImpl;
	private ExistCardImpl cards;
	
	//a la hora de cargar
	private Deck loadDeck;
	
	public Logica() {
		mongoDeckImpl = new MongoDeckImpl();
	}
	
	//vaciamos las listas y ponemos todas las cartas a la lista de la seleccion
	public void loadCards(DefaultListModel<Card> pLeftListModel, DefaultListModel<Card> pRightListModel) {
		cards = ExistCardImpl.getInstance();
		pLeftListModel.clear();
		pRightListModel.clear();
		valTotal=0;
		for (Card card : cards.getAllCards()) {
			pLeftListModel.addElement(card);
		}
	}
	
	//hacemos una baraja aleatoria
	public void randomCards(DefaultListModel<Card> pLeftListModel, DefaultListModel<Card> pRightListModel) {
		do {
			Card selectedCard = pLeftListModel.get(new Random().nextInt(pLeftListModel.size()));
			if((valTotal+selectedCard.getValue())<=20) {
				pRightListModel.addElement(selectedCard);
				pLeftListModel.removeElement(selectedCard);
				valTotal = valTotal+selectedCard.getValue();
			}else {
				break;
			}
		}while(true);
	}
	
	//eliminamos una carta de la baraja
	public void deleteCardFromDeck(DefaultListModel<Card> pLeftListModel, DefaultListModel<Card> pRightListModel, JList<Card> pRightList) {
		if(pRightList.getSelectedValue()!=null) {
			valTotal = valTotal - pRightList.getSelectedValue().getValue();
			pLeftListModel.addElement(pRightList.getSelectedValue());
			pRightListModel.remove(pRightList.getSelectedIndex());
		}
	}
	//añadimos una carta a la baraja
	public void addCardToDeck(DefaultListModel<Card> pLeftListModel, DefaultListModel<Card> pRightListModel, JList<Card> pLeftList) {
		if(pLeftList.getSelectedValue()!=null) {
			if(!isDeckCompleted(pLeftList)) {
				valTotal = valTotal + pLeftList.getSelectedValue().getValue();
				pRightListModel.addElement(pLeftList.getSelectedValue());
				pLeftListModel.remove(pLeftList.getSelectedIndex());
			}else {
				JOptionPane.showMessageDialog(null, "Mazo lleno");
			}
		}
	}
	
	//si aun nos queda sitio en la baraja, devolvemos que no esta completo
	private boolean isDeckCompleted(JList<Card> pLeftList) {
		return valTotal+pLeftList.getSelectedValue().getValue()>20;
	}
	
	//guarda un mazo en mognoDB
	public void saveDeck(DefaultListModel<Card> pLeftListModel, DefaultListModel<Card> pRightListModel, String deckName) {
		ArrayList<Card> allCards = new ArrayList<Card>();
		if(loadDeck==null) {
			deckName=JOptionPane.showInputDialog("Introduce el nombre del mazo");
			if(deckName!=null) {
				if(!deckName.equals("")) {
					for (Object card : pRightListModel.toArray()) {
						allCards.add((Card)card);
					}
					Deck deck = new Deck(deckName, valTotal, allCards);
					if(mongoDeckImpl.saveDeck(deck)) {
						JOptionPane.showMessageDialog(null, "Mazo insertado correcamente");
						loadCards(pLeftListModel, pRightListModel);
					}else {
						JOptionPane.showMessageDialog(null, "Este mazo ya existe", "Error al insertar el mazo", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}else {//si se habia cargado un mazo, no preguntamos nombre y actualizamos el guardado
			int deckValue = 0;
			for (Object card : pRightListModel.toArray()) {
				allCards.add((Card)card);
				deckValue = deckValue + ((Card)card).getValue();
			}
			loadDeck.setCards(allCards);
			loadDeck.setDeckValue(deckValue);
			if(mongoDeckImpl.updateDeck(loadDeck)) {
				JOptionPane.showMessageDialog(null, "Mazo actualizado correcamente");
				loadCards(pLeftListModel, pRightListModel);
				loadDeck=null;
			}else {
				JOptionPane.showMessageDialog(null, "Vuelva a cargar el mazo e intente volver a actualizarlo", "Error al actualizar el mazo", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	//cargamos un mazo con el nombre seleccionado
	public void getCardsFromDeck(DefaultListModel<Card> pLeftListModel, DefaultListModel<Card> pRightListModel, String name){
		if(!name.equals("")) {
			loadDeck = mongoDeckImpl.getDeckFromName(name);
			
			if(loadDeck!=null) {
				loadCards(pLeftListModel, pRightListModel);
				for (Card card : loadDeck.getCards()) {
					for (Card cardInUse : ExistCardImpl.getInstance().getAllCards()) {
						if(card.getId() == cardInUse.getId()) {
							valTotal = valTotal + cardInUse.getValue();
							pRightListModel.addElement(cardInUse);
							pLeftListModel.removeElement(cardInUse);
						}
					}
				}
			}else {
				JOptionPane.showMessageDialog(null, "Este mazo no existe");
			}
		}else {
			JOptionPane.showMessageDialog(null, "Se tiene que poner un nombre");
		}
	}
}
