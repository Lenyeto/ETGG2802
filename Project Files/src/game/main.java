package game;

import Entity.MeshEntity;
import framework.math3d.vec3;
import framework.math3d.mat4;
import java.util.Set;
import java.util.TreeSet;
import static JGL.JGL.*;
import static JSDL.JSDL.*;
import static framework.math3d.math3d.mul;
import static framework.math3d.math3d.sub;
import static framework.math3d.math3d.translation;
import framework.math3d.vec2;
import framework.math3d.vec4;
import framework.*;
import Entity.Player;
import JSDL.JSDL.SDL_MouseMotionEvent;
import java.util.List;

public class main{
    
    
    public static void main(String[] args){
        
        int screenWidth = 512;
        int screenHeight = 512;
        
        if (args.length >= 2) {
                String[] tmp = args[1].split("x");
                if (tmp.length >= 2) {
                    screenWidth = Integer.parseInt(tmp[0]);
                    screenHeight = Integer.parseInt(tmp[1]);
                }
        }
        
        SDL_Init(SDL_INIT_VIDEO);
        long win = SDL_CreateWindow("ETGG 2802",40,60, screenWidth,screenHeight, SDL_WINDOW_OPENGL );
        SDL_GL_SetAttribute(SDL_GL_CONTEXT_PROFILE_MASK,SDL_GL_CONTEXT_PROFILE_CORE);
        SDL_GL_SetAttribute(SDL_GL_DEPTH_SIZE,24);
        SDL_GL_SetAttribute(SDL_GL_STENCIL_SIZE,8);
        SDL_GL_SetAttribute(SDL_GL_CONTEXT_MAJOR_VERSION,3);
        SDL_GL_SetAttribute(SDL_GL_CONTEXT_MINOR_VERSION,2);
        SDL_GL_SetAttribute(SDL_GL_CONTEXT_FLAGS, SDL_GL_CONTEXT_DEBUG_FLAG);
        SDL_GL_CreateContext(win);
        
        glDebugMessageControl(GL_DONT_CARE,GL_DONT_CARE,GL_DONT_CARE, 0,null, true );
        glEnable(GL_DEBUG_OUTPUT_SYNCHRONOUS);
        glDebugMessageCallback(
                (int source, int type, int id, int severity, String message, Object obj ) -> {
                    System.out.println("GL message: "+message);
                    //if( severity == GL_DEBUG_SEVERITY_HIGH )
                    //    System.exit(1);
                },
                null);

        int[] tmp = new int[1];
        glGenVertexArrays(1,tmp);
        int vao = tmp[0];
        glBindVertexArray(vao);

        glClearColor(0.2f,0.4f,0.6f,1.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        Set<Integer> keys = new TreeSet<>();
        Camera cam;
        Program prog;
        Program blurprog;
        float prev;
        Mesh column;

        Framebuffer fbo1;
        Framebuffer fbo2;
        Player player;
        Texture2D dummytex = new SolidTexture(GL_UNSIGNED_BYTE,0,0,0,0);
        column = new Mesh("assets/column.obj.mesh");


        fbo1 = new Framebuffer(screenWidth,screenHeight);
        fbo2 = new Framebuffer(screenWidth,screenHeight);

        prog = new Program("vs.txt","fs.txt");
        blurprog = new Program("blurvs.txt","blurfs.txt");

        
        
        
//        TreeSet<MeshEntity> entities = new TreeSet();
//        if (args.length >= 1) {
//            if (args[0] == "1") {
//                player = new Player(0,0,0, "assets/tetraship.obj.mesh", SDLK_w, SDLK_s, SDLK_a, SDLK_d);
//                entities.add(player);
//            } else if (args[0] == "2") {
//
//            } else if (args[0] == "3") {
//
//            } else if (args[0] == "4"){
//
//            } else {
//                System.exit(1);
//            }
//            
//        } else {
//            player = new Player(0,0,0, "assets/tetraship.obj.mesh", SDLK_w, SDLK_s, SDLK_a, SDLK_d);
//            entities.add(player);
//        }
        
        player = new Player(0, 0, 0, "assets/tetraship.obj.mesh", SDLK_w, SDLK_s, SDLK_a, SDLK_d, SDLK_SPACE, SDLK_RIGHT, SDLK_LEFT, SDLK_UP, SDLK_DOWN, screenWidth, screenHeight);
        
        
        
        
        
        //cam = new Camera((float)screenWidth/(float)screenHeight);
        //cam.lookAt( new vec3(0,0,5), new vec3(0,0,0), new vec3(0,1,0) );

        prev = (float)(System.nanoTime()*1E-9);

        float xrel = 0f;
        float yrel = 0f;
        
        SDL_Event ev=new SDL_Event();
        while(true){
            while(true){
                
                
                int rv = SDL_PollEvent(ev);
                if( rv == 0 )
                    break;
                //System.out.println("Event "+ev.type);
                if( ev.type == SDL_QUIT )
                    System.exit(0);
                if( ev.type == SDL_MOUSEMOTION ){
                    //System.out.println("Mouse motion "+ev.motion.x+" "+ev.motion.y+" "+ev.motion.xrel+" "+ev.motion.yrel);
                    
                    xrel = ev.motion.xrel;
                    yrel = ev.motion.yrel;
                } else {
                    xrel = 0f;
                    yrel = 0f;
                }
                if( ev.type == SDL_KEYDOWN ){
                    //System.out.println("Key press "+ev.key.keysym.sym+" "+ev.key.keysym.sym);
                    if (ev.key.keysym.sym == SDLK_ESCAPE) {
                        System.exit(0);
                    }
                    keys.add(ev.key.keysym.sym);
                    
                }
                if( ev.type == SDL_KEYUP ){
                    keys.remove(ev.key.keysym.sym);
                }
            }

            float now = (float)(System.nanoTime()*1E-9);
            float elapsed = now-prev;
            
            prev=now;

//            if( keys.contains(SDLK_w ))
//                cam.walk(0.5f*elapsed);
//            if( keys.contains(SDLK_s))
//                cam.walk(-0.5f*elapsed);
//            if( keys.contains(SDLK_a))
//                cam.turn(0.4f*elapsed);
//            if( keys.contains(SDLK_d))
//                cam.turn(-0.4f*elapsed);
//            if( keys.contains(SDLK_r))
//                cam.tilt(0.4f*elapsed);
//            if( keys.contains(SDLK_t))
//                cam.tilt(-0.4f*elapsed);
            player.update(ev, xrel, yrel, keys, elapsed);
            
            //the fbo stuff is for later...
            //fbo1.bind();
            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            prog.use();
            prog.setUniform("lightPos",new vec3(50,50,50) );
            //cam.draw(prog);
            player.getCam().draw(prog);
            prog.setUniform("worldMatrix",mat4.identity());
            column.draw(prog);
            
//            for (MeshEntity e : entities) {
//                e.render(prog);
//            }
            
            player.render(prog);
            
            
            //fbo1.unbind();

            //this is also for later...
/*
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            blurprog.use();
            blurprog.setUniform("diffuse_texture",fbo1.texture);
            usq.draw(blurprog);
            blurprog.setUniform("diffuse_texture",dummytex);
*/
            if (SDL_GetMouseFocus() == win) {
                SDL_SetRelativeMouseMode(1);
            } else {
                SDL_SetRelativeMouseMode(0);
            }
                
            

            SDL_GL_SwapWindow(win);


        }//endwhile
    }//end main
}
