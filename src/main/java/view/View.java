package view;

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
		pConstraints.fill = GridBagConstraints.BOTH;

		pBtLoadCards = new JButton("Load Cards");
		pConstraints.gridx=0;
		pConstraints.gridy=0;
		pPanel.add(pBtLoadCards, pConstraints);

		pBtRnd = new JButton("Random Cards");
		pConstraints.gridx=2;
		pConstraints.gridy=0;
		pPanel.add(pBtRnd, pConstraints);

		pLeftPane = new JPanel();
		pConstraints.gridx=0;
		pConstraints.gridy=1;
		pPanel.add(pLeftPane, pConstraints);
		
		pRightPane = new JPanel();
		pConstraints.gridx=2;
		pConstraints.gridy=1;
		pPanel.add(pRightPane, pConstraints);

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
		pConstraints.gridy=4;
		pPanel.add(pBtSave, pConstraints);
	}
/*Bot�1: Load cards, ha de permetre carregar les cartes emmagatzemades en una base de dades exist-db en format xml (consulta el format a l'apartat especificacions no funcionals)
Bot�2: Rnd Deck, ha de generar una barralla de cartes aleatoria respectant la restricci� de valors de cartes definit a l'apartat especificacions no funcionals
Bot�3: Save Deck, ha de permetre guarda una barralla de cartes creada o per selecci� manual de l'usuari o pel bot� 2 en una base de dades MongoDB, una vegada guardada la baralla, ha de sortir un missatge per informar a l'usuari i netejar el panell de baralles.
Bot�4: ->, ha de permetre moure una o m�s d'una carta de la col�lecci� a la baralla
Bot�5: <-, ha de permetre moure una o m�s d'una carta de la baralla a la col�lecci�
Bot�6 + input: Load Deck, carrega una baralla introduint en el input el nom de la baralla, si no existeix s'ha d'indicar a l'usuari, si existeix es carrega la baralla i permet fer modificacions i guardar-les
*/
}
