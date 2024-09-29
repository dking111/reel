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
import main.GUI.Button;
import main.GameObjects.Background;
import main.GameObjects.ChargeMeter;
import main.GameObjects.TimeOfDayTint;
import main.GameObjects.Light;
import main.GameObjects.Door;
import main.GameObjects.FireLight;
import main.GameObjects.Fish;
import main.GameObjects.FishingLine;
import main.GameObjects.FishingSpot;
import main.GameObjects.Player;
import main.GameObjects.Shelf;
import main.GameObjects.Sprite;
import main.GameObjects.Water;
import main.GameObjects.WindowLight;

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
    private float fishWaitTimer;
    private Fish fish;
    private Boolean isReeling;
    private Boolean isCaught;
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
    private java.util.List<Fish> possibleFishes;
    private String currentHabitat;
    private Random random;
    
    // Day-Night cycle
    private float timeOfDay; // 0.0f = Midnight, 0.5f = Noon, 1.0f = Midnight
    private float timeSpeed = 0.0001f; // Controls the speed of the day-night cycle

    private TimeOfDayTint dayLight;
    private java.util.List<Light> lights;


    /**
     * Initializes the game loop by setting up the panel properties, game data, player, camera,
     * background, and other game elements. It also configures mouse and key listeners for user input.
     */
    public GameLoop(Main mainFrame) {
        // Set panel properties
        this.mainFrame = mainFrame;
        setPreferredSize(new Dimension(1920, 1080));
        setBackground(Color.BLACK);
        maxSpeed = 9;
        mouseX = 0;
        mouseY = 0;
        gameData = new GameData("src/main/resources/levels/house.json");
        player = new Player(1920/2-(75), 1080/2-(75), gameData.getPlayerWidth(), gameData.getPlayerWidth(), gameData.getPlayer(), maxSpeed);
        logic = new Logic(player, gameData);
        camera = new Camera(logic, gameData, player);
        background = new Background(0, 0, 1920, 1080, gameData.getBackground());
        lights = gameData.getLights();
        System.out.println(lights);
        isFishing = false;
        isCharging = false;
        isCasting = false;
        isWaitingForFish = false;
        isReeling = false;
        isCaught = false;
        isMouseHeld = false;
        isLeftHeld = false;
        isRightHeld = false;
        debug=false;
        god=false;
        currentHabitat = gameData.getHabitat();
        if (currentHabitat!=null)
        {possibleFishes = Database.getAllFishByHabitat(currentHabitat);}
        random = new Random();

                // Day-Night initialization
                timeOfDay = 0f; // Starting at noon
                dayLight = new TimeOfDayTint();
        
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
                if (isFishing == null) {
                    isFishing = false;
                }

            
                int clicked = e.getButton();
                if (clicked == 1) {
                    if(button!= null)
                    {button.listener(mouseX, mouseY, true);}
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
                    isFishing  = false;

                }
                if (key == KeyEvent.VK_ESCAPE){
                    if(isFishing){
                    for (FishingSpot fishingSpot : gameData.getFishingSpots()) {
                        fishingSpot.setFishing(false);
                        isCasting = false;
                        isWaitingForFish=false;
                        isCaught = false;
                        isCharging = false;
                        isReeling = false;
                        fishingLine = null;
                        fish = null;
                    }
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
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        // Cast to Graphics2D for more advanced operations
        Graphics2D g2d = (Graphics2D) g;
    
        // Calculate the scaling factors for width and height
        double scaleX = getWidth() / 1920.0;  
        double scaleY = getHeight() / 1080.0; 
    
        // Apply the scaling transformation to the Graphics2D object
        g2d.scale(scaleX, scaleY);
    
        // Draw all the game components using the scaled Graphics2D object
        draw(g2d);

        
    }
    

    /**
     * Draws all game components, including the background, player, buttons, and other sprites.
     * 
     * @param g The Graphics2D object used for drawing.
     */
    private void draw(Graphics2D g) {
        
        background.draw(g);
        if (button!= null)
        {button.draw(g);}
        if (fishingLine != null) {
            fishingLine.draw(g);
        }
        
        if (lights!=null){
        for (Light light : lights){
            light.update(timeOfDay);
            light.draw(g);
        }
    }


        player.draw(g);
        dayLight.draw(g, timeOfDay);





        if (chargeMeter != null) {
            chargeMeter.draw(g);
        }
        if (fish!=null){
            fish.draw(g);
        }
        if(debug){
        if(gameData.getWater()!=null){
            gameData.getWater().draw(g);
        }

        if(gameData.getShelves()!=null){
            for(Shelf shelf : gameData.getShelves()){
                shelf.draw(g);
            }
        }

        for (Door door : gameData.getDoors()) {
            door.draw(g);
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
        
        timeOfDay += timeSpeed;
        if (timeOfDay > 1) {
            timeOfDay = 0;
        }

    
        if (fish!=null)
    {System.out.println(fish.getIsVisible());}
        if (isFishing != null) {
            if (isCharging) {
                if (isMouseHeld && chargeMeter != null) {
                    chargeMeter.increaseCharge();
                }
            }
            if (isCasting) {
                if(player.getState()!="casting"){
                    player.setState("casting");
                    player.refreshAnimation();
                    player.setAnimationSpeed(10);

                }
                if (player.getIsAnimationComplete()){
                fishingLine = new FishingLine((player.getX() + player.getW() / 2), player.getY() - Math.round(chargePower * 100), 15, 15, (player.getX() + player.getW() -15 ), (player.getY() + player.getH() / 4));
                fishWaitTimer = (1-chargePower) + 1;
                chargePower = 0;
                isCasting = false;
                isWaitingForFish = true;
                player.setState("idle_fishing");
                player.refreshAnimation();
                player.setAnimationSpeed(5);
                }
            }
            if (isWaitingForFish){
                fishWaitTimer-=0.01;
                if(fishWaitTimer<=0){
                    fishWaitTimer=0;
                if (fish==null){
                    while(fish==null){
                    for(Fish possibleFish: possibleFishes){
                        if (random.nextInt(0,10)<possibleFish.getRarity()){
                            fish = possibleFish;
                            break;
                        }
                    }
                    }
                    fish.spawn(gameData.getWater(),fishingLine.getFishingLineFloat().getX(), fishingLine.getFishingLineFloat().getY());
                }
            
                if(fish.getHooked()){
                    if(fish.getIsAnimationComplete()){
                    isWaitingForFish=false;
                    isReeling=true;
                    fish.setIsVisible(false);
                    }
                    
                }
                else{
                    fish.update(fishingLine.getFishingLineFloat().getX(),fishingLine.getFishingLineFloat().getY());
                }
            }

            }

            if (isReeling) {
                
                fishingLine.update(isLeftHeld, isRightHeld, isMouseHeld);

                if (isLeftHeld && !isRightHeld){
                    player.setState("reelingLeft");
                }
                else if (isRightHeld && !isLeftHeld){
                    player.setState("reelingRight");
                }
                else{
                    player.setState("reeling");
                }
                if (fishingLine.getIsGameOver()) {
                    fish = null;
                    fishingLine = null;
                    isReeling = false;
    
                }
            }
            if (isCaught){
                fishingLine = null;
                if(player.getState()!="catching"){
                player.setState("catching");
                player.refreshAnimation();
                player.setAnimationSpeed(10);
                }
                if(player.getIsAnimationComplete()){
                    isCaught=false;
                    player.refreshAnimation();
                    player.setAnimationSpeed(5);

                    Database.setFishWeight(fish);
                    fish=null;
                }
                
                
            }
            if (isFishing && !isCaught && !isReeling && !isCasting && !isCharging && !isWaitingForFish){
                player.setState("idle_back");
            }
        }

        // Check if level has changed and reload data if necessary
        for (Door door : gameData.getDoors()) {
            String newPath = door.levelChanged();
            if (newPath != null) {
                gameData.loadGameData(newPath);
                player = new Player(door.getToX(), door.getToY(),  gameData.getPlayerWidth(), gameData.getPlayerWidth(), gameData.getPlayer(), maxSpeed);
                background = new Background(0, 0, background.getW(), background.getH(), gameData.getBackground());
                lights = gameData.getLights();
                logic.setPlayer(player);
                camera.setPlayer(player);
                camera.setLogic(logic);
                currentHabitat = gameData.getHabitat();
                if (currentHabitat!=null)
                {possibleFishes = Database.getAllFishByHabitat(currentHabitat);}
            }
        }

        if (gameData.getFishingSpots() != null) {
            for (FishingSpot fishingSpot : gameData.getFishingSpots()) {
                isFishing = fishingSpot.getFishing();
                if(isFishing!=null)
                {player.setIsFishing(isFishing);}
            }
        }
        System.out.println(timeOfDay);
        // Update game state
        if (button != null)
        {button.listener(mouseX, mouseY, false);}

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
        if (fishingLine != null && isReeling) {
            isReeling = !(logic.isColliding(player, fishingLine.getFishingLineFloat()));
            isCaught = !isReeling;
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
