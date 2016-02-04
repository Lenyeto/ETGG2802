
package Entity;

import framework.math3d.vec3;

public class BaseEntity {
    //Creates the position variables.
    private float x, y, z;
    
    public BaseEntity(float x, float y, float z) {
        //Sets the position variables to whatever is passed through.
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void update() {
        
    }
    
    //Returns the x position of the Entity.
    public float getX() {
        return x;
    }
    
    //Returns the y position of the Entity.
    public float getY() {
        return y;
    }
    
    //Returns the z position of the Entity.
    public float getZ() {
        return z;
    }
    
    //Sets the x position to whatever is passed.
    public void setX(float i) {
        x = i;
    }
    
    //Sets the y position to whatever is passed.
    public void setY(float i) {
        y = i;
    }
    
    //Sets the z position to whatever is passed.
    public void setZ(float i) {
        z = i;
    }
    
    //Sets the position to the components of a vector 3 that is passed.
    public void setPos(vec3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }
    
    //Sets the position to the x, y, and z that is passed.
    public void setPos(float x_, float y_, float z_) {
        x = x_;
        y = y_;
        z = z_;
    }
}
