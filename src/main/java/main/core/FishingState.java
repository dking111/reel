package main.core;

/**
 * Represents the various states of fishing in the game.
 * This enum is used to track the different stages of the fishing process.
 */
public enum FishingState {
    /**
     * Not fishing.
     */
    FALSE,         

    /**
     * The player is in a fishing state but not actively fishing.
     */
    IDLE,          

    /**
     * The player is charging power for casting the fishing line.
     */
    CHARGING,      

    /**
     * The player is casting the fishing line into the water.
     */
    CASTING,       

    /**
     * The player is waiting for a fish to bite the line.
     */
    WAITING,       

    /**
     * The player is reeling in the fishing line after a fish has bitten.
     */
    REELING,       

    /**
     * The player has successfully caught a fish.
     */
    CAUGHT         
}
