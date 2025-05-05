package figury;

import gra.GamePanel;
import gra.Typ;

public class Hetman extends figura {

	public Hetman(int kolor, int col, int row) {
		super(kolor, col, row);
		typ = Typ.HETMAN;
		if(color==GamePanel.WHITE) {
			image=getImage("/figury/w-queen");
		}
		else {
			image=getImage("/figury/b-queen");
		}
	}
	
	public boolean canMove(int targetCol, int targetRow) {
		if(isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {
			
			if(targetCol==preCol || targetRow==preRow) {//ruch gora dol i prawo lewo
				if(isValidSquare(targetCol, targetRow) && pieceIsOnStraightLine(targetCol, targetRow) == false) {
					return true;
				}
			}
			
			if(Math.abs(targetCol-preCol) == Math.abs(targetRow-preRow)) {//ruch po diagonali
				if(isValidSquare(targetCol, targetRow) && pieceIsOnDiagonalLine(targetCol, targetRow) == false) {
					return true;
				}
			}
		}
		return false;
	}

}
