package main.GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.net.URL;

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
        animationDict = loadAnimations(path);
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
     */
    public Dictionary<String, List<Image>> loadAnimations(String path) {
        Dictionary<String, List<Image>> dict = new Hashtable<>();
        List<String> folders = listFolderInResourceFolder(path);
        List<String> imagePaths = null;
        for (String subfolder :folders){
            imagePaths = listFilesInResourceFolder(subfolder);

            List<Image> animation = new ArrayList<>();
            for (String imagePath : imagePaths){
                animation.add(loadImage('/'+imagePath));
            }
            dict.put(subfolder.substring(subfolder.lastIndexOf("/") + 1), animation);
        }

        return dict;
    }

    /**
     * Lists the subdirectories in the specified resource folder.
     * 
     * @param path the path to the resource folder.
     * @return a list of folder paths.
     */
    public List<String> listFolderInResourceFolder(String path) {
        List<String> paths = new ArrayList<>();  // Initialize the list to store file paths
        try {
            // Get the resource as a URL
            URL resourceUrl = getClass().getClassLoader().getResource(path);
    
            if (resourceUrl != null) {
                // Check if running from a JAR file
                if (resourceUrl.getProtocol().equals("jar")) {
                    String jarPath = resourceUrl.getPath().substring(5, resourceUrl.getPath().indexOf("!"));
                    try (JarFile jar = new JarFile(jarPath)) {
                        // Iterate through the JAR file's entries
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            // Check if the entry is inside the desired folder
                            if (entry.getName().startsWith(path + "/") && entry.getName().length() > path.length() + 1 && !entry.getName().endsWith(".png")) {
                                paths.add(entry.getName().substring(0, entry.getName().length() - 1));  // Add the file name (without directory structure)
                            }
                        }
                    }
                } else {
                    // If not in a JAR, read from file system (during development)
                    try (InputStream in = getClass().getClassLoader().getResourceAsStream(path);
                         BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                        String fileName;
                        while ((fileName = reader.readLine()) != null) {
                            paths.add(path + "/" + fileName);  // Add the file path to the list
                        }
                    }
                }
            } else {
                System.out.println("Resource folder not found: " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return paths;  // Ensure the method returns the paths list
    }

    /**
     * Lists the files in the specified resource folder.
     * 
     * @param path the path to the resource folder.
     * @return a list of file paths.
     */
    public List<String> listFilesInResourceFolder(String path) {
        List<String> paths = new ArrayList<>();  // Initialize the list to store file paths
        try {
            // Get the resource as a URL
            URL resourceUrl = getClass().getClassLoader().getResource(path);
    
            if (resourceUrl != null) {
                // Check if running from a JAR file
                if (resourceUrl.getProtocol().equals("jar")) {
                    String jarPath = resourceUrl.getPath().substring(5, resourceUrl.getPath().indexOf("!"));
                    try (JarFile jar = new JarFile(jarPath)) {
                        // Iterate through the JAR file's entries
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            // Check if the entry is inside the desired folder
                            if (entry.getName().startsWith(path) && !entry.isDirectory()) {
                                paths.add(entry.getName());  // Add the file path to the list
                            }
                        }
                    }
                } else {
                    // If not in a JAR, read from file system (during development)
                    try (InputStream in = getClass().getClassLoader().getResourceAsStream(path);
                         BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
    
                        String fileName;
                        while ((fileName = reader.readLine()) != null) {
                            paths.add(path + "/" + fileName);  // Add the file path to the list
                        }
                    }
                }
            } else {
                System.out.println("Resource folder not found: " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return paths;  // Ensure the method returns the paths list
    }

    /**
     * Loads the image from the classpath (from within the JAR if needed).
     * 
     * @param path the path of the image resource.
     * @return The loaded Image object, or null if there was an error.
     */
    private Image loadImage(String path) {
        try {
            // Load the resource using class loader
            InputStream is = getClass().getResourceAsStream(path);

            if (is != null) {
                // Use ImageIO to read the image from the InputStream
                return ImageIO.read(is);
            } else {
                System.err.println("Resource not found: " + path);
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
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
