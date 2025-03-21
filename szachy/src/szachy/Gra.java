package szachy;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Gra extends JFrame {

	public Gra() throws HeadlessException {
		 setTitle("Gra");
	        setSize(400, 300);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setLocationRelativeTo(null);
	        JLabel label = new JLabel("Tu będzie gra!", SwingConstants.CENTER);
	        add(label);
	        setVisible(true);
	}

	
}
