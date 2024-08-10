package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite {
    private List<Image> currentAnimation;
    private int currentFrame;

    public Player(int x, int y, int w, int h,String path) {
        super(x, y, w, h);
        currentFrame = 0;
        currentAnimation = loadAnimation(path);
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(currentAnimation.get(currentFrame), getX(), getY(), getW(), getH(), null);
        currentFrame=((currentFrame+1) % currentAnimation.size());
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
