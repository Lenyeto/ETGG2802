
package Entity;

import framework.Mesh;

public class Projectile extends MeshEntity {

    int timeToDelete;
    
    
    
    public Projectile(float x, float y, float z, Mesh mesh_) {
        super(x, y, z, mesh_);
        super.setScale((float) 0.2);
        timeToDelete = 100;
    }
    
    public void update(float dtime) {
        super.update();
        
        setPos(getMatrix().forward().mul(dtime));
        
        timeToDelete -= dtime;
        
        
    }
}
