
package Entity;
import static JSDL.JSDL.SDL_CONTROLLER_AXIS_LEFTX;
import static JSDL.JSDL.SDL_CONTROLLER_AXIS_LEFTY;
import static JSDL.JSDL.SDL_CONTROLLER_AXIS_RIGHTX;
import static JSDL.JSDL.SDL_CONTROLLER_AXIS_RIGHTY;
import static JSDL.JSDL.SDL_CONTROLLER_AXIS_TRIGGERRIGHT;
import static JSDL.JSDL.SDL_CONTROLLER_BUTTON_DPAD_DOWN;
import static JSDL.JSDL.SDL_CONTROLLER_BUTTON_DPAD_LEFT;
import static JSDL.JSDL.SDL_CONTROLLER_BUTTON_DPAD_RIGHT;
import static JSDL.JSDL.SDL_CONTROLLER_BUTTON_DPAD_UP;
import static JSDL.JSDL.SDL_CONTROLLER_BUTTON_LEFTSHOULDER;
import framework.Mesh;
import framework.Program;
import framework.Camera;
import framework.math3d.vec3;
import static java.lang.Math.abs;
import static JSDL.JSDL.SDL_CONTROLLER_BUTTON_RIGHTSHOULDER;
import static JSDL.JSDL.SDL_CONTROLLER_BUTTON_START;
import static JSDL.JSDL.SDL_GameControllerGetAxis;
import static JSDL.JSDL.SDL_GameControllerGetButton;
import java.util.ArrayList;
import framework.GameController;

public class Player extends MeshEntity {
    //Initializes variables for input information from the player.
    
    private final float speed_multiplier;
    private float speed = 0;
    private final float rotate_speed = 2.0f;
    private vec3 rotateVec = new vec3(0,0,1);
    private long controller = 0;
    
    private ArrayList<Bullet> bullets = new ArrayList();
    
    private float resetCameraTimerDefault = 8;
    private float resetCameraTimer;
    
    private vec3 cameraOffset;
    
    //Creates a camera that the palyer uses.
    private final Camera cam;
    
    //Creates a mesh variable that will hold the mesh of the projectiles that are fired from the player.
    private final Mesh projectileMesh;
    
    float tmpTime = 0;
    
    public Player(float x, float y, float z, String filename, int screenWidth, int screenHeight) {
        //Obvious
        super(x, y, z, new Mesh(filename));
        
        //Creates a new camera for the player to use with the windows width and height as the aspect ratio.
        cam = new Camera(screenWidth/screenHeight);
        
        //Sets the initial position (currently not relevent as it ends up being set to a position around the players mesh.
        cam.setPosition(new vec3(x, y, z + 1.0f));
        
        //Sets the camera to look at a certain location in respect to the mesh.
        cam.lookAt( new vec3(x + 0,y + 0,z + 5), new vec3(x + 0,y + 0,z + 0), new vec3(x + 0,y + 1,z + 0) );
        
        //Sets the projectile mesh.
        projectileMesh = new Mesh("assets/column.obj.mesh");
        
        //Sets the working scale of the ship
        this.setScale(0.01f);
        
        //Sets the speed multiplier.
        this.speed_multiplier = 800.0f;
    }
    
    public void update(float dtime) {
        
        if (SDL_GameControllerGetButton(controller, SDL_CONTROLLER_BUTTON_START) > 0) {
            System.exit(0);
        }
        //rotates the ship left and right
        if (SDL_GameControllerGetButton(controller, SDL_CONTROLLER_BUTTON_LEFTSHOULDER) > 0){
            rotateVec = super.getMatrix().up();
            rotate(rotateVec, -abs(rotate_speed - ((int)speed >> 7))  * dtime);     //bit shifting (dividing by 128)
        }
        else if(SDL_GameControllerGetButton(controller, SDL_CONTROLLER_BUTTON_RIGHTSHOULDER) > 0){
            rotateVec = super.getMatrix().up();
            rotate(rotateVec, abs(rotate_speed - ((int)speed >> 7)) * dtime);       //bit shifting (dividing by 128)
        }
        //rotates the ship up and down
        if (SDL_GameControllerGetAxis(controller, SDL_CONTROLLER_AXIS_LEFTY) != 0.0f){
            rotateVec = super.getMatrix().right();
            rotate(rotateVec, (SDL_GameControllerGetAxis(controller, SDL_CONTROLLER_AXIS_LEFTY)/16000) * dtime);
        }
        if ((SDL_GameControllerGetAxis(controller, SDL_CONTROLLER_AXIS_LEFTX) != 0.0f)) {
            rotateVec = super.getMatrix().backward();
            rotate(rotateVec, (SDL_GameControllerGetAxis(controller, SDL_CONTROLLER_AXIS_LEFTX)/16000) * dtime);
        }
        //propels the ship forward faster
        
        tmpTime += dtime;
        //move_forward = abs((float) sin(tmpTime));
        
        if ((SDL_GameControllerGetAxis(controller, SDL_CONTROLLER_AXIS_TRIGGERRIGHT)/30000) != 0.0f)
            speed = ((SDL_GameControllerGetAxis(controller, SDL_CONTROLLER_AXIS_TRIGGERRIGHT)/30000) * speed_multiplier);
        else
            speed = 50.0f;
        
        
        
        
        //moves the ship forward and sideways based on its relative position/direction
        vec3 leftVec = mPosition.sub(getMatrix().left().mul(0));     //calculates the x direction...
        vec3 forwardVec = leftVec.sub(getMatrix().backward().mul(speed * dtime));    //calculates the z direction...
        super.setPos(forwardVec.x, forwardVec.y, forwardVec.z);                                 //sets that shit
        
        
        //Allows for the camera to be controlled by the player, drifts, so that needs to be fixed.
        cam.axisTurn(cam.getViewMatrix().right(), -200.0f * dtime);
        
        cameraOffset = getMatrix().right().mul(-0.005f * SDL_GameControllerGetAxis(controller, SDL_CONTROLLER_AXIS_RIGHTX))
                .add(getMatrix().up().mul(-0.005f * SDL_GameControllerGetAxis(controller, SDL_CONTROLLER_AXIS_RIGHTY)));
        
        if (SDL_GameControllerGetButton(controller, SDL_CONTROLLER_BUTTON_DPAD_RIGHT) > 0) {
            cameraOffset = getMatrix().right().mul(500);
        }
        if (SDL_GameControllerGetButton(controller, SDL_CONTROLLER_BUTTON_DPAD_LEFT) > 0) {
            cameraOffset = getMatrix().left().mul(500);
        }
        if (SDL_GameControllerGetButton(controller, SDL_CONTROLLER_BUTTON_DPAD_UP) > 0) {
            cameraOffset = getMatrix().down().mul(500);
        }
        if (SDL_GameControllerGetButton(controller, SDL_CONTROLLER_BUTTON_DPAD_DOWN) > 0) {
            cameraOffset = getMatrix().up().mul(500);
        }
        
        System.out.println(cameraOffset);
        
        //Constantly sets the camera position to be relative to the player position.
        vec3 forward = getMatrix().forward();
        vec3 down = getMatrix().down();
        vec3 cpos = mPosition.sub(forward.mul(200.0f)).sub(down.mul(100.0f));
        cam.lookAt(cpos.add(cameraOffset), super.getMatrix().getPos(), super.getMatrix().up());

        for (Bullet b : bullets) {
            b.update(this, dtime, GameController.getInstance().getEntities());
        }
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