package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Menu extends JFrame {
	
	//tworzenie nowych paneli do ramki
	boolean przeciwnik_bot=true;
	JPanel panel_gora=new JPanel();
	JPanel panel_dol=new JPanel();
	JPanel panel_prawo=new JPanel();
	JPanel panel_lewo=new JPanel();
	JPanel backgroundPanel;
	Gra gra = null;
	
	//importowanie obrazka
	private BufferedImage image;
	
	//deklaracja guzikow (4)
	JButton guzik_wybor_przeciwnika, guzik_tutorial, guzik_jezyk, guzik_graj;
	
	//tablica stringow do przycisku do wyboru trybu
	String[] tryby_gry = {"3+2", "15+10", "1+0"};
	
	//lista wyboru trybu gry
	JComboBox<String> lista_trybow = new JComboBox<String>(tryby_gry);
	
	//konstruktor
	public Menu() throws HeadlessException {
		//podstawowe metody
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600,400);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		setLocationRelativeTo(null); //okno pojawia sie na srodku ekranu
		
		URL resource = getClass().getResource("tlo.jpg");
		try {
			image=ImageIO.read(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Blad odczytu obrazka");
			e.printStackTrace();
		}
		
		 //tworzenie nowego panelu ktorego tlem jest obrazek
		 JPanel backgroundPanel = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                Graphics2D g2d = (Graphics2D) g;
	                g2d.drawImage(image, 0, 0, getWidth(), getHeight()+70, this);
	            }
	     };
	        
	        
	    //definicja przeciwskow (4)
		guzik_wybor_przeciwnika=new JButton("bot");
		guzik_tutorial=new JButton("Tutorial");
		guzik_graj=new JButton("Graj");
		guzik_jezyk=new JButton("Jezyk polski");
		
		//ustawienia rozmiaru przyciskow (5)
		Dimension buttonSize = new Dimension(120, 40);
		guzik_wybor_przeciwnika.setPreferredSize(buttonSize);
        guzik_tutorial.setPreferredSize(buttonSize);
        guzik_jezyk.setPreferredSize(buttonSize);
        lista_trybow.setPreferredSize(buttonSize);
        guzik_graj.setPreferredSize(buttonSize);
        guzik_wybor_przeciwnika.setMaximumSize(buttonSize);
        guzik_tutorial.setMaximumSize(buttonSize);
        guzik_jezyk.setMaximumSize(buttonSize);
        lista_trybow.setMaximumSize(buttonSize);
        guzik_graj.setMaximumSize(buttonSize);

        // Ustawienie układu pionowego i wyśrodkowanie elementów
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Wysrodkowanie przyciskow (3)
        backgroundPanel.add(Box.createVerticalGlue()); // Dodaje przestrzeń na górze (wysrodkowanie wzgledem osi Y)
        lista_trybow.setAlignmentX(Component.CENTER_ALIGNMENT); //wysrodkowanie wzgledem osi X
        guzik_wybor_przeciwnika.setAlignmentX(Component.CENTER_ALIGNMENT);
        guzik_tutorial.setAlignmentX(Component.CENTER_ALIGNMENT);
        guzik_graj.setAlignmentX(Component.CENTER_ALIGNMENT);

        //dodanie przyciskow i przestrzeni miedzy nimi (4)
        backgroundPanel.add(lista_trybow);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Odstęp
        backgroundPanel.add(guzik_wybor_przeciwnika);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Odstęp
        backgroundPanel.add(guzik_tutorial);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Odstęp
        backgroundPanel.add(guzik_graj);
        backgroundPanel.add(Box.createVerticalGlue());
        
        // dodanie do lewego przycisku guzika do zmiany jezyka
        panel_lewo.add(guzik_jezyk);
        
        //przycisk wybierajacy przeciwnika
        ActionListener ustaw_przeciwnika = new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent arg0) {
        		if(przeciwnik_bot==true) {
        			przeciwnik_bot=false;
        			guzik_wybor_przeciwnika.setText("gracz");
        		}
        		else {przeciwnik_bot=true;
        		guzik_wybor_przeciwnika.setText("bot");
        		}
        				
        	}	
        };
        guzik_wybor_przeciwnika.addActionListener(ustaw_przeciwnika);
        
        //przycisk przenoszacy do tutorialu
        ActionListener przenies_do_tutorialu = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "Przenoszę do tutorialu...");//okienko posrednie miedzy menu a tutorialem
                new Tutorial();  // Otwiera nowe okno z tutorialem
                //dispose(); //zamyka menu
                //setVisible(false); //ukrywa menu
            }
        };
        guzik_tutorial.addActionListener(przenies_do_tutorialu);
        
        //przycisk przenoszacy do rozgrywki
        ActionListener zacznij_gre = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "Przenoszę do tutorialu...");//okienko posrednie miedzy menu a rozgrywka
            	
            	if(gra==null || !gra.isDisplayable()) {//sprawdza czy okno istnieje albo czy nie jest wyswietlane
            		gra = new Gra(); // Otwiera nowe okno z rozgrywka
            	}
                //dispose(); //zamyka menu
                //setVisible(false); //ukrywa menu, zamykajac okno z gra nie konczy programu
            }
        };
        guzik_graj.addActionListener(zacznij_gre);
        
        //dodanie naglowka do menu
        JLabel naglowek = new JLabel("SZACHY");
        naglowek.setFont(new Font("BOLD", Font.BOLD, 40));
        panel_gora.add(naglowek);
        //panel_gora.setBorder(BorderFactory.createTitledBorder("SZACHY")); wariant z ramka ale nie wiem jak wysrodkowac
        
        //dodanie paneli do ramki
        this.add(backgroundPanel, BorderLayout.CENTER);
        this.add(panel_gora, BorderLayout.PAGE_START);//przycisk zmien kolor
        this.add(panel_lewo, BorderLayout.WEST);//radioguziki
        this.add(panel_prawo, BorderLayout.EAST);//wartosc
        
	}

	public static void main(String[] args) {
		Menu menu=new Menu();
		menu.setVisible(true);
	}

}
