package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class WindowLight extends Light {



    public WindowLight(int lightPosX, int lightPosY, float radius){
        super(lightPosX, lightPosY, radius);

    }
    public void draw(Graphics2D g) {
        if(!(timeOfDay<0.15 || timeOfDay>0.85)){
        // Light source position (you can tweak this to be near a fire or torch)
        Point2D.Float lightPosition = new Point2D.Float(lightPosX+247/2, lightPosY);
        float lightIntensity = (float) ((float)  0.5f - Math.abs(timeOfDay - 0.5 ));

        Color[] brightColors = {
            new Color(255, 245, 245, (int) (255*lightIntensity)),  // Bright orange near the fire
            new Color(255, 245, 245, (int) (140*lightIntensity)),  // Darker orange/red fading out
            new Color(255, 245, 245, 0)                                  // Darker, transparent at the edge
        };
    
        // Define the gradient points for the light
        float[] fractions = {0.0f, 0.3f, 0.8f};  // 60% of the radius will be brightly lit, fading afterward
    
        // Create a radial gradient for a warm light effect
        RadialGradientPaint rgp = new RadialGradientPaint(
                lightPosition, radius,
                fractions, brightColors
        );
    int[] xPoints = {lightPosX,lightPosX+247,(lightPosX-273)+247+(Math.round(timeOfDay*1000)),(lightPosX-273)+(Math.round(timeOfDay*1000))};
    int[] yPoints = {lightPosY,lightPosY,lightPosY+500,lightPosY+500};
    g.setPaint(rgp);
    g.fillPolygon(xPoints, yPoints, 4);
    }
    }

    
}


