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
import static java.lang.Math.abs;
import static java.lang.Math.sin;
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
    private float speed_multiplier;
    private float speed = 0;
    private float rotate_speed = 2.0f;
    private vec3 rotateVec = new vec3(0,0,1);
    private long controller = 0;
    
    //Creates a camera that the palyer uses.
    private Camera cam;
    
    //Creates a mesh variable that will hold the mesh of the projectiles that are fired from the player.
    private Mesh projectileMesh;
    
    float tmpTime = 0;
    
    public Player(float x, float y, float z, String filename, int forward, int backward, int key_strafe_left, int key_strafe_right, int shoot, int lookRight, int lookLeft, int lookUp, int lookDown, float screenWidth, float screenHeight) {
        //Obvious
        super(x, y, z, new Mesh(filename));
        
        //Creates a new camera for the player to use with the windows width and height as the aspect ratio.
        cam = new Camera(screenWidth/screenHeight);
        
        //Sets the initial position (currently not relevent as it ends up being set to a position around the players mesh.
        cam.setPosition(new vec3(x, y, z + 1.0f));
        
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
        
        
        //Sets the projectile mesh.
        projectileMesh = new Mesh("assets/column.obj.mesh");
        
        //Sets the speed multiplier.
        this.speed_multiplier = 800.0f;
    }
                     //right-trigger       left-stick up/down    left-stick left/right   obvious       again, obvious
    public void update(float move_forward, float rotate_forward, float move_sideways, int LBumper, int RBumper, float dtime) {
        //rotates the ship left and right
        if (RBumper > 0){
            rotateVec = super.getMatrix().up();
            rotate(rotateVec, -abs(rotate_speed - ((int)speed >> 7))  * dtime);     //bit shifting (dividing by 128)
        }
        else if(LBumper > 0){
            rotateVec = super.getMatrix().up();
            rotate(rotateVec, abs(rotate_speed - ((int)speed >> 7)) * dtime);       //bit shifting (dividing by 128)
        }
        //rotates the ship up and down
        if (rotate_forward != 0.0f){
            rotateVec = super.getMatrix().right();
            rotate(rotateVec, rotate_forward * abs(rotate_speed - ((int)speed >> 7)) * dtime);
        }
        //propels the ship forward faster
        
        tmpTime += dtime;
        //move_forward = abs((float) sin(tmpTime));
        
        if (move_forward != 0.0f)
            speed = (move_forward * speed_multiplier);
        else
            speed = 50.0f;
        //slightly rotates the ship and moves it sideways
        if (move_sideways != 0.0f){
            rotateVec = getMatrix().forward();
            rotate(rotateVec, move_sideways * abs(rotate_speed - ((int)speed >> 7)) * dtime);
        }
        //moves the ship forward and sideways based on its relative position/direction
        vec3 leftVec = mPosition.sub(getMatrix().left().mul(move_sideways * 2.0f * dtime));     //calculates the x direction...
        vec3 forwardVec = leftVec.sub(getMatrix().backward().mul(speed * dtime));    //calculates the z direction...
        super.setPos(forwardVec.x, forwardVec.y, forwardVec.z);                                 //sets that shit
        
        
        //Allows for the camera to be controlled by the player, drifts, so that needs to be fixed.
            cam.axisTurn(cam.getViewMatrix().right(), -200.0f * dtime);
            //cam.axisTurn(super.getMatrix().up(super.getRotation()), -xrel * dtime);
        
        
        //Constantly sets the camera position to be relative to the player position.
        vec3 dank = getMatrix().forward();
        vec3 memes = getMatrix().down();
        vec3 cpos = mPosition.sub(dank.mul(200.0f)).sub(memes.mul(100.0f));
        cam.lookAt(cpos, super.getMatrix().getPos(), super.getMatrix().up());

        
    }

    //Returns the camera of the player.
    public Camera getCam() {
        return cam;
    }
    
    //Renders the player and the projectiles.
    public void render(Program prog) {
        super.render(prog);
    }
    
    public void setController(long i) {
        controller = i;
    }
    
    public long getController() {
        return controller;
    }
    
    public float getSpeed(){
        return this.speed;
    }
}