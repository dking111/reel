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
    protected List<Image> currentAnimation; 
    protected Dictionary<String, List<Image>> animationDict; 
    protected int currentFrame; 
    protected int frameCounter; 
    protected String state; 
    protected int animationSpeed; 
    protected Boolean isAnimationComplete;

    /**
     * Constructs an AnimatedSprite object with the specified position, size, and animation directory.
     * 
     * @param x the x-coordinate of the sprite.
     * @param y the y-coordinate of the sprite.
     * @param w the width of the sprite.
     * @param h the height of the sprite.
     * @param path the path to the directory containing animation subdirectories.
     * @param animationSpeed the speed of the animation.
     */
    public AnimatedSprite(int x, int y, int w, int h, String path, int animationSpeed) {
        super(x, y, w, h);
        currentFrame = 0;
        state = "idle";
        animationDict = loadAnimation(path);
        currentAnimation = animationDict.get("idle");
        frameCounter = 0;
        this.animationSpeed = animationSpeed;
        isAnimationComplete = false;
    }

    /**
     * Draws the current frame of the animation at the sprite's position.
     * 
     * @param g the Graphics2D object used for drawing.
     */
    @Override
    public void draw(Graphics2D g) {
        if (isVisible) {
            currentAnimation = animationDict.get(state);
            g.drawImage(currentAnimation.get(currentFrame), getX(), getY(), getW(), getH(), null);
            isAnimationComplete = (currentFrame == currentAnimation.size() - 1);

            if (frameCounter == 1) {
                currentFrame = (currentFrame + 1) % currentAnimation.size();
            }
            frameCounter = (frameCounter + 1) % animationSpeed;
        }
    }

    /**
     * Loads animations from the specified directory path. Each subdirectory represents a different
     * animation state and contains images that make up the frames of that animation.
     * 
     * @param path the path to the directory containing animation subdirectories.
     * @return a dictionary mapping animation states to lists of images.
     * TODO: convert to jar readable format
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

    /**
     * Refreshes the current animation, resetting it to the beginning.
     */
    public void refreshAnimation() {
        currentAnimation = animationDict.get(state);
        currentFrame = 0;
        frameCounter = 0;
        isAnimationComplete = false;
    }

    /**
     * Sets the state of the sprite and refreshes the animation if the state changes.
     * 
     * @param newState the new state to set for the sprite.
     */
    public void setState(String newState) {
        if (!state.equals(newState)) {
            state = newState;
            refreshAnimation();
            isAnimationComplete = false;
        }
    }

    /**
     * Gets the bearing between two points in degrees.
     * 
     * @param x1 the x-coordinate of the first point.
     * @param y1 the y-coordinate of the first point.
     * @param x2 the x-coordinate of the second point.
     * @param y2 the y-coordinate of the second point.
     * @return the bearing in degrees.
     */
    public int getBearing(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        double angleRadians = Math.atan2(dy, dx);
        double angleDegrees = Math.toDegrees(angleRadians);
        double bearing = (angleDegrees + 90);

        if (bearing < 0) {
            bearing += 360;
        }

        return (int) Math.round(bearing);
    }

    /**
     * Rotates the Graphics2D context around the center of the image by the specified angle.
     * 
     * @param degree The angle in degrees to rotate the graphics context.
     * @param g      The Graphics2D context to rotate.
     * @return The rotated Graphics2D context.
     */
    public Graphics2D rotate(int degree, Graphics2D g) {
        int centerX = getX() + getW() / 2;
        int centerY = getY() + getH() / 2;
        g.rotate(Math.toRadians(degree), centerX, centerY);
        return g;
    }

    /**
     * Moves the sprite towards a target position at a specified speed.
     * 
     * @param targetX the target x-coordinate.
     * @param targetY the target y-coordinate.
     * @param speed the speed at which to move the sprite.
     */
    public void moveTowards(int targetX, int targetY, int speed) {
        double stepSize = speed;
        double dx = targetX - getX();
        double dy = targetY - getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < stepSize) {
            setX(targetX);
            setY(targetY);
        } else {
            dx /= distance;
            dy /= distance;
            move((int) Math.round(dx * stepSize), (int) Math.round(dy * stepSize));
        }
    }

    /**
     * Gets whether the animation is complete for the current state.
     * 
     * @return True if the animation is complete, false otherwise.
     */
    public Boolean getIsAnimationComplete() {
        return isAnimationComplete;
    }

    /**
     * Gets the current state of the sprite.
     * 
     * @return The current state of the sprite.
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the speed of the animation.
     * 
     * @param animationSpeed the speed of the animation (higher values make the animation slower).
     */
    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    /**
     * Gets the current animation speed.
     * 
     * @return The speed of the animation.
     */
    public int getAnimationSpeed() {
        return animationSpeed;
    }
}

