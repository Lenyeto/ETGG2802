package Physics;

import framework.Mesh;
import framework.math3d.vec3;
import framework.Utility;
public class HitSphere implements HitData {

    private double mRadius = 1;
    private vec3 mPosition = new vec3(); 
    private Mesh m;
    public HitSphere(){
        
    }
    
    
    
    
    
    @Override
    public boolean CheckCollision(HitSphere sp) {
        
        double dist;
        dist = Utility.Dist(this.mPosition,sp.mPosition);
        return dist <= (this.mRadius+sp.mRadius);
    }

    @Override
    public boolean CheckCollision(HitPlane pl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean CheckCollision(HitBox box) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     
}