package main;

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
    private int dx , dy;
    private int speed;

    public Interface() {
        // Set panel properties
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        speed = 5;
        logic = new Logic();



        // Initialize the timer
        timer = new Timer(16, this); // 60 FPS (1000ms/60)
        timer.start();
        setFocusable(true);

        // Add key listener for player controls
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT) {
                    dx = -speed;
                }
        
                if (key == KeyEvent.VK_RIGHT) {
                    dx = speed;
                }
        
                if (key == KeyEvent.VK_UP) {
                    dy = -speed;
                }
        
                if (key == KeyEvent.VK_DOWN) {
                    dy = speed;
                }
            
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                    dx = 0;
                }
        
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                    dy = 0;
                }
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
        for (Sprite sprite : logic.gameData.getSprites()) {
            sprite.draw(g);
        }
        for (Door door : logic.gameData.getDoors()) {
            door.draw(g);
        }

        for (Sprite sprite : logic.gameData.getCameraBounds()){
            sprite.draw(g);
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update game state
        if (!logic.cameraMove()){
            logic.getPlayer().move(dx,dy);
        }
        else{
            for (Sprite sprite : logic.gameData.getSprites()){
                sprite.move(-dx,-dy);
            } 
        }
        logic.collisionDetection(logic.getPlayer(), logic.gameData.getSprites());
        repaint(); // Redraw the screen
    }
}
