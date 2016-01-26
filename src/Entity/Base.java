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
public class Base {
    private int x, y, z;
    
    public Base(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void update() {
        
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getZ() {
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
