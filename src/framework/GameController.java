
package framework;

import Entity.Player;

public class GameController {
    private final static GameController instance = new GameController();
    private int controllerCount;
    private Player[] players;
    private int screenWidth = 512;
    private int screenHeight = 512;
    
    
    private GameController() {
        
    }
    
    public static GameController getInstance() {
        return instance;
    }
    
    public void setPlayerCount(int count) {
        controllerCount = count;
        Input.getInstance().setCount(count);
        players = new Player[count];
    }
    
    public void init() {
        
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(i * 10, 0, 0, "assets/tie_fighter/Creature.obj.mesh", screenWidth, screenHeight);
            players[i].setController(Input.getInstance().getControllers()[i]);
        }
    }
    
    public void setPlayerPosition(int playerNum, int x, int y, int z) {
        if (playerNum >= 0 && playerNum < players.length) {
            players[playerNum].setPos(x, y, z);
        }
    }
    
    public void setResolution(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }
    
    public int getControllerCount() {
        return controllerCount;
    }
    
    public Player[] getPlayers() {
        return players;
    }
    
    public int[] getResolution() {
        int[] tmp;
        tmp = new int[2];
        tmp[0] = screenWidth;
        tmp[1] = screenHeight;
        return tmp;
    }
}
