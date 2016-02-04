
package Entity;
import framework.Mesh;
import framework.Program;
import framework.math3d.mat4;

public class MeshEntity extends BaseEntity {
    //Creates a variable for the entity's mesh.
    private Mesh mesh;
    
    //Creates a new world matrix for the entity for positon, scale, and rotation purposes.
    private mat4 worldMatrix = mat4.identity();
    
    //Holds a variable for whether or not the entity should be removed from what ever list it is in.
    private boolean delete;
    
    //Holds the rotation variables for the entity.
    private float pr, yr, rr;
    
    public MeshEntity(float x, float y, float z, Mesh mesh_) {
        //Obvious
        super(x, y, z);
        
        //Sets the entity's mesh.
        mesh = mesh_;
    }
    
    //Sets the entity's scale.
    public void setScale(float x) {
        worldMatrix.setScale(x, x, x);
    }
    
    //Sets the rotation variables to what ever is passed.
    public void setRotation(float pr_, float yr_, float rr_) {
        pr = pr_;
        yr = yr_;
        rr = rr_;
    }
    
    //Returns the pitch.
    public float getPr() {
        return pr;
    }
    
    //Returns the yaw.
    public float getYr() {
        return yr;
    }
    
    //Returns the Roll.
    public float getRr() {
        return rr;
    }
    
    //Returns the mesh.
    public Mesh getMesh(){
        return mesh;
    }
     
    public void update() {
        //Updates the entity's position.
        worldMatrix.setPos(getX(), getY(), getZ());
    }
    
    //Returns the world matrix.
    public mat4 getMatrix() {
        return worldMatrix;
    }
    
    //Returns the rotation matrix.
    public mat4 getRotation() {
        return worldMatrix.getRotation(pr, yr, pr);
    }
    
    //Renders the entity.
    public void render(Program prog){ 
        //Sets the wrold matrix to that of the position and the rotation. Needs fixed.
        prog.setUniform("worldMatrix", worldMatrix.getRotation(pr, yr, pr).mul(worldMatrix));
        mesh.draw(prog);
    }

    
}
