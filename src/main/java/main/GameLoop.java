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
    private Boolean isReeling;
    private Boolean isMouseHeld;
    private ChargeMeter chargeMeter;
    private float chargePower;
    private FishingLine fishingLine;

    public GameLoop() {
        // Set panel properties
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        maxSpeed = 5;
        mouseX=0;
        mouseY=0;
        gameData = new GameData("src/main/resources/levels/1.json");
        player = new Player(100, 100, 100, 100,gameData.getPlayer(),maxSpeed);
        logic = new Logic(player,gameData);
        camera = new Camera(logic,gameData,player);
        background = new Background(0, 0, gameData.getWidth(), gameData.getHeight(), gameData.getBackground());
        button = new Button(50,50,50,50,Color.RED,Color.MAGENTA,Color.ORANGE,logic);
        isFishing = false;
        isCharging = false;
        isCasting = false;
        isReeling = false;
        isMouseHeld = false;





        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

            }

        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                isMouseHeld = true;
                if (isFishing==null){
                    isFishing = false;
                }
                int clicked = e.getButton();
                //System.out.println(clicked);
                if(clicked==1){
                button.listener(mouseX, mouseY,true);
                if(isFishing && !isCasting){
                    isCharging = true;
                    chargeMeter = new ChargeMeter(player.getX(), player.getY(), 50, 200,5 , 0.05f,0.9f);
                }
            }
            }
            @Override
            public void mouseReleased(MouseEvent e){
                if (isFishing==null){
                    isFishing = false;
                }
                isMouseHeld = false;
                int released = e.getButton();
                
                if(isFishing && !isCasting){
                    chargePower = chargeMeter.getAccuracy();
                    chargeMeter = null;
                    isCasting = true;
                    isCharging = false;
                }

            }
            
        });
        // Add key listener for player controls
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (isFishing==null){
                    isFishing = false;
                }
                if (key == KeyEvent.VK_LEFT) {
                    if(isFishing){

                    }
                    else{
                    player.setDx(-player.getMaxSpeed());
                    }
                }
        
                if (key == KeyEvent.VK_RIGHT) {
                    if(isFishing){

                    }
                    else{
                    player.setDx(player.getMaxSpeed());
                    }
                }
        
                if (key == KeyEvent.VK_UP) {
                    if(isFishing){

                    }
                    else{
                        player.setDy(-player.getMaxSpeed());
                    }
                    
                }
        
                if (key == KeyEvent.VK_DOWN) {
                    if(isFishing){

                    }
                    else{
                        player.setDy(player.getMaxSpeed());
                    }
                    
                }
            
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                    player.setDx(0);
                }
        
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                    player.setDy(0);
                }
            }
        });

        
        // Initialize the timer
        timer = new Timer(16, this); // 60 FPS (1000ms/60)
        timer.start();
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // Cast Graphics to Graphics2D
        draw(g2d);
    }

    private void draw(Graphics2D g) {
        background.draw(g);
        button.draw(g);
        // Draw the player
        if(fishingLine!=null){
            fishingLine.draw(g);
        }
        player.draw(g);
        for (Sprite sprite : gameData.getSprites()) {
            sprite.draw(g);
        }
        for (Door door : gameData.getDoors()) {
            door.draw(g);
        }
        if(gameData.getFishingSpots()!=null){
        for (FishingSpot fishingSpot : gameData.getFishingSpots()){
            fishingSpot.draw(g);
        }
        if(chargeMeter!=null){
            chargeMeter.draw(g);
        }

    }


        
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        
        if(isFishing!=null){
            if(isCharging){
                if(isMouseHeld && chargeMeter!=null){
                chargeMeter.increaseCharge();
                }
            }
            if (isCasting){
                fishingLine = new FishingLine((player.getX() + player.getW() / 2), player.getY()-Math.round(chargePower*100), 15, 15, (player.getX() + player.getW() / 2), (player.getY() + player.getH() / 2));
                chargePower = 0;
                isCasting = false;
                isReeling = true;
            }
            if(isReeling){
                
            }


    }


        //check if level has changed
        for(Door door : gameData.getDoors()){
            String newPath = door.levelChanged();
            if(newPath != null){
                gameData.loadGameData(newPath);
                player = new Player(player.getX(), player.getY(), player.getW(), player.getH(), gameData.getPlayer(),maxSpeed);
                background = new Background(background.getX(), background.getY(), background.getW(), background.getH(), gameData.getBackground());
                logic.setPlayer(player);
                camera.setPlayer(player);
                camera.setLogic(logic);
            }
        }


        if(gameData.getFishingSpots()!=null){
            for (FishingSpot fishingSpot: gameData.getFishingSpots()){
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
            for (Door door: gameData.getDoors()){
                door.move(camera.getDx(), camera.getDy());
            }
            if(gameData.getFishingSpots()!=null){
                for (FishingSpot fishingSpot: gameData.getFishingSpots()){
                    fishingSpot.move(camera.getDx(), camera.getDy());
                }
            }
        }
    
        // Check collisions with other sprites
        logic.collisionDetection(player, gameData.getSprites());
        logic.collisionDetection(player, gameData.getDoors());
        if (gameData.getFishingSpots()!=null){
        logic.collisionDetection(player, gameData.getFishingSpots());
        }
    
        // Redraw the screen
        repaint();
    }
    
}
