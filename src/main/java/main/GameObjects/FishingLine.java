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
    private int playerX, playerY;
    private int frameCounter;
    private Random random;
    private Sprite fishingLineFloat;
    private Boolean isGameOver;
    private int amplitude;
    private int maxFishSpeed;
    private int tension;
    private int struggleCounter;
    private int maxStruggle;
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
        this.frameCounter = 0;
        random = new Random();
        
        strugglesBeforeRest = random.nextInt(2, 3);
        maxStruggle = random.nextInt(4, 10);
        struggleCounter = 0;
        maxFishSpeed = random.nextInt(4, 6);
        dx = random.nextInt(1, 5);
        amplitude = random.nextInt(150, 400);
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
        // Draw the fishing line
        g.setColor(Color.RED); 
        g.fillRect(x, y, w, h);

        // Set color based on tension
        if (tension < 10) {
            g.setColor(Color.WHITE);   
        } else if (tension < 30) {
            g.setColor(Color.ORANGE);
        } else if (tension < 70) {
            g.setColor(Color.RED);
        } else {
            isGameOver = true;
        }

        // Draw the rest state if applicable
        if (restCounter != 0) {
            g.setColor(new Color(136, 236, 136));
        }

        // Draw the fishing line to the player
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

        // Handle the rest state when the fishing line is resting
        if (restCounter > 0) {
            handleRestState(isMouseHeld);
            return;
        }

        // Update tension and direction based on user input
        updateTensionAndDirection(isLeftHeld, isRightHeld);

        // Move the fishing line based on the current dx value
        move(dx, 0);

        // Increment the frame counter
        frameCounter++;
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
            // Resume moving with the appropriate speed
            dx = (dx < 0) ? -maxFishSpeed : maxFishSpeed;
            strugglesBeforeRest = random.nextInt(2, 3);
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
        if (struggleCounter != maxStruggle) {
            move(moveAmount, 0);
            dx = resetDx;
            struggleCounter++;
        } else {
            strugglesBeforeRest--;
            if (strugglesBeforeRest == 0) {
                dx = -resetDx;
                restCounter = 60;
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
