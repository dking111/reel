package main.GUI;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;





public class PageData {
    private List<Page> pages;
    private Page menuPage;
    private Page settingsPage;
    private Page savesPage;
    private GUI gui;
    

    public PageData(GUI gui) {
        this.gui = gui;
        pages = new ArrayList<Page>();
        menuPage = setMenuPage();
        settingsPage = setSettingsPage();
        savesPage = setSavesPage();
        pages.add(menuPage);
        pages.add(settingsPage);
        pages.add(savesPage);
    }

    private Page setMenuPage(){
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<ImageSprite> imageSprites = new ArrayList<ImageSprite>();
        buttons.add(new TextButton(275, 475, 200, 50,
                    new Color(133, 186, 200),                   // Inactive color
                    new Color(165, 208, 222),                   // Hover color
                    new Color(183, 226, 240),                   // Active color
                    () -> gui.setCurrentPage("saves"),      // Action using a lambda to pass parameters
                    10,                                     // Arc width for rounded corners
                    10,
                    "Start",                                    // Text to display
                    25,                                     // Font size
                    Color.WHITE                                     // Text color
                    ));
        buttons.add(new ImageButton(10, 10, 50, 50,
                    new Color(133, 186, 200),                   // Inactive color
                    new Color(165, 208, 222),                   // Hover color
                    new Color(183, 226, 240),                   // Active color
                    () -> gui.setCurrentPage("settings"),      // Action using a lambda to pass parameters
                    50,                                     // Arc width for rounded corners
                50,
                    "src\\main\\resources\\GUI\\settingsIcon.png"                                    // Text to display
                    ));



        imageSprites.add(new ImageSprite(0, 0, 800, 600, "src\\main\\resources\\GUI\\menuBackground.png"));
            
        
        Page menu = new Page("menu", buttons, texts, imageSprites);
        return menu;

    }

    private Page setSettingsPage(){
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<ImageSprite> imageSprites = new ArrayList<ImageSprite>();
        imageSprites.add(new ImageSprite(0, 0, 800, 600, "src\\main\\resources\\GUI\\settingsBackground.png"));

        texts.add(new Text(300, 100, 30, "Settings", Color.WHITE));
            
        buttons.add(new ImageButton(10, 10, 50, 50,
                    new Color(133, 186, 200),                   // Inactive color
                    new Color(165, 208, 222),                   // Hover color
                    new Color(183, 226, 240),                   // Active color
                    () -> gui.setCurrentPage("menu"),      // Action using a lambda to pass parameters
                    50,                                     // Arc width for rounded corners
                50,
                    "src\\main\\resources\\GUI\\backIcon.png"                                    // Text to display
                    ));
        Page menu = new Page("settings", buttons, texts, imageSprites);
        return menu;

    }

    private Page setSavesPage(){
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<ImageSprite> imageSprites = new ArrayList<ImageSprite>();
        imageSprites.add(new ImageSprite(0, 0, 800, 600, "src\\main\\resources\\GUI\\savesBackground.png"));


            
        buttons.add(new ImageButton(10, 10, 50, 50,
                    new Color(133, 186, 200),                   // Inactive color
                    new Color(165, 208, 222),                   // Hover color
                    new Color(183, 226, 240),                   // Active color
                    () -> gui.setCurrentPage("menu"),      // Action using a lambda to pass parameters
                    50,                                     // Arc width for rounded corners
                50,
                    "src\\main\\resources\\GUI\\backIcon.png"                                    // Text to display
                    ));    
        buttons.add(new TextButton(275, 475, 200, 50,
                    new Color(133, 186, 200),                   // Inactive color
                    new Color(165, 208, 222),                   // Hover color
                    new Color(183, 226, 240),                   // Active color
                    () -> gui.switchToGameLoop(),      // Action using a lambda to pass parameters
                    10,                                     // Arc width for rounded corners
                    10,
                    "Play",                                    // Text to display
                    25,                                     // Font size
                    Color.WHITE                                     // Text color
                    ));
        
        
        Page menu = new Page("saves", buttons, texts, imageSprites);
        return menu;

    }



    public Page getPage(String name){
        for (Page page : pages){
            if (page.getName()==name){
                return page;
            }
        }
        return null;
    }
}