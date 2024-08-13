package main;

import javax.swing.JFrame;

public class Main extends JFrame {

    public Main() {
        setTitle("misherfan");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add game panel
        GameLoop gamePanel = new GameLoop();
        add(gamePanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}


//To Do
//Graphics (initial) DONE
//Animation (inital) DONE
//Changing location (loading different levels) DONE
//enhanced animation (changing state, forward, backward) DONE
//Fishing controls
//Game interface (GUI)
//Create an actual level