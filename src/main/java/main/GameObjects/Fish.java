package main.GameObjects;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;


public class Fish extends AnimatedSprite{
    private Water water;
    private Random random;
    private int angle;
    private int fishingLineX;
    private int fishingLineY;
    private int speed;
    private Boolean hooked;
    private Boolean hasBeenHooked;
    private String name;
    private int rarity;
    private int maxWeight,minWeight, weight;

    public Fish(int x,int y,int w,int h,int maxWeight,int minWeight,String name,int rarity,String path){
        super(x, y, w, h,path,5);

        this.maxWeight = maxWeight;
        this.minWeight = minWeight;
        this.name = name;
        this.rarity = rarity;
        
        isVisible = false;
        random = new Random();
        angle = 0;
        hooked = false;
        hasBeenHooked=false;
        speed=5;
    }

    public void spawn(Water water,int fishingLineX,int fishingLineY){
        System.out.print(minWeight);
        System.out.print(" ");
        System.out.println(maxWeight);
        this.weight = random.nextInt(minWeight,maxWeight);
        this.w = weight;
        this.h = weight;
        this.water = water;
        this.fishingLineX = fishingLineX;
        this.fishingLineY = fishingLineY;
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
    
    public int getRarity() {
        return rarity;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
}

