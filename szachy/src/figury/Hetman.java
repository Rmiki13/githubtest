package figury;

import gra.GamePanel;

public class Hetman extends figura {

	public Hetman(int kolor, int col, int row) {
		super(kolor, col, row);
		if(color==GamePanel.WHITE) {
			image=getImage("/figury/w-queen");
		}
		else {
			image=getImage("/figury/b-queen");
		}
	}

}
