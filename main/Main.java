package main;

import javax.swing.JFrame;
import game.Interface;

public class Main extends JFrame {

    public Main() {
        setTitle("misherfan");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add game panel
        Interface gamePanel = new Interface();
        add(gamePanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
