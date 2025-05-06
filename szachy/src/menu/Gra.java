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

import gra.GamePanel;

public class Gra extends JFrame {

	//konstruktor
	public Gra() throws HeadlessException {
		//podstawowe metody
		setTitle("Gra");
	    setSize(800, 600);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);//wyswietla sie gra na srodku ekranu
	    this.setVisible(true);
	    this.setLayout(new BorderLayout());
	    
	    this.setResizable(false);//nie mozna rozciagac ekarnu
		
		GamePanel gp=new GamePanel();
		this.add(gp, BorderLayout.CENTER);
		this.pack();//dostosowuje sie window do rozmiaru gp
		
		gp.lunchgame();//wlacza watek gry
	    
	    //deklaracja paneli
	    JPanel top_panel = new JPanel();
	    //JPanel left_panel = new JPanel();
	    JPanel right_panel = new JPanel();
	    //JPanel bot_panel = new JPanel();
	    
	    
	    
	    //naglowek
	    JLabel label = new JLabel("Tu będzie gra! (albo zegar)", SwingConstants.CENTER);
	    top_panel.add(label);
	    
	    JLabel kartka = new JLabel("Tutaj będą się zapisywać ruchy");
	    right_panel.add(kartka);
	    
	    //dodanie paneli do ramki
	    this.add(top_panel, BorderLayout.PAGE_START);
	    this.add(right_panel, BorderLayout.LINE_END);
	}

	
}