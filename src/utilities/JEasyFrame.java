package utilities;
import game1.BasicController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

// import statements have been omitted
public class JEasyFrame extends JFrame {
    public Component comp;
    public JEasyFrame(Component comp, String title, BasicController ctrl) {
        super(title);
        addKeyListener((KeyListener) ctrl);
        this.comp = comp;
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
}