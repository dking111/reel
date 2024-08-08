package main;

import java.awt.Color;
import java.awt.Graphics;

public class Sprite {
    protected int x, y, w, h; // Changed to protected


    // Constructor
    public Sprite(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    // Draw method to render the sprite
    public void draw(Graphics g) {
        g.setColor(Color.BLUE); // Set the color for the sprite
        g.fillRect(x, y, w, h); // Draw the sprite as a filled rectangle        
    }

    public void move(int dx, int dy) {
        setX(getX()+dx);
        setY(getY()+dy);
    }

    // Getters and setters (if needed)
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getW() { return w; }
    public void setW(int w) { this.w = w; }
    public int getH() { return h; }
    public void setH(int h) { this.h = h; }
}
