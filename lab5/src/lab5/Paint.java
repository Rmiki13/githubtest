package lab5;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JFileChooser;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Paint extends JFrame {
    private DrawingPanel panel;
    private JButton colorButton, loadImageButton;
    private JSlider thicknessSlider;
    private JRadioButton pencilButton, rectButton, eraserButton;

    public Paint() {
        setTitle("Krok 3 - Ołówek, Prostokąt, Gumka");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new DrawingPanel();
        add(panel, BorderLayout.CENTER);

        JPanel toolbar = new JPanel();

        // Wybór koloru
        colorButton = new JButton("Kolor");
        colorButton.addActionListener(e -> {
            Color selected = JColorChooser.showDialog(this, "Wybierz kolor", panel.getCurrentColor());
            if (selected != null) panel.setCurrentColor(selected);
        });
        toolbar.add(colorButton);

        // Grubość linii
        toolbar.add(new JLabel("Grubość:"));
        thicknessSlider = new JSlider(1, 20, 3);
        thicknessSlider.setPaintTicks(true);
        thicknessSlider.setPaintLabels(true);
        thicknessSlider.setMajorTickSpacing(5);
        thicknessSlider.setMinorTickSpacing(1);
        thicknessSlider.addChangeListener(e -> panel.setStrokeSize(thicknessSlider.getValue()));
        toolbar.add(thicknessSlider);

        // Narzędzia
        pencilButton = new JRadioButton("Ołówek", true);
        rectButton = new JRadioButton("Prostokąt");
        eraserButton = new JRadioButton("Gumka");
        ButtonGroup group = new ButtonGroup();
        group.add(pencilButton);
        group.add(rectButton);
        group.add(eraserButton);

        pencilButton.addActionListener(e -> panel.setTool("Ołówek"));
        rectButton.addActionListener(e -> panel.setTool("Prostokąt"));
        eraserButton.addActionListener(e -> panel.setTool("Gumka"));

        toolbar.add(pencilButton);
        toolbar.add(rectButton);
        toolbar.add(eraserButton);

        // Wczytywanie tła
        loadImageButton = new JButton("Wczytaj obrazek");
        loadImageButton.addActionListener(e -> loadBackgroundImage());
        toolbar.add(loadImageButton);

        add(toolbar, BorderLayout.NORTH);
        setVisible(true);
    }

    private void loadBackgroundImage() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                BufferedImage image = ImageIO.read(file);
                panel.setBackgroundImage(image);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Paint());
    }
}
