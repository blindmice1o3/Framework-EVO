import javax.swing.*;
import java.awt.*;

public class Displayer {

    //CONSTANTS
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    //MEMBER FIELDS
    private JFrame frame;
    private JPanel panel;

    //CONSTRUCTOR
    public Displayer(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panel = new MyPanel();

        frame.setContentPane(panel);

        frame.setVisible(true);
    }

    //INNER-CLASS
    class MyPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(Assets.chapter2Intro, 0, 0, getWidth(), getHeight(), null);
        }
    }

}
