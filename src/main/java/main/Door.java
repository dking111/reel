package main;

import java.awt.Color;
import java.awt.Graphics;

public class Door extends Sprite {

    // Use the inherited constructor from Obstacle
    public Door(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN); // Set the color for the door
        g.fillRect(x, y, w, h);; // Draw the door as a filled rectangle using inherited fields
    }
}
