package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

/**
 * The {@code WindowLight} class represents a light effect that emanates
 * from a window, with a radial gradient that changes based on the time of day.
 * This light effect is drawn onto a {@code Graphics2D} object.
 */
public class WindowLight extends Light {

    private static final int WINDOW_WIDTH = 247;
    private static final int WINDOW_HEIGHT = 500;
    private static final int LIGHT_COLOR_ALPHA_1 = 255;
    private static final int LIGHT_COLOR_ALPHA_2 = 140;
    private static final int OFFSCREEN_OFFSET = 273;

    /**
     * Constructs a {@code WindowLight} with the specified position and radius.
     *
     * @param lightPosX The X position of the light.
     * @param lightPosY The Y position of the light.
     * @param radius    The radius of the light's effect.
     */
    public WindowLight(int lightPosX, int lightPosY, float radius) {
        super(lightPosX, lightPosY, radius);
    }

    /**
     * Draws the window light effect on the given {@code Graphics2D} object.
     * The light is only visible during certain times of the day (between 0.15 and 0.85).
     *
     * @param g The {@code Graphics2D} object to draw the light on.
     */
    @Override
    public void draw(Graphics2D g) {
        if (isDaytime()) {
            Point2D.Float lightPosition = new Point2D.Float(lightPosX + WINDOW_WIDTH / 2, lightPosY);
            float lightIntensity = calculateLightIntensity();
            Color[] lightColors = createLightColors(lightIntensity);
            float[] fractions = {0.0f, 0.3f, 0.8f};

            RadialGradientPaint rgp = new RadialGradientPaint(lightPosition, radius, fractions, lightColors);

            int[] xPoints = calculateXPoints();
            int[] yPoints = {lightPosY, lightPosY, lightPosY + WINDOW_HEIGHT, lightPosY + WINDOW_HEIGHT};

            g.setPaint(rgp);
            g.fillPolygon(xPoints, yPoints, 4);
        }
    }

    /**
     * Determines if it is daytime based on the {@code timeOfDay} field.
     * The light is visible between 0.15 and 0.85 in the {@code timeOfDay} range.
     *
     * @return {@code true} if it is daytime, otherwise {@code false}.
     */
    private boolean isDaytime() {
        return timeOfDay >= 0.15 && timeOfDay <= 0.85;
    }

    /**
     * Calculates the intensity of the light based on the {@code timeOfDay} field.
     * The intensity varies as the time moves closer to or further from noon (0.5).
     *
     * @return The calculated light intensity.
     */
    private float calculateLightIntensity() {
        return 0.5f - Math.abs(timeOfDay - 0.5f);
    }

    /**
     * Creates an array of {@code Color} objects representing the gradient of the light
     * based on the current light intensity.
     *
     * @param lightIntensity The intensity of the light, used to adjust alpha values.
     * @return An array of {@code Color} objects for the radial gradient.
     */
    private Color[] createLightColors(float lightIntensity) {
        return new Color[] {
            new Color(255, 245, 245, (int) (LIGHT_COLOR_ALPHA_1 * lightIntensity)),
            new Color(255, 245, 245, (int) (LIGHT_COLOR_ALPHA_2 * lightIntensity)),
            new Color(255, 245, 245, 0)
        };
    }

    /**
     * Calculates the X coordinates for the polygon that defines the light effect.
     * The polygon shifts based on the time of day to simulate movement of light.
     *
     * @return An array of X coordinates for the light polygon.
     */
    private int[] calculateXPoints() {
        int timeOffset = Math.round(timeOfDay * 1000);
        return new int[] {
            lightPosX,
            lightPosX + WINDOW_WIDTH,
            lightPosX + WINDOW_WIDTH - OFFSCREEN_OFFSET + timeOffset,
            lightPosX - OFFSCREEN_OFFSET + timeOffset
        };
    }
}
