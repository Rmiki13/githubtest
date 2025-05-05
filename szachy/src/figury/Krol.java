package figury;

import gra.GamePanel;
import gra.Typ;

public class Krol extends figura {

	public Krol(int kolor, int col, int row) {
		super(kolor, col, row);
		typ = Typ.KROL;
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
			
			if(ruszony == false) {
				
				if(targetCol == preCol+2 && targetRow == preRow && pieceIsOnStraightLine(targetCol, targetRow) == false) {//w prawo
					for(figura f : GamePanel.obecne_figury) {
						if(f.col == preCol+3 && f.row == preRow && f.ruszony == false) {
							GamePanel.roszadaP = f;
							return true;
						}
					}
				}
				
				if(targetCol == preCol-2 && targetRow == preRow && pieceIsOnStraightLine(targetCol, targetRow) == false) {//w lewo
					figura tmp[] = new figura[2];//w lewo jest o jedno pole wiecej wiec trzeba sprawdzic dodatkowe pole miedzy wieza a krolem
					for(figura f : GamePanel.obecne_figury) {
						if(f.col == preCol-3 && f.row == targetRow) {
							tmp[0] = f;
						}
						if(f.col == preCol-4 && f.row == targetRow) {
							tmp[1] = f;
						}
						
						if(tmp[0]==null && tmp[1]!=null && tmp[1].ruszony==false) {
							GamePanel.roszadaP = tmp[1];
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
