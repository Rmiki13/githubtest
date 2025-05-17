package lab5_2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {

    enum Mode { RECTANGLE, PENCIL }

    private Mode mode = Mode.RECTANGLE;
    private Color currentColor = Color.BLACK;
    private Point startPoint;
    private Rectangle currentRect;

    private ArrayList<Rectangle> rectList = new ArrayList<>();
    private ArrayList<Color> rectColorList = new ArrayList<>();

    private ArrayList<Point> pencilPoints = new ArrayList<>();
    private ArrayList<Color> pencilColorList = new ArrayList<>();

    public DrawPanel() {
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void set_curr_Color(Color c) {
        currentColor = c;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void clearDrawing() {
        rectList.clear();
        rectColorList.clear();
        pencilPoints.clear();
        pencilColorList.clear();
        repaint();
    }

    public void saveImage() {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        paint(g2);
        g2.dispose();

        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                ImageIO.write(image, "png", file);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadImage() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                BufferedImage image = ImageIO.read(file);
                Graphics g = getGraphics();
                g.drawImage(image, 0, 0, null);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Rysuj zapisane prostokąty
        for (int i = 0; i < rectList.size(); i++) {
            g2.setColor(rectColorList.get(i));
            g2.fill(rectList.get(i));
        }

        // Rysuj zapisane punkty ołówka
        for (int i = 0; i < pencilPoints.size(); i++) {
            g2.setColor(pencilColorList.get(i));
            Point p = pencilPoints.get(i);
            g2.fillRect(p.x, p.y, 2, 2);
        }

        // Rysuj aktualnie rysowany prostokąt
        if (currentRect != null) {
            g2.setColor(currentColor);
            g2.fill(currentRect);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (mode == Mode.RECTANGLE) {
            startPoint = e.getPoint();
        } else if (mode == Mode.PENCIL) {
            pencilPoints.add(e.getPoint());
            pencilColorList.add(currentColor);
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mode == Mode.RECTANGLE && startPoint != null) {
            int x = Math.min(startPoint.x, e.getX());
            int y = Math.min(startPoint.y, e.getY());
            int width = Math.abs(startPoint.x - e.getX());
            int height = Math.abs(startPoint.y - e.getY());
            currentRect = new Rectangle(x, y, width, height);
            repaint();
        } else if (mode == Mode.PENCIL) {
            pencilPoints.add(e.getPoint());
            pencilColorList.add(currentColor);
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (mode == Mode.RECTANGLE && currentRect != null) {
            rectList.add(currentRect);
            rectColorList.add(currentColor);
            currentRect = null;
            repaint();
        }
    }

    // Puste metody
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}
/*public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {
	
	ArrayList<Point> pointList=new ArrayList();
	ArrayList<Color> colorList = new ArrayList<>();
    Color curr_Color = Color.BLACK;
    Point startPoint = null;
	
	DrawPanel(){
		this.addMouseListener(this);
		this.setBackground(Color.WHITE);
	}
	public void set_curr_Color(Color c) {
		curr_Color=c;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	System.out.print("sad");
	Point p = new Point(e.getX(), e.getY());
	pointList.add(p);
	colorList.add(curr_Color);
	repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		 startPoint = e.getPoint();
		
	}
	@Override
    public void mouseDragged(MouseEvent e) {
        
        if (startPoint != null) {
           
            int x = Math.min(startPoint.x, e.getX());
            int y = Math.min(startPoint.y, e.getY());
            int width = Math.abs(startPoint.x - e.getX());
            int height = Math.abs(startPoint.y - e.getY());

            
            rectList.clear(); 
            rectList.add(new Rectangle(x, y, width, height));
            repaint();
        }
    }
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < pointList.size(); i++) {
            Point p = pointList.get(i);
            Color color = colorList.get(i); // Pobieramy kolor z listy
            g2.setColor(color);
            g2.fillRect(p.x, p.y, 100, 100);  // Rysowanie wypełnionego prostokąta
        }
    }
}
*/
