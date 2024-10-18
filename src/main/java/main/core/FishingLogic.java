package main.core;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import main.GUI.TextTemp;
import main.GUI.Text;
import main.GameObjects.ChargeMeter;
import main.GameObjects.Fish;
import main.GameObjects.FishingLine;
import main.GameObjects.Player;
import main.GameObjects.Water;

/**
 * Manages the fishing logic, including charging, casting, waiting, reeling, and catching fish.
 * Handles interactions between the player, fishing line, and fish.
 */
public class FishingLogic {

    private FishingLine fishingLine;
    private Fish fish;
    private float fishWaitTimer;
    private FishingState fishingState;
    private Random random;
    private float fishWeightMultiplier;

    /**
     * Initializes the FishingLogic with a new random number generator.
     */
    public FishingLogic() {
        random = new Random();
    }

    /**
     * Updates the fishing logic based on the player's input and current fishing state.
     * Handles all stages of fishing: charging, casting, waiting, reeling, and catching.
     * 
     * @param isMouseHeld    Whether the mouse is held down.
     * @param isLeftHeld     Whether the left mouse button is held.
     * @param isRightHeld    Whether the right mouse button is held.
     * @param chargeMeter    The charge meter for casting.
     * @param player         The player object.
     * @param chargePower    The power of the cast.
     * @param gameData       The game data containing the environment and fishing spots.
     * @param possibleFishes The list of possible fish to catch.
     * @param texts          The list of texts to display.
     * @param fishingState   The current fishing state.
     * @return The updated fishing state.
     */
    public FishingState update(boolean isMouseHeld, boolean isLeftHeld, boolean isRightHeld, ChargeMeter chargeMeter, Player player, float chargePower, GameData gameData, List<Fish> possibleFishes, List<Text> texts, FishingState fishingState, Water water) {
        this.fishingState = fishingState;
        if (fishingState != FishingState.FALSE) {
            switch (fishingState) {
                case CHARGING:
                    handleCharging(isMouseHeld, chargeMeter);
                    break;
                case CASTING:
                    handleCasting(player, chargePower,water);
                    break;
                case WAITING:
                    handleWaiting(gameData, possibleFishes);
                    break;
                case REELING:
                    handleReeling(isLeftHeld, isRightHeld, isMouseHeld, player);
                    break;
                case CAUGHT:
                    handleCaught(player, texts);
                    break;
                case IDLE:
                    handleIdle(player);
                    break;
                default:
                    break;
            }
        }
        return this.fishingState;
    }

    /**
     * Handles the charging phase of fishing.
     * In this phase, the player holds the mouse to increase the charge for casting.
     * 
     * @param isMouseHeld   Whether the mouse is being held down.
     * @param chargeMeter   The charge meter used to increase the charge for casting.
     */
    private void handleCharging(boolean isMouseHeld, ChargeMeter chargeMeter) {
        if (isMouseHeld && chargeMeter != null) {
            chargeMeter.increaseCharge();
        }
    }

    /**
     * Handles the casting phase of fishing.
     * The player releases the mouse after charging, casting the fishing line into the water.
     * 
     * @param player        The player object.
     * @param chargePower   The power of the cast.
     * @param water         The water object.
     */
    private void handleCasting(Player player, float chargePower,Water water) {
        if (!player.getState().equals("casting")) {
            player.setState("casting");
            player.refreshAnimation();
            player.setAnimationSpeed(10);
        }

        if (player.getIsAnimationComplete()) {
            fishingLine = new FishingLine((player.getX() + player.getW() / 2), (player.getY()+player.getH()) - Math.round(chargePower * water.getH()), 15, 15, (player.getX() + player.getW() / 2), (player.getY() + (player.getH() - player.getH() / 4)));
            fishWaitTimer = (1 - chargePower) + 1;
            fishWeightMultiplier = 1+ chargePower/2;
            chargePower = 0;
            fishingState = FishingState.WAITING;
            player.setState("idle_fishing");
            player.refreshAnimation();
            player.setAnimationSpeed(5);
            
        }
    }

