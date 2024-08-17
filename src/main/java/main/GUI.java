package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;

public class GUI extends JPanel implements ActionListener {
    private int mouseX, mouseY;
    private Timer timer;
    private Button button,button1;

    public GUI() {
        // Set panel properties
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);

        button = new Button(100, 100, 100, 100, Color.GREEN, Color.RED, Color.BLUE, this::timerStop);
        button1 = new Button(500, 100, 100, 100, Color.ORANGE, Color.PINK, Color.BLUE, this::timerStart);

        // Add mouse motion listener to track mouse position
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        // Add mouse listener for button interactions
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int clicked = e.getButton();
                if (clicked == MouseEvent.BUTTON1) {
                    button.listener(mouseX, mouseY, true);
                    button1.listener(mouseX, mouseY, true);
                    // Example action for left mouse button click
                    
                }
            }
        });

        // Initialize and start the timer
        timer = new Timer(16, this); // Approximately 60 FPS (1000ms/60)
        timer.start();

        // Ensure the panel can receive focus
        setFocusable(true);
    }

    public void timerStart() {
        timer.start();
    }

    public void timerStop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Perform periodic updates
        System.out.println(mouseX);
        button.listener(mouseX, mouseY, false);
        button1.listener(mouseX, mouseY, false);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        button.draw(g2d);
        button1.draw(g2d);
    }
}
