package main.core;

import java.util.ArrayList;
import java.util.List;

import main.GameObjects.Player;
import main.GameObjects.Sprite;

/**
 * Handles the game's logic, including collision detection and response.
 * It manages interactions between the player and other game objects.
 */
public class Logic {
    private Player player;
    private GameData gameData;

    /**
     * Initializes the game logic with the given player and game data.
     * 
     * @param player The player object to be managed.
     * @param gameData The game data containing the game's state and objects.
     */
    public Logic(Player player, GameData gameData) {
        this.player = player;
        this.gameData = gameData;
    }

    /**
     * Checks for collisions between the player and a single sprite.
     * 
     * @param player The player object to check for collisions.
     * @param sprite The sprite object to check for collisions with the player.
     */
    public void collisionDetection(Sprite player, Sprite sprite) {
        List<Sprite> spriteList = new ArrayList<>();
        spriteList.add(sprite);
        collisionDetection(player, spriteList);
    }

    public void collisionDetection(Sprite player, Sprite sprite, Integer clearance) {
        List<Sprite> spriteList = new ArrayList<>();
        spriteList.add(sprite);
        collisionDetection(player, spriteList,clearance);
    }

    /**
     * Checks for collisions between the player and a list of sprites.
     * 
     * @param player The player object to check for collisions.
     * @param sprites The list of sprite objects to check for collisions with the player.
     */
    public void collisionDetection(Sprite player, List<? extends Sprite> sprites) {
        collisionDetection(player, sprites, 0);
    }

    /**
     * Checks for collisions between the player and a list of sprites,
     * with a specified clearance value to adjust collision handling.
     * 
     * @param player The player object to check for collisions.
     * @param sprites The list of sprite objects to check for collisions with the player.
     * @param clearance The clearance value to adjust collision handling.
     */
    public void collisionDetection(Sprite player, List<? extends Sprite> sprites, int clearance) {
        for (Sprite sprite : sprites) {
            if (isColliding(player, sprite)) {
                handleCollision(player, sprite, clearance);
                sprite.collided();
            }
        }
    }

    /**
     * Determines if the player is colliding with a sprite.
     * 
     * @param player The player object to check for collisions.
     * @param sprite The sprite object to check for collisions with the player.
     * @return True if the player and sprite are colliding, otherwise false.
     */
    public boolean isColliding(Sprite player, Sprite sprite) {
        return player.getX() < sprite.getX() + sprite.getW() &&
               player.getX() + player.getW() > sprite.getX() &&
               player.getY() < sprite.getY() + sprite.getH() &&
               player.getY() + player.getH() > sprite.getY();
    }

    /**
     * Handles the collision response by adjusting the player's position
     * to prevent overlap with the sprite, considering a clearance value.
     * 
     * @param player The player object involved in the collision.
     * @param sprite The sprite object involved in the collision.
     * @param clearance The clearance value to adjust collision handling.
     */
    private void handleCollision(Sprite player, Sprite sprite, int clearance) {
        int overlapX = Math.min(player.getX() + player.getW() - sprite.getX(), sprite.getX() + sprite.getW() - player.getX());
        int overlapY = Math.min(player.getY() + player.getH() - sprite.getY(), sprite.getY() + sprite.getH() - player.getY());

        if (overlapX < overlapY) {
            if (player.getX() < sprite.getX()) {
                player.setX(sprite.getX() - (player.getW() + clearance)); // Move player to the left of the sprite
            } else {
                player.setX(sprite.getX() + (sprite.getW() + clearance)); // Move player to the right of the sprite
            }
        } else {
            if (player.getY() < sprite.getY()) {
                player.setY(sprite.getY() - (player.getH() + clearance)); // Move player above the sprite
            } else {
                player.setY(sprite.getY() + (sprite.getH() + clearance)); // Move player below the sprite
            }
        }
        //resets speed to prevent running into object again
        player.setDx(0);
        player.setDy(0);
        
    }


    public boolean isContaining(Sprite sprite1,Sprite sprite2){
        return (sprite1.getX() <= sprite2.getX()                                    &&
                sprite1.getX() + sprite1.getW() >= sprite2.getX() + sprite2.getW()  &&
                sprite1.getY() <= sprite2.getY()                                    &&
                sprite1.getY() + sprite1.getH() >= sprite2.getY() + sprite2.getH());

        
    }



    /**
     * Sets a new player object for the logic to manage.
     * 
     * @param player The new player object.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
