package main;

import javax.swing.JFrame;

import main.GUI.GUI;
import main.core.GameLoop;

/**
 * The main entry point of the application that sets up and displays the main game window.
 * It extends {@link JFrame} to create a window for the game.
 */
public class Main extends JFrame {
    private GUI gui;
    private GameLoop gameLoop;

    /**
     * Constructs a new {@code Main} window with specified properties.
     * Sets up the window title, size, and other properties, and adds the game panel.
     */
    public Main() {
        setTitle("misherfan");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize panels
        gui = new GUI(this);
        gameLoop = new GameLoop(this);
        gameLoop.timerStop();
        // Start with the GUI panel
        add(gui);

        setVisible(true);
    }

    /**
     * Switches the view from the GUI panel to the GameLoop panel.
     */
    public void switchToGameLoop() {
        // Remove the GUI panel and add the GameLoop panel
        remove(gui);
        gui.timerStop();
        add(gameLoop);
        revalidate();
        repaint();
        gameLoop.requestFocus(); // Make sure the GameLoop panel can receive input focus
        gameLoop.timerStart();
    }

    public void switchToGUI() {
        // Remove the GUI panel and add the GameLoop panel
        remove(gameLoop);
        gameLoop.timerStop();
        add(gui);
        revalidate();
        repaint();
        gui.requestFocus(); // Make sure the GameLoop panel can receive input focus
        gui.timerStart();
    }

    /**
     * The main method that launches the game by creating an instance of {@code Main}.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Main();
    }
}



//Enhanced animations DONE
//fix bug with door in house DONE was a misplaced bracket DONE
//Enhance gameplay, create levels and house, give reason to play
//convert all measurments to be with constants (percentages/ preset sizes)
//fix bug with door in house
//Create saves / persistent data
//