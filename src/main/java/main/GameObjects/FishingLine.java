package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.util.Random;

/**
 * Represents a fishing line in the game.
 * The fishing line's behavior changes based on user input and its state relative to the player.
 */
public class FishingLine extends Sprite {
    
    private static final int MIN_STRUGGLES_BEFORE_REST = 2;
    private static final int MAX_STRUGGLES_BEFORE_REST = 6;
    private static final int MIN_MAX_FISH_SPEED = 4;
    private static final int MAX_MAX_FISH_SPEED = 6;
    private static final int MIN_AMPLITUDE = 150;
    private static final int MAX_AMPLITUDE = 400;
    private static final int MAX_TENSION = 70;
    private static final int REST_DURATION = 40;
    
    private int playerX, playerY;
    private Random random;
    private Sprite fishingLineFloat;
    private Boolean isGameOver;
    private int amplitude;
    private int maxFishSpeed;
    private int tension;
    private int struggleCounter;
    private int maxStruggles;
    private int strugglesBeforeRest;
    private int restCounter;
    private int reelSpeed;

    /**
     * Constructs a FishingLine instance with initial parameters.
     *
     * @param x The x-coordinate of the fishing line.
     * @param y The y-coordinate of the fishing line.
     * @param w The width of the fishing line.
     * @param h The height of the fishing line.
     * @param playerX The x-coordinate of the player.
     * @param playerY The y-coordinate of the player.
     */
    public FishingLine(int x, int y, int w, int h, int playerX, int playerY) {
        super(x, y, w, h);
        this.playerX = playerX;
        this.playerY = playerY;
        random = new Random();
        strugglesBeforeRest = random.nextInt(MAX_STRUGGLES_BEFORE_REST-MIN_STRUGGLES_BEFORE_REST)+MIN_STRUGGLES_BEFORE_REST;
        maxStruggles = random.nextInt(10 - 4) + 4;
        struggleCounter = 0;
        maxFishSpeed = random.nextInt(MAX_MAX_FISH_SPEED-MIN_MAX_FISH_SPEED)+MIN_MAX_FISH_SPEED;
        dx = random.nextInt(5 - 1) + 1;
        amplitude = random.nextInt(MAX_AMPLITUDE-MIN_AMPLITUDE)+MIN_AMPLITUDE;
        fishingLineFloat = new Sprite(x, y, w, h);
        isGameOver = false;
        tension = 0;
        restCounter = 0;
        reelSpeed = 2;
    }

    /**
     * Draws the fishing line and its current state on the screen.
     *
     * @param g The Graphics2D object used for drawing.
     */
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, w, h);

        if (tension < 10) {
            g.setColor(Color.WHITE);
        } else if (tension < 30) {
            g.setColor(Color.ORANGE);
        } else if (tension < MAX_TENSION) {
            g.setColor(Color.RED);
        } else {
            isGameOver = true;
        }

        if (restCounter != 0) {
            g.setColor(new Color(136, 236, 136));
        }

        g.setStroke(new BasicStroke(5));
        g.drawLine(x + w / 2, y + h / 2, playerX, playerY);
    }

    /**
     * Updates the state of the fishing line based on user input and current state.
     *
     * @param isLeftHeld Indicates if the left input is being held.
     * @param isRightHeld Indicates if the right input is being held.
     * @param isMouseHeld Indicates if the mouse input is being held.
     */
    public void update(Boolean isLeftHeld, Boolean isRightHeld, Boolean isMouseHeld) {
        setFishingLineFloat(x, y);

        if (restCounter > 0) {
            handleRestState(isMouseHeld);
            return;
        }

        updateTensionAndDirection(isLeftHeld, isRightHeld);
        move(dx, 0);
    }

    /**
     * Handles the state when the fishing line is resting.
     *
     * @param isMouseHeld Indicates if the mouse input is being held.
     */
    private void handleRestState(Boolean isMouseHeld) {
        tension = 0;
        restCounter--;

        if (isMouseHeld) {
            move(dx, reelSpeed);
        }

        if (restCounter == 0) {
            dx = (dx < 0) ? -maxFishSpeed : maxFishSpeed;
            strugglesBeforeRest = random.nextInt(MAX_STRUGGLES_BEFORE_REST-MIN_STRUGGLES_BEFORE_REST)+MIN_STRUGGLES_BEFORE_REST;
        }
    }

    /**
     * Updates the tension and direction based on the position relative to the player.
     *
     * @param isLeftHeld Indicates if the left input is being held.
     * @param isRightHeld Indicates if the right input is being held.
     */
    private void updateTensionAndDirection(Boolean isLeftHeld, Boolean isRightHeld) {
        if (x > playerX + amplitude / 2) {
            tension++;
            handleLeftHeld(isLeftHeld);
        } else if (x < playerX - amplitude / 2) {
            tension++;
            handleRightHeld(isRightHeld);
        } else {
            tension = 0;
            struggleCounter = 0;
            dx = (dx < 0) ? -maxFishSpeed - random.nextInt(6) : maxFishSpeed + random.nextInt(6);
        }
    }

    /**
     * Handles the logic when the left input is being held.
     *
     * @param isLeftHeld Indicates if the left input is being held.
     */
    private void handleLeftHeld(Boolean isLeftHeld) {
        if (isLeftHeld) {
            if (dx > -maxFishSpeed) {
                dx -= 1;
            }

            if (dx == 0) {
                handleStruggle(-5, 3);
            }
        }
    }

    /**
     * Handles the logic when the right input is being held.
     *
     * @param isRightHeld Indicates if the right input is being held.
     */
    private void handleRightHeld(Boolean isRightHeld) {
        if (isRightHeld) {
            if (dx < maxFishSpeed) {
                dx += 1;
            }

            if (dx == 0) {
                handleStruggle(5, -3);
            }
        }
    }

    /**
     * Handles the struggle logic for the fishing line.
     *
     * @param moveAmount The amount to move the fishing line during a struggle.
     * @param resetDx The value to reset dx after a struggle.
     */
    private void handleStruggle(int moveAmount, int resetDx) {
        if (struggleCounter != maxStruggles) {
            move(moveAmount, 0);
            dx = resetDx;
            struggleCounter++;
        } else {
            strugglesBeforeRest--;
            if (strugglesBeforeRest == 0) {
                dx = -resetDx;
                restCounter = REST_DURATION;
            }
        }
    }

    /**
     * Gets the game over status.
     *
     * @return True if the game is over, otherwise false.
     */
    public Boolean getIsGameOver() {
        return isGameOver;
    }

    /**
     * Sets the game over status.
     *
     * @param isGameOver True if the game is over, otherwise false.
     */
    public void setIsGameOver(Boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    /**
     * Gets the fishing line float sprite.
     *
     * @return The fishing line float sprite.
     */
    public Sprite getFishingLineFloat() {
        return fishingLineFloat;
    }

    /**
     * Sets the position of the fishing line float sprite.
     *
     * @param x The x-coordinate of the fishing line float.
     * @param y The y-coordinate of the fishing line float.
     */
    public void setFishingLineFloat(int x, int y) {
        fishingLineFloat.setX(x);
        fishingLineFloat.setY(y);
    }

    public int getMaxFishSpeed(){
        return maxFishSpeed;
    }
}
