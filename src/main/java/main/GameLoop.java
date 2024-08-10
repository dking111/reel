package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import javax.swing.*;
import java.awt.*;


public class GameLoop extends JPanel implements ActionListener {

    private Timer timer;
    private Logic logic;
    private Camera camera;
    private Player player;
    private GameData gameData;
    private int speed;
    private Background background;

    public GameLoop() {
        // Set panel properties
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        speed = 5;
        gameData = new GameData("src/main/resources/levels/1.json");
        player = new Player(100, 100, 50, 50,gameData.getPlayer());
        logic = new Logic(player,gameData);
        camera = new Camera(logic,gameData,player);
        background = new Background(0, 0, gameData.getWidth(), gameData.getHeight(), gameData.getBackground());
      
        



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
                    player.setDx(-speed);
                }
        
                if (key == KeyEvent.VK_RIGHT) {
                    player.setDx(speed);
                }
        
                if (key == KeyEvent.VK_UP) {
                    player.setDy(-speed);
                }
        
                if (key == KeyEvent.VK_DOWN) {
                    player.setDy(speed);
                }
            
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                    player.setDx(0);
                }
        
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                    player.setDy(0);
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
        background.draw(g);
        // Draw the player
        player.draw(g);
        for (Sprite sprite : gameData.getSprites()) {
            sprite.draw(g);
        }
        for (Door door : gameData.getDoors()) {
            door.draw(g);
        }


        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update game state
        
        // Move player based on current dx/dy values
        player.move(player.getDx(), player.getDy());
    
        // Adjust the camera movement if the player reaches near the screen boundary
        camera.cameraMove();
    
        // Apply camera movement to all sprites (parallax effect)
        if (camera.getDx() != 0 || camera.getDy() != 0) {
            for (Sprite sprite : gameData.getSprites()) {
                sprite.move(camera.getDx(), camera.getDy());
            }
        }
    
        // Check collisions with other sprites
        logic.collisionDetection(player, gameData.getSprites());
    
        // Redraw the screen
        repaint();
    }
    
}
