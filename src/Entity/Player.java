/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;
import JSDL.JSDL.*;
import static JSDL.JSDL.SDL_KEYDOWN;
import java.util.Set;

/**
 *
 * @author buellw
 */
public class Player extends MeshEntity {
    
    
    public Player(int x, int y, int z, String filename) {
        super(x, y, z, filename);
    }
    
    public void update(SDL_Event ev, Set<Integer> keys, int key_foward, int key_backward, int key_strafe_left, int key_strafe_right) {
        if (ev.type == SDL_KEYDOWN) {
            if (keys.contains(key_foward)) {
                //Create a .forward function to get the forward vector to have the position of this moved in the foward vector.
            }
            if (keys.contains(key_backward)) {
                
            }
            if (keys.contains(key_strafe_left)) {
                
            }
            if (keys.contains(key_strafe_right)) {
                //Create a .right function to get the right vector to have the position of this moved in the right vector.
                
            }
        }
    }
}
