package figury;

import gra.GamePanel;

public class Krol extends figura {

	public Krol(int kolor, int col, int row) {
		super(kolor, col, row);
		if(color==GamePanel.WHITE) {
			image=getImage("/figury/w-king");
		}
		else {
			image=getImage("/figury/b-king");
		}
	}
	public boolean canMove(int targetCol,int targetRow) {
		if(isWithinBoard(targetCol,targetRow)) {
			if(Math.abs(targetCol-preCol)+Math.abs(targetRow-preRow)==1||Math.abs(targetCol-preCol)*Math.abs(targetRow-preRow)==1) {
				if(	isValidSquare(targetCol,targetRow)) {
					
					return true;
				}
				}
			
		}
		return false;
	}
}
