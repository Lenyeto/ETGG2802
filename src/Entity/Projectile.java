
package Entity;

public class Projectile extends MeshEntity {

    int timeToDelete;
    
    public Projectile(float x, float y, float z, String filename) {
        super(x, y, z, filename);
        super.setScale((float) 0.2);
        timeToDelete = 10;
    }
    
    public void update(float dtime) {
        super.update();
        setZ(getZ() - 10 * dtime);
        timeToDelete -= dtime;
    }
}
