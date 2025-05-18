package gra;

import java.awt.Color;
import java.awt.Graphics2D;

public class Szachownica {
	int MAX_COL=8;
	int MAX_ROW=8;
	public static int SQUARE_SIZE=100;
	public static int Half_SQUARE_SIZE=SQUARE_SIZE/2;
	public void draw(Graphics2D g2) {
		int c=0;
		for(int row=0;row<MAX_ROW;row++) {
			for(int col=0;col<MAX_COL;col++) {
				if(c==0) {
					g2.setColor(new Color(0,0,0));
					c=1;
				}
				else {
					g2.setColor(new Color(255,255,255));
					c=0;
				}
				g2.fillRect(col* SQUARE_SIZE, row*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			}
			if(c==0) {
				c=1;
			}
			else {
				c=0;
			}
		}
	}
	
}
