package framework;

import framework.math3d.vec2;
import framework.math3d.vec3;
import framework.math3d.vec4;


/**
 *
 * @author curetonj
 */
public class Utility {

    
    
    public static double Dist(Object... args){
        double[] v1 = new double[3];
        double[] v2 = new double[3];
        Object a = args[0];
        Object a2 = args[1];
        //takes in any type of vector(vec2,vec3,vec4) so we can get the diatance
        
        
        if( a instanceof vec2 ){
                v1[0] = ((vec2)a).x;
                v1[1] = ((vec2)a).y;
               
            }
        else if( a instanceof vec3 ){
                v1[0] = ((vec3)a).x;
                v1[1] = ((vec3)a).y;
                v1[2] = ((vec3)a).z;
            }
        else if( a instanceof vec4 ){
                v1[0] = ((vec4)a).x;
                v1[1] = ((vec4)a).y;
                v1[2] = ((vec4)a).z;
                
        }
            
        else
            throw new RuntimeException("Bad type for distance computation");
        
        
            
        if( a2 instanceof vec2 ){
                v2[0] = ((vec2)a2).x;
                v2[1] = ((vec2)a2).y;
                
        }
        else if( a instanceof vec3 ){
                v2[0] = ((vec3)a2).x;
                v2[1] = ((vec3)a2).y;
                v2[2] = ((vec3)a2).z;
        }
        else if( a instanceof vec4 ){
            v2[0] = ((vec4)a2).x;
            v2[1] = ((vec4)a2).y;
            v2[2] = ((vec4)a2).z;
                
        }
            
        else
            throw new RuntimeException("Bad type for distance computation");
        
        
        
        double d = Math.sqrt(((v2[0]-v1[0])+(v2[1]-v1[1])+(v2[2]-v1[2])));
        
        
        
        return d;
        
        
    }
    
    
}