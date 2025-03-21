package szachy;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Tutorial extends JFrame {

	public Tutorial() throws HeadlessException {
		 setTitle("Tutorial");
	        setSize(400, 300);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setLocationRelativeTo(null);
	        JLabel label = new JLabel("Tu będzie tutorial szachowy!", SwingConstants.CENTER);
	        add(label);
	        setVisible(true);
	}




}
