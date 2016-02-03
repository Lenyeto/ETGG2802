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
    private int key_look_right;
    private int key_look_left;
    private int key_look_up;
    private int key_look_down;
    private Camera cam;
    private Mesh projectileMesh;
    List<Projectile> projectiles;
    
    public Player(float x, float y, float z, String filename, int forward, int backward, int key_strafe_left, int key_strafe_right, int shoot, int lookRight, int lookLeft, int lookUp, int lookDown, float screenWidth, float screenHeight) {
        super(x, y, z, new Mesh(filename));
        cam = new Camera(screenWidth/screenHeight);
        cam.setPosition(new vec3(x, y, z));
        cam.lookAt( new vec3(x + 0,y + 0,z + 5), new vec3(x + 0,y + 0,z + 0), new vec3(x + 0,y + 1,z + 0) );
        this.key_forward = forward;
        this.key_backward = backward;
        this.key_left = key_strafe_left;
        this.key_right = key_strafe_right;
        this.key_shoot = shoot;
        this.key_look_down = lookDown;
        this.key_look_up = lookUp;
        this.key_look_right = lookRight;
        this.key_look_left = lookLeft;
        projectiles = new LinkedList<>();
        projectileMesh = new Mesh("assets/column.obj.mesh");
    }
    
    public void update(SDL_Event ev, Set<Integer> keys, float dtime) {
        super.update();
        if (keys.contains(key_forward)) {
            //setY(getY() + 2 * dtime);
//            vec3 forward = super.getMatrix().forward();
//            setX(getX() + forward.x * dtime);
//            setY(getY() + forward.y * dtime);
//            setZ(getZ() + forward.z * dtime);
            setPos(super.getMatrix().getPos().add(super.getMatrix().forward().mul(dtime)));
        }
        if (keys.contains(key_backward)) {
            setPos(super.getMatrix().getPos().add(super.getMatrix().backward().mul(dtime)));
        }
        if (keys.contains(key_left)) {
            setPos(super.getMatrix().getPos().add(super.getMatrix().left().mul(dtime)));
        }
        if (keys.contains(key_right)) {
            setPos(super.getMatrix().getPos().add(super.getMatrix().right().mul(dtime)));
        }
        if (keys.contains(key_shoot)) {
            projectiles.add(new Projectile(getX(), getY(), getZ(), projectileMesh));
            //super.getMatrix().rotate(0, dtime, 0);
            //setPos(super.getMatrix().getPos().add(super.getMatrix().up().mul(dtime)));
            //super.getMatrix().setRotate(0, 10, 0);
        }
        
        if (keys.contains(key_look_up)) {
            //super.getMatrix().setRotate(0, 20, 0);
        }
        if (keys.contains(key_look_down)) {
            
        }
        if (keys.contains(key_look_left)) {
            
        }
        if (keys.contains(key_look_right)) {
            
        }
        
        for (Projectile p : projectiles) {
            p.update(dtime);
            if (p.timeToDelete <= 0) {
                projectiles.remove(p);
                break;
            }
        }
        
        ///SOMEBODY FIX THIS
        
        
        //Updates the position of the Camera
//        vec3 tmpBack = super.getMatrix().backward().mul(10);
//        vec3 tmpRight = super.getMatrix().right().mul(4);
//        vec3 tmpUp = super.getMatrix().up().mul(2);
//        System.out.println("Forward" + super.getMatrix().forward());
//        System.out.println("Right" + super.getMatrix().right());
//        System.out.println("Up" + super.getMatrix().up());
        //cam.setPosition(super.getMatrix().getPos().add(super.getMatrix().backward().mul(10)).add(super.getMatrix().right().mul(4)).add(super.getMatrix().up().mul(5)));
        cam.setPosition(super.getMatrix().getPos().add(super.getMatrix().backward().mul(10)).add(super.getMatrix().up().mul(5)));
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