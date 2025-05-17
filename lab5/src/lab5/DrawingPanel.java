package lab5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
    private ArrayList<FreehandLine> lines = new ArrayList<>();
    private ArrayList<RectangleShape> rectangles = new ArrayList<>();
    private String currentTool = "Ołówek";
    private Color currentColor = Color.BLACK;
    private int currentSize = 5;
    private BufferedImage backgroundImage = null;
    private FreehandLine currentLine = null;
    private RectangleShape currentRect = null;

    public DrawingPanel() {
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (currentTool.equals("Ołówek") || currentTool.equals("Gumka")) {
                    Color drawColor = currentTool.equals("Gumka") ? Color.WHITE : currentColor;
                    currentLine = new FreehandLine(drawColor, currentSize);
                    currentLine.addPoint(e.getPoint());
                    lines.add(currentLine);
                } else if (currentTool.equals("Prostokąt")) {
                    currentRect = new RectangleShape(currentColor, e.getPoint());
                    rectangles.add(currentRect);
                }
            }

            public void mouseReleased(MouseEvent e) {
                currentLine = null;
                currentRect = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (currentLine != null) {
                    currentLine.addPoint(e.getPoint());
                    repaint();
                } else if (currentRect != null) {
                    currentRect.setEnd(e.getPoint());
                    repaint();
                }
            }
        });
    }

    public void setTool(String tool) {
        this.currentTool = tool;
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public void setStrokeSize(int size) {
        this.currentSize = size;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setBackgroundImage(BufferedImage img) {
        this.backgroundImage = img;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, this);
        }

        // Rysowanie linii ręcznych (ołówek)
        for (FreehandLine line : lines) {
            g2.setColor(line.color);
            for (Point p : line.points) {
                g2.fillOval(p.x - line.size / 2, p.y - line.size / 2, line.size, line.size);
            }
        }

        // Rysowanie prostokątów
        for (RectangleShape rect : rectangles) {
            g2.setColor(rect.color);
            int x = Math.min(rect.start.x, rect.end.x);
            int y = Math.min(rect.start.y, rect.end.y);
            int w = Math.abs(rect.start.x - rect.end.x);
            int h = Math.abs(rect.start.y - rect.end.y);
            g2.drawRect(x, y, w, h);
        }
    }
}

class FreehandLine {
    ArrayList<Point> points = new ArrayList<>();
    Color color;
    int size;

    public FreehandLine(Color c, int s) {
        color = c;
        size = s;
    }

    public void addPoint(Point p) {
        points.add(p);
    }
}

class RectangleShape {
    Point start;
    Point end;
    Color color;

    public RectangleShape(Color c, Point s) {
        color = c;
        start = s;
        end = s;
    }

    public void setEnd(Point e) {
        end = e;
    }
}
