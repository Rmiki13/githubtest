package figury;

import gra.GamePanel;
import gra.Typ;

public class Pion extends figura {

	public Pion(int kolor, int col, int row) {
		super(kolor, col, row);
		typ = Typ.PION;
		
		if(color==GamePanel.WHITE) {
			image=getImage("/figury/w-pawn");
		}
		else {
			image=getImage("/figury/b-pawn");
		}
	}

	public boolean canMove(int targetCol, int targetRow) {
		
		if(isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {
			
			int direction = (color == GamePanel.WHITE) ? -1 : 1;//definiuje czy pionek bedzie ruszac sie w gore czy dol na podstawie koloru
			
			//zabawa z pokreconymi warunkami START
			hittingP = getHittingP(targetCol, targetRow);
			
			if(targetCol == preCol && targetRow == preRow + direction && hittingP == null) {//zwykly ruch o jedno pole do przodu
				return true;
			}
			
			if(targetCol == preCol && targetRow == preRow + 2*direction && hittingP == null && ruszony == false && pieceIsOnStraightLine(targetCol, targetRow) == false) {
				return true; //pierwszy ruch piona w grze o 2 pola do przodu
			}
			
			if(Math.abs(targetCol-preCol) == 1 && targetRow == preRow + direction && hittingP != null && hittingP.color != color) {
				return true;
			}
		}
		return false;
	}
}
