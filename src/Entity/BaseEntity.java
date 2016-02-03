/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import framework.math3d.vec3;

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
    
    public void setX(float i) {
        x = i;
    }
    
    public void setY(float i) {
        y = i;
    }
    
    public void setZ(float i) {
        z = i;
    }
    
    public void setPos(vec3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }
    
    public void setPos(float x_, float y_, float z_) {
        x = x_;
        y = y_;
        z = z_;
    }
}
