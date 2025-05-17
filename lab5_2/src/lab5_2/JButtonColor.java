package lab5_2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;

public class JButtonColor extends JButton implements ActionListener {
	Color red_color=null;
	DrawPanel panel;
	public JButtonColor(String text,DrawPanel panel) {
		super(text);
		this.addActionListener(this);
		this.panel=panel;
		
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		 red_color = JColorChooser.showDialog(this,
                "Select a color", Color.WHITE);
		 panel.set_curr_Color(red_color);
	}

}
