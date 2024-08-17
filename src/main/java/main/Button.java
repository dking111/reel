package main;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents a clickable button in the game with different color states
 * and logic to be executed when pressed.
 */
public class Button extends Sprite {
    private Color inactiveColor, hoverColor, activeColor;
    private Runnable action;
    private boolean isHovered;
    private boolean isActive;

    /**
     * Constructs a Button instance with the specified parameters.
     *
     * @param x The x-coordinate of the button.
     * @param y The y-coordinate of the button.
     * @param w The width of the button.
     * @param h The height of the button.
     * @param inactiveColor The color of the button when inactive.
     * @param hoverColor The color of the button when hovered.
     * @param activeColor The color of the button when active (pressed).
     * @param action The logic to execute when the button is pressed.
     */
    public Button(int x, int y, int w, int h, Color inactiveColor, Color hoverColor, Color activeColor, Runnable action) {
        super(x, y, w, h);
        this.inactiveColor = inactiveColor;
        this.hoverColor = hoverColor;
        this.activeColor = activeColor;
        this.action = action;
        this.isHovered = false;
        this.isActive = false;

        // Add mouse listener for hover and click effects
        // This would generally be done in a GUI framework setup, not shown here
    }

    /**
     * Draws the button on the screen with its current state color.
     *
     * @param g The Graphics2D object used for drawing the button.
     */
    @Override
    public void draw(Graphics2D g) {
        if (isActive) {
            g.setColor(activeColor);
            //reset
            isActive = false;
        } else if (isHovered) {
            g.setColor(hoverColor);
        } else {
            g.setColor(inactiveColor);
        }

        g.fillRect(x, y, w, h);
    }

    /**
     * Called when the button is pressed.
     * Prints a message to the console and executes the associated logic.
     */
    public void onPress() {
        System.out.println("Button Pressed!");
        if (action != null) {
            action.run(); // Execute the passed function
        }
        // You can implement specific logic for what happens when the button is pressed
    }

    /**
     * Updates the button's state based on mouse interaction.
     *
     * @param mouseX The x-coordinate of the mouse pointer.
     * @param mouseY The y-coordinate of the mouse pointer.
     * @param isClicked Indicates whether the mouse button is pressed.
     */
    public void listener(int mouseX, int mouseY, boolean isClicked) {
        if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
            isHovered = true;
            if (isClicked) {
                isActive = true;
                onPress();
            }
        } else {
            //reset
            isHovered = false;
        }
    }
}
