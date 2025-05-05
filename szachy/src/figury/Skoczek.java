package figury;

import gra.GamePanel;
import gra.Typ;

public class Skoczek extends figura {

	public Skoczek(int kolor, int col, int row) {
		super(kolor, col, row);
		typ = Typ.SKOCZEK;
		if(color==GamePanel.WHITE) {
			image=getImage("/figury/w-knight");
		}
		else {
			image=getImage("/figury/b-knight");
		}
	}
	public boolean canMove(int targetCol,int targetRow) {
	if(isWithinBoard(targetCol,targetRow)) {
		if(Math.abs(targetCol-preCol)*Math.abs(targetRow-preRow)==2) {
			if(isValidSquare(targetCol,targetRow)) {
				return true;
			}
		}
	}
	return false;
	}
}
