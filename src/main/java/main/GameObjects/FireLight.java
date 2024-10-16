package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Represents a flickering fire light source in the game.
 * The light flickers to simulate a fire-like glow.
 */
public class FireLight extends Light {

    private float flickerIntensity;
    private final float flickerBaseIntensity = 0.8f;
    private long startTime;

    /**
     * Constructs a FireLight instance with the specified position and radius.
     *
     * @param lightPosX The x-coordinate of the light source.
     * @param lightPosY The y-coordinate of the light source.
     * @param radius    The radius of the light source.
     */
    public FireLight(int lightPosX, int lightPosY, float radius) {
        super(lightPosX, lightPosY, radius);
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Draws the fire light on the screen with a flickering effect.
     *
     * @param g The Graphics2D object used for drawing the light.
     */
    @Override
    public void draw(Graphics2D g) {
        if (timeOfDay < 0.15 || timeOfDay > 0.85) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            float flickerFactor = (float) Math.sin(elapsedTime * 0.02);
            flickerIntensity = flickerBaseIntensity + (flickerFactor * 0.5f);
            flickerIntensity = Math.max(0.0f, Math.min(flickerIntensity, 1.0f));

            Point2D.Float lightPosition = new Point2D.Float(lightPosX, lightPosY);
            Color[] warmColors = {
                new Color(255, 200, 70, (int) (200 + (55 * flickerIntensity))),
                new Color(255, 120, 30, (int) (150 + (50 * flickerIntensity))),
                new Color(150, 50, 0, 0)
            };

            float[] fractions = {0.0f, 0.6f, 1.0f};
            RadialGradientPaint rgp = new RadialGradientPaint(lightPosition, radius, fractions, warmColors);

            Arc2D.Double arc = new Arc2D.Double(
                lightPosition.getX() - radius,
                lightPosition.getY() - radius,
                2 * radius,
                2 * radius,
                200, 140, Arc2D.PIE
            );

            g.setClip(arc);
            Rectangle2D rect = new Rectangle2D.Double(
                lightPosition.getX() - 400,
                lightPosition.getY() + 43,
                800,
                800
            );
            g.setClip(rect);
            g.setPaint(rgp);
            g.fill(arc);
            g.setClip(null);
        }
    }
}
