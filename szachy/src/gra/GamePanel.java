package gra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import figury.Goniec;
import figury.Hetman;
import figury.Krol;
import figury.Pion;
import figury.Skoczek;
import figury.Wieza;
import menu.Gra;
import menu.Menu;
import figury.Figura;

public class GamePanel extends JPanel	implements Runnable {
	public static int WIDTH=1100;//MOZE BYC TEZ FINALE	jezeli moze byc to w takim razie chyba powinna byc to zmienna typu final
	public static int HEIGHT=800;
	int FPS=60;
	boolean canMove;
	boolean validSquare;
	boolean koniecGry;
	boolean pat;
	Thread gameThread; //watek
	Szachownica szachownica=new Szachownica(); //klasa w paczce
	Mouse mouse=new Mouse(); //klasa w paczce
	
	public static int WHITE=1;//kolor	tez chyba powinien byc final
	public static int BLACK=0;
	int currentColor=WHITE;
	//figury
	public static ArrayList<Figura>figury=new ArrayList<>();//lista figura na szachownicy wraz z  polozeniami
	public static ArrayList<Figura>obecne_figury=new ArrayList<>();//aktualizowana figur pozostalych na planszy
	Figura activeP, szachP;//klasa figura z paczki figury
	public static Figura roszadaP; //powinno byc chyba private z geterami i seterami
	//roszada zrobiona brakuje bicia w locie i awansow!!!!!!!!!!!!!!!
	
	public GamePanel() {//konstruktor
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setBackground(Color.black);
		setFigury();
		copyFigury(figury,obecne_figury);
		addMouseMotionListener(mouse);//dodanie listenerow
		addMouseListener(mouse);
	}

	public void lunchgame() {//definicja watku i jego wystartowanie
		gameThread=new Thread(this);
		gameThread.start();
		
	}
	
