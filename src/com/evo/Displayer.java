package com.evo;

import com.evo.states.StateManager;

import javax.swing.*;
import java.awt.*;

public class Displayer extends JFrame {

    //MEMBER FIELDS
    private JPanel panel;

    //CONSTRUCTOR
    public Displayer(String title, int width, int height) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setResizable(false);
        setLocationRelativeTo(null);

        panel = new MyPanel();

        setContentPane(panel);

        setVisible(true);
    }

    //GETTERS-SETTERS
    public JPanel getPanel() {
        return panel;
    }

    //INNER-CLASS
    class MyPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            StateManager.getCurrentState().render(g);
            //g.drawImage(com.evo.gfx.evo.Assets.chapter2Intro, 0, 0, getWidth(), getHeight(), null);
        }
    }

}