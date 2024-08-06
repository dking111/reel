package game;

public class Logic {
    private Player player;
    private GameData gameData;


    public Logic(){
        // Initialize the player
        player = new Player(50, 50);
        gameData = new GameData();
    }

    public Player getPlayer(){
        return player;
    }

    public GameData getGameData(){
        return gameData;
    }
}
