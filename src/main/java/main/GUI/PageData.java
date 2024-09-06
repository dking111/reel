package main.GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.Main;
import main.GameObjects.Fish;
import main.GameObjects.Sprite;
import main.core.Database;
import main.core.GUI;

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
        List<Sprite> sprites = new ArrayList<>();

        
        buttons.add(new TextButton(1920/2-100, 800, 200, 100,
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

        sprites.add(new ImageSprite(0, 0, 1920, 1080, "src\\main\\resources\\GUI\\menuBackground.png"));

        return new Page("menu",false, buttons, texts, sprites);
    }

    /**
     * Sets up the settings page with buttons, texts, and image sprites.
     * 
     * @return The configured settings page.
     */
    private Page setSettingsPage() {
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<Sprite> sprites = new ArrayList<>();
        
        sprites.add(new ImageSprite(0, 0, 1920, 1080, "src\\main\\resources\\GUI\\settingsBackground.png"));

        texts.add(new Text(1080/2-100, 100, 30, "Settings", Color.WHITE));

        buttons.add(new ImageButton(10, 10, 50, 50,
                new Color(133, 186, 200),                   // Inactive color
                new Color(165, 208, 222),                   // Hover color
                new Color(183, 226, 240),                   // Active color
                () -> gui.setCurrentPage("menu"),           // Action using a lambda to pass parameters
                50,                                        // Arc width for rounded corners
                50,
                "src\\main\\resources\\GUI\\backIcon.png"   // Path to the image file
                ));

        buttons.add(new TextButton(1920/2-100, 800, 200, 100,
                new Color(133, 186, 200),                   // Inactive color
                new Color(165, 208, 222),                   // Hover color
                new Color(183, 226, 240),                   // Active color
                () -> Main.setWindowDimentions(1920, 1080),          // Action using a lambda to pass parameters
                10,                                        // Arc width for rounded corners
                10,
                "1920 x 1080",                                    // Text to display
                25,                                        // Font size
                Color.WHITE                                // Text color
                ));

        buttons.add(new TextButton(1920/2-100, 900, 200, 100,
                new Color(133, 186, 200),                   // Inactive color
                new Color(165, 208, 222),                   // Hover color
                new Color(183, 226, 240),                   // Active color
                () -> Main.setWindowDimentions(1600, 900),          // Action using a lambda to pass parameters
                10,                                        // Arc width for rounded corners
                10,
                "1600 x 900",                                    // Text to display
                25,                                        // Font size
                Color.WHITE                                // Text color
                ));
        
        buttons.add(new TextButton(1920/2-100, 1000, 200, 100,
                new Color(133, 186, 200),                   // Inactive color
                new Color(165, 208, 222),                   // Hover color
                new Color(183, 226, 240),                   // Active color
                () -> Main.setWindowDimentions(400, 300),          // Action using a lambda to pass parameters
                10,                                        // Arc width for rounded corners
                10,
                "400 x 300",                                    // Text to display
                25,                                        // Font size
                Color.WHITE                                // Text color
                ));

        return new Page("settings",false, buttons, texts, sprites);
    }

    /**
     * Sets up the saves page with buttons, texts, and image sprites.
     * 
     * @return The configured saves page.
     */
    private Page setSavesPage() {
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<Sprite> sprites = new ArrayList<>();
        
        sprites.add(new ImageSprite(0, 0, 1920, 1080, "src\\main\\resources\\GUI\\savesBackground.png"));

        buttons.add(new ImageButton(10, 10, 50, 50,
                new Color(133, 186, 200),                   // Inactive color
                new Color(165, 208, 222),                   // Hover color
                new Color(183, 226, 240),                   // Active color
                () -> gui.setCurrentPage("menu"),           // Action using a lambda to pass parameters
                50,                                        // Arc width for rounded corners
                50,
                "src\\main\\resources\\GUI\\backIcon.png"   // Path to the image file
                ));

        buttons.add(new TextButton(1920/2-100, 800, 200, 100,
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

        return new Page("saves",false, buttons, texts, sprites);
    }

    public void refreshShelfPage(){
        pages.remove(shelfPage);
        shelfPage = setShelfPage();
        pages.add(shelfPage);
    }

    private Page setShelfPage() {
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<Sprite> sprites = new ArrayList<>();
        sprites.add(new ImageSprite(100, 100, 1920/2, 1080/2, "src\\main\\resources\\GUI\\shelf.png"));
        
        List<Fish> allFish = Database.getAllFish();

        int column = 0;
        int row = 0;
        for (Fish fish : allFish){
            fish.setIsVisible(true);
            fish.setW(70);
            fish.setH(70);
            fish.setX(115+column*100);
            fish.setY(115+row*100);
            if(fish.getWeight()==0){
                fish.setState("uncaught");
            }
            else{
                fish.setState("dead");

                buttons.add(new TextButton(115+column*100, 195+row*100, 75, 10,
                     Color.YELLOW,                   // Inactive color
                     Color.YELLOW,                   // Hover color
                     Color.YELLOW,                   // Active color
                    null,               // Action using a lambda to pass parameters
                    0,                                        // Arc width for rounded corners
                    0,
                    String.valueOf(fish.getName()),                                    // Text to display
                    10,                                        // Font size
                    Color.BLACK                                // Text color
                    ));


                buttons.add(new TextButton(135+column*100, 205+row*100, 30, 10,
                     Color.YELLOW,                   // Inactive color
                     Color.YELLOW,                   // Hover color
                     Color.YELLOW,                   // Active color
                    null,               // Action using a lambda to pass parameters
                    0,                                        // Arc width for rounded corners
                    0,
                    String.valueOf(fish.getWeight()) + " lb",                                    // Text to display
                    10,                                        // Font size
                    Color.BLACK                                // Text color
                    ));
            }
            sprites.add(fish);
            column = (column+1)%6;
            if (column==0){
                row+=1;
            }

        }


        return new Page("shelf",true, buttons, texts, sprites);
    }

    private Page setPausePage() {
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<Sprite> sprites = new ArrayList<>();
        
        buttons.add(new Button(300, 100, 200, 400, Color.RED, Color.RED, Color.RED, Main::switchToGameLoop, 10, 10));
        

        return new Page("pause",true, buttons, texts, sprites);
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
