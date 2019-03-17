package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import daoImpl.ExistCardImpl;
import model.Card;

public class View implements ActionListener{

	private Logica logic;
	private ExistCardImpl cards;
	
	private JPanel pPanel;
	private GridBagConstraints pConstraints;
	
	private JButton pBtLoadCards, pBtRnd, pBtSave, pBtToLeft, pBtToRight, pBtLoadDeck;
	private JTextField pTfDeckName;

	private JScrollPane pLeftPanel, pRightPanel;
	private JList<Card> pLeftList, pRightList;
	private DefaultListModel<Card> pLeftListModel, pRightListModel;
	
	public View(JPanel pPanel) {
		this.pPanel = pPanel;
		logic = new Logica();
		view();
	}
	
	private void view() {
		pConstraints = new GridBagConstraints();

		pConstraints.insets = new Insets(10, 10, 10, 10);

		pBtLoadCards = new JButton("Load Cards");
		pConstraints.gridx=0;
		pConstraints.gridy=0;
		pPanel.add(pBtLoadCards, pConstraints);

		pBtRnd = new JButton("Random Cards");
		pConstraints.gridx=2;
		pConstraints.gridy=0;
		pPanel.add(pBtRnd, pConstraints);

		pConstraints.gridheight=4;

		//los dos paneles (son un scrollpanel con un jlist dentro)
		pLeftListModel = new DefaultListModel<Card>();
		pLeftList = new JList<Card>(pLeftListModel);
		pLeftList.setLayoutOrientation(JList.VERTICAL);
		pLeftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pLeftPanel = new JScrollPane(pLeftList);
		pLeftPanel.setPreferredSize(new Dimension(350,160));
		pConstraints.gridx=0;
		pConstraints.gridy=1;
		pPanel.add(pLeftPanel, pConstraints);

		pRightListModel = new DefaultListModel<Card>();
		pRightList = new JList<Card>(pRightListModel);
		pRightList.setLayoutOrientation(JList.VERTICAL);
		pRightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pRightPanel = new JScrollPane(pRightList);
		pRightPanel.setPreferredSize(new Dimension(350,160));
		pConstraints.gridx=2;
		pConstraints.gridy=1;
		pPanel.add(pRightPanel, pConstraints);

		pConstraints.fill = GridBagConstraints.HORIZONTAL;
		pConstraints.gridheight=1;

		pBtToLeft = new JButton("<=");
		pConstraints.gridx=1;
		pConstraints.gridy=2;
		pPanel.add(pBtToLeft, pConstraints);

		pBtToRight = new JButton("=>");
		pConstraints.gridx=1;
		pConstraints.gridy=3;
		pPanel.add(pBtToRight, pConstraints);

		pConstraints.insets = new Insets(2, 10, 2, 10);
		pTfDeckName = new JTextField();
		pConstraints.gridx=3;
		pConstraints.gridy=1;
		pPanel.add(pTfDeckName, pConstraints);

		pBtLoadDeck = new JButton("Load Deck");
		pConstraints.gridx=3;
		pConstraints.gridy=2;
		pPanel.add(pBtLoadDeck, pConstraints);

		pConstraints.insets = new Insets(10, 10, 10, 10);
		
		pBtSave = new JButton("Save Deck");
		pConstraints.gridx=3;
		pConstraints.gridy=5;
		pPanel.add(pBtSave, pConstraints);
		
		//las acciones de los botones
		pBtLoadCards.addActionListener(this);
		pBtRnd.addActionListener(this);
		pBtSave.addActionListener(this);
		pBtToLeft.addActionListener(this);
		pBtToRight.addActionListener(this);
		pBtLoadDeck.addActionListener(this);
	}
/*
Botó1: Load cards, ha de permetre carregar les cartes emmagatzemades en una base de dades exist-db en format xml (consulta el format a l'apartat especificacions no funcionals)
Botó2: Rnd Deck, ha de generar una barralla de cartes aleatoria respectant la restricció de valors de cartes definit a l'apartat especificacions no funcionals
Botó3: Save Deck, ha de permetre guarda una barralla de cartes creada o per selecció manual de l'usuari o pel botó 2 en una base de dades MongoDB, una vegada guardada la baralla, ha de sortir un missatge per informar a l'usuari i netejar el panell de baralles.
Botó4: ->, ha de permetre moure una o més d'una carta de la col·lecció a la baralla
Botó5: <-, ha de permetre moure una o més d'una carta de la baralla a la col·lecció
Botó6 + input: Load Deck, carrega una baralla introduint en el input el nom de la baralla, si no existeix s'ha d'indicar a l'usuari, si existeix es carrega la baralla i permet fer modificacions i guardar-les
*/

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == pBtLoadCards) {//carga las cartas
			logic.loadCards();
		}else if(e.getSource() == pBtToLeft) {//quita una carta del mazo
			logic.deleteCardFromDeck();
		}else if(e.getSource() == pBtToRight) {//añade una carta al mazo
			logic.addCardToDeck();
		}else if (e.getSource() == pBtRnd) {//carga un mazo aleatorio
			logic.loadCards();
			logic.randomCards();
		}
			//		pBtLoadCards.addActionListener(this);
			//		pBtRnd.addActionListener(this);
//		pBtSave.addActionListener(this);
			//		pBtToLeft.addActionListener(this);
			//		pBtToRight.addActionListener(this);
//		pBtLoadDeck.addActionListener(this);
	}
	
	//clase privada para la logica de las vistas
	private class Logica {
		
		private int valTotal=0;
		
		//vaciamos las listas y ponemos todas las cartas a la lista de la seleccion
		private void loadCards() {
			cards = ExistCardImpl.getInstance();
			pLeftListModel.clear();
			pRightListModel.clear();
			valTotal=0;
			for (Card card : cards.getAllCards()) {
				pLeftListModel.addElement(card);
			}
		}
		
		//hacemos una baraja aleatoria
		private void randomCards() {
			do {
				Card selectedCard = cards.getAllCards().get(new Random().nextInt(cards.getAllCards().size()));
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
		private void deleteCardFromDeck() {
			valTotal = valTotal - pRightList.getSelectedValue().getValue();
			pLeftListModel.addElement(pRightList.getSelectedValue());
			pRightListModel.remove(pRightList.getSelectedIndex());
		}
		//añadimos una carta a la baraja
		private void addCardToDeck() {
			if(!isDeckCompleted()) {
				valTotal = valTotal + pLeftList.getSelectedValue().getValue();
				pRightListModel.addElement(pLeftList.getSelectedValue());
				pLeftListModel.remove(pLeftList.getSelectedIndex());
			}else {
				JOptionPane.showMessageDialog(null, "Mazo lleno");
			}
		}
		
		//si aun nos queda sitio en la baraja, devolvemos que no esta completo
		private boolean isDeckCompleted() {
			return valTotal+pLeftList.getSelectedValue().getValue()>20;
		}
	}
	
}
