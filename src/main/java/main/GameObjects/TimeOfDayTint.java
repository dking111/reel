package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The {@code TimeOfDayTint} class is responsible for applying a day-to-night tint effect on the game screen.
 * This effect is based on the time of day, simulating a transition from bright daylight to a darker night.
 */
public class TimeOfDayTint {

    // Constants for screen resolution and color values
    private static final int SCREEN_WIDTH = 1920;
    private static final int SCREEN_HEIGHT = 1080;
    private static final Color DAY_COLOR = new Color(0, 0, 0, 0);           // No tint at noon (transparent)
    private static final Color NIGHT_COLOR = new Color(0, 0, 50, 100);      // Dark blue tint at night

    /**
     * Constructs a {@code TimeOfDayTint} object.
     * This class handles the visual tint that changes depending on the time of day.
     */
    public TimeOfDayTint() {
    }

    /**
     * Draws a semi-transparent overlay on the game screen to simulate the transition
     * between day and night based on the given {@code timeOfDay} value.
     *
     * @param g         The {@code Graphics2D} object used to draw the overlay.
     * @param timeOfDay A value representing the time of day, where 0.0 is midnight, 0.5 is noon, and 1.0 is the next midnight.
     *                  The brightness is adjusted accordingly (darkest at 0.0 and 1.0, brightest at 0.5).
     */
    public void draw(Graphics2D g, float timeOfDay) {
        // Calculate brightness factor based on the time of day (sinusoidal curve)
        float brightnessFactor = (float) Math.sin(timeOfDay * Math.PI); 

        // Calculate the resulting color by blending between day and night colors
        int r = (int) (NIGHT_COLOR.getRed() * (1 - brightnessFactor) + DAY_COLOR.getRed() * brightnessFactor);
        int gC = (int) (NIGHT_COLOR.getGreen() * (1 - brightnessFactor) + DAY_COLOR.getGreen() * brightnessFactor);
        int b = (int) (NIGHT_COLOR.getBlue() * (1 - brightnessFactor) + DAY_COLOR.getBlue() * brightnessFactor);
        int a = (int) (NIGHT_COLOR.getAlpha() * (1 - brightnessFactor) + DAY_COLOR.getAlpha() * brightnessFactor);

        // Create the final overlay color
        Color overlayColor = new Color(r, gC, b, a);

        // Apply the overlay tint over the entire game screen
        g.setColor(overlayColor);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);  // Apply tint over the entire screen
    }
}
