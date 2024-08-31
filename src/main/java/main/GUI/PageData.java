package main.GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.Main;

/**
 * Manages and provides access to different pages in the GUI.
 * Each page contains buttons, texts, and image sprites that define its content and behavior.
 */
public class PageData {
    private List<Page> pages;
    private Page menuPage;
    private Page settingsPage;
    private Page savesPage;
    private Page shelfPage;
    private Page pausePage;
    private GUI gui;

    /**
     * Constructs a PageData instance and initializes the pages.
     *
     * @param gui The GUI instance that this PageData interacts with.
     */
    public PageData(GUI gui) {
        this.gui = gui;
        pages = new ArrayList<Page>();
        menuPage = setMenuPage();
        settingsPage = setSettingsPage();
        savesPage = setSavesPage();
        shelfPage = setShelfPage();
        pausePage = setPausePage();
        pages.add(menuPage);
        pages.add(settingsPage);
        pages.add(savesPage);
        pages.add(shelfPage);
        pages.add(pausePage);
    }

    /**
     * Sets up the menu page with buttons, texts, and image sprites.
     * 
     * @return The configured menu page.
     */
    private Page setMenuPage() {
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<ImageSprite> imageSprites = new ArrayList<ImageSprite>();
        
        buttons.add(new TextButton(275, 475, 200, 50,
                new Color(133, 186, 200),                   // Inactive color
                new Color(165, 208, 222),                   // Hover color
                new Color(183, 226, 240),                   // Active color
                () -> gui.setCurrentPage("saves"),          // Action using a lambda to pass parameters
                10,                                        // Arc width for rounded corners
                10,
                "Start",                                    // Text to display
                25,                                        // Font size
                Color.WHITE                                // Text color
                ));
                
        buttons.add(new ImageButton(10, 10, 50, 50,
                new Color(133, 186, 200),                   // Inactive color
                new Color(165, 208, 222),                   // Hover color
                new Color(183, 226, 240),                   // Active color
                () -> gui.setCurrentPage("settings"),      // Action using a lambda to pass parameters
                50,                                        // Arc width for rounded corners
                50,
                "src\\main\\resources\\GUI\\settingsIcon.png" // Path to the image file
                ));

        imageSprites.add(new ImageSprite(0, 0, 800, 600, "src\\main\\resources\\GUI\\menuBackground.png"));

        return new Page("menu",false, buttons, texts, imageSprites);
    }

    /**
     * Sets up the settings page with buttons, texts, and image sprites.
     * 
     * @return The configured settings page.
     */
    private Page setSettingsPage() {
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<ImageSprite> imageSprites = new ArrayList<ImageSprite>();
        
        imageSprites.add(new ImageSprite(0, 0, 800, 600, "src\\main\\resources\\GUI\\settingsBackground.png"));

        texts.add(new Text(300, 100, 30, "Settings", Color.WHITE));

        buttons.add(new ImageButton(10, 10, 50, 50,
                new Color(133, 186, 200),                   // Inactive color
                new Color(165, 208, 222),                   // Hover color
                new Color(183, 226, 240),                   // Active color
                () -> gui.setCurrentPage("menu"),           // Action using a lambda to pass parameters
                50,                                        // Arc width for rounded corners
                50,
                "src\\main\\resources\\GUI\\backIcon.png"   // Path to the image file
                ));

        return new Page("settings",false, buttons, texts, imageSprites);
    }

    /**
     * Sets up the saves page with buttons, texts, and image sprites.
     * 
     * @return The configured saves page.
     */
    private Page setSavesPage() {
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<ImageSprite> imageSprites = new ArrayList<ImageSprite>();
        
        imageSprites.add(new ImageSprite(0, 0, 800, 600, "src\\main\\resources\\GUI\\savesBackground.png"));

        buttons.add(new ImageButton(10, 10, 50, 50,
                new Color(133, 186, 200),                   // Inactive color
                new Color(165, 208, 222),                   // Hover color
                new Color(183, 226, 240),                   // Active color
                () -> gui.setCurrentPage("menu"),           // Action using a lambda to pass parameters
                50,                                        // Arc width for rounded corners
                50,
                "src\\main\\resources\\GUI\\backIcon.png"   // Path to the image file
                ));

        buttons.add(new TextButton(275, 475, 200, 50,
                new Color(133, 186, 200),                   // Inactive color
                new Color(165, 208, 222),                   // Hover color
                new Color(183, 226, 240),                   // Active color
                () -> gui.switchToGameLoop(),               // Action using a lambda to pass parameters
                10,                                        // Arc width for rounded corners
                10,
                "Play",                                    // Text to display
                25,                                        // Font size
                Color.WHITE                                // Text color
                ));

        return new Page("saves",false, buttons, texts, imageSprites);
    }


    private Page setShelfPage() {
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<ImageSprite> imageSprites = new ArrayList<ImageSprite>();
        
        buttons.add(new Button(0, 0, 100, 100, Color.RED, Color.RED, Color.RED, Main::switchToGameLoop, 10, 10));
        

        return new Page("shelf",true, buttons, texts, imageSprites);
    }

    private Page setPausePage() {
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<ImageSprite> imageSprites = new ArrayList<ImageSprite>();
        
        buttons.add(new Button(300, 100, 200, 400, Color.RED, Color.RED, Color.RED, Main::switchToGameLoop, 10, 10));
        

        return new Page("pause",true, buttons, texts, imageSprites);
    }

    /**
     * Retrieves a page by its name.
     *
     * @param name The name of the page to retrieve.
     * @return The Page object with the specified name, or null if not found.
     */
    public Page getPage(String name) {
        for (Page page : pages) {
            if (page.getName().equals(name)) {
                return page;
            }
        }
        return null;
    }
}
