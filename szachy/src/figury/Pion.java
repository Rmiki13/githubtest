package figury;

import gra.GamePanel;

public class Pion extends figura {

	public Pion(int kolor, int col, int row) {
		super(kolor, col, row);
		
		if(color==GamePanel.WHITE) {
			image=getImage("/figury/w-pawn");
		}
		else {
			image=getImage("/figury/b-pawn");
		}
	}

}
