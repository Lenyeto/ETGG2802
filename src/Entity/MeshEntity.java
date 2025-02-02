
package Entity;
import framework.Mesh;
import framework.Program;
import framework.math3d.mat4;
import framework.math3d.math3d;
import framework.math3d.vec3;
import framework.GameController;

public class MeshEntity{
    //Creates a variable for the entity's mesh.
    public Mesh mesh;
    
    //Creates a new world matrix for the entity for positon, scale, and rotation purposes.
    private mat4 worldMatrix = mat4.identity();
    
    //Holds a variable for whether or not the entity should be removed from what ever list it is in.
    private boolean delete;
    
    //Holds the rotation variables for the entity.
    private float pr, yr, rr;
    
    public vec3 mPosition = new vec3();
    
    //1 is Sphere
    //2 is Cube
    private int collisionType;
    private float collisionSize;
    
    
    public MeshEntity(float x, float y, float z, Mesh mesh_) {
        //Obvious
        
        //Sets the entity's mesh.
        mesh = mesh_;
        setPos(x, y, z);
    }
    
    //Sets the entity's scale.
    public void setScale(float x) {
        worldMatrix = math3d.mul(math3d.scaling(new vec3(x,x,x)), worldMatrix);
    }
    

    //rotates that mother fucker
    public void rotate(vec3 axis, float angle){
        mat4 m = math3d.axisRotation(axis, angle);
        worldMatrix = worldMatrix.mul(m);
    }
    
    //puts the mother fucker in its place
    public void setPos(float x, float y, float z)
    {
        worldMatrix.setPos(x, y, z);
        mPosition = worldMatrix.getPos();
    }
    //Returns the mesh.
    public Mesh getMesh(){
        return mesh;
    }
     
    public void update() {
        //Updates the entity's position.
        //worldMatrix.setPos(mPosition.x, mPosition.y, mPosition.z);
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

    public boolean collides(MeshEntity mesh) {
        vec3 zone1 = this.mPosition.mul(1/GameController.getInstance().getGridSize()).floor();
        vec3 zone2 = mesh.mPosition.mul(1/GameController.getInstance().getGridSize()).floor();
        
        if (zone1 == zone2) {
            return true;
        }
        
        return false;
    }
}
