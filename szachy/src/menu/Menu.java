package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
	JPanel panel_centrum=new JPanel();
	JPanel panel_gora=new JPanel();
	JPanel panel_dol=new JPanel();
	JPanel panel_prawo=new JPanel();
	JPanel panel_lewo=new JPanel();
	
	//importowanie obrazka
	ImageIcon temporaryImage = new ImageIcon("tlo.jpg");
	Image backgroundImage = temporaryImage.getImage();
	
	//deklaracja guzikow (4)
	JButton guzik_wybor_przeciwnika, guzik_tutorial, guzik_jezyk, guzik_graj;
	
	//tablica stringow do przycisku do wyboru trybu
	String[] tryby_gry = {"3+2", "15+10", "1+0","horda"};
	
	//lista wyboru trybu gry
	JComboBox<String> lista_trybow = new JComboBox<String>(tryby_gry);
	
	//konstruktor
	public Menu() throws HeadlessException {
		//podstawowe metody
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(600,400);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		setLocationRelativeTo(null); //okno pojawia sie na srodku ekranu
		
		 //W TEORII tworzenie nowego panelu ktorego tlem jest obrazek
		 JPanel backgroundPanel = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
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
        panel_centrum.setLayout(new BoxLayout(panel_centrum, BoxLayout.Y_AXIS));
        panel_centrum.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Wysrodkowanie przyciskow (3)
        panel_centrum.add(Box.createVerticalGlue()); // Dodaje przestrzeń na górze (wysrodkowanie wzgledem osi Y)
        lista_trybow.setAlignmentX(Component.CENTER_ALIGNMENT); //wysrodkowanie wzgledem osi X
        guzik_wybor_przeciwnika.setAlignmentX(Component.CENTER_ALIGNMENT);
        guzik_tutorial.setAlignmentX(Component.CENTER_ALIGNMENT);
        guzik_graj.setAlignmentX(Component.CENTER_ALIGNMENT);

        //dodanie przyciskow i przestrzeni miedzy nimi (4)
        panel_centrum.add(lista_trybow);
        panel_centrum.add(Box.createRigidArea(new Dimension(0, 10))); // Odstęp
        panel_centrum.add(guzik_wybor_przeciwnika);
        panel_centrum.add(Box.createRigidArea(new Dimension(0, 10))); // Odstęp
        panel_centrum.add(guzik_tutorial);
        panel_centrum.add(Box.createRigidArea(new Dimension(0, 10))); // Odstęp
        panel_centrum.add(guzik_graj);
        panel_centrum.add(Box.createVerticalGlue());
        
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
            	String wybranyTryb = (String) lista_trybow.getSelectedItem();
            	int czasStartowy;
            	int czasDodawany;
            	boolean czy_horda;
            	if(wybranyTryb=="horda") {
            		czasStartowy=600;
            		czasDodawany=32;
            		czy_horda=true;
            		
            		
                }
            	else {
           		 czasStartowy = parseCzasMinuty(wybranyTryb) * 60; // w sekundach
               	 czasDodawany = parseCzasDodawanySekundy(wybranyTryb); // w sekundach
               	 czy_horda=false;
           	}
            	new Gra(czasStartowy, czasDodawany, przeciwnik_bot,czy_horda);
                // Otwiera nowe okno z rozgrywka
                  dispose(); //zamyka menu
        		
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
        backgroundPanel.add(panel_centrum, BorderLayout.CENTER);
        this.add(panel_centrum, BorderLayout.CENTER);
        this.add(panel_gora, BorderLayout.PAGE_START);//przycisk zmien kolor
        this.add(panel_lewo, BorderLayout.WEST);//radioguziki
        this.add(panel_prawo, BorderLayout.EAST);//wartosc
        
        
        //Sprawdzanie czy obraz sie laduje
    	if (backgroundImage == null) {
    	    panel_centrum.setBackground(Color.red);
    	} else {
    		panel_centrum.setBackground(Color.blue);
    	}
    	
	}
	public static int parseCzasMinuty(String tryb) {
	    String[] czesci = tryb.split("\\+");
	    return Integer.parseInt(czesci[0]); // np. "3" z "3+0"
	}

	public static int parseCzasDodawanySekundy(String tryb) {
	    String[] czesci = tryb.split("\\+");
	    return Integer.parseInt(czesci[1]); // np. "0" z "3+0"
	}


	public static void main(String[] args) {
		Menu menu=new Menu();
		menu.setVisible(true);
	}

}
