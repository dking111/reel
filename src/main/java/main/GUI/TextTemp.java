package main.GUI;

import java.awt.Color;
import java.awt.Graphics2D;

    
public class TextTemp extends Text {
    private int duration;
    private boolean done;
    public TextTemp(int x, int y, int fontSize, String text , Color color,int duration){
        super(x, y, fontSize, text, color);
        this.duration = duration;
        this.done = false;
    }

    @Override
    public void draw(Graphics2D g){
        if(!done){
            System.out.println("showing");
            super.draw(g);
        }
    }

    @Override
    public void update(){
        duration-=1;
        if (duration<=0){
            done = true;
        }
    }



    
}
