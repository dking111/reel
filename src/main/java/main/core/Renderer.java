package main.core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import main.GameObjects.Background;
import main.GameObjects.Door;
import main.GUI.Button;
import main.GameObjects.Player;
import main.GameObjects.Shelf;
import main.GameObjects.Sprite;
import main.GUI.Text;
import main.GameObjects.FishingLine;
import main.GameObjects.FishingSpot;
import main.GameObjects.Light;
import main.GameObjects.Fish;
import main.GameObjects.TimeOfDayTint;
import java.util.List;

public class Renderer {

    public void draw(Graphics2D g, Background background, Button button, FishingLine fishingLine,
                     List<Light> lights, Player player, TimeOfDayTint dayLight,
                     List<Text> texts, Fish fish, float timeOfDay) {
        background.draw(g);
        
        if (button != null) {
            button.draw(g);
        }

        if (fishingLine != null) {
            fishingLine.draw(g);
        }

        if (lights != null) {
            for (Light light : lights) {
                light.draw(g);
            }
        }

        player.draw(g);
        dayLight.draw(g,timeOfDay);

        for (Text text : texts) {
            text.draw(g);
        }

        if (fish != null) {
            fish.draw(g);
        }


    }

    public void drawDebug(Graphics2D g2d, GameData gameData) {
        // Implement any debug drawing logic here
        if (gameData.getWater() != null) {
            gameData.getWater().draw(g2d);
        }

        if (gameData.getShelves() != null) {
            for (Shelf shelf : gameData.getShelves()) {
                shelf.draw(g2d);
            }
        }

        for (Door door : gameData.getDoors()) {
            door.draw(g2d);
        }

        for (Sprite sprite : gameData.getSprites()) {
            sprite.draw(g2d);
        }

        if (gameData.getFishingSpots() != null) {
            for (FishingSpot fishingSpot : gameData.getFishingSpots()) {
                fishingSpot.draw(g2d);
            }
        }
    }
}
