package main;

import javax.swing.*;
import java.awt.*;


public class Background extends Sprite {
    private Image image;
    public Background(int x, int y, int w, int h, String path){
        super(x, y, w, h);
        ImageIcon icon = new ImageIcon(path);
        image = icon.getImage();
    }

    public void draw(Graphics g){
        g.drawImage(image, getX(), getY(), getW(), getH(), null); 
    }
    
}
