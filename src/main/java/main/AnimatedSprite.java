package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Dictionary;
import java.util.Hashtable;


public class AnimatedSprite extends Sprite {
    protected List<Image> currentAnimation;
    protected Dictionary<String,List<Image>> animationDict;
    protected int currentFrame;
    protected int frameCounter;

    public AnimatedSprite(int x, int y, int w, int h,String path) {
        super(x, y, w, h);
        currentFrame = 0;
        animationDict = loadAnimation(path);
        currentAnimation = animationDict.get("idle");
        System.out.println(currentAnimation);
        //System.exit(0);
        frameCounter = 0;
    }

    @Override
    public void draw(Graphics2D g) {
        
        g.drawImage(currentAnimation.get(currentFrame), getX(), getY(), getW(), getH(), null);
        if (frameCounter==1){
        currentFrame=((currentFrame+1) % currentAnimation.size());
        }
        //1 per second, add setting!
        frameCounter=((frameCounter+1) % 60);
    }

    public Dictionary<String,List<Image>> loadAnimation(String path){
        Dictionary<String,List<Image>> dict = new Hashtable<>();
        File folder = new File(path);
        File[] listOfSubFolders = folder.listFiles();
        for (File directory : listOfSubFolders){
            List<Image> animation = new ArrayList<>();
            if (directory.isDirectory()){
                File subFolder = new File((path+"\\"+directory.getName()));
                File[] listOfFiles = subFolder.listFiles();
                
                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        ImageIcon icon = new ImageIcon(file.getPath());
                        animation.add(icon.getImage());
                    
                        }
                    }
            }
            dict.put(directory.getName(), animation);
            
        }
        return dict;

        // List<Image> animation = new ArrayList<>();
        // File subFolder = new File(path);
        // File[] listOfFiles = subFolder.listFiles();
        
        // for (File file : listOfFiles) {
        //     if (file.isFile()) {
        //         ImageIcon icon = new ImageIcon(file.getPath());
        //         animation.add(icon.getImage());
            
        //         }
        //     }
        //return animation;
        }

}

 
