
package framework;

import static JGL.JGL.glViewport;


public class ScreenSpace {
    static public void setViewport(int playerCount, int playerID) {
        int screenWidth = GameController.getInstance().getResolution()[0];
        int screenHeight = GameController.getInstance().getResolution()[1];
        if (playerCount == 1) {
            glViewport(0, 0, screenWidth, screenHeight);
        } else if (playerCount == 2) {
            if (playerID == 0) {
                glViewport(screenWidth/2, screenHeight/2, screenWidth/2, screenHeight/2);
            } else if (playerID == 1) {
                glViewport(0, 0, screenWidth/2, screenHeight/2);
            }
        } else if (playerCount == 3) {
            if (playerID == 0) {
                glViewport(0, screenHeight/2, screenWidth/2, screenHeight/2);
            } else if (playerID == 1) {
                glViewport(screenWidth/2, screenHeight/2, screenWidth/2, screenHeight/2);
            } else if (playerID == 2) {
                glViewport(0, 0, screenWidth/2, screenHeight/2);
            }
        } else {
            if (playerID == 0) {
                glViewport(0, screenHeight/2, screenWidth/2, screenHeight/2);
            } else if (playerID == 1) {
                glViewport(screenWidth/2, screenHeight/2, screenWidth/2, screenHeight/2);
            } else if (playerID == 2) {
                glViewport(0, 0, screenWidth/2, screenHeight/2);
            } else if (playerID == 3) {
                glViewport(screenWidth/2, 0, screenWidth/2, screenHeight/2);
            }
        }
    }

}
