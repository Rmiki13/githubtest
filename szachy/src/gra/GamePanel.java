package gra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JPanel;

import figury.Goniec;
import figury.Hetman;
import figury.Krol;
import figury.Pion;
import figury.Skoczek;
import figury.Wieza;
import figury.figura;

public class GamePanel extends JPanel	implements Runnable {
	public static int WIDTH=1100;//MOZE BYC TEZ FINALE
	public static int HEIGHT=800;
	int FPS=60;
	boolean canMove;
	boolean validSquare;
	Thread gameThread;
	Szachownica szachownica=new Szachownica();
	Mouse mouse=new Mouse();
	
	public static int WHITE=1;//kolor 
	public static int BLACK=0;
	int currentColor=WHITE;
	//figury
	public static ArrayList<figura>figury=new ArrayList<>();//lista figura na szachownicy wraz z  polozeniami
	public static ArrayList<figura>obecne_figury=new ArrayList<>();
	figura activeP;
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setBackground(Color.black);
		setFigury();
		copyFigury(figury,obecne_figury);
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
	}

	public void lunchgame() {
		gameThread=new Thread(this);
		gameThread.start();
		
	}
	private void update() {
		if(mouse.pressed) {
		if(activeP==null)	{
			for(figura f:obecne_figury) {
				if(f.color==currentColor&&f.col==mouse.x/Szachownica.SQUARE_SIZE&&f.row==mouse.y/Szachownica.SQUARE_SIZE) {
					activeP=f;
				}
			//	System.out.print(mouse.x/Szachownica.SQUARE_SIZE);
				//System.out.print("\n");
			}
			}
		else {
			simulate();
		}
		}
		if(mouse.pressed==false) {
			if(activeP!=null) {
				if(validSquare) {
					copyFigury(obecne_figury,figury);//updejt listy figur jesli cos jest  zbite
					activeP.updatePosition();
				}
				else {
					//ruch jest zabroniony wiec wszystko wraca na poczatek
					copyFigury(figury,obecne_figury);
					activeP.resetPosition();
					activeP=null;
				}
				
			}
			
		}
	}
	
	private void simulate() {
		canMove=false;
		validSquare=false;
		copyFigury(figury,obecne_figury);//jesli ruch jest niemozliwy to tzeba przechowac stara pozycje
		activeP.x=mouse.x-Szachownica.Half_SQUARE_SIZE;
		activeP.y=mouse.y-Szachownica.Half_SQUARE_SIZE;
		activeP.col=activeP.getCol(activeP.x);
		activeP.row=activeP.getRow(activeP.y);
		
		if(activeP.canMove(activeP.col,activeP.row)) {
			canMove=true;
			validSquare=true;
			if(activeP.hittingP!=null) {
				obecne_figury.remove(activeP.hittingP.getIndex());
			}
		}
	}	
	
	
	public void 	paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		szachownica.draw(g2);
		for(figura f:obecne_figury) {
			f.draw(g2);
		}
		if(activeP!=null) {
			if(canMove) {
				//zrbic jak na lichess
			}
			activeP.draw(g2);
		}
		
		
	}
		public void setFigury() {
			figury.add(new Pion(WHITE,0,6));
			figury.add(new Pion(WHITE,1,6));
			figury.add(new Pion(WHITE,2,6));
			figury.add(new Pion(WHITE,3,6));
			figury.add(new Pion(WHITE,4,6));
			figury.add(new Pion(WHITE,5,6));
			figury.add(new Pion(WHITE,6,6));
			figury.add(new Pion(WHITE,7,6));
			figury.add(new Skoczek(WHITE,1,7));
			figury.add(new Skoczek(WHITE,6,7));
			figury.add(new Wieza(WHITE,0,7));
			figury.add(new Wieza(WHITE,7,7));
			figury.add(new Goniec(WHITE,2,7));
			figury.add(new Goniec(WHITE,5,7));
			figury.add(new Hetman(WHITE,3,7));
			figury.add(new Krol(WHITE,4,7));
			
			figury.add(new Pion(BLACK,0,1));
			figury.add(new Pion(BLACK,1,1));
			figury.add(new Pion(BLACK,2,1));
			figury.add(new Pion(BLACK,3,1));
			figury.add(new Pion(BLACK,4,1));
			figury.add(new Pion(BLACK,5,1));
			figury.add(new Pion(BLACK,6,1));
			figury.add(new Pion(BLACK,7,1));
			figury.add(new Skoczek(BLACK,1,0));
			figury.add(new Skoczek(BLACK,6,0));
			figury.add(new Wieza(BLACK,0,0));
			figury.add(new Wieza(BLACK,7,0));
			figury.add(new Goniec(BLACK,2,0));
			figury.add(new Goniec(BLACK,5,0));
			figury.add(new Hetman(BLACK,3,0));
			figury.add(new Krol(BLACK,4,0));
			figury.add(new Goniec(WHITE,4,4));
		
	}
	private void copyFigury(ArrayList<figura> source,ArrayList<figura> target) {
		target.clear();
		for(int i=0;i<source.size();i++) {
			target.add(source.get(i));
		}
		
	}
	@Override
	public void run() {	
		//zwykle sleep nie jest dobre do gier zreszta ma milisekdowa dokladnosc
	double drawInterval=1000000000/FPS;//czas (w nanosekundach) przypadający na jedną klatkę.
	double 	delta=0;
	long lastTime=System.nanoTime();
	long currentTime;
	while(gameThread!=null) {
		currentTime=System.nanoTime();
		delta+=(currentTime-lastTime)/drawInterval;	
		lastTime=currentTime;
		
		if(delta>=1) {//sprawdzamy czy minel czas jednej klatki
			update();
			repaint();
			delta--;
		}
		
	}
	
	
		
	}
	
}
