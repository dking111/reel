package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.core.Database;
import main.core.GUI;
import main.core.GameLoop;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 * The main entry point of the application that sets up and displays the main game window.
 * It extends {@link JFrame} to create a window for the game.
 */
public class Main extends JFrame {
    private static Main instance; // Singleton instance
    private GUI gui;
    private GameLoop gameLoop;
    private Database db;
    private int windowWidth, windowHeight;
    
    /**
     * Constructs a new {@code Main} window with specified properties.
     * Sets up the window title, size, and other properties, and adds the game panel.
     */
    private Main() {
        windowWidth = 1920;
        windowHeight = 1080;
        setTitle("misherfan");
        setSize(windowWidth, windowHeight);
        setResizable(false); // Make the window resizable to accommodate scaling
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //db
        db = new Database();
        // Initialize panels
        gui = new GUI(this);
        gameLoop = new GameLoop(this);
        gameLoop.timerStop();

        // Add panels to frame
        add(gui);

        setVisible(true);
    }

    /**
     * Public method to access the Singleton instance.
     * @return The singleton instance of the Main class.
     */
    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    /**
     * Captures the current output of the GameLoop panel as a BufferedImage.
     * 
     * @return A BufferedImage containing the current GameLoop rendering.
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
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Ensure the GUI creation is done on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> getInstance());
    }

    public static GameLoop getGameLoop() {
        return getInstance().gameLoop;
    }

    public static GUI getGui() {
        return getInstance().gui;
    }

    public static int getWindowWidth() {
        Main mainInstance = getInstance();
        return mainInstance.windowWidth;
    }

    public static int getWindowHeight() {
        Main mainInstance = getInstance();
        return mainInstance.windowHeight;
    }

    public static void setWindowDimentions(int width, int height){
        Main mainInstance = getInstance();
        mainInstance.windowWidth = width;
        mainInstance.windowHeight = height;
        mainInstance.setSize(width,height);

    }
}


//to do

//database
//enhance gameplay (see discord) done
//sound/music
//resizeable options (optional) DONE
//-----Default 1920x1080 then is just shrunk in 16:9 ratio DONE

//bug fish cant be seen on second reel DONE
//graphics overhaul DONE



//fish graphics DONE
//changing fish distribution DONE
//music SCRAPPED
//escape menu and help menu DONE
//fancy lighting DONE
//refactor code
//add "YOU CAUGHT FISH" DONE