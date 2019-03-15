package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class View {
	
	private JPanel pPanel;
	private GridBagConstraints pConstraints;
	
	private JButton pBtLoadCards, pBtRnd, pBtSave, pBtToLeft, pBtToRight, pBtLoadDeck;
	private JPanel pLeftPane, pRightPane;
	private JTextField pTfDeckName;
	
	public View(JPanel pPanel) {
		this.pPanel = pPanel;
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

		pConstraints.fill = GridBagConstraints.BOTH;
		pConstraints.gridheight=4;

		pLeftPane = new JPanel();
		pLeftPane.setBackground(Color.white);
		pLeftPane.setPreferredSize(new Dimension(250,300));
		pConstraints.gridx=0;
		pConstraints.gridy=1;
		pPanel.add(pLeftPane, pConstraints);
		
		pRightPane = new JPanel();
		pRightPane.setBackground(Color.white);
		pRightPane.setPreferredSize(new Dimension(250,300));
		pConstraints.gridx=2;
		pConstraints.gridy=1;
		pPanel.add(pRightPane, pConstraints);

		pConstraints.gridheight=1;

		pBtToLeft = new JButton("<=");
		pConstraints.gridx=1;
		pConstraints.gridy=2;
		pPanel.add(pBtToLeft, pConstraints);

		pBtToRight = new JButton("=>");
		pConstraints.gridx=1;
		pConstraints.gridy=3;
		pPanel.add(pBtToRight, pConstraints);
		
		pTfDeckName = new JTextField();
		pConstraints.gridx=3;
		pConstraints.gridy=1;
		pPanel.add(pTfDeckName, pConstraints);

		pBtLoadDeck = new JButton("Load Deck");
		pConstraints.gridx=3;
		pConstraints.gridy=2;
		pPanel.add(pBtLoadDeck, pConstraints);
		
		pBtSave = new JButton("Save Deck");
		pConstraints.gridx=3;
		pConstraints.gridy=5;
		pPanel.add(pBtSave, pConstraints);
	}
/*
Botó1: Load cards, ha de permetre carregar les cartes emmagatzemades en una base de dades exist-db en format xml (consulta el format a l'apartat especificacions no funcionals)
Botó2: Rnd Deck, ha de generar una barralla de cartes aleatoria respectant la restricció de valors de cartes definit a l'apartat especificacions no funcionals
Botó3: Save Deck, ha de permetre guarda una barralla de cartes creada o per selecció manual de l'usuari o pel botó 2 en una base de dades MongoDB, una vegada guardada la baralla, ha de sortir un missatge per informar a l'usuari i netejar el panell de baralles.
Botó4: ->, ha de permetre moure una o més d'una carta de la col·lecció a la baralla
Botó5: <-, ha de permetre moure una o més d'una carta de la baralla a la col·lecció
Botó6 + input: Load Deck, carrega una baralla introduint en el input el nom de la baralla, si no existeix s'ha d'indicar a l'usuari, si existeix es carrega la baralla i permet fer modificacions i guardar-les
*/
}
