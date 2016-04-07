
package Physics;

/**
 *
 * @author Jalen
 */
public interface HitData {
    
   
     boolean CheckCollision(HitSphere sp);
     boolean CheckCollision(HitPlane pl);
     boolean CheckCollision(HitBox box);
     void draw();
}
