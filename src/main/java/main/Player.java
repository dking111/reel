package main;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;

public class Player extends AnimatedSprite {
    private String direction;
    private int maxSpeed;

    public Player(int x, int y, int w, int h, String path,int maxSpeed) {
        super(x, y, w, h, path);
        direction = "up";
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void draw(Graphics2D g) {
        // Save the original transformation
        AffineTransform originalTransform = g.getTransform();

        if(dx==maxSpeed && dy== maxSpeed){
            rotate(135, g);
        }
        else if(dx==maxSpeed && dy==-maxSpeed){
            rotate(45, g);
        }
        else if(dx==-maxSpeed && dy==maxSpeed){
            rotate(225, g);
        }
        else if(dx==-maxSpeed && dy==-maxSpeed){
            rotate(315, g);
        }
        else if(dx==0 && dy==maxSpeed){
            rotate(180, g);
        }
        else if(dx==0 &&dy==-maxSpeed){
        }
        else if (dx==maxSpeed && dy==0) {
            rotate(90, g);   
        }
        else if(dx==-maxSpeed && dy==0){
            rotate(270, g);
        }


        // Draw the image
        g.drawImage(currentAnimation.get(currentFrame), getX(), getY(), getW(), getH(), null);

        // Restore the original transformation
        g.setTransform(originalTransform);

        // Frame handling
        if (frameCounter == 1) {
            currentFrame = (currentFrame + 1) % currentAnimation.size();
        }
        frameCounter = (frameCounter + 1) % 60;
    }

    private Graphics2D rotate (int degree, Graphics2D g){
        // Calculate the center of the image
        int centerX = getX() + getW() / 2;
        int centerY = getY() + getH() / 2;

        // Rotate the graphics context around the center of the image
        g.rotate(Math.toRadians(degree), centerX, centerY);

        return g;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    public String getDirection() {
        return direction;
    }
    public void setMaxSpeed(int maxSpeed){
        this.maxSpeed = maxSpeed;
    }
    public int getMaxSpeed(){
        return maxSpeed;
    }
}
