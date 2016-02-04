
package Entity;

import framework.Mesh;
import framework.math3d.mat4;
import framework.math3d.vec3;

public class Projectile extends MeshEntity {
    //Creates a variable to hold how much longer the Entity will be active.
    int timeToDelete;
    
    public Projectile(float x, float y, float z, Mesh mesh_, vec3 rot) {
        //Runs the MeshEntity initialization.
        super(x, y, z, mesh_);
        super.setRotation(rot.x, rot.y, rot.z);
        
        //Sets the scale of the projectile.
        super.setScale((float) 0.2);
        
        //Sets an initial period of time the projectile will stay active.
        timeToDelete = 10000;
    }
    
    public void update(float dtime) {
        //Uses MeshEntity's update, which keeps the position correct.
        super.update();
        
        //Sets the position to move forward every time this is ran.
        setPos(getRotation().forward().mul(dtime));
        
        //Keeps track of how long this should stay active.
        timeToDelete -= dtime;
    }
}
