


package framework;

import static JSDL.JSDL.SDL_GameControllerOpen;
import static JSDL.JSDL.SDL_INIT_GAMECONTROLLER;
import static JSDL.JSDL.SDL_Init;
import static JSDL.JSDL.SDL_IsGameController;
import static JSDL.JSDL.SDL_NumJoysticks;

public class Input {
    private long controllers[];
    private final static Input instance = new Input();
    
    private Input() {
        SDL_Init(SDL_INIT_GAMECONTROLLER);
    }
    
    public static Input getInstance() {
        return instance;
    }
    
    public long[] getControllers() {
        return controllers;
    }
    
    public void setCount(int controllerCount) {
        long controller;
        controllers = new long[controllerCount];
        
        for (long j : controllers) {
            for (int i = 0; i < SDL_NumJoysticks(); ++i) {
                if (SDL_IsGameController(i) > 0) {
                    controller = SDL_GameControllerOpen(i);
                    if (controller > 0) {
                        for (int k = 0; k < controllers.length; k++) {
                            if (controllers[k] == 0) {
                                controllers[k] = controller;
                                break;
                            }
                        }
                    } else {
                        System.out.println("Coult not open game controller");
                    }
                }
            }   
        }
        
    }
}