    /**
     * Handles the waiting phase of fishing.
     * In this phase, the player waits for a fish to bite the hook.
     * 
     * @param gameData       The game data containing the environment and fishing spots.
     * @param possibleFishes The list of possible fishes that can be caught.
     */
    private void handleWaiting(GameData gameData, List<Fish> possibleFishes) {
        fishWaitTimer -= 0.01;

        if (fishWaitTimer <= 0) {
            fishWaitTimer = 0;

            if (fish == null) {
                while (fish == null) {
                    for (Fish possibleFish : possibleFishes) {
                        if (random.nextInt(10) < possibleFish.getRarity()) {
                            fish = possibleFish;
                            break;
                        }
                    }
                }
                fish.spawn(gameData.getWater(), fishingLine.getFishingLineFloat().getX(), fishingLine.getFishingLineFloat().getY());
            }

            if (fish.getHooked()) {
                if (fish.getIsAnimationComplete()) {
                    fishingState = FishingState.REELING;
                    fish.setIsVisible(false);
                }
            } else {
                fish.update(fishingLine.getFishingLineFloat().getX(), fishingLine.getFishingLineFloat().getY());
            }
        }
    }

    /**
     * Handles the reeling phase of fishing.
     * The player reels in the line by holding the left or right mouse button.
     * 
     * @param isLeftHeld     Whether the left mouse button is held.
     * @param isRightHeld    Whether the right mouse button is held.
     * @param isMouseHeld    Whether the mouse is held down.
     * @param player         The player object.
     */
    private void handleReeling(boolean isLeftHeld, boolean isRightHeld, boolean isMouseHeld, Player player) {
        fishingLine.update(isLeftHeld, isRightHeld, isMouseHeld);

        if (isLeftHeld && !isRightHeld) {
            player.setState("reelingLeft");
        } else if (isRightHeld && !isLeftHeld) {
            player.setState("reelingRight");
        } else {
            player.setState("reeling");
        }

        if (fishingLine.getIsGameOver()) {
            fish = null;
            fishingLine = null;
            fishingState = FishingState.IDLE;
        }
    }

    /**
     * Handles the catching phase of fishing.
     * In this phase, the player catches the fish and updates the game state.
     * 
     * @param player  The player object.
     * @param texts   The list of texts to display information about the fish.
     */
    private void handleCaught(Player player, List<Text> texts)  {
        fishingLine = null;

        if (!player.getState().equals("catching")) {
            player.setState("catching");
            player.refreshAnimation();
            player.setAnimationSpeed(10);
        }

        if (player.getIsAnimationComplete()) {
            fishingState = FishingState.IDLE;
            player.refreshAnimation();
            player.setAnimationSpeed(5);
            
            
            fish.setWeight(Math.round(fish.getWeight()*fishWeightMultiplier));
            Database.setFishWeight(fish);
            texts.add(new TextTemp(1920 / 4, player.getY() - 200, 100, fish.getName(), Color.white, 100));
            texts.add(new TextTemp(1920 / 3, player.getY() - 100, 100, String.valueOf(fish.getWeight()) + "lb", Color.white, 100));
            fish = null;
        }
    }

    /**
     * Handles the idle phase of fishing when no activity is taking place.
     * 
     * @param player  The player object.
     */
    private void handleIdle(Player player) {
        player.setState("idle_back");
    }

    /**
     * Gets the current fishing line.
     * 
     * @return The current fishing line.
     */
    public FishingLine getFishingLine() {
        return fishingLine;
    }

    /**
     * Sets the current fishing line.
     * 
     * @param fishingLine The fishing line to set.
     */
    public void setFishingLine(FishingLine fishingLine) {
        this.fishingLine = fishingLine;
    }

    /**
     * Gets the current fish being caught.
     * 
     * @return The current fish.
     */
    public Fish getFish() {
        return fish;
    }

    /**
     * Sets the current fish being caught.
     * 
     * @param fish The fish to set.
     */
    public void setFish(Fish fish) {
        this.fish = fish;
    }
}
