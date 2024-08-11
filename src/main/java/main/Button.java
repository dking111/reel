package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class Button extends Sprite {
    private Color inactiveColor, hoverColor, activeColor;
    private Logic logic;
    private boolean isHovered;
    private boolean isActive;

    public Button(int x, int y, int w, int h, Color inactiveColor, Color hoverColor, Color activeColor, Logic logic) {
        super(x, y, w, h);
        this.inactiveColor = inactiveColor;
        this.hoverColor = hoverColor;
        this.activeColor = activeColor;
        this.logic = logic;
        this.isHovered = false;
        this.isActive = false;

        // Add mouse listener for hover and click effects
        
    }

    

    @Override
    public void draw(Graphics g) {
        if (isActive) {
            g.setColor(activeColor);
            //reset
            isActive = false;
        } else if (isHovered) {
            g.setColor(hoverColor);
        } else {
            g.setColor(inactiveColor);
        }

        g.fillRect(x, y, w, h);
    }

    // This method is called when the button is pressed
    public void onPress() {
        System.out.println("Button Pressed!");
        // You can implement specific logic for what happens when the button is pressed
    }

    // Method to check if the mouse is within the button's bounds
    public void listener(int mouseX, int mouseY,boolean isClicked) {
        if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h){
            isHovered = true;
            if(isClicked){
                isActive = true;
                onPress();
            }

              
        }
        else{
            //reset
            isHovered = false;
        }
    }
}
