package szachy;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Menu extends JFrame {
	boolean przeciwnik_bot=true;
	JPanel panel_centrum=new JPanel();
	JPanel panel_gora=new JPanel();
	JPanel panel_dol=new JPanel();
	JPanel panel_prawo=new JPanel();
	JPanel panel_lewo=new JPanel();
	Image backgroundImage;
	JButton guzik_wybor_przeciwnika,guzik_tutorial,guzik_jezyk,guzik_graj;
	String[] tryby_gry = {"3+2", "15+10", "1+0"};
	JComboBox<String> lista_trybow = new JComboBox<String>(tryby_gry);
	public Menu() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(600,400);
		this.setLayout(new BorderLayout());
		
		 backgroundImage = new ImageIcon("C:/Users/User/Desktop/Downloads/tlo.jpg").getImage();//nie chce sie ladowac obrazek
		 JPanel backgroundPanel = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	            }
	        };
		
		Dimension buttonSize = new Dimension(120, 40);
		guzik_wybor_przeciwnika=new JButton("bot");
		guzik_tutorial=new JButton("Tutorial");
		guzik_graj=new JButton("Graj");
		guzik_jezyk=new JButton("Jezyk polski");
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

        panel_centrum.add(Box.createVerticalGlue()); // Dodaje przestrzeń na górze
        lista_trybow.setAlignmentX(Component.CENTER_ALIGNMENT);
        guzik_wybor_przeciwnika.setAlignmentX(Component.CENTER_ALIGNMENT);
        guzik_tutorial.setAlignmentX(Component.CENTER_ALIGNMENT);
        guzik_graj.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel_centrum.add(lista_trybow);
        panel_centrum.add(Box.createRigidArea(new Dimension(0, 10))); // Odstęp
        panel_centrum.add(guzik_wybor_przeciwnika);
        panel_centrum.add(Box.createRigidArea(new Dimension(0, 10))); // Odstęp
        panel_centrum.add(guzik_tutorial);
        panel_centrum.add(Box.createRigidArea(new Dimension(0, 10))); // Odstęp
        panel_centrum.add(guzik_graj);
        panel_centrum.add(Box.createVerticalGlue());
        
        panel_lewo.add(guzik_jezyk);
        
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
        
        ActionListener przenies_do_tutorialu = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Przenoszę do tutorialu...");
                new Tutorial();  // Otwiera nowe okno z tutorialem
            }
        };
        guzik_tutorial.addActionListener(przenies_do_tutorialu);
        
        ActionListener zacznij_gre = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Przenoszę do tutorialu...");
                new Gra();  // Otwiera nowe okno z tutorialem
            }
        };
        guzik_graj.addActionListener(zacznij_gre);
        
        backgroundPanel.add(panel_centrum, BorderLayout.CENTER);
        this.add(panel_centrum, BorderLayout.CENTER);
        this.add(panel_gora, BorderLayout.PAGE_END);//przycisk zmien kolor
        this.add(panel_lewo, BorderLayout.WEST);//radioguziki
        this.add(panel_prawo, BorderLayout.EAST);//wartosc
        
       
	}


	public static void main(String[] args) {
		Menu menu=new Menu();
		menu.setVisible(true);

	}

}
