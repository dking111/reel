package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.lang.Math;

public class ChargeMeter extends Sprite{
    float currentCharge;
    float chargeSpeed;
    float optimalCharge;
    int border;
    public ChargeMeter(int x, int y, int w, int h,int border,float chargeSpeed,float optimalCharge){
        super(x,y,w,h);
        currentCharge = 0;
        this.chargeSpeed = chargeSpeed;
        this.border = border;
        this.optimalCharge = optimalCharge;

    }
    public void increaseCharge(){
        currentCharge+=chargeSpeed;
        if (currentCharge>1|| currentCharge<0){
            chargeSpeed=-chargeSpeed;
        }
    }
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK); // Set the color for the door

        g.fillRect(x, y, w, h); // Draw the door as a filled rectangle using inherited fields
        g.setColor(Color.GRAY);
        g.fillRect(x+border, y+border, w-2*border, h-2*border);
        
        g.setColor(Color.GREEN);
        g.fillRect(x+border,(y+border)+(h-(Math.round(h*currentCharge))),w-2*border ,(Math.round(h*currentCharge))-2*border);
        g.setColor(Color.RED);
        g.fillRect(x+border, (y+border)+(h-(Math.round(h*optimalCharge))), w-2*border , 5);
    }

    public float getAccuracy(){
        return 1-Math.abs(optimalCharge-currentCharge);
    }
}