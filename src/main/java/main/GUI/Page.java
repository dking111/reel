package main.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Represents a page in the GUI that contains various graphical elements.
 * A page consists of buttons, texts, and image sprites.
 */
public class Page extends JPanel { // Extend JPanel to create a GUI component
    private String name;
    private Boolean transparent;
    private List<Button> buttons;
    private List<Text> texts;
    private List<ImageSprite> imageSprites;

    /**
     * Constructs a Page instance with the specified parameters.
     *
     * @param name The name of the page.
     * @param buttons A list of buttons to be displayed on the page.
     * @param texts A list of texts to be displayed on the page.
     * @param imageSprites A list of image sprites to be displayed on the page.
     */
    public Page(String name,Boolean transparent, List<Button> buttons, List<Text> texts, List<ImageSprite> imageSprites) {
        this.name = name;
        this.transparent = transparent;
        this.buttons = buttons;
        this.texts = texts;
        this.imageSprites = imageSprites;

        // Make the panel transparent


        // Add components to the panel
    }



    /**
     * Override the paintComponent method to support custom drawing.
     * 
     * @param g The Graphics object to protect.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Custom painting code here if needed
        super.paintComponent(g); // Ensure the panel itself is painted correctly
        // You can draw your custom graphics here if needed
    }

    /**
     * Returns the name of the page.
     * 
     * @return The name of the page.
     */
    public String getName() {
        return name;
    }
    
    public Boolean getTransparent() {
        return transparent;
    }

    /**
     * Returns the list of buttons on the page.
     * 
     * @return The list of buttons.
     */
    public List<Button> getButtons() {
        return buttons;
    }

    /**
     * Returns the list of image sprites on the page.
     * 
     * @return The list of image sprites.
     */
    public List<ImageSprite> getImageSprites() {
        return imageSprites;
    }

    /**
     * Returns the list of texts on the page.
     * 
     * @return The list of texts.
     */
    public List<Text> getTexts() {
        return texts;
    }
}
