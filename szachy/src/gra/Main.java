package gra;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class Main extends JFrame {

	
	public static void main(String[] args) {
		JFrame window=new JFrame("simple chess");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//program sie wylacza po zamknieciu okna
		window.setResizable(false);//nie mozna rozciagac ekarnu
		
		//
		GamePanel gp=new GamePanel();
		window.add(gp);
		window.pack();//dostosowuje sie window do rozmiaru gp
		
		//
		window.setLocationRelativeTo(null);//wyswietla sie gra na srodku ekranu
		window.setVisible(true);
		gp.lunchgame();//wlacza watek gry
	}

}
