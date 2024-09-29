package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Light {

    protected float radius;
    protected int lightPosX;
    protected int lightPosY;
    protected float timeOfDay;

    public Light(int lightPosX, int lightPosY, float radius){
        this.lightPosX = lightPosX;
        this.lightPosY = lightPosY;
        this.radius = radius;
        this.timeOfDay = 0;
    }

    public void update(float timeOfDay){
        this.timeOfDay = timeOfDay;

    }

    public void draw(Graphics2D g) {
        return;
    }

    
}


