
package Entity;

public class Projectile extends MeshEntity {

    
    
    public Projectile(float x, float y, float z, String filename) {
        super(x, y, z, filename);
    }
    
    public void update(float dtime) {
        super.update();
        setZ(getZ() - 10 * dtime);
    }
}
