package main.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import java.util.Random;
//import java.util.List;

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

import java.util.ArrayList;
import java.util.List;

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
    private int mouseX, mouseY;
    private Boolean isMouseHeld;
    private Boolean isLeftHeld;
    private Boolean isRightHeld;
    private ChargeMeter chargeMeter;
    private float chargePower;
    private Water water;
    private Boolean debug;
    private Boolean god;
    private List<Fish> possibleFishes;
    private String currentHabitat;
    private Random random;
    private List<Text> texts;
    private FishingLogic fishingLogic;
    private Renderer renderer;
    private FishingState fishingState;
    
    // Day-Night cycle
    private float timeOfDay; // 0.0f = Midnight, 0.5f = Noon, 1.0f = Midnight
    private float timeSpeed = 0.001f; // Controls the speed of the day-night cycle



    /**
     * Initializes the game loop by setting up the panel properties, game data, player, camera,
     * background, and other game elements. It also configures mouse and key listeners for user input.
     */
    public GameLoop(Main mainFrame) {
        // Set panel properties
        setPreferredSize(new Dimension(1920, 1080));
        setBackground(Color.BLACK);
        maxSpeed = 9;
        mouseX = 0;
        mouseY = 0;
        gameData = new GameData("src/main/resources/levels/house.json");
        player = new Player(1920/2-(75), 1080/2-(75), gameData.getPlayerWidth(), gameData.getPlayerWidth(), gameData.getPlayer(), maxSpeed);
        logic = new Logic();
        camera = new Camera(gameData, player);
        texts = new ArrayList<>();
        fishingLogic = new FishingLogic();
        renderer = new Renderer();

        isMouseHeld = false;
        isLeftHeld = false;
        isRightHeld = false;
        debug=false;
        god=false;

        fishingState = FishingState.FALSE;
        
        currentHabitat = gameData.getHabitat();
        if (currentHabitat!=null)
        {possibleFishes = Database.getAllFishByHabitat(currentHabitat);}
        random = new Random();

                // Day-Night initialization
                timeOfDay = 0f; // Starting at noon
        
        // Add mouse motion listener to track mouse position
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                double scaleX = 1920.0 / getWidth();  // Original width / current width
                double scaleY = 1080.0 / getHeight(); // Original height / current height
        
                // Convert the current mouse position to the original coordinate system
                mouseX = (int) (e.getX() * scaleX);
                mouseY = (int) (e.getY() * scaleY);
            }
        });

        // Add mouse listener for button interactions and fishing mechanics
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isMouseHeld = true;

                if(debug){
                    System.out.print(mouseX);
                    System.out.print(" ");
                    System.out.println(mouseY);
                }


            
                int clicked = e.getButton();
                if (clicked == 1) {
                    if (fishingState == FishingState.IDLE) {
                        fishingState = FishingState.CHARGING;
                        chargeMeter = new ChargeMeter(player.getX()+player.getW(), player.getY(), 50, 200, 5, 0.05f, 0.9f);
                        System.out.println("made chargemeter");
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                isMouseHeld = false;
                int released = e.getButton();

                if (chargeMeter != null && fishingState == FishingState.CHARGING) {
                    chargePower = chargeMeter.getAccuracy();
                    chargeMeter = null;
                    fishingState = FishingState.CASTING;
                }
            }
        });

        // Add key listener for player movement
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_ESCAPE){
                    if(fishingState!=FishingState.FALSE){
                    for (FishingSpot fishingSpot : gameData.getFishingSpots()) {
                        fishingSpot.setFishing(false);}

                        fishingLogic.setFishingLine(null);
                        fishingLogic.setFish(null);
                        fishingState = FishingState.FALSE;
                        player.setIsFishing(false);
                    
                    }
                    else{
                        Main.switchToGUI("pause");
                    }
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
                    if (fishingState!=FishingState.FALSE) {
                        // Implement fishing movement if needed
                    } else {
                        player.setDx(-player.getMaxSpeed());
                    }
                }

                if (key == KeyEvent.VK_RIGHT) {
                    isRightHeld = true;
                    if (fishingState!=FishingState.FALSE) {
                        // Implement fishing movement if needed
                    } else {
                        player.setDx(player.getMaxSpeed());
                    }
                }

                if (key == KeyEvent.VK_UP) {
                    if (fishingState!=FishingState.FALSE) {
                        // Implement fishing movement if needed
                    } else {
                        player.setDy(-player.getMaxSpeed());
                    }
                }

                if (key == KeyEvent.VK_DOWN) {
                    if (fishingState!=FishingState.FALSE) {
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
@Override
public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    
    double scaleX = getWidth() / 1920.0;  
    double scaleY = getHeight() / 1080.0; 
    g2d.scale(scaleX, scaleY);
    
    // Call the draw method from Renderer
    renderer.draw(g2d, fishingLogic.getFishingLine(), player, texts, fishingLogic.getFish(), timeOfDay,chargeMeter,gameData);
    if (debug) {
        renderer.drawDebug(g2d, gameData);
    }
}


    

    /**
     * Draws all game components, including the background, player, buttons, and other sprites.
     * 
     * @param g The Graphics2D object used for drawing.
     */


    /**
     * Handles timer-based updates for the game, including player movement, camera adjustments,
     * fishing mechanics, collision detection, and screen repainting.
     * 
     * @param e The ActionEvent triggered by the Timer.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        for(Text text :texts){
            text.update();
        }

        timeOfDay += timeSpeed;
        if (timeOfDay > 1) {
            timeOfDay = 0;
        }


        fishingState = fishingLogic.update(isMouseHeld,isLeftHeld,isRightHeld,chargeMeter,player,chargePower,gameData,possibleFishes,texts,fishingState);

        // Check if level has changed and reload data if necessary
        for (Door door : gameData.getDoors()) {
            String newPath = door.levelChanged();
            if (newPath != null) {
                gameData.loadGameData(newPath);
                player = new Player(door.getToX(), door.getToY(),  gameData.getPlayerWidth(), gameData.getPlayerWidth(), gameData.getPlayer(), maxSpeed);
                camera.setPlayer(player);
                currentHabitat = gameData.getHabitat();
                if (currentHabitat!=null)
                {possibleFishes = Database.getAllFishByHabitat(currentHabitat);}
            }
        }

        if (gameData.getFishingSpots() != null) {
            for (FishingSpot fishingSpot : gameData.getFishingSpots()) {
                if (fishingSpot.getFishing()){
                    if(fishingState == FishingState.FALSE)
                    {fishingState = FishingState.IDLE;}
                    player.setIsFishing(true);
                }
       
            }
        }


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

        if(gameData.getShelves()!=null){
            for(Shelf shelf : gameData.getShelves()){
                logic.collisionDetection(player,shelf ,1);
            }
        }
        logic.collisionDetection(player, gameData.getDoors());
        if (gameData.getFishingSpots() != null) {
            logic.collisionDetection(player, gameData.getFishingSpots());
        }
    }
        if (fishingLogic.getFishingLine() != null && fishingState==FishingState.REELING) {
            if(!(logic.isColliding(player, fishingLogic.getFishingLine().getFishingLineFloat()))){
                fishingState = FishingState.REELING;
            }
            else{
                fishingState = FishingState.CAUGHT;
            }
        

        }
        if(fishingLogic.getFishingLine()!=null && fishingLogic.getFish()!=null){
            if(logic.isColliding(fishingLogic.getFish(),fishingLogic.getFishingLine().getFishingLineFloat())){
                fishingLogic.getFish().hooking();
            }
                
            
        }

        // Redraw the screen
        repaint();
    }






}
