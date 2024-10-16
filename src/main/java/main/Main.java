package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import main.core.GUI;
import main.core.GameLoop;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 * The main entry point of the application that sets up and displays the main game window.
 * It extends {@link JFrame} to create a window for the game.
 * The Main class follows the Singleton design pattern to ensure only one instance of the game window exists.
 */
public class Main extends JFrame {
    private static Main instance; // Singleton instance
    private GUI gui;
    private GameLoop gameLoop;
    private int windowWidth, windowHeight;

    /**
     * Private constructor to initialize the main game window.
     * Sets the window title, size, properties, and adds the game panel.
     */
    private Main() {
        windowWidth = 1920;
        windowHeight = 1080;
        setTitle("misherfan");
        setSize(windowWidth, windowHeight);
        setResizable(false); // Make the window resizable to accommodate scaling
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize  panels
        gui = new GUI();
        gameLoop = new GameLoop(this);
        gameLoop.timerStop();

        // Add GUI panel to frame
        add(gui);

        setVisible(true);
    }

    /**
     * Public method to access the Singleton instance of the Main class.
     * Ensures only one instance of the game window exists at a time.
     * 
     * @return The singleton instance of the Main class.
     */
    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    /**
     * Captures the current output of the GameLoop panel as a {@link BufferedImage}.
     * This image can be used for rendering or saving the current game state as a screenshot.
     * 
     * @return A {@link BufferedImage} containing the current GameLoop rendering.
     */
    public BufferedImage captureGameLoopImage() {
        BufferedImage image = new BufferedImage(gameLoop.getWidth(), gameLoop.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        gameLoop.paint(g2d); // Paints the current state of the GameLoop panel into the image
        g2d.dispose();
        return image;
    }

    /**
     * Switches the view from the GUI panel to the GameLoop panel.
     * Removes the GUI panel and adds the GameLoop panel to the frame.
     * The GameLoop panel is then started to begin the game.
     */
    public static void switchToGameLoop() {
        Main mainInstance = getInstance(); // Get the singleton instance

        // Remove the GUI panel and add the GameLoop panel
        mainInstance.remove(mainInstance.gui);
        mainInstance.gui.timerStop();
        mainInstance.add(mainInstance.gameLoop);
        mainInstance.revalidate();
        mainInstance.repaint();
        mainInstance.gameLoop.requestFocus(); // Make sure the GameLoop panel can receive input focus
        mainInstance.gameLoop.timerStart();
    }

    /**
     * Switches the view from the GameLoop panel to the GUI panel.
     * Captures the GameLoop output and sets it as the background of the GUI.
     * Removes the GameLoop panel and adds the GUI panel to the frame.
     * 
     * @param page The name of the page to be displayed on the GUI after the switch.
     */
    public static void switchToGUI(String page) {
        Main mainInstance = getInstance(); // Get the singleton instance

        // Capture the GameLoop output before switching
        BufferedImage gameLoopImage = mainInstance.captureGameLoopImage();
        
        // Set the captured image as the background of the GUI
        mainInstance.gui.setBackgroundImage(gameLoopImage);
        
        // Remove the GameLoop panel and add the GUI panel
        mainInstance.remove(mainInstance.gameLoop);
        mainInstance.gameLoop.timerStop();
        mainInstance.add(mainInstance.gui);
        mainInstance.revalidate();
        mainInstance.repaint();
        mainInstance.gui.requestFocus(); // Make sure the GUI panel can receive input focus
        mainInstance.gui.timerStart();
        mainInstance.gui.setCurrentPage(page);
    }

    /**
     * The main method that launches the game by creating an instance of {@code Main}.
     * The GUI creation is done on the Event Dispatch Thread to ensure thread safety.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Ensure the GUI creation is done on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> getInstance());
    }

    /**
     * Gets the current instance of the GameLoop.
     * 
     * @return The current instance of the GameLoop.
     */
    public static GameLoop getGameLoop() {
        return getInstance().gameLoop;
    }

    /**
     * Gets the current instance of the GUI panel.
     * 
     * @return The current instance of the GUI panel.
     */
    public static GUI getGui() {
        return getInstance().gui;
    }

    /**
     * Gets the width of the game window.
     * 
     * @return The width of the game window.
     */
    public static int getWindowWidth() {
        Main mainInstance = getInstance();
        return mainInstance.windowWidth;
    }

    /**
     * Gets the height of the game window.
     * 
     * @return The height of the game window.
     */
    public static int getWindowHeight() {
        Main mainInstance = getInstance();
        return mainInstance.windowHeight;
    }

    /**
     * Sets the dimensions of the game window.
     * 
     * @param width  The width to set the game window to.
     * @param height The height to set the game window to.
     */
    public static void setWindowDimentions(int width, int height) {
        Main mainInstance = getInstance();
        mainInstance.windowWidth = width;
        mainInstance.windowHeight = height;
        mainInstance.setSize(width, height);
    }
}
