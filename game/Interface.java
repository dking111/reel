package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Interface extends JPanel implements ActionListener {

    private Timer timer;
    private Logic logic;

    public Interface() {
        // Set panel properties
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);

        logic = new Logic();



        // Initialize the timer
        timer = new Timer(16, this); // 60 FPS (1000ms/60)
        timer.start();
        setFocusable(true);

        // Add key listener for player controls
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                logic.getPlayer().keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                logic.getPlayer().keyReleased(e);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        // Draw the player
        logic.getPlayer().draw(g);
        for (Obstacle obstacle : logic.getObstacles) {
            obstacle.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update game state
        logic.getPlayer().move();
        repaint(); // Redraw the screen
    }
}
