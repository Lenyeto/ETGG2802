
package Entity;
import framework.Mesh;
import framework.math3d.vec3;
import framework.math3d.mat4;
import java.util.ArrayList;

public class Bullet extends MeshEntity {
    mat4 player_matrix;
    float dist;
    public Boolean isActive = true;
    public float size = 1.0f;
    public Bullet(Player player){
        super(player.mPosition.x,player.mPosition.y,player.mPosition.z,new Mesh("assets/bullet.obj.mesh"));
        player_matrix = player.getMatrix();
        super.setScale(.1f);
        size *= .1f;
    }
    
    public void update(Player player, float dtime, ArrayList<MeshEntity> entities)
    {
        vec3 leftVec = mPosition.sub(player_matrix.left().mul(dtime));
        vec3 forwardVec = leftVec.sub(player_matrix.backward().mul(5000.0f * dtime));
        super.setPos(forwardVec.x, forwardVec.y, forwardVec.z);
        dist += dtime;
        if (dist >= 10.0f)
            isActive = false;
        
        for (MeshEntity mesh : entities) {
            if (mesh.collides(this)) {
                
            }
        }
    }
}
