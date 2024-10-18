package main.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import main.Main;
import main.GUI.Text;
import main.GameObjects.Background;
import main.GameObjects.ChargeMeter;
import main.GameObjects.Door;
import main.GameObjects.Fish;
import main.GameObjects.FishingSpot;
import main.GameObjects.Player;
import main.GameObjects.Shelf;
import main.GameObjects.Sprite;
import main.GameObjects.Water;

/**
 * Manages the main game loop, including rendering, user input, game logic, and updates.
 * It extends JPanel to provide a custom drawing surface and implements ActionListener for timer-based updates.
 */
public class GameLoop extends JPanel implements ActionListener {
    // Constants
    private final int MAXSPEED = 9;
    private final float TIMESPEED = 0.0001f;

    // Core components
    private Timer timer;
    private Logic logic;
    private Camera camera;
    private Player player;
    private GameData gameData;
    private Background background;
    private Renderer renderer;
    private FishingLogic fishingLogic;

    // Event listener
    private int mouseX, mouseY;
    private Boolean isMouseHeld;
    private Boolean isLeftHeld;
    private Boolean isRightHeld;

    // Game objects
    private ChargeMeter chargeMeter;
    private float chargePower;
    private Water water;
    private List<Fish> possibleFishes;
    private String currentHabitat;
    private FishingState fishingState;
    private List<Text> texts;
    private float timeOfDay;

    // Debugging
    private Boolean debug;
    private Boolean god;

    /**
     * Initializes the game loop by setting up the panel properties, game data, player, camera,
     * background, and other game elements. It also configures mouse and key listeners for user input.
     */
    public GameLoop(Main mainFrame) {
        // Set panel properties
        setPreferredSize(new Dimension(1920, 1080));
        setBackground(Color.BLACK);

        // Core components
        gameData = new GameData("levels/house.json");
        player = new Player(1920 / 2 - 75, 1080 / 2 - 75, gameData.getPlayerWidth(), gameData.getPlayerWidth(), gameData.getPlayer(), MAXSPEED);
        logic = new Logic();
        camera = new Camera(gameData, player);
        texts = new ArrayList<>();
        fishingLogic = new FishingLogic();
        renderer = new Renderer();

        // Event listening
        mouseX = 0;
        mouseY = 0;
        isMouseHeld = false;
        isLeftHeld = false;
        isRightHeld = false;

        // Debug
        debug = false;
        god = false;

        // Game objects
        background = new Background(0, 0, 1920, 1080, gameData.getBackground());
        fishingState = FishingState.FALSE;
        currentHabitat = gameData.getHabitat();
        if (currentHabitat != null) {
            possibleFishes = Database.getAllFishByHabitat(currentHabitat);
        }
        timeOfDay = 0f;

        // Add event listeners
        setEventListeners();

        timer = new Timer(16, this); // Timer for 60 FPS
        timerStart();
        setFocusable(true);
    }

    /**
     * Starts the game timer.
     */
    public void timerStart() {
        timer.start();
    }

    /**
     * Stops the game timer.
     */
    public void timerStop() {
        timer.stop();
    }

