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
import framework.Camera;
import framework.math3d.mat4;
import framework.math3d.vec3;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;


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
    private Camera cam;
    List<Projectile> projectiles;
    
    public Player(float x, float y, float z, String filename, int forward, int backward, int key_strafe_left, int key_strafe_right, int shoot, float screenWidth, float screenHeight) {
        super(x, y, z, filename);
        cam = new Camera(screenWidth/screenHeight);
        cam.setPosition(new vec3(x, y, z));
        cam.lookAt( new vec3(x + 0,y + 0,z + 5), new vec3(x + 0,y + 0,z + 0), new vec3(x + 0,y + 1,z + 0) );
        this.key_forward = forward;
        this.key_backward = backward;
        this.key_left = key_strafe_left;
        this.key_right = key_strafe_right;
        this.key_shoot = shoot;
        projectiles = new LinkedList<>();
    }
    
    public void update(SDL_Event ev, Set<Integer> keys, float dtime) {
        super.update();
        if (keys.contains(key_forward)) {
            setY(getY() + 2 * dtime);
        }
        if (keys.contains(key_backward)) {
            setY(getY() - 2 * dtime);
        }
        if (keys.contains(key_left)) {
            setX(getX() - 2 * dtime);
        }
        if (keys.contains(key_right)) {
            setX(getX() + 2 * dtime);
        }
        if (keys.contains(key_shoot)) {
            projectiles.add(new Projectile(getX(), getY(), getZ(), "assets/column.obj.mesh"));
        }
        
        for (Projectile p : projectiles) {
            p.update(dtime);
        }
        //Updates the position of the Camera
        
        
    }

    public Camera getCam() {
        return cam;
    }
    
    public void render(Program prog) {
        super.render(prog);
        for (Projectile p : projectiles) {
            p.render(prog);
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