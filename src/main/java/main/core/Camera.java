package main.core;

import java.lang.Math;

import main.Main;
import main.GameObjects.Player;

/**
 * Represents a camera that follows the player within the game world.
 * The camera adjusts its position based on the player's movement to keep the player in view.
 */
public class Camera {
    private GameData gameData;
    private Player player;
    private int dx, dy;

    /**
     * Constructs a Camera instance with the specified parameters.
     *
     * @param logic The logic to manage camera-related decisions.
     * @param gameData The game data providing information about the game world dimensions.
     * @param player The player whose movement the camera follows.
     */
    public Camera(GameData gameData, Player player) {
        this.gameData = gameData;
        this.player = player;
        dx = 0;
        dy = 0;
    }

    /**
     * Updates the camera's position based on the player's movement.
     * Adjusts the camera to keep the player within the defined bounds of the game world.
     */
    public void cameraMove() {
        if(gameData.getCameraBounds()==0){
            dx=0;
            dy=0;
            return;
        }
        float playerRight = player.getX() + player.getW();
        float playerBottom = player.getY() + player.getH();
        float boundedHeight = Main.getWindowHeight() * gameData.getCameraBounds();
        float boundedWidth = Main.getWindowWidth() * gameData.getCameraBounds();

        // Horizontal camera movement
        if (playerRight > boundedWidth) {
            dx = speedAdjusted(-player.getDx());
            player.setX(Math.round(boundedWidth - player.getW()));
        } else if (player.getX() < (Main.getWindowWidth() - boundedWidth)) {
            dx = speedAdjusted(-player.getDx());
            player.setX(Math.round(Main.getWindowWidth() - boundedWidth));
        } else {
            dx = 0;
        }

        // Vertical camera movement
        if (playerBottom > boundedHeight) {
            dy = speedAdjusted(-player.getDy());
            player.setY(Math.round(boundedHeight - player.getH()));
        } else if (player.getY() < (Main.getWindowHeight() - boundedHeight)) {
            dy = speedAdjusted(-player.getDy());
            player.setY(Math.round(Main.getWindowHeight() - boundedHeight));
        } else {
            dy = 0;
        }
    }

    /**
     * Adjusts the speed of the camera movement.
     * Currently, it returns the input value without modification, but can be extended for adjustments.
     *
     * @param value The speed value to be adjusted.
     * @return The adjusted speed value.
     */
    private int speedAdjusted(int value) {
        return value; // You can add further adjustments or scaling to dx/dy if needed
    }

    /**
     * Gets the current x-direction speed of the camera.
     *
     * @return The x-direction speed of the camera.
     */
    public int getDx() { return dx; }

    /**
     * Sets the x-direction speed of the camera.
     *
     * @param dx The new x-direction speed of the camera.
     */
    public void setDx(int dx) { this.dx = dx; }

    /**
     * Gets the current y-direction speed of the camera.
     *
     * @return The y-direction speed of the camera.
     */
    public int getDy() { return dy; }

    /**
     * Sets the y-direction speed of the camera.
     *
     * @param dy The new y-direction speed of the camera.
     */
    public void setDy(int dy) { this.dy = dy; }

    /**
     * Sets a new player for the camera to follow.
     *
     * @param player The new player instance to be followed by the camera.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
