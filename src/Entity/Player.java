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
    
    Mesh m;
    public Player(int x, int y, int z, String filename) {
        super(x, y, z, filename);
        m = getMesh();
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
    public void render(Program prog){ 
        prog.setUniform("worldMatrix", mat4.identity());
        prog.setUniform("viewMatrix", mat4.identity());
        prog.setUniform("projMatrix", mat4.identity());
        m.draw(prog);
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