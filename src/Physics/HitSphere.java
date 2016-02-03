package Physics;

public class HitSphere implements HitData {
    
    
    public HitSphere() {
        
    }
    
    public boolean CheckCollision(HitSphere other){
        return false;
    }
    public boolean CheckCollision(HitPlane other){
        return false;
    }

    @Override
    public boolean CheckCollision(HitBox box) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
