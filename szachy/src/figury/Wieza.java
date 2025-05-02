package figury;

import gra.GamePanel;

public class Wieza extends figura {

	public Wieza(int kolor, int col, int row) {
		super(kolor, col, row);
		
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
	