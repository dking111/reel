package main;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Sprite{



    public Player(int x, int y,int w, int h) {
        super(x, y, w, h);

    }
    

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(getX(), getY(), getW(), getH());;
    }


}
