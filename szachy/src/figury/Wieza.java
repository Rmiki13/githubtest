package figury;

import gra.GamePanel;
import gra.Typ;

public class Wieza extends Figura {

	public Wieza(int kolor, int col, int row) {
		super(kolor, col, row);
		typ = Typ.WIEZA;
		
		if(color==GamePanel.WHITE) {
			image=getImage("/figury/w-rook");
		}
		else {
			image=getImage("/figury/b-rook");
		}
	}
	public boolean canMove(int targetCol,int targetRow) {
		if(isWithinBoard(targetCol,targetRow)&& isSameSquare(targetCol,targetRow)==false) {
			if(targetCol==preCol||targetRow==preRow) {
				if(isValidSquare(targetCol,targetRow)&&pieceIsOnStraightLine(targetCol,targetRow)==false) {
					return true;
				}
			}
		}
		
		return false;
	}
}
	