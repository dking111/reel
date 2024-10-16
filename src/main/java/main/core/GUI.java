package main.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;
import javax.swing.Timer;

import main.Main;
import main.GUI.Button;
import main.GUI.Text;
import main.GUI.Page;
import main.GUI.PageData;
import main.GameObjects.Sprite;



import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * The GUI class handles the graphical user interface elements and interactions.
 * It extends JPanel and implements ActionListener to handle periodic updates
 * via a timer. The GUI class is responsible for rendering pages, handling
 * mouse interactions, and switching between different pages and game states.
 */
public class GUI extends JPanel implements ActionListener {
    private int mouseX, mouseY;
    private Timer timer;
    private Page currentPage;
    private PageData pageData;
    private BufferedImage backgroundImage; // New field to store the background image

    /**
     * Constructs the GUI panel.
     * 
     * @param mainFrame The main application frame that contains this GUI panel.
     */
    public GUI() {
        this.pageData = new PageData(this);
        // Set panel properties
        setPreferredSize(new Dimension(1920, 1080));
        setBackground(Color.BLACK);

        currentPage = pageData.getPage("menu");

        // Add mouse motion listener to track mouse position
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                double scaleX = 1920.0 / getWidth();  // Original width / current width
                double scaleY = 1080.0 / getHeight(); // Original height / current height
        
                mouseX = (int) (e.getX() * scaleX);
                mouseY = (int) (e.getY() * scaleY);
            }
        });

        // Add mouse listener for button interactions
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int clicked = e.getButton();
                if (clicked == MouseEvent.BUTTON1) {
                    for (Button button : currentPage.getButtons()) {
                        button.listener(mouseX, mouseY, true);
                    }
                }
            }
        });

        // Add key listener for player movement
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ESCAPE){   
                    Main.switchToGameLoop();   
                }
            }
        });

        // Initialize and start the timer
        timer = new Timer(16, this); // Approximately 60 FPS (1000ms/60)
        timer.start();

        // Ensure the panel can receive focus
        setFocusable(true);
    }

    /**
     * Sets the background image for the GUI.
     * 
     * @param image The BufferedImage to be set as the background.
     */
    public void setBackgroundImage(BufferedImage image) {
        this.backgroundImage = image;
        repaint(); // Repaint the panel to update the background
    }

    /**
     * Starts the timer, which controls the periodic updates of the GUI.
     */
    public void timerStart() {
        timer.start();
    }

    /**
     * Stops the timer, halting the periodic updates of the GUI.
     */
    public void timerStop() {
        timer.stop();
    }

    /**
     * Sets the current page to be displayed in the GUI.
     * 
     * @param pageName The name of the page to be displayed.
     */
    public void setCurrentPage(String pageName) {
        if (pageName == "shelf"){
            pageData.refreshShelfPage();
        }
        currentPage = pageData.getPage(pageName);
    }

    /**
     * Switches the application from the GUI to the game loop.
     */
    public void switchToGameLoop() {
        Main.switchToGameLoop();
    }

    /**
     * Called by the timer to perform periodic updates, such as checking button
     * states and repainting the GUI.
     * 
     * @param e The ActionEvent triggered by the timer.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Perform periodic updates
        for (Button button : currentPage.getButtons()) {
            button.listener(mouseX, mouseY, false);
        }
        repaint();
    }

    /**
     * Overrides the paintComponent method to render the GUI, including images,
     * buttons, and text, onto the panel.
     * 
     * @param g The Graphics object used for drawing the components.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        
        Graphics2D g2d = (Graphics2D) g;

        // Calculate the scaling factors for width and height
        double scaleX = getWidth() / 1920.0;  
        double scaleY = getHeight() / 1080.0; 
    
        // Apply the scaling transformation to the Graphics2D object
        g2d.scale(scaleX, scaleY);


        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        draw(g2d);

    }
    public void draw(Graphics2D g2d){
        // Draw the background image if it exists
        if (backgroundImage != null && currentPage.getTransparent()) {
            
            g2d.drawImage(backgroundImage, 0, 0, 1920,1080,null);
        }

        // Draw other GUI elements on top
        for (Sprite imageSprite : currentPage.getSprites()) {
            imageSprite.draw(g2d);
        }
        for (Button button : currentPage.getButtons()) {
            button.draw(g2d);
        }
        for (Text text : currentPage.getTexts()) {
            text.draw(g2d);
        }
    }
}
