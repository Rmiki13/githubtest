package lab5_2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Paint extends JFrame {
    DrawPanel dp = new DrawPanel();
    JPanel toolbar = new JPanel();
    JButtonColor color_button = new JButtonColor("Zmień kolor", dp);

    public Paint() {
        setTitle("Paint");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initMenuBar();
        initToolbar();

        add(toolbar, BorderLayout.NORTH);
        add(dp, BorderLayout.CENTER);
        setVisible(true);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Plik");

        JMenuItem saveItem = new JMenuItem("Zapisz do pliku");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dp.saveImage();
            }
        });

        JMenuItem loadItem = new JMenuItem("Wczytaj obraz");
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dp.loadImage();
            }
        });

        JMenuItem clearItem = new JMenuItem("Wyczyść");
        clearItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dp.clearDrawing();
            }
        });

        menu.add(saveItem);
        menu.add(loadItem);
        menu.add(clearItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void initToolbar() {
        JButton rectBtn = new JButton("Tryb prostokąt");
        rectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dp.setMode(DrawPanel.Mode.RECTANGLE);
            }
        });

        JButton pencilBtn = new JButton("Tryb ołówek");
        pencilBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dp.setMode(DrawPanel.Mode.PENCIL);
            }
        });

        toolbar.add(color_button);
        toolbar.add(rectBtn);
        toolbar.add(pencilBtn);
    }

    public static void main(String[] args) {
        new Paint();
    }
}
