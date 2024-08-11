package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Player extends AnimatedSprite {
    private String direction;


    public Player(int x, int y, int w, int h,String path) {
        super(x, y, w, h,path);
        direction = "up";
        

    }
    @Override
    public void draw(Graphics2D g) {

        int centerX = w / 2;
        int centerY = h / 2;
        g.translate(w / 2, h / 2);
        // Rotate the image 90 degrees (π/2 radians) to the right
        g.rotate(Math.toRadians(90));
        // Move the origin back to the top-left corner of the image
        g.translate(-centerX, -centerY);

        g.drawImage(currentAnimation.get(currentFrame), getX(), getY(), getW(), getH(), null);
        if (frameCounter==1){
        currentFrame=((currentFrame+1) % currentAnimation.size());
        }
        //1 per second, add setting!
        frameCounter=((frameCounter+1) % 60);
    }
}
