package main.GameObjects;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Represents an animated sprite that can switch between different animations based on its state.
 * The sprite's animations are loaded from a directory structure where each subdirectory contains
 * images for a particular animation state.
 */
public class AnimatedSprite extends Sprite {
    protected List<Image> currentAnimation; // List of images representing the current animation
    protected Dictionary<String, List<Image>> animationDict; // Dictionary of all animations by state
    protected int currentFrame; // Current frame index in the animation
    protected int frameCounter; // Counter to control frame rate
    protected String state; // Current animation state (e.g., "idle", "run")
    protected int animationSpeed; // Speed of animation in frames per second
    protected Boolean isAnimationComplete;

    /**
     * Constructs an AnimatedSprite object with the specified position, size, and animation directory.
     * 
     * @param x the x-coordinate of the sprite.
     * @param y the y-coordinate of the sprite.
     * @param w the width of the sprite.
     * @param h the height of the sprite.
     * @param path the path to the directory containing animation subdirectories.
     */
    public AnimatedSprite(int x, int y, int w, int h, String path,int animationSpeed) {
        super(x, y, w, h);
        currentFrame = 0;
        state = "idle";
        animationDict = loadAnimation(path);
        currentAnimation = animationDict.get("idle");
        frameCounter = 0;
        this.animationSpeed = animationSpeed; // Default speed is 1 frame per second (assuming 60 FPS)
        isAnimationComplete=false;
    }

    /**
     * Draws the current frame of the animation at the sprite's position.
     * 
     * @param g the Graphics2D object used for drawing.
     */
    @Override
    public void draw(Graphics2D g) {
        if(isVisible){
        currentAnimation = animationDict.get(state);
        g.drawImage(currentAnimation.get(currentFrame), getX(), getY(), getW(), getH(), null);
        

        isAnimationComplete = (currentFrame==currentAnimation.size()-1)?true:false;

        // Update frame if frameCounter is at the update interval
        if (frameCounter == 1) {
            currentFrame = (currentFrame + 1) % currentAnimation.size();
        }

        // Increment frameCounter and reset it when it reaches the animation speed
        frameCounter = (frameCounter + 1) % animationSpeed;
    }
    }

    /**
     * Loads animations from the specified directory path. Each subdirectory represents a different
     * animation state and contains images that make up the frames of that animation.
     * 
     * @param path the path to the directory containing animation subdirectories.
     * @return a dictionary mapping animation states to lists of images.
     */
    public Dictionary<String, List<Image>> loadAnimation(String path) {
        Dictionary<String, List<Image>> dict = new Hashtable<>();
        File folder = new File(path);
        File[] listOfSubFolders = folder.listFiles();

        for (File directory : listOfSubFolders) {
            List<Image> animation = new ArrayList<>();
            
            if (directory.isDirectory()) {
                File subFolder = new File(path + "\\" + directory.getName());
                File[] listOfFiles = subFolder.listFiles();

                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        ImageIcon icon = new ImageIcon(file.getPath());
                        animation.add(icon.getImage());
                    }
                }
            }
            dict.put(directory.getName(), animation);
        }

        return dict;
    }
    public void refreshAnimation(){
        currentAnimation = animationDict.get(state);
        currentFrame = 0;
        frameCounter=0;
    }


    

    public int getBearing(int x1, int y1, int x2, int y2) {
        // Calculate the differences in x and y coordinates
        int dx = x2 - x1;
        int dy = y2 - y1;
    
        // Calculate the angle in radians using atan2 (angle relative to the x-axis)
        double angleRadians = Math.atan2(dy, dx);
    
        // Convert the angle to degrees
        double angleDegrees = Math.toDegrees(angleRadians);
    
        // Adjust angle to bearing:
        // 0 degrees should point to the north, and it increases clockwise.
        // So subtract 90 degrees to rotate the angle and convert to bearing.
        double bearing = (angleDegrees + 90);
    
        // Normalize the bearing to a range of 0 to 360 degrees
        if (bearing < 0) {
            bearing += 360;
        }
    
        // Round the result to the nearest integer and return
        return (int) Math.round(bearing);
    }
    
    /**
     * Rotates the {@code Graphics2D} context around the center of the image by the specified angle.
     *
     * @param degree The angle in degrees to rotate the graphics context.
     * @param g      The {@code Graphics2D} context to rotate.
     * @return The rotated {@code Graphics2D} context.
     */
    public Graphics2D rotate(int degree, Graphics2D g) {
        // Calculate the center of the image
        int centerX = getX() + getW() / 2;
        int centerY = getY() + getH() / 2;

        // Rotate the graphics context around the center of the image
        g.rotate(Math.toRadians(degree), centerX, centerY);

        return g;
    }

    public void moveTowards(int targetX, int targetY, int speed) {
        // Calculate the differences in x and y coordinates
        double stepSize = speed;
        double dx = targetX - getX();
        double dy = targetY - getY();
        
        // Calculate the distance between the current position and the target position
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        // If the distance is less than the step size, move directly to the target
        if (distance < stepSize) {
            setX(targetX);
            setY(targetY);
        } else {
            // Normalize the direction vector
            dx /= distance;
            dy /= distance;
            
            // Update the position by moving stepSize in the direction of the target
            move((int)Math.round(dx * stepSize),(int)Math.round(dy * stepSize));

        }
    }
    public Boolean getIsAnimationComplete() {
        return isAnimationComplete;
    }
    public void setState(String state) {
        this.state = state;
}
    public String getState() {
        return state;
}
public void setAnimationSpeed(int animationSpeed) {
    this.animationSpeed = animationSpeed;
}
public int getAnimationSpeed() {
    return animationSpeed;
}
}
