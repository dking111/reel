package main;

import java.util.List;

public class Logic {
    private Player player;
    private GameData gameData;

    public Logic(Player player, GameData gameData) {
        // Initialize the player
        this.player = player;
        this.gameData = gameData;
        
    }


    public void collisionDetection(Player player, List<Sprite> sprites) {
        for (Sprite sprite : sprites) {
            if (isColliding(player, sprite)) {
                //could cause issues!!!
                handleCollision(player, sprite,0);
            }
        }
    }

    public boolean isColliding(Player player, Sprite sprite) {
        return player.getX() < sprite.getX() + sprite.getW() &&
               player.getX() + player.getW() > sprite.getX() &&
               player.getY() < sprite.getY() + sprite.getH() &&
               player.getY() + player.getH() > sprite.getY();
    }

    private void  handleCollision(Player player, Sprite sprite,int clearance) {
        // Assuming you have player velocity or direction to handle precise collisions
        // Adjust player's position to avoid overlap
        int overlapX = Math.min(player.getX() + player.getW() - sprite.getX(), sprite.getX() + sprite.getW() - player.getX());
        int overlapY = Math.min(player.getY() + player.getH() - sprite.getY(), sprite.getY() + sprite.getH() - player.getY());

        if (overlapX < overlapY) {
            if (player.getX() < sprite.getX()) {
                player.setX(sprite.getX() - (player.getW()+clearance)); // Move player to the left of the sprite
            } else {
                player.setX(sprite.getX() + (sprite.getW()+clearance)); // Move player to the right of the sprite
            }
        } else {
            if (player.getY() < sprite.getY()) {
                player.setY(sprite.getY() - (player.getH()+clearance)); // Move player above the sprite
            } else {
                player.setY(sprite.getY() + (sprite.getH()+clearance)); // Move player below the sprite
            }
        }
    }


}