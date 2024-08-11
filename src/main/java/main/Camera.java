package main;

import java.lang.Math;

public class Camera {
    private GameData gameData;
    private Player player;
    private int dx, dy;
    private Logic logic;

    public Camera(Logic logic, GameData gameData, Player player) {
        this.logic = logic;
        this.gameData = gameData;
        this.player = player;
        dx = 0;
        dy = 0;
    }

    public void cameraMove() {
        float playerRight = player.getX() + player.getW();
        float playerBottom = player.getY() + player.getH();
        float boundedHeight = gameData.getHeight()*gameData.getCameraBounds();
        float boundedWidth = gameData.getWidth()*gameData.getCameraBounds();
        // Horizontal camera movement
        if (playerRight > boundedWidth) {
            dx = speedAdjusted(-player.getDx());
            player.setX(Math.round(boundedWidth - player.getW()));
        } else if (player.getX() < (gameData.getWidth()-boundedWidth)) {
            dx = speedAdjusted(-player.getDx());
            player.setX(Math.round(gameData.getWidth()-boundedWidth));
        } else {
            dx = 0;
        }

        // Vertical camera movement
        if (playerBottom > boundedHeight) {
            dy = speedAdjusted(-player.getDy());
            player.setY(Math.round(boundedHeight - player.getH()));
        } else if (player.getY() < (gameData.getHeight()-boundedHeight)) {
            dy = speedAdjusted(-player.getDy());
            player.setY(Math.round(gameData.getHeight()-boundedHeight));
        } else {
            dy = 0;
        }
    }

    private int speedAdjusted(int value) {
        return value; // You can add further adjustments or scaling to dx/dy if needed
    }

    public int getDx() { return dx; }
    public void setDx(int dx) { this.dx = dx; }
    public int getDy() { return dy; }
    public void setDy(int dy) { this.dy = dy; }

    //to fix the pass by value issues
    public void setPlayer(Player player){
        this.player = player;
    }
    public void setLogic(Logic logic){
        this.logic = logic;
    }
}
