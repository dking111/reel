package main.core;

public enum FishingState {
    IDLE,         // Not fishing or finished fishing
    CHARGING,     // Charging power for casting
    CASTING,      // Casting the fishing line
    WAITING,      // Waiting for a fish to bite
    REELING,      // Reeling in the fishing line
    CAUGHT        // Fish has been caught
}