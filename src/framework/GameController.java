
package framework;

import Entity.MeshEntity;
import Entity.Player;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class GameController {
    private final static GameController instance = new GameController();

    private boolean isOnline = false;
    private boolean isClient = true;
    private String serverAddress;
    private int port;
    
    private int controllerCount;
    private Player[] players;
    private int screenWidth = 512;
    private int screenHeight = 512;
    private ArrayList<MeshEntity> entities;
    private static final int gridSize = 10;
    
    
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
        for (MeshEntity mesh : players) {
            addEntity(mesh);
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
    
    public ArrayList<MeshEntity> getEntities() {
        return entities;
    }
    
    public void addEntity(MeshEntity entity) {
        //entities.add(entity);
    }
    
    public int getGridSize() {
        return gridSize;
    }
    
    public void setServerInfo(String s) {
        isOnline = true;
        
        String[] tmp = s.split(":");
        serverAddress = tmp[0];
        port = Integer.parseInt(tmp[1]);
    }
    
    public void setToServer() {
        isOnline = true;
        isClient = false;
    }
    
    public String getServerAddress() {
        return serverAddress;
    }
    
    public int getPort() {
        return port;
    }
    
}
