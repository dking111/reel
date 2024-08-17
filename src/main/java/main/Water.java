package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Water extends Sprite{
    public Water(int x, int y,int w,int h){
        super(x, y, w, h);
    }
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE); // Set the color for the outline
        g.drawRect(x, y, w, h); // Draws the outline of a square at (50, 50) with width and height of 100
    }
    
}
