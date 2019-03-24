package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import controlador.Logica;
import model.Card;

public class View implements ActionListener{

	private Logica logic;
	
	private JPanel pPanel;
	private GridBagConstraints pConstraints;
	
	private JButton pBtLoadCards, pBtRnd, pBtSave, pBtToLeft, pBtToRight, pBtLoadDeck;
	private JTextField pTfDeckName;

	private JScrollPane pLeftPanel, pRightPanel;
	private JList<Card> pLeftList, pRightList;
	private DefaultListModel<Card> pLeftListModel, pRightListModel;
	
	public View(JPanel pPanel, Logica logic) {
		this.pPanel = pPanel;
		this.logic = new Logica();
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

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == pBtLoadCards) {//carga las cartas
			logic.loadCards(pLeftListModel, pRightListModel);
		}else if(e.getSource() == pBtToLeft) {//quita una carta del mazo
			logic.deleteCardFromDeck(pLeftListModel, pRightListModel, pRightList);
		}else if(e.getSource() == pBtToRight) {//añade una carta al mazo
			logic.addCardToDeck(pLeftListModel, pRightListModel, pLeftList);
		}else if (e.getSource() == pBtRnd) {//carga un mazo aleatorio
			logic.loadCards(pLeftListModel, pRightListModel);
			logic.randomCards(pLeftListModel, pRightListModel);
		}else if(e.getSource() == pBtSave) {//guarda un mazo
			logic.saveDeck(pLeftListModel, pRightListModel, null);
		}else if(e.getSource() == pBtLoadDeck) {//carga un mazo en concreto
			logic.getCardsFromDeck(pLeftListModel, pRightListModel, pTfDeckName.getText());
		}
	}
}
