package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Door extends Sprite {
    private String path;
    private Boolean levelChange;
    // Use the inherited constructor from Obstacle
    public Door(int x, int y, int w, int h,String path) {
        super(x, y, w, h);
        this.path = path;
        levelChange = false;
        
        

    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN); // Set the color for the door
        g.fillRect(x, y, w, h);; // Draw the door as a filled rectangle using inherited fields
    }
    @Override
    public void collided(){
        levelChange = true;
    }

    public String levelChanged(){
        try{
        if (levelChange){
            levelChange = false;
            return path;
        }
        else{
            return null;
        }
    }
    catch(NullPointerException e){
        return null;
    }

}

}
