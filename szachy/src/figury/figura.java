package figury;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import gra.GamePanel;
import gra.Szachownica;
import gra.Typ;

public class figura {
	public BufferedImage image;
	public int x,y; //??????????
	public int	col,row,preCol,preRow;
	public int color;
	public figura hittingP; //tworzenie instancji klasy wewn niej samej
	public boolean ruszony; //specjalna zmienna dla pionka by okreslic czy sie wczesniej ruszal, no i do roszady
	public Typ typ;

	public figura(int kolor,int col,int row) { //konstruktor
	this.col=col;
	this.color=kolor;
	this.row=row;
	x=getX(col); //????????
	y=getY(row); //??????????????
	preCol=col;
	preRow=row;
	}
	
	public boolean pieceIsOnStraightLine(int targetCol,int targetRow) {
		//ruch w lewo
		for(int c=preCol-1;c>targetCol;c--) {
			for(figura f:GamePanel.obecne_figury) {
				if(f.col==c && f.row==targetRow) {
					hittingP=f;
					return true;
				}
			}
		}
		//ruch w prawo
		for(int c=preCol+1;c<targetCol;c++) {
			for(figura f:GamePanel.obecne_figury) {
				if(f.col==c && f.row==targetRow) {
					hittingP=f;
					return true;
				}
			}
		}
		
		//ruch w gore
		for(int c=preRow-1;c>targetRow;c--) {
			for(figura f:GamePanel.obecne_figury) {
				if(f.col==targetCol && f.row==c) {
					hittingP=f;
					return true;
				}
			}
		}
		//ruch w dol 
		for(int c=preCol+1;c<targetCol;c++) {
			for(figura f:GamePanel.obecne_figury) {
				if(f.col==targetCol && f.row==c) {
					hittingP=f;
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean pieceIsOnDiagonalLine(int targetCol,int targetRow) {
			if(targetRow<preRow) {
				//gorne lewo
				for(int c=preCol-1;c>targetCol;c--) {
					int diff=Math.abs(c-preCol);
					for(figura f:GamePanel.obecne_figury) {
						if(f.col==c && f.row==preRow-diff) {
							hittingP=f;
							return true;
						}
					}
				}
				
				//gorne prawo
				for(int c=preCol+1;c<targetCol;c++) {
					int diff=Math.abs(c-preCol);
					for(figura f:GamePanel.obecne_figury) {
						if(f.col==c && f.row==preRow-diff) {
							hittingP=f;
							return true;
						}
					}
				}
			}
			if(targetRow>preRow) {
				//dol lewo
				for(int c=preCol-1;c>targetCol;c--) {
					int diff=Math.abs(c-preCol);
					for(figura f:GamePanel.obecne_figury) {
						if(f.col==c && f.row==preRow+diff) {
							hittingP=f;
							return true;
						}
					}
				}
				
				//dol prawo
				for(int c=preCol+1;c<targetCol;c++) {
					int diff=Math.abs(c-preCol);
					for(figura f:GamePanel.obecne_figury) {
						if(f.col==c && f.row==preRow+diff) {
							hittingP=f;
							return true;
						}
					}
				}
			}
		return false;
	}
	
	public BufferedImage getImage(String imagePath) {//konstruktor klasy BufferedImage - laduje obrazek
		BufferedImage image =null;
		try {
			image=ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public boolean isSameSquare(int targetCol,int targetRow) { //ten sam kwadrat ale patrzy na poprzedni? czy dziala to z konstruktora ze precol=col
		if(targetCol==preCol&&targetRow==preRow) {
			return true;
		}	
		return false;
	}
	
	public int getX(int col) {//jak sie to liczy? lewy gorny rog kwadratu?
		return col*Szachownica.SQUARE_SIZE;
	}
	
	public int getIndex() {//petli sie po figurach az natrafi na ta na ktorej wywolana funkcja jest (co to ten index)?
		for(int index=0;index<GamePanel.obecne_figury.size();index++) {
			if(GamePanel.obecne_figury.get(index)==this) {
				return index;
			}
		}
		return 0;
	}
	
	public int getY(int row) {//tak samo jak getX()? lewy gorny rog kwadratu?
		return row*Szachownica.SQUARE_SIZE;
	}
	
	public int getCol(int x) {//przenosi nas na srodek kolumny i dzieli przez rozmiar kwadratu (potrzebne to przeniesienie?
		return(x+Szachownica.Half_SQUARE_SIZE)/Szachownica.SQUARE_SIZE;
	}
	
	public int getRow(int y) {//to samo co getCol()
		return(y+Szachownica.Half_SQUARE_SIZE)/Szachownica.SQUARE_SIZE;
	}
	
	public void updatePosition() {//dzialamy pewnie na col i row wiec nadpisuje stare x i y oraz usuwa stara pozycje z preCol i preRow
		x=getX(col);
		y=getY(row);
		preCol=getCol(x);//dlaczego nie preCol=col?
		preRow=getRow(y);
		ruszony = true;//specjalna zmienna piona
	}
	
	public figura getHittingP(int targetCol,int targetRow) {//czy cos trafi po wybraniu lokalizacji, petli sie na kazdej figurze sprawdzajac czy sie znajduje w podanej lokalizacji i czy nie jest nia sama
		for(figura f:GamePanel.obecne_figury) {
			if(f.col==targetCol&&f.row==targetRow&&f!=this) {
				return f;
			}
		}
		return null;
	}
	
	public boolean isValidSquare(int targetCol,int targetRow) {//sprawdza chyba czy mozna tam przejsc, czy to pionek przeciwnika albo czy puste pole
		hittingP=getHittingP(targetCol,targetRow);
		if(hittingP==null) {//pole puste
			return true;
		}
		else {
			if(hittingP.color!=this.color) {
				return true;
			}
			else {
				hittingP=null;	
			}
		}
		return false;
	}
	
	public boolean canMove(int targetCol,int targetRow) {//chyba cantMove ale po co to?????????????????
		return false;
	}
	
	public void resetPosition() {//resetuje na odwrot niz updatePosition() jakie to ma znaczenie?????????????????
		col=preCol;
		row=preRow;
		x=getX(col);
		y=getY(row);
		
	}
	
	public boolean isWithinBoard(int targetCol,int targetRow) {//czy wybrane pole znajduje sie na planszy
		if(targetCol>=0 && targetCol<=7 && targetRow>=0 && targetRow<=7) {
			return true;
		}
		else {return false;}
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(image, x, y,Szachownica.SQUARE_SIZE, Szachownica.SQUARE_SIZE,null);//obraz, x i y gornego lewego rogu, wysokosc i szerokosc obrazu, null nie wiem?????????????/
	}

}
