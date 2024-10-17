package main.GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.Main;
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
    private Page helpPage;
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
        helpPage = setHelpPage();
        shelfPage = setShelfPage();
        pausePage = setPausePage();
        pages.add(menuPage);
        pages.add(settingsPage);
        pages.add(helpPage);
        pages.add(shelfPage);
        pages.add(pausePage);
    }

    private static class Constants {
        // Colors
        static final Color INACTIVE_COLOR = new Color(133, 186, 200);
        static final Color HOVER_COLOR = new Color(165, 208, 222);
        static final Color ACTIVE_COLOR = new Color(183, 226, 240);
        static final Color BUTTON_TEXT_COLOR = Color.WHITE;
        static final Color TROPHY_TEXT_COLOR = Color.BLACK;
        static final Color PAUSE_BUTTON_COLOR = Color.lightGray;
        static final Color PAUSE_BUTTON_HOVER_COLOR = Color.gray;
        static final Color PAUSE_BUTTON_ACTIVE_COLOR = Color.darkGray;

        // Font Sizes
        static final int TEXT_FONT_SIZE = 25;
        static final int TROPHY_FONT_SIZE = 20;

        // Dimensions
        static final int BUTTON_WIDTH = 200;
        static final int BUTTON_HEIGHT = 100;
        static final int ICON_SIZE = 50;
        static final int PAGE_WIDTH = 1920;
        static final int PAGE_HEIGHT = 1080;

        // Image Paths 
        static final String MENU_BACKGROUND = "/GUI/menuBackground.png";
        static final String SETTINGS_BACKGROUND = "/GUI/settingsBackground.png";
        static final String HELP_BACKGROUND = "/GUI/helpBackground.png";
        static final String SHELF_IMAGE = "/GUI/shelf.png";
        static final String SETTINGS_ICON = "/GUI/settingsIcon.png";
        static final String BACK_ICON = "/GUI/backIcon.png";
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

        buttons.add(new TextButton(
                Constants.PAGE_WIDTH / 2 - 100, 
                800, 
                Constants.BUTTON_WIDTH, 
                Constants.BUTTON_HEIGHT,
                Constants.INACTIVE_COLOR, 
                Constants.HOVER_COLOR, 
                Constants.ACTIVE_COLOR, 
                () -> gui.setCurrentPage("help"), 
                10, 
                10, 
                "Start", 
                Constants.TEXT_FONT_SIZE, 
                Constants.BUTTON_TEXT_COLOR
        ));
        
        buttons.add(new ImageButton(
                10, 
                10, 
                Constants.ICON_SIZE, 
                Constants.ICON_SIZE,
                Constants.INACTIVE_COLOR, 
                Constants.HOVER_COLOR, 
                Constants.ACTIVE_COLOR, 
                () -> gui.setCurrentPage("settings"), 
                50, 
                50, 
                Constants.SETTINGS_ICON
        ));

        sprites.add(new ImageSprite(0, 0, Constants.PAGE_WIDTH, Constants.PAGE_HEIGHT, Constants.MENU_BACKGROUND));

        return new Page("menu", false, buttons, texts, sprites);
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

        sprites.add(new ImageSprite(0, 0, Constants.PAGE_WIDTH, Constants.PAGE_HEIGHT, Constants.SETTINGS_BACKGROUND));

        texts.add(new Text(Constants.PAGE_WIDTH / 2 - 100, 100, 30, "Settings", Constants.BUTTON_TEXT_COLOR));

        buttons.add(new ImageButton(
                10, 
                10, 
                Constants.ICON_SIZE, 
                Constants.ICON_SIZE,
                Constants.INACTIVE_COLOR, 
                Constants.HOVER_COLOR, 
                Constants.ACTIVE_COLOR, 
                () -> gui.setCurrentPage("menu"), 
                50, 
                50, 
                Constants.BACK_ICON
        ));

        buttons.add(new TextButton(
                Constants.PAGE_WIDTH / 2 - 100, 
                200, 
                Constants.BUTTON_WIDTH, 
                Constants.BUTTON_HEIGHT,
                Constants.INACTIVE_COLOR, 
                Constants.HOVER_COLOR, 
                Constants.ACTIVE_COLOR, 
                () -> Main.setWindowDimentions(1920, 1080), 
                10, 
                10, 
                "1920 x 1080", 
                Constants.TEXT_FONT_SIZE, 
                Constants.BUTTON_TEXT_COLOR
        ));

        buttons.add(new TextButton(
                Constants.PAGE_WIDTH / 2 - 100, 
                400, 
                Constants.BUTTON_WIDTH, 
                Constants.BUTTON_HEIGHT,
                Constants.INACTIVE_COLOR, 
                Constants.HOVER_COLOR, 
                Constants.ACTIVE_COLOR, 
                () -> Main.setWindowDimentions(1600, 900), 
                10, 
                10, 
                "1600 x 900", 
                Constants.TEXT_FONT_SIZE, 
                Constants.BUTTON_TEXT_COLOR
        ));

        buttons.add(new TextButton(
                Constants.PAGE_WIDTH / 2 - 100, 
                600, 
                Constants.BUTTON_WIDTH, 
                Constants.BUTTON_HEIGHT,
                Constants.INACTIVE_COLOR, 
                Constants.HOVER_COLOR, 
                Constants.ACTIVE_COLOR, 
                () -> Main.setWindowDimentions(400, 300), 
                10, 
                10, 
                "400 x 300", 
                Constants.TEXT_FONT_SIZE, 
                Constants.BUTTON_TEXT_COLOR
        ));

        return new Page("settings", false, buttons, texts, sprites);
    }

    /**
     * Sets up the help page with buttons, texts, and image sprites.
     * 
     * @return The configured help page.
     */
    private Page setHelpPage() {
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<Sprite> sprites = new ArrayList<>();

        sprites.add(new ImageSprite(0, 0, Constants.PAGE_WIDTH, Constants.PAGE_HEIGHT, Constants.HELP_BACKGROUND));

        buttons.add(new ImageButton(
                10, 
                10, 
                Constants.ICON_SIZE, 
                Constants.ICON_SIZE,
                Constants.INACTIVE_COLOR, 
                Constants.HOVER_COLOR, 
                Constants.ACTIVE_COLOR, 
                () -> gui.setCurrentPage("menu"), 
                50, 
                50, 
                Constants.BACK_ICON
        ));

        buttons.add(new TextButton(
                Constants.PAGE_WIDTH / 2 - 100, 
                800, 
                Constants.BUTTON_WIDTH, 
                Constants.BUTTON_HEIGHT,
                Constants.INACTIVE_COLOR, 
                Constants.HOVER_COLOR, 
                Constants.ACTIVE_COLOR, 
                () -> gui.switchToGameLoop(), 
                10, 
                10, 
                "Play", 
                Constants.TEXT_FONT_SIZE, 
                Constants.BUTTON_TEXT_COLOR
        ));

        return new Page("help", false, buttons, texts, sprites);
    }

    public void refreshShelfPage() {
        pages.remove(shelfPage);
        shelfPage = setShelfPage();
        pages.add(shelfPage);
    }

    private Page setShelfPage() {
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<Sprite> sprites = new ArrayList<>();
        sprites.add(new ImageSprite(100, 100, 1720, 880, Constants.SHELF_IMAGE));
        
        List<Trophy> allFish = Database.getAllFish();

        int column = 0;
        int row = 0;
        for (Trophy fish : allFish) {
            fish.setIsVisible(true);
            fish.setW(150);
            fish.setH(150);
            fish.setX(255 + column * 250);
            fish.setY(170 + row * 215);
 
            buttons.add(new TextButton(
                255 + column * 250, 
                170 + 155 + row * 215, 
                165, 
                20,
                Color.YELLOW, 
                Color.YELLOW, 
                Color.YELLOW, 
                null, 
                0, 
                0, 
                String.valueOf(fish.getName()), 
                Constants.TROPHY_FONT_SIZE, 
                Constants.TROPHY_TEXT_COLOR
            ));

            buttons.add(new TextButton(
                255 + 55 + column * 250, 
                170 + 175 + row * 215, 
                60, 
                20,
                Color.YELLOW, 
                Color.YELLOW, 
                Color.YELLOW, 
                null, 
                0, 
                0, 
                String.valueOf(fish.getWeight()) + " lb", 
                Constants.TROPHY_FONT_SIZE, 
                Constants.TROPHY_TEXT_COLOR
            ));
            
            sprites.add(fish);
            column = (column + 1) % 6;
            if (column == 0) {
                row += 1;
            }
        }

        return new Page("shelf", true, buttons, texts, sprites);
    }

    private Page setPausePage() {
        List<Button> buttons = new ArrayList<Button>();
        List<Text> texts = new ArrayList<Text>();
        List<Sprite> sprites = new ArrayList<>();
        
        sprites.add(new ImageSprite(810, 240, 300, 600, Constants.SHELF_IMAGE));
        buttons.add(new TextButton(825, 270, 270, 125, 
            Constants.PAUSE_BUTTON_COLOR, 
            Constants.PAUSE_BUTTON_HOVER_COLOR, 
            Constants.PAUSE_BUTTON_ACTIVE_COLOR, 
            Main::switchToGameLoop, 
            5, 5, "Return", 20, Color.white));
        
        buttons.add(new TextButton(825, 420, 270, 125, 
            Constants.PAUSE_BUTTON_COLOR, 
            Constants.PAUSE_BUTTON_HOVER_COLOR, 
            Constants.PAUSE_BUTTON_ACTIVE_COLOR, 
            () -> gui.setCurrentPage("help"), 
            5, 5, "Help", 20, Color.white));
        
        buttons.add(new TextButton(825, 570, 270, 125, 
            Constants.PAUSE_BUTTON_COLOR, 
            Constants.PAUSE_BUTTON_HOVER_COLOR, 
            Constants.PAUSE_BUTTON_ACTIVE_COLOR, 
            () -> gui.setCurrentPage("menu"), 
            5, 5, "Exit", 20, Color.white));

        return new Page("pause", true, buttons, texts, sprites);
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
