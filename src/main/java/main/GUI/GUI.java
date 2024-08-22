package main.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


import javax.swing.JPanel;
import javax.swing.Timer;

import main.Main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;

/**
 * The GUI class handles the graphical user interface elements and interactions.
 * It extends JPanel and implements ActionListener to handle periodic updates
 * via a timer. The GUI class is responsible for rendering pages, handling
 * mouse interactions, and switching between different pages and game states.
 */
public class GUI extends JPanel implements ActionListener {
    private int mouseX, mouseY;
    private Timer timer;
    private Main mainFrame;
    private Page currentPage;
    private PageData pageData;

    /**
     * Constructs the GUI panel.
     * 
     * @param mainFrame The main application frame that contains this GUI panel.
     */
    public GUI(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.pageData = new PageData(this);
        // Set panel properties
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);

        currentPage = pageData.getPage("menu");

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

                    for (Button button : currentPage.getButtons()) {
                        button.listener(mouseX, mouseY, true);
                    }
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
        currentPage = pageData.getPage(pageName);
    }

    /**
     * Switches the application from the GUI to the game loop.
     */
    public void switchToGameLoop() {
        mainFrame.switchToGameLoop();
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

        for (ImageSprite imageSprite : currentPage.getImageSprites()) {
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