	private void update() {
		
		if(koniecGry==false && pat==false) {
			if(mouse.pressed) {
				if(activeP==null)	{
					for(Figura f:obecne_figury) {
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
			
			if(mouse.pressed==false) {//upuszczenie figury
				if(activeP!=null) {
					if(validSquare) {//potwierdzenie ze ruch jest legalny
						copyFigury(obecne_figury,figury);//updejt listy figur jesli cos jest  zbite
						activeP.updatePosition();
						if(roszadaP != null) roszadaP.updatePosition(); //updejt polozenia figur bioracych udzial w roszadzie
						
						if(krolWSzachu() && szachMat()) {
							koniecGry = true;
							String koniec ="";
							if(currentColor==WHITE) {
								koniec="Gracz bialych wygrywa!!!";
							}else {
								koniec="Gracz czarnych wygrywa!!!";
							}
							JOptionPane.showMessageDialog(null, koniec);
						}else if(krolWSzachu()){
							System.out.println(szachP.typ + " " + szachP.col + " " + szachP.row);//sprawdzenie co powoduje niepozadanego szacha
							String szach ="";
							if(szachP.color==BLACK) {
								szach="gracz bialych jest pod szachem";
							}else {
								szach="gracz czarnych jest pod szachem";
							}
							JOptionPane.showMessageDialog(null, szach, "Szach", JOptionPane.WARNING_MESSAGE);
						}else if(remis()) {
							pat = true;
							JOptionPane.showMessageDialog(null, "Pat");
						}
						zmianaTury(); //zmiana gracza
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
	}
	
	private void simulate() {
		canMove=false;
		validSquare=false;
		copyFigury(figury,obecne_figury);//jesli ruch jest niemozliwy to tzeba przechowac stara pozycje
		activeP.x=mouse.x-Szachownica.Half_SQUARE_SIZE;
		activeP.y=mouse.y-Szachownica.Half_SQUARE_SIZE;
		activeP.col=activeP.getCol(activeP.x);
		activeP.row=activeP.getRow(activeP.y);
		
		if(roszadaP != null) {//reset pozycji wiezy gdy krol sie ruszy gdzie indziej
			roszadaP.col = roszadaP.preCol;
			roszadaP.x = roszadaP.getX(roszadaP.col);
			roszadaP = null;
		}
		
		if(activeP.canMove(activeP.col,activeP.row)) {
			canMove=true;
			if(krolNieBezpieczny(activeP)==false && krolWNiebezpieczenstwie()==false) validSquare=true;
			if(activeP.hittingP!=null) {
				obecne_figury.remove(activeP.hittingP.getIndex());
			}
			
			sprawdzRoszade();
		}
	}	
	
	
	public void 	paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		szachownica.draw(g2);
		for(Figura f:obecne_figury) {
			f.draw(g2);
		}
		if(activeP!=null) {
			if(canMove) {
				//tutaj hovereffect!!!!!!!!!!
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
		
	}
	private void copyFigury(ArrayList<Figura> source,ArrayList<Figura> target) {
		target.clear();
		for(int i=0;i<source.size();i++) {
			target.add(source.get(i));
		}
		
	}
	
	private void sprawdzRoszade() {
		if(roszadaP!=null) {
			if(roszadaP.col == 0) {//wieza po lewej musi sie ruszyc o 3 pola
				roszadaP.col += 3;
			}else if(roszadaP.col == 7) {//wieza po prawej musi sie ruszyc o 2 pola
				roszadaP.col -=2;
			}
			roszadaP.x = roszadaP.getX(roszadaP.col);//musimy uaktualnic x zeby byl tozsamy z kolumna nowa
		}
	}
	
	private boolean krolNieBezpieczny(Figura krol) {//sprawdza czy krol moze sie ruszyc w dane miejsce
		if(krol.typ == Typ.KROL) {
			for(Figura f : obecne_figury) {
				if(f!=krol && f.color!=krol.color && f.canMove(krol.col, krol.row)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean krolWSzachu() {
		Figura krol = getKrol(true);//krol przeciwnika
		
		if(activeP.canMove(krol.col, krol.row)) {
			szachP = activeP;
			return true;
		}else{
			szachP = null;
		}
		
		return false;
	}
	
	private Figura getKrol(boolean przeciwnik) {//zwraca obiekt figura ktora jest krolem twoim(false) lub przeciwnika(true) potrzebne do metody krolWSzachu()
		Figura krol = null;
		for(Figura f: obecne_figury) {
			if(przeciwnik) {
				if(f.typ==Typ.KROL && f.color!=currentColor) {
					krol=f;
				}
			}else{
				if(f.typ==Typ.KROL && f.color==currentColor) {
					krol=f;
				}
			}
		}
		return krol;
	}
	
	private boolean krolWNiebezpieczenstwie() {//sprawdzamy czy pionek przeciwnika moze ruszyc sie na pole na ktorym znajduje sie krol
		Figura krol = getKrol(false);//twoj krol
		for(Figura f: obecne_figury) {
			if(f.color!=krol.color && f.canMove(krol.col, krol.row)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean remis() {
		int licznik=0;
		
		for(Figura f:obecne_figury) {//liczenie obecnych figur na planszy
			if(f.color!=currentColor) {
				licznik++;
			}
		}
		
		if(licznik==1) {//zostal tylko krol
			if(krolMozeUciec(getKrol(true))==false) {//nie moze uciec
				return true;
			}
		}
		
		return false;
	}
	
	//SZACHMAT @#$%^&*&^%$#@#$%^&*!@#$%^&*(*&^%$#@#$%^&*&^%$#%^&^%$%^&*()(*(&^&^%$#@!(*&^%$#@
	private boolean szachMat() {
		Figura krol = getKrol(true);//krol przeciwnika
		
		if(krolMozeUciec(krol)) {//krol przeciwnika moze uciec
			return false;
		}else {
			//ile przestrzeni jest miedzy matujaca figura a krolem
			int colDiff = Math.abs(szachP.col - krol.col);
			int rowDiff = Math.abs(szachP.row - krol.row);
			
			//sprawdzamy sciezke ataku
			if(colDiff==0) {//atak z gory lub dolu
				if(szachP.row < krol.row) {//atak z gory
					for(int miejsce = szachP.row; miejsce < krol.row; miejsce++) {
						for(Figura f : obecne_figury) {
							if(f!=krol && f.color!=currentColor && f.canMove(szachP.col, miejsce)) {
								return false;
							}
						}
					}
				}
				if(szachP.row > krol.row) {//atak z dolu
					for(int miejsce = szachP.row; miejsce > krol.row; miejsce--) {
						for(Figura f : obecne_figury) {
							if(f!=krol && f.color!=currentColor && f.canMove(szachP.col, miejsce)) {
								return false;
							}
						}
					}
				}
			}else if(rowDiff==0) {//atak z lewej lub prawej
				if(szachP.col < krol.col) {//atak z lewej
					for(int miejsce = szachP.col; miejsce < krol.col; miejsce++) {
						for(Figura f : obecne_figury) {
							if(f!=krol && f.color!=currentColor && f.canMove(miejsce, szachP.row)) {
								return false;
							}
						}
					}
				}
				if(szachP.col > krol.col) {//atak z prawej
					for(int miejsce = szachP.col; miejsce > krol.col; miejsce--) {
						for(Figura f : obecne_figury) {
							if(f!=krol && f.color!=currentColor && f.canMove(miejsce, szachP.row)) {
								return false;
							}
						}
					}
				}
			}else if(colDiff==rowDiff) {//atak po diagonali
				if(szachP.row < krol.row) {//atak z gory
					if(szachP.col < krol.col) {//atak lewa gora
						for(int col = szachP.col, row = szachP.row; col < krol.col; col++, row++) {
							for(Figura f: obecne_figury) {
								if(f!=krol && f.color!=currentColor && f.canMove(col, row)) {
									return false;
								}
							}
						}
					}
					if(szachP.col > krol.col) {//atak prawa gora
						for(int col = szachP.col, row = szachP.row; col > krol.col; col--, row++) {
							for(Figura f: obecne_figury) {
								if(f!=krol && f.color!=currentColor && f.canMove(col, row)) {
									return false;
								}
							}
						}
					}
				}
				if(szachP.row > krol.row) {//atak z dolu
					if(szachP.col < krol.col) {//atak lewy dol
						for(int col = szachP.col, row = szachP.row; col < krol.col; col++, row--) {
							for(Figura f: obecne_figury) {
								if(f!=krol && f.color!=currentColor && f.canMove(col, row)) {
									return false;
								}
							}
						}
					}
					if(szachP.col > krol.col) {//atak prawy dol
						for(int col = szachP.col, row = szachP.row; col > krol.col; col--, row--) {
							for(Figura f: obecne_figury) {
								if(f!=krol && f.color!=currentColor && f.canMove(col, row)) {
									return false;
								}
							}
						}
					}
				}
			}//atak skoczka pomijamy bo przeskakuje
		}
		
		return true;
	}
	
	private boolean krolMozeUciec(Figura krol) {
		
		if(czyMozliwyRatunek(krol, -1, -1)) return true;
		if(czyMozliwyRatunek(krol, 0, -1)) return true;
		if(czyMozliwyRatunek(krol, 1, -1)) return true;
		if(czyMozliwyRatunek(krol, -1, 0)) return true;
		if(czyMozliwyRatunek(krol, 1, 0)) return true;
		if(czyMozliwyRatunek(krol, -1, 1)) return true;
		if(czyMozliwyRatunek(krol, 0, 1)) return true;
		if(czyMozliwyRatunek(krol, 1, 1)) return true;
		
		return false;
	}
	
	private boolean czyMozliwyRatunek(Figura krol, int colPlus, int rowPlus) {
		boolean ratunek = false;
		
		krol.col += colPlus;//symuluje ruch krola zeby zobaczyc czy moge w dane pole przejsc
		krol.row += rowPlus;
		
		if(krol.canMove(krol.col, krol.row)) {//czy moze sie ruszyc
			if(krol.hittingP!=null) {//czy cos zbija
				obecne_figury.remove(krol.hittingP.getIndex());
			}
			if(krolNieBezpieczny(krol)==false) {//czy moze tam zostac
				ratunek = true;
			}
		}
		krol.resetPosition();//reset tej malej "symulacji"
		copyFigury(figury, obecne_figury);
		return ratunek;
	}
	//@#$%^&*&^%#$#$%$%^%^&^&*&^&*^&^%$%$#@#$#@&*(&*()*&^*(&^%&%$%^$#%^$#@$%$#@$%$#$%^%$^&%&*^*(*&(*&))))
	
	private void zmianaTury() {
		currentColor = (currentColor == WHITE) ? BLACK : WHITE;
		activeP = null;
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
