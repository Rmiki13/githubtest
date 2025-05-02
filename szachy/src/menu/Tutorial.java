package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Tutorial extends JFrame {

	//numeracja stron
    int s = 1;
    
    String [] poradnik = new String[3]; //może zastąpić to listą?????
    {
    poradnik [0] = "Tu będzie tutorial szachowy!";
    poradnik [1] = "Tutaj będzie druga strona tutorialu szachowego!";
    poradnik [2] = "Tutaj będzie trzecia strona tutorialu szachowego!";
    }
	
	public Tutorial() throws HeadlessException {
		//podstawowe metody
		setTitle("Tutorial");
	    setSize(400, 300);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
	    setVisible(true);
	    this.setLayout(new BorderLayout());
	    
	    //roboczy tekst
	    JLabel label = new JLabel(poradnik[0], SwingConstants.CENTER);
	    this.add(label, BorderLayout.CENTER);
	    
	    JPanel stopka = new JPanel();
	    stopka.setLayout(new BoxLayout(stopka, BoxLayout.LINE_AXIS));
	    stopka.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    
	    //definicja przycisków do przewijania stron
	    JButton next = new JButton("następna strona");
	    JButton previous = new JButton("poprzednia strona");
	    previous.setBackground(Color.gray);
	    
	    JLabel licznik = new JLabel(s + "/" + poradnik.length);
	    
	    //dodanie przycisków z pustym przyciskiem między nimi
	    stopka.add(previous);
	    stopka.add(Box.createHorizontalGlue());
	    stopka.add(licznik);
	    stopka.add(Box.createHorizontalGlue());
	    stopka.add(next);
	    
	    
	    next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(s==poradnik.length) return; //nic się nie dzieje po jego kliknięciu jeśli if spełniony
				s++;
				label.setText(poradnik[s-1]);
				licznik.setText(s + "/" + poradnik.length); //ostatecznie ma to się zmieniać z wartością s
				//if(s>1) {stopka.add(previous);} //docelowo przycisk ma się dopiero pojawiać jak będzie mógł być użyty
				if(s>1) previous.setBackground(null);
			}
		});
	    
	    
	    previous.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(s==1) return; //nic się nie dzieje po jego kliknięciu jeśli if spełniony
				s--;
				label.setText(poradnik[s-1]);	
				licznik.setText(s + "/" + poradnik.length); // ostatecznie ma to się zmieniać z wartością s
				if(s==1) previous.setBackground(Color.gray);
			}
		});
	    
	    this.add(stopka, BorderLayout.PAGE_END);
	}




}
