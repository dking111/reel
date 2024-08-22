package main.GameObjects;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

import main.core.Logic;

public class Fish extends AnimatedSprite{
    private Water water;
    private Logic logic;
    private Random random;
    private int angle;
    private int fishingLineX;
    private int fishingLineY;
    private int speed;
    private Boolean hooked;
    private Boolean hasBeenHooked;
    public Fish(int x,int y,int w,int h,String path,int fishingLineX,int fishingLineY,Water water, Logic logic){
        super(x, y, w, h,path,5);
        this.water = water;
        this.logic = logic;
        this.fishingLineX = fishingLineX;
        this.fishingLineY = fishingLineY;
        isVisible =false;
        random = new Random();
        angle = 0;
        hooked = false;
        hasBeenHooked=false;
        speed=5;
    }

    public void spawn(){
        x = random.nextInt(water.getX(),water.getX()+water.getW());
        y = random.nextInt(water.getY(),water.getY()+water.getH());
        isVisible = true;
        angle = getBearing(x+w/2, y, fishingLineX, fishingLineY);
    }

    public void update(int fishingLineX,int fishingLineY){
        this.fishingLineX =fishingLineX;
        this.fishingLineY = fishingLineY;
        moveTowards(fishingLineX,fishingLineY, speed);

    }


    @Override
    public void draw(Graphics2D g){
        // Save the original transformation
        AffineTransform originalTransform = g.getTransform();
        if (hooked){
        angle = getBearing(x+w/2, y, fishingLineX, fishingLineY);
        }
        rotate(angle, g);
        super.draw(g);
        // Restore the original transformation
        g.setTransform(originalTransform);
    }

    public void hooking(){
        if(!hasBeenHooked){
        state = "sinking";
        refreshAnimation();
        hooked=true;
        hasBeenHooked=true;
        }
    }

    public void setHooked(Boolean hooked){
        this.hooked=hooked;
    }

    public Boolean getHooked(){
        return hooked;
    }

    public void setSpeed(int speed){
        this.speed=speed;
    }
    
}

