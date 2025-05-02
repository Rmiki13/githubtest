package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Gra extends JFrame {

	//konstruktor
	public Gra() throws HeadlessException {
		//podstawowe metody
		setTitle("Gra");
	    setSize(800, 600);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
	    this.setVisible(true);
	    this.setLayout(new BorderLayout());
	    
	    //deklaracja paneli
	    JPanel top_panel = new JPanel();
	    JPanel left_panel = new JPanel();
	    JPanel mid_panel = new JPanel();
	    JPanel right_panel = new JPanel();
	    JPanel bot_panel = new JPanel();
	    
	    //szachownica
	    mid_panel.setLayout(new GridLayout(8, 8));
	    
	    for(int i=1; i<=8; i++) {
	    	for(int a=65; a<=72; a++) { //takie liczby z kodu ASCII zeby po kolei byly litery A-H
	    		JButton button = new JButton((char)a + "" + i);
	    		mid_panel.add(button);
	    		button.setBorderPainted(false);// usuwa obramowania przyciskow
	    		if((i+a)%2!=0) { //warunek na pomalowanie pol na czarno
	    			button.setForeground(Color.white);
	    			button.setBackground(Color.black);
	    		}
	    		button.setName("pole_"+(char)a+i); //zmienia nazwe przycisku na np. pole_A1
	    	}
	    }
	    
	    //naglowek
	    JLabel label = new JLabel("Tu będzie gra! (albo zegar)", SwingConstants.CENTER);
	    top_panel.add(label);
	    
	    JLabel kartka = new JLabel("Tutaj będą się zapisywać ruchy");
	    right_panel.add(kartka);
	    
	    //dodanie paneli do ramki
	    this.add(top_panel, BorderLayout.PAGE_START);
	    this.add(mid_panel, BorderLayout.CENTER);
	    this.add(right_panel, BorderLayout.LINE_END);
	}

	
}