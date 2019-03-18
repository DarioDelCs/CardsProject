package main;

import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controlador.Logica;
import view.View;

public class Main extends JFrame{
	
	private JPanel pPanel;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		pPanel = new JPanel();
		pPanel.setLayout(new GridBagLayout());

		new View(pPanel, new Logica());
		
		add(pPanel);
		setTitle("Deck Selector");
		setSize(1200, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}