/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;
import Entity.MeshEntity;
import framework.Mesh;
import framework.math3d.vec3;
import framework.math3d.mat4;
/**
 *
 * @author Dalton
 */
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
    
    public void update(Player player, float dtime)
    {
        vec3 leftVec = mPosition.sub(player_matrix.left().mul(dtime));
        vec3 forwardVec = leftVec.sub(player_matrix.backward().mul(5000.0f * dtime));
        super.setPos(forwardVec.x, forwardVec.y, forwardVec.z);
        dist += dtime;
        if (dist >= 10.0f)
            isActive = false;
    }
}
