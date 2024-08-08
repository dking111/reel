package main;

import java.util.List;

public class Logic {
    private Player player;
    public GameData gameData;

    public Logic() {
        // Initialize the player
        player = new Player(100, 100, 50, 50);
        gameData = new GameData("src/main/resources/levels/1.json");
    }

    public Player getPlayer() {
        return player;
    }

    public GameData getGameData() {
        return gameData;
    }

    public void collisionDetection(Player player, List<Sprite> sprites) {
        for (Sprite sprite : sprites) {
            if (isColliding(player, sprite)) {
                handleCollision(player, sprite,0);
            }
        }
    }

    private boolean isColliding(Player player, Sprite sprite) {
        return player.getX() < sprite.getX() + sprite.getW() &&
               player.getX() + player.getW() > sprite.getX() &&
               player.getY() < sprite.getY() + sprite.getH() &&
               player.getY() + player.getH() > sprite.getY();
    }

    private void handleCollision(Player player, Sprite sprite,int clearance) {
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

    public boolean cameraMove(){
        boolean returnval;
        for (Sprite bound : gameData.getCameraBounds()) {
            if (isColliding(player, bound)) {
                returnval = true;
                handleCollision(player, bound,0);
            }
        }
        returnval = false;
        return returnval;
    }
}