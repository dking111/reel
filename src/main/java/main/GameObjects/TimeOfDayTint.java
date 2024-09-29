package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;


public class TimeOfDayTint {

        // Flickering light source variables


    public TimeOfDayTint(){

    }
    public void draw(Graphics2D g,float timeOfDay) {
        // Calculate brightness factor based on the time of day
        float brightnessFactor = (float) Math.sin(timeOfDay * Math.PI); // 0.0 = darkest, 1.0 = brightest

        // Create a semi-transparent overlay color for day/night effect
        Color dayColor = new Color(0, 0, 0, 0);  // No tint at noon
        Color nightColor = new Color(0, 0, 50, 100);   // Dark blue tint at night

        int r = (int) (nightColor.getRed() * (1 - brightnessFactor) + dayColor.getRed() * brightnessFactor);
        int gC = (int) (nightColor.getGreen() * (1 - brightnessFactor) + dayColor.getGreen() * brightnessFactor);
        int b = (int) (nightColor.getBlue() * (1 - brightnessFactor) + dayColor.getBlue() * brightnessFactor);
        int a = (int) (nightColor.getAlpha() * (1 - brightnessFactor) + dayColor.getAlpha() * brightnessFactor);

        Color overlayColor = new Color(r, gC, b, a);
        g.setColor(overlayColor);
        g.fillRect(0, 0, 1920, 1080);  // Apply overlay over the entire game screen
    }

    
}


