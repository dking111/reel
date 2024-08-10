package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AnimatedSprite extends Sprite {
    private List<Image> currentAnimation;
    private int currentFrame;
    private int frameCounter;

    public AnimatedSprite(int x, int y, int w, int h,String path) {
        super(x, y, w, h);
        currentFrame = 0;
        currentAnimation = loadAnimation(path);
        frameCounter = 0;
    }

    @Override
    public void draw(Graphics g) {
        
        g.drawImage(currentAnimation.get(currentFrame), getX(), getY(), getW(), getH(), null);
        if (frameCounter==1){
        currentFrame=((currentFrame+1) % currentAnimation.size());
        }
        //1 per second, add setting!
        frameCounter=((frameCounter+1) % 60);
    }

    public List<Image> loadAnimation(String path){
        List<Image> animation = new ArrayList<>();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        
        for (File file : listOfFiles) {
            if (file.isFile()) {
                ImageIcon icon = new ImageIcon(file.getPath());
                animation.add(icon.getImage());
            
                }
            }
        return animation;
        }

}

 
