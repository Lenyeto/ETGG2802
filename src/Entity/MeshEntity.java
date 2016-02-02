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
    
    public MeshEntity(float x, float y, float z, String filename) {
        super(x, y, z);
        mesh = new Mesh(filename);
    }
    
    public void setScale(float x) {
        worldMatrix.setScale(x, x, x);
    }
    
    public Mesh getMesh(){
        return mesh;
    }
    
    public void update() {
        worldMatrix.setPos(getX(), getY(), getZ());
    }
        
    public void render(Program prog){ 
        
        prog.setUniform("worldMatrix", worldMatrix);
        
        //prog.setUniform("viewMatrix", mat4.identity());
        //prog.setUniform("projMatrix", mat4.identity());
        mesh.draw(prog);
    }
}
