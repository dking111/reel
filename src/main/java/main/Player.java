package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Player extends AnimatedSprite {
    private List<Image> currentAnimation;
    private int currentFrame;

    public Player(int x, int y, int w, int h,String path) {
        super(x, y, w, h,path);
        

    }
}
