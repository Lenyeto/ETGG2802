/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;
import framework.Mesh;
import framework.Program;
import framework.math3d.mat4;

/**
 *
 * @author buellw
 */
public class MeshEntity extends BaseEntity {
    private Mesh mesh;
    private mat4 worldMatrix = mat4.identity();
    private boolean delete;
    private float pr, yr, rr;
    
    public MeshEntity(float x, float y, float z, Mesh mesh_) {
        super(x, y, z);
        mesh = mesh_;
    }
    
    public void setScale(float x) {
        worldMatrix.setScale(x, x, x);
    }
    
    public void setRotation(float pr_, float yr_, float rr_) {
        pr = pr_;
        yr = yr_;
        rr = rr_;
    }
    
    public Mesh getMesh(){
        return mesh;
    }
    
    public void update() {
        worldMatrix.setPos(getX(), getY(), getZ());
    }
        
    public mat4 getMatrix() {
        return worldMatrix;
    }
    
    public mat4 getRotation() {
        return worldMatrix.getRotation(pr, yr, pr);
    }
    
    public void render(Program prog){ 
        
        prog.setUniform("worldMatrix", worldMatrix.mul(worldMatrix.getRotation(pr, yr, rr)));
        System.out.println("POS"+worldMatrix.getPos());
        //prog.setUniform("viewMatrix", mat4.identity());
        //prog.setUniform("projMatrix", mat4.identity());
        mesh.draw(prog);
    }
}
