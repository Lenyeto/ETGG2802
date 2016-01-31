/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;
import JSDL.JSDL.*;
import static JSDL.JSDL.SDL_KEYDOWN;
import java.util.Set;
import framework.Mesh;
import framework.Program;
import static JGL.JGL.*;
import framework.math3d.mat4;


/**
 *
 * @author buellw
 */
public class Player extends MeshEntity {
    
    private int key_forward;
    private int key_backward;
    private int key_left;
    private int key_right;
    private int key_shoot;
    
    public Player(int x, int y, int z, String filename, int key_foward, int key_backward, int key_strafe_left, int key_strafe_right) {
        super(x, y, z, filename);
        
    }
    
    public void update(SDL_Event ev, Set<Integer> keys, float dtime) {
        if (ev.type == SDL_KEYDOWN) {
            if (keys.contains(key_forward)) {
                setX(getX() + dtime);
            }
            if (keys.contains(key_backward)) {
                setX(getX() - dtime);
            }
            if (keys.contains(key_left)) {
                setX(getY() + dtime);
            }
            if (keys.contains(key_right)) {
                setX(getY() - dtime);
                
            }
        }
    }
    
    
}

/*
def draw(self,prog):


        prog.setUniform("worldMatrix",self.matrix)
        prog.setUniform("viewMatrix",self.viewIdentity)
        prog.setUniform("projMatrix",self.viewIdentity)
        prog.setUniform("alpha",1)
        self.M.draw(prog)

*/