package main;

import javax.swing.JFrame;

/**
 * The main entry point of the application that sets up and displays the main game window.
 * It extends {@link JFrame} to create a window for the game.
 */
public class Main extends JFrame {

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

        // Add the game panel to the frame
        GUI gui = new GUI();
        GameLoop gamePanel = new GameLoop();
        add(gui);
        //add(gamePanel);

        setVisible(true);
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

// To Do
// Graphics (initial) DONE
// Animation (initial) DONE
// Changing location (loading different levels) DONE
// Enhanced animation (changing state, forward, backward) DONE
// Fishing controls DONE
// Game interface (GUI)
// Create an actual level
