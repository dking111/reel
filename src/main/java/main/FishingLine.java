package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;


public class FishingLine extends Sprite {
    private int playerX, playerY;
    public FishingLine(int x, int y, int w, int h,int playerX,int playerY){
        super(x, y, w, h);
        this.playerX = playerX;
        this.playerY = playerY;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.RED); 
        g.fillRect(x, y, w, h);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(5));
        g.drawLine(x+w/2, y+h/2, playerX, playerY);
    }
    
}
