package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;

/**
 * Manages the main game loop, including rendering, user input, game logic, and updates.
 * It extends JPanel to provide a custom drawing surface and implements ActionListener for timer-based updates.
 */
public class GameLoop extends JPanel implements ActionListener {

    private Timer timer;
    private Logic logic;
    private Camera camera;
    private Player player;
    private GameData gameData;
    private int maxSpeed;
    private Background background;
    private Button button;
    private int mouseX, mouseY;
    private Boolean isFishing;
    private Boolean isCharging;
    private Boolean isCasting;
    private Boolean isWaitingForFish;
    private Fish fish;
    private Boolean isReeling;
    private Boolean isMouseHeld;
    private Boolean isLeftHeld;
    private Boolean isRightHeld;
    private ChargeMeter chargeMeter;
    private float chargePower;
    private FishingLine fishingLine;
    private Water water;
    private Boolean debug;
    private Boolean god;
    private Main mainFrame;

    /**
     * Initializes the game loop by setting up the panel properties, game data, player, camera,
     * background, and other game elements. It also configures mouse and key listeners for user input.
     */
    public GameLoop(Main mainFrame) {
        // Set panel properties
        this.mainFrame = mainFrame;
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        maxSpeed = 5;
        mouseX = 0;
        mouseY = 0;
        gameData = new GameData("src/main/resources/levels/1.json");
        player = new Player(350, 400, 100, 100, gameData.getPlayer(), maxSpeed);
        logic = new Logic(player, gameData);
        camera = new Camera(logic, gameData, player);
        background = new Background(0, 0, gameData.getWidth(), gameData.getHeight(), gameData.getBackground());
        button = new Button(50, 50, 50, 50, Color.RED, Color.MAGENTA, Color.ORANGE, mainFrame::switchToGUI);
        isFishing = false;
        isCharging = false;
        isCasting = false;
        isWaitingForFish = false;
        isReeling = false;
        isMouseHeld = false;
        isLeftHeld = false;
        isRightHeld = false;
        debug=false;
        god=false;

        // Add mouse motion listener to track mouse position
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        // Add mouse listener for button interactions and fishing mechanics
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isMouseHeld = true;
                if (isFishing == null) {
                    isFishing = false;
                }
                int clicked = e.getButton();
                if (clicked == 1) {
                    button.listener(mouseX, mouseY, true);
                    if (isFishing && !isCasting && !isReeling &&!isWaitingForFish) {
                        isCharging = true;
                        chargeMeter = new ChargeMeter(player.getX(), player.getY(), 50, 200, 5, 0.05f, 0.9f);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isFishing == null) {
                    isFishing = false;
                }
                isMouseHeld = false;
                int released = e.getButton();

                if (isFishing && !isCasting && !isReeling&&!isWaitingForFish && chargeMeter != null) {
                    chargePower = chargeMeter.getAccuracy();
                    chargeMeter = null;
                    isCasting = true;
                    isCharging = false;
                }
            }
        });

        // Add key listener for player movement
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (isFishing == null) {
                    isFishing = false;
                }
                if (key == KeyEvent.VK_ESCAPE){
                    timerStop();
                }
                if (key == KeyEvent.VK_1){
                    debug=!debug;
                    System.out.println("debugging");
                }
                if (key == KeyEvent.VK_2){
                    god=!god;
                    System.out.println("god activated");
                }
                if (key == KeyEvent.VK_LEFT) {
                    isLeftHeld = true;
                    if (isFishing) {
                        // Implement fishing movement if needed
                    } else {
                        player.setDx(-player.getMaxSpeed());
                    }
                }

                if (key == KeyEvent.VK_RIGHT) {
                    isRightHeld = true;
                    if (isFishing) {
                        // Implement fishing movement if needed
                    } else {
                        player.setDx(player.getMaxSpeed());
                    }
                }

                if (key == KeyEvent.VK_UP) {
                    if (isFishing) {
                        // Implement fishing movement if needed
                    } else {
                        player.setDy(-player.getMaxSpeed());
                    }
                }

                if (key == KeyEvent.VK_DOWN) {
                    if (isFishing) {
                        // Implement fishing movement if needed
                    } else {
                        player.setDy(player.getMaxSpeed());
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                
                if (key == KeyEvent.VK_LEFT) {
                    isLeftHeld = false;
                    player.setDx(0);
                }
                if (key == KeyEvent.VK_RIGHT) {
                    isRightHeld = false;
                    player.setDx(0);
                }

                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                    player.setDy(0);
                }
            }
        });

        // Initialize the timer to call actionPerformed() at regular intervals
        timer = new Timer(16, this); // Approximately 60 FPS (1000ms/60)
        timerStart();
        setFocusable(true);
    }
    public void timerStart(){
        timer.start();
    }
    public void timerStop(){
        timer.stop();

    }

    /**
     * Paints the game components onto the panel.
     * 
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // Cast Graphics to Graphics2D
        draw(g2d);
    }

    /**
     * Draws all game components, including the background, player, buttons, and other sprites.
     * 
     * @param g The Graphics2D object used for drawing.
     */
    private void draw(Graphics2D g) {
        background.draw(g);
        button.draw(g);
        if (fishingLine != null) {
            fishingLine.draw(g);
        }
        player.draw(g);




        if (chargeMeter != null) {
            chargeMeter.draw(g);
        }
        if (fish!=null){
            fish.draw(g);
        }
        if(debug){
        if(gameData.getWater()!=null){
            gameData.getWater().draw(g);

        for (Door door : gameData.getDoors()) {
            door.draw(g);
        }
        }
        for (Sprite sprite : gameData.getSprites()) {
            sprite.draw(g);
        }
        if (gameData.getFishingSpots() != null) {
            for (FishingSpot fishingSpot : gameData.getFishingSpots()) {
                fishingSpot.draw(g);
            }
        }
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
System.out.println("hey");
        if (isFishing != null) {
            if (isCharging) {
                if (isMouseHeld && chargeMeter != null) {
                    chargeMeter.increaseCharge();
                }
            }
            if (isCasting) {
                fishingLine = new FishingLine((player.getX() + player.getW() / 2), player.getY() - Math.round(chargePower * 100), 15, 15, (player.getX() + player.getW() / 2), (player.getY() + player.getH() / 2));
                chargePower = 0;
                isCasting = false;
                isWaitingForFish = true;
            }
            if (isWaitingForFish){
                if (fish==null){
                    fish = new Fish(350, 400,50,50,gameData.getFish(),fishingLine.getFishingLineFloat().getX(), fishingLine.getFishingLineFloat().getY(),gameData.getWater(),logic);
                    fish.spawn();
                }
            
                if(fish.getHooked()){
                    if(fish.getIsAnimationComplete()){
                    isWaitingForFish=false;
                    isReeling=true;
                    fish = null;
                    }
                    
                }
                else{
                    fish.update(fishingLine.getFishingLineFloat().getX(),fishingLine.getFishingLineFloat().getY());
                }

            }

            if (isReeling) {
                
                fishingLine.update(isLeftHeld, isRightHeld, isMouseHeld);
                if (fishingLine.getIsGameOver()) {
                    isReeling = false;
                    fishingLine = null;
                    for (FishingSpot fishingSpot : gameData.getFishingSpots()) {
                        fishingSpot.setFishing(false);
                    }
                }
            }
        }

        // Check if level has changed and reload data if necessary
        for (Door door : gameData.getDoors()) {
            String newPath = door.levelChanged();
            if (newPath != null) {
                gameData.loadGameData(newPath);
                player = new Player(350, 400, 100, 100, gameData.getPlayer(), maxSpeed);
                background = new Background(0, 0, background.getW(), background.getH(), gameData.getBackground());
                logic.setPlayer(player);
                camera.setPlayer(player);
                camera.setLogic(logic);
            }
        }

        if (gameData.getFishingSpots() != null) {
            for (FishingSpot fishingSpot : gameData.getFishingSpots()) {
                isFishing = fishingSpot.getFishing();
            }
        }

        // Update game state
        button.listener(mouseX, mouseY, false);

        // Move player based on current dx/dy values
        player.move(player.getDx(), player.getDy());

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
            if (gameData.getWater()!=null){
                gameData.getWater().move(camera.getDx(), camera.getDy());
            }
        }

        // Check collisions with other sprites
        if(!god){
        logic.collisionDetection(player, gameData.getSprites());
        logic.collisionDetection(player, gameData.getDoors());
        if (gameData.getFishingSpots() != null) {
            logic.collisionDetection(player, gameData.getFishingSpots());
        }
    }
        if (fishingLine != null) {
            fishingLine.setIsGameOver(logic.isColliding(player, fishingLine.getFishingLineFloat()));
        }
        if(fishingLine!=null && fish!=null){
            if(logic.isColliding(fish,fishingLine.getFishingLineFloat())){
                fish.hooking();
            }
                
            
        }

        // Redraw the screen
        repaint();
    }
}
