package main.GameObjects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * Represents a fish in the game that can swim and be hooked by a fishing line.
 * The fish has properties such as weight, rarity, and visibility.
 */
public class Fish extends AnimatedSprite {
    private static final String ASSET_PATH = "assets/fish"; 
    private static final int DEFAULT_SPEED = 5; 

    private Random random; 
    private int angle; 
    private int fishingLineX; 
    private int fishingLineY; 
    private int speed; 
    private Boolean hooked; 
    private Boolean hasBeenHooked; 
    private String name; 
    private int rarity; 
    private int maxWeight; 
    private int minWeight; 
    private int weight; 

    /**
     * Constructs a Fish instance with the specified parameters.
     *
     * @param x The x-coordinate of the fish.
     * @param y The y-coordinate of the fish.
     * @param w The width of the fish.
     * @param h The height of the fish.
     * @param weight The initial weight of the fish.
     * @param maxWeight The maximum weight the fish can reach.
     * @param minWeight The minimum weight the fish can reach.
     * @param name The name of the fish.
     * @param rarity The rarity level of the fish.
     */
    public Fish(int x, int y, int w, int h, int weight, int maxWeight, int minWeight, String name, int rarity) {
        super(x, y, w, h, ASSET_PATH, 5);
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.minWeight = minWeight;
        this.name = name;
        this.rarity = rarity;
        isVisible = false;
        random = new Random();
        angle = 0;
        hooked = false;
        hasBeenHooked = false;
        speed = DEFAULT_SPEED;
    }

    /**
     * Spawns the fish in the water at a random position and sets its initial state.
     *
     * @param water The water object in which the fish will be spawned.
     * @param fishingLineX The x-coordinate of the fishing line.
     * @param fishingLineY The y-coordinate of the fishing line.
     */
    public void spawn(Water water, int fishingLineX, int fishingLineY) {
        this.weight = random.nextInt(maxWeight-minWeight)+minWeight;
        this.w = 10 + weight;
        this.h = 10 + weight * 2;
        this.fishingLineX = fishingLineX;
        this.fishingLineY = fishingLineY;
        x = random.nextInt( water.getX() + water.getW()-water.getX())+water.getX();
        y = random.nextInt( water.getY() + water.getH()-water.getY())+water.getY();
        isVisible = true;
        angle = getBearing(x + w / 2, y, fishingLineX, fishingLineY);
    }

    /**
     * Updates the fish's position based on the fishing line's coordinates.
     *
     * @param fishingLineX The new x-coordinate of the fishing line.
     * @param fishingLineY The new y-coordinate of the fishing line.
     */
    public void update(int fishingLineX, int fishingLineY) {
        this.fishingLineX = fishingLineX;
        this.fishingLineY = fishingLineY;
        moveTowards(fishingLineX, fishingLineY, speed);
    }

    /**
     * Draws the fish on the screen with the correct rotation based on its angle.
     *
     * @param g The Graphics2D object used for drawing the fish.
     */
    @Override
    public void draw(Graphics2D g) {
        AffineTransform originalTransform = g.getTransform();
        if (hooked) {
            angle = getBearing(x + w / 2, y, fishingLineX, fishingLineY);
        }
        rotate(angle, g);
        super.draw(g);
        g.setTransform(originalTransform);
    }

    /**
     * Triggers the hooking state of the fish.
     * Sets the state to "sinking" and updates the animation.
     */
    public void hooking() {
        if (!hasBeenHooked) {
            state = "sinking";
            refreshAnimation();
            hooked = true;
            hasBeenHooked = true;
        }
    }

    /**
     * Sets whether the fish is hooked.
     * 
     * @param hooked A boolean indicating if the fish is hooked.
     */
    public void setHooked(Boolean hooked) {
        this.hooked = hooked;
    }

    /**
     * Gets the current hooked state of the fish.
     *
     * @return A boolean indicating whether the fish is hooked.
     */
    public Boolean getHooked() {
        return hooked;
    }

    /**
     * Sets the speed of the fish.
     *
     * @param speed The speed of the fish.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Gets the rarity of the fish.
     *
     * @return The rarity level of the fish.
     */
    public int getRarity() {
        return rarity;
    }

    /**
     * Gets the current weight of the fish.
     *
     * @return The weight of the fish.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the fish.
     *
     * @param weight The weight of the fish.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Gets the name of the fish.
     *
     * @return The name of the fish.
     */
    public String getName() {
        return name;
    }
}
