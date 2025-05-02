package figury;

import gra.GamePanel;

public class Goniec extends figura {

	public Goniec(int kolor, int col, int row) {
		super(kolor, col, row);
		if(color==GamePanel.WHITE) {
			image=getImage("/figury/w-bishop");
		}
		else {
			image=getImage("/figury/b-bishop");
		}
	}
	public boolean canMove(int targetCol,int targetRow) {
		if(isWithinBoard(targetCol,targetRow) && isSameSquare(targetCol,targetRow)==false) {
			if(Math.abs(targetCol-preCol)==Math.abs(targetRow-preRow)){
				if(isValidSquare(targetCol,targetRow) && pieceIsOnDiagonalLine(targetCol,targetRow)==false) {
					return true;
				}
			}
		}
		return false;
	}
	
}
