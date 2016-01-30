/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;
import framework.Mesh;

/**
 *
 * @author buellw
 */
public class MeshEntity extends BaseEntity {
    private Mesh mesh;
    
    public MeshEntity(int x, int y, int z, String filename) {
        super(x, y, z);
        mesh = new Mesh(filename);
    }
    public Mesh getMesh(){
        return mesh;
    }
}
