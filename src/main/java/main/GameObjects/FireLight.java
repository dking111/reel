package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class FireLight extends Light {

    // Flickering light source variables
    private float flickerIntensity; // Intensity of the light (1.0f is max)
    private float flickerBaseIntensity = 0.8f; // Base intensity before flicker
    private long startTime; // Track the start time for flickering

    public FireLight(int lightPosX, int lightPosY, float radius) {
        super(lightPosX, lightPosY, radius);
        this.startTime = System.currentTimeMillis(); // Initialize start time
    }

    public void draw(Graphics2D g) {
        if ((timeOfDay < 0.15 || timeOfDay > 0.85)) {
            // Calculate flicker intensity using a sine wave
            long elapsedTime = System.currentTimeMillis() - startTime; // Calculate elapsed time
            
            // Increased frequency for faster flicker
            float flickerFactor = (float) Math.sin(elapsedTime * 0.02); // Adjusted frequency

            // Increase the flicker intensity to make it brighter
            flickerIntensity = flickerBaseIntensity + (flickerFactor * 0.5f); // Increased flicker range

            // Ensure flicker intensity is within a valid range
            flickerIntensity = Math.max(0.0f, Math.min(flickerIntensity, 1.0f));

            // Light source position (you can tweak this to be near a fire or torch)
            Point2D.Float lightPosition = new Point2D.Float(lightPosX, lightPosY);

            // Adjust color palette to simulate warm, fire-like glow
            Color[] warmColors = {
                new Color(255, 200, 70, (int) (200 + (55 * flickerIntensity))),  // Brighter orange near the fire
                new Color(255, 120, 30, (int) (150 + (50* flickerIntensity))),  // Darker orange/red fading out
                new Color(150, 50, 0, 0)                                  // Darker, transparent at the edge
            };

            // Define the gradient points for the light
            float[] fractions = {0.0f, 0.6f, 1.0f};  // 60% of the radius will be brightly lit, fading afterward

            // Create a radial gradient for a warm light effect
            RadialGradientPaint rgp = new RadialGradientPaint(
                lightPosition, radius,
                fractions, warmColors
            );

            // Create a semicircular clipping area to simulate a semicircle
            Arc2D.Double arc = new Arc2D.Double(
                lightPosition.getX() - radius, lightPosition.getY() - radius,
                2 * radius, 2 * radius,
                200, 140, Arc2D.PIE // Draw only the top half (semicircle)
            );

            // Clip the graphics object to this semicircle before drawing
            g.setClip(arc);
            Rectangle2D rect = new Rectangle2D.Double(lightPosition.getX() - 400, lightPosition.getY() + 43, 800, 800);
            g.setClip(rect);

            // Apply the gradient as a warm light effect
            g.setPaint(rgp);
            g.fill(arc);  // Fill the semicircle with the light gradient

            // Reset the clip so it doesn't affect the rest of the game rendering
            g.setClip(null);
        }
    }
}