    /**
     * Paints the game components onto the screen.
     * 
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        double scaleX = getWidth() / 1920.0;
        double scaleY = getHeight() / 1080.0;
        g2d.scale(scaleX, scaleY);

        // Call the draw method from Renderer
        renderer.draw(g2d, fishingLogic.getFishingLine(), player, texts, fishingLogic.getFish(), timeOfDay, chargeMeter, gameData,background);

        if (debug) {
            renderer.drawDebug(g2d, gameData);
        }
    }

    /**
     * Handles timer-based updates for the game, including player movement, camera adjustments,
     * fishing mechanics, collision detection, and screen repainting.
     * 
     * @param e The ActionEvent triggered by the Timer.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Update all text elements
        for (Text text : texts) {
            text.update();
        }

        // Increment the time of day
        timeOfDay += TIMESPEED;
        if (timeOfDay > 1) {
            timeOfDay = 0;
        }

        // Update fishing logic
        fishingState = fishingLogic.update(isMouseHeld, isLeftHeld, isRightHeld, chargeMeter, player, chargePower, gameData, possibleFishes, texts, fishingState,water);

        // Check for level change and fishing state
        checkLevelChange();
        checkFishing();

        // Move player based on current dx/dy values
        player.move(player.getDx(), player.getDy());
        adjustCamera();
        checkCollisions();

        // Redraw the screen
        repaint();
    }

    /**
     * Sets up the mouse and keyboard event listeners for the game.
     * 
     * TODO: The current event listener code is embedded directly in the `GameLoop` class. 
     * This violates the Single Responsibility Principle and makes the class harder to maintain and test.
     * I should refactor this by moving the event listeners into their own dedicated classes, e.g., 
     * `MouseEventListener` and `KeyboardEventListener`. This will improve readability and allow for easier unit testing of event handling logic.
     */
    private void setEventListeners() {
        // Mouse motion listener for tracking mouse movement
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                double scaleX = 1920.0 / getWidth();
                double scaleY = 1080.0 / getHeight();
                // Convert the current mouse position to the original coordinate system
                mouseX = (int) (e.getX() * scaleX);
                mouseY = (int) (e.getY() * scaleY);
            }
        });

        // Mouse listener for button interactions and fishing mechanics
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isMouseHeld = true;
                if (debug) {
                    System.out.print(mouseX);
                    System.out.print(" ");
                    System.out.println(mouseY);
                }

                int clicked = e.getButton();
                if (clicked == 1) {
                    if (fishingState == FishingState.IDLE) {
                        fishingState = FishingState.CHARGING;
                        chargeMeter = new ChargeMeter(player.getX() + player.getW(), player.getY(), 50, 200, 5, 0.05f, 0.9f);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isMouseHeld = false;
                if (chargeMeter != null && fishingState == FishingState.CHARGING) {
                    chargePower = chargeMeter.getAccuracy();
                    chargeMeter = null;
                    fishingState = FishingState.CASTING;
                }
            }
        });

        // Key listener for player movement and other controls
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ESCAPE) {
                    if (fishingState != FishingState.FALSE) {
                        for (FishingSpot fishingSpot : gameData.getFishingSpots()) {
                            fishingSpot.setFishing(false);
                        }
                        fishingLogic.setFishingLine(null);
                        fishingLogic.setFish(null);
                        fishingState = FishingState.FALSE;
                        player.setIsFishing(false);
                    } else {
                        Main.switchToGUI("pause");
                    }
                }
                if (key == KeyEvent.VK_F1) {
                    debug = !debug;
                    System.out.println("Debugging");
                }
                if (key == KeyEvent.VK_F2) {
                    god = !god;
                    System.out.println("God mode activated");
                }
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    isLeftHeld = true;
                    if (fishingState != FishingState.FALSE) {
                        // Implement fishing movement if needed
                    } else {
                        player.setDx(-player.getMaxSpeed());
                    }
                }
                if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    isRightHeld = true;
                    if (fishingState != FishingState.FALSE) {
                        // Implement fishing movement if needed
                    } else {
                        player.setDx(player.getMaxSpeed());
                    }
                }
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    if (fishingState != FishingState.FALSE) {
                        // Implement fishing movement if needed
                    } else {
                        player.setDy(-player.getMaxSpeed());
                    }
                }
                if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                    if (fishingState != FishingState.FALSE) {
                        // Implement fishing movement if needed
                    } else {
                        player.setDy(player.getMaxSpeed());
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    isLeftHeld = false;
                    player.setDx(0);
                }
                if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    isRightHeld = false;
                    player.setDx(0);
                }

                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                    player.setDy(0);
                }
            }
        });
    }

    /**
     * Checks for collisions between the player and other objects in the game.
     */
    private void checkCollisions() {
        // Check collisions with other sprites
        if (!god) {
            logic.collisionDetection(player, gameData.getSprites());

            if (gameData.getShelves() != null) {
                for (Shelf shelf : gameData.getShelves()) {
                    logic.collisionDetection(player, shelf, 1);
                }
            }
            logic.collisionDetection(player, gameData.getDoors());
            if (gameData.getFishingSpots() != null) {
                logic.collisionDetection(player, gameData.getFishingSpots());
            }
        }

        if (fishingLogic.getFishingLine() != null && fishingState == FishingState.REELING) {
            if (!logic.isColliding(player, fishingLogic.getFishingLine().getFishingLineFloat())) {
                fishingState = FishingState.REELING;
            } else {
                fishingState = FishingState.CAUGHT;
            }
        }

        if (fishingLogic.getFishingLine() != null && fishingLogic.getFish() != null) {
            if (logic.isColliding(fishingLogic.getFish(), fishingLogic.getFishingLine().getFishingLineFloat())) {
                fishingLogic.getFish().hooking();
            }
        }
    }

    /**
     * Adjusts the camera based on player movement and screen boundaries.
     */
    public void adjustCamera() {
        // Adjust the camera movement if the player reaches near the screen boundary
        camera.cameraMove();

        // Apply camera movement to all sprites (parallax effect)
        if (camera.getDx() != 0 || camera.getDy() != 0) {
            background.move(camera.getDx(), camera.getDy());
            for (Sprite sprite : gameData.getSprites()) {
                sprite.move(camera.getDx(), camera.getDy());
            }
            for (Door door : gameData.getDoors()) {
                door.move(camera.getDx(), camera.getDy());
            }
            if (gameData.getFishingSpots() != null) {
                for (FishingSpot fishingSpot : gameData.getFishingSpots()) {
                    fishingSpot.move(camera.getDx(), camera.getDy());
                }
            }
            if (gameData.getWater() != null) {
                gameData.getWater().move(camera.getDx(), camera.getDy());
            }
        }
    }

    /**
     * Checks if the player is at a fishing spot and updates fishing state accordingly.
     */
    public void checkFishing() {
        if (gameData.getFishingSpots() != null) {
            for (FishingSpot fishingSpot : gameData.getFishingSpots()) {
                if (fishingSpot.getFishing()) {
                    if (fishingState == FishingState.FALSE) {
                        fishingState = FishingState.IDLE;
                    }
                    player.setIsFishing(true);
                }
            }
        }
    }

    /**
     * Checks if the level has changed and reloads data if necessary.
     */
    public void checkLevelChange() {
        // Check if level has changed and reload data if necessary
        for (Door door : gameData.getDoors()) {
            String newPath = door.levelChanged();
            if (newPath != null) {
                gameData.loadGameData(newPath);
                background = new Background(background.getX(), background.getY(), background.getW(), background.getH(), gameData.getBackground());
                player.setX(door.getToX());
                player.setY(door.getToY());
                player.setW(gameData.getPlayerWidth());
                player.setH(gameData.getPlayerWidth());
                water = gameData.getWater();
                camera.setPlayer(player);
                currentHabitat = gameData.getHabitat();
                if (currentHabitat != null) {
                    possibleFishes = Database.getAllFishByHabitat(currentHabitat);
                }
            }
        }
    }
}
