package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents a charge meter that visually indicates the current charge level
 * and displays the optimal charge level as a reference.
 * The meter includes functionality to increase the charge and provides visual feedback.
 */
public class ChargeMeter extends Sprite {
    private float currentCharge;
    private float chargeSpeed;
    private float optimalCharge;
    private int border;

    /**
     * Constructs a ChargeMeter instance with the specified parameters.
     *
     * @param x The x-coordinate of the charge meter.
     * @param y The y-coordinate of the charge meter.
     * @param w The width of the charge meter.
     * @param h The height of the charge meter.
     * @param border The border thickness around the charge meter.
     * @param chargeSpeed The speed at which the charge level increases or decreases.
     * @param optimalCharge The optimal charge level that is visually indicated.
     */
    public ChargeMeter(int x, int y, int w, int h, int border, float chargeSpeed, float optimalCharge) {
        super(x, y, w, h);
        this.currentCharge = 0;
        this.chargeSpeed = chargeSpeed;
        this.border = border;
        this.optimalCharge = optimalCharge;
    }

    /**
     * Increases the current charge level. If the charge level exceeds 1 or drops below 0,
     * the direction of the charge speed is reversed.
     */
    public void increaseCharge() {
        currentCharge += chargeSpeed;
        if (currentCharge > 1 || currentCharge < 0) {
            chargeSpeed = -chargeSpeed;
        }
    }

    /**
     * Draws the charge meter on the screen.
     * The meter is drawn with a border, a background, the current charge level,
     * and a line indicating the optimal charge level.
     *
     * @param g The Graphics2D object used for drawing the charge meter.
     */
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, w, h);

        g.setColor(Color.GRAY);
        g.fillRect(x + border, y + border, w - 2 * border, h - 2 * border);

        g.setColor(Color.GREEN);
        g.fillRect(x + border, (y + border) + (h - Math.round(h * currentCharge)), w - 2 * border, Math.round(h * currentCharge) - 2 * border);

        g.setColor(Color.RED);
        g.fillRect(x + border, (y + border) + (h - Math.round(h * optimalCharge)), w - 2 * border, 5);
    }

    /**
     * Calculates the accuracy of the current charge level compared to the optimal charge level.
     * The accuracy is the inverse of the absolute difference between the current and optimal charges.
     *
     * @return The accuracy of the current charge level, where 1 represents perfect accuracy.
     */
    public float getAccuracy() {
        return 1 - Math.abs(optimalCharge - currentCharge);
    }
}
