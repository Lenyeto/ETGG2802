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
    //Initializes variables for input information from the player.
    private int key_forward;
    private int key_backward;
    private int key_left;
    private int key_right;
    private int key_shoot;
    private int key_look_right;
    private int key_look_left;
    private int key_look_up;
    private int key_look_down;
    
    //Creates a camera that the palyer uses.
    private Camera cam;
    
    //Creates a mesh variable that will hold the mesh of the projectiles that are fired from the player.
    private Mesh projectileMesh;
    
    //Creates a list to hold all the projectiles for the player.
    List<Projectile> projectiles;
    
    public Player(float x, float y, float z, String filename, int forward, int backward, int key_strafe_left, int key_strafe_right, int shoot, int lookRight, int lookLeft, int lookUp, int lookDown, float screenWidth, float screenHeight) {
        //Obvious
        super(x, y, z, new Mesh(filename));
        
        //Creates a new camera for the player to use with the windows width and height as the aspect ratio.
        cam = new Camera(screenWidth/screenHeight);
        
        //Sets the initial position (currently not relevent as it ends up being set to a position around the players mesh.
        cam.setPosition(new vec3(x, y, z));
        
        //Sets the camera to look at a certain location in respect to the mesh.
        cam.lookAt( new vec3(x + 0,y + 0,z + 5), new vec3(x + 0,y + 0,z + 0), new vec3(x + 0,y + 1,z + 0) );
        
        //Sets the input variables to what are passed in the creation of the player.
        this.key_forward = forward;
        this.key_backward = backward;
        this.key_left = key_strafe_left;
        this.key_right = key_strafe_right;
        this.key_shoot = shoot;
        this.key_look_down = lookDown;
        this.key_look_up = lookUp;
        this.key_look_right = lookRight;
        this.key_look_left = lookLeft;
        
        //Creates a new empty list for the projectiles.
        projectiles = new LinkedList<>();
        
        //Sets the projectile mesh.
        projectileMesh = new Mesh("assets/column.obj.mesh");
    }
    
    public void update(SDL_Event ev, float xrel, float yrel, Set<Integer> keys, float dtime) {
        super.update();
        
        //Checks what the player is pressing and applies the appropriate action / movement.
        if (keys.contains(key_forward)) {
            setPos(super.getMatrix().getPos().add(super.getMatrix().forward(super.getRotation()).mul(dtime)));
        }
        if (keys.contains(key_backward)) {
            setPos(super.getMatrix().getPos().add(super.getMatrix().backward(super.getRotation()).mul(dtime)));
        }
        if (keys.contains(key_left)) {
            setPos(super.getMatrix().getPos().add(super.getMatrix().left(super.getRotation()).mul(dtime)));
        }
        if (keys.contains(key_right)) {
            setPos(super.getMatrix().getPos().add(super.getMatrix().right(super.getRotation()).mul(dtime)));
        }
        if (keys.contains(key_shoot)) {
            setPos(super.getMatrix().getPos().add(super.getMatrix().up(super.getRotation()).mul(dtime)));
        }
        
        //Sets the rotation to -50 degrees pitch, for testing purposes.
        if (keys.contains(key_look_up)) {
            super.setRotation(-50, 0, 0);
        }
        
        //Sets the rotation to 50 degrees pitch, for testin purposes.
        if (keys.contains(key_look_down)) {
            super.setRotation(50, 0, 0);
        }
        
        //Turns the player to the left.
        if (keys.contains(key_look_left)) {
            
            cam.turn((float) (5*dtime*0.5));
        }
        
        //Turns the player to the right.
        if (keys.contains(key_look_right)) {
            
            cam.turn((float) (-5*dtime*0.5));
        }
        
        //Allows for the camera to be controlled by the player, drifts, so that needs to be fixed.
        cam.axisTurn(cam.getViewMatrix().right(), -yrel * dtime);
        cam.axisTurn(super.getMatrix().up(super.getRotation()), -xrel * dtime);
        
        //Updates each of the projectiles in the projectiles list.
        for (Projectile p : projectiles) {
            p.update(dtime);
            if (p.timeToDelete <= 0) {
                projectiles.remove(p);
                break;
            }
        }
        
        //Constantly sets the camera position to be relative to the player position.
        cam.setPosition(super.getMatrix().getPos().add(super.getMatrix().backward(super.getRotation())));
    }

    //Returns the camera of the player.
    public Camera getCam() {
        return cam;
    }
    
    //Renders the player and the projectiles.
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