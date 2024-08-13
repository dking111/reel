package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class FishingSpot extends Sprite {
    // Use the inherited constructor from Obstacle
    private Boolean fishing;
    public FishingSpot(int x, int y, int w, int h) {
        super(x, y, w, h);
        fishing = false;

        
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK); // Set the color for the door
        g.fillRect(x, y, w, h);; // Draw the door as a filled rectangle using inherited fields
    }
    @Override
    public void collided(){
        fishing = true;
    }

    public Boolean getFishing(){
        return fishing;
    }





}
