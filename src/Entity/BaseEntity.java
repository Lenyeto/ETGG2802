/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author buellw
 */
public class BaseEntity {
    private float x, y, z;
    
    public BaseEntity(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void update() {
        
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getZ() {
        return z;
    }
    
    public void setX(int i) {
        x = i;
    }
    
    public void setY(int i) {
        y = i;
    }
    
    public void setZ(int i) {
        z = i;
    }
}
