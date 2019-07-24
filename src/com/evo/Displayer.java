package com.evo;

import com.evo.states.StateManager;

import javax.swing.*;
import java.awt.*;

public class Displayer extends JFrame {

    //MEMBER FIELDS
    private Handler handler;
    private JPanel panel;

    //CONSTRUCTOR
    public Displayer(String title, int width, int height, Handler handler) {
        this.handler = handler;
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setResizable(false);
        setLocationRelativeTo(null);

        addKeyListener(handler.getGame().getKeyManager());

        panel = new MyPanel();
        setContentPane(panel);

        setFocusable(true);
        requestFocus();
        setVisible(true);
    }

    //GETTERS-SETTERS
    public JPanel getPanel() {
        return panel;
    }

    //INNER-CLASS
    class MyPanel extends JPanel {

        public MyPanel() {
            setDoubleBuffered(true);
            setFocusable(false);
        }

        @Override
        public void paintComponent(Graphics g) {
            handler.getGame().getStateManager().render(g);
        }
    }

}