package game;

import framework.math3d.vec3;
import framework.math3d.mat4;
import java.util.Set;
import java.util.TreeSet;
import static JGL.JGL.*;
import static JSDL.JSDL.*;
import framework.math3d.vec2;
import framework.*;
import Entity.Player;
import java.util.List;
import java.util.ArrayList;
import Entity.PlanetEntity;
//Oculus Stuff
//import de.fruitfly.ovr.OculusRift;
//import de.fruitfly.ovr.enums.EyeType;

public class SinglePlayer{
    
    
    public static void main(String[] args){
        
        
        char playerCount = 0;
        
        //Oculus Stuff
        //System.loadLibrary("JRiftLibrary64");
        
        //OculusRift or = new OculusRift();
        
        //if (or.init()) {
        //    System.out.println(or._initSummary);
        //}
        
        
        //System.out.println(or.getEyePos(EyeType.ovrEye_Left).x);
        
        
        if (args.length >= 1) {
            playerCount = (char) Integer.parseInt(args[0]);
        }
        
        
        
        int screenWidth = 512;
        int screenHeight = 512;
        
        
        
        if (args.length >= 2) {
                String[] tmp = args[1].split("x");
                if (tmp.length >= 2) {
                    screenWidth = Integer.parseInt(tmp[0]);
                    screenHeight = Integer.parseInt(tmp[1]);
                }
        }
        
        
        
        
        
        
        int windowedOption = 0;
        if (args.length >= 3){
            String tmp = args[2];
            if (tmp.equals("Fullscreen")) {
                windowedOption = SDL_WINDOW_FULLSCREEN;
            } else if (tmp.equals("Fullscreen 2")) {
                windowedOption = SDL_WINDOW_FULLSCREEN_DESKTOP;
            } else if (tmp.equals("Windowed")) {
                windowedOption = 0;
            } else {
                
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
        SDL_SetWindowFullscreen(win,windowedOption);     //SDL_WINDOW_FULLSCREEN_DESKTOP
        glDebugMessageControl(GL_DONT_CARE,GL_DONT_CARE,GL_DONT_CARE, 0,null, true );
        glEnable(GL_DEBUG_OUTPUT_SYNCHRONOUS);
        glDebugMessageCallback(
                (int source, int type, int id, int severity, String message, Object obj ) -> {
                    //System.out.println("GL message: "+message);
                },
                null);

        int[] tmp = new int[1];
        glGenVertexArrays(1,tmp);
        int vao = tmp[0];
        glBindVertexArray(vao);

        glClearColor(0.0f,0.0f,0.0f,1.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            
        int i;
        int p;
        Set<Integer> keys = new TreeSet<>();
        Program prog;
        Program skyprog;
        Program blurprog;
        //Program bumpprog;
        Program explodeprog;
        float prev;
        
        
        Framebuffer FBO1;
        Framebuffer FBO2;
        
        FBO1 = new Framebuffer(GameController.getInstance().getResolution()[0], GameController.getInstance().getResolution()[1]);
        FBO2 = new Framebuffer(GameController.getInstance().getResolution()[0], GameController.getInstance().getResolution()[1]);
        
        //Initializes the players in the GameController.
        GameController.getInstance().init();
        
        
        List<PlanetEntity> planetList = new ArrayList<>();
        PlanetEntity mercury;
        PlanetEntity earth;
        PlanetEntity moon;
        PlanetEntity mars;
        PlanetEntity death_star;
        PlanetEntity jupiter;
        Texture2D dummytex = new SolidTexture(GL_UNSIGNED_BYTE,0,0,0,0);   
        String[] s = { "assets/skybox/stars_lf.jpg", "assets/skybox/stars_rt.jpg", "assets/skybox/stars_up.jpg",
            "assets/skybox/stars_dn.jpg","assets/skybox/stars_fr.jpg", "assets/skybox/stars_bk.jpg"};
        
        
        
        prog = new Program("vs.txt","fs.txt");
        skyprog = new Program("skyvs.txt","skyfs.txt");
        
        blurprog = new Program("blurvs.txt","blurfs.txt");
        //bumpprog = new Program("bumpvs.txt", "bumpfs.txt");
        explodeprog = new Program("explodevs.txt", "explodegs.txt", "explodefs.txt");
        //glowprog = new Program("glowvs.txt","glowfs.txt");
        skyprog.use();
        SkyBox skybox = new SkyBox(s, skyprog);
        
        
        mercury = new PlanetEntity(5.0f, -20.f, 2.0f, 3, .75f, new Mesh("assets/mercury.obj.mesh"), "mercury");
        earth = new PlanetEntity(-7.0f, -25.0f, -10.0f, 6, 2.0f, new Mesh("assets/earth.obj.mesh"), "earth");
        earth.mesh.normal_map = new ImageTexture("assets/earth_normal.jpg");
        moon = new PlanetEntity(-6.0f, -19.0f, -11.0f, 2, .75f, new Mesh("assets/moon.obj.mesh"), "moon");
        mars = new PlanetEntity(3.0f,0,0, 3, 1.0f, new Mesh("assets/mars.obj.mesh"), "mars");
        jupiter = new PlanetEntity(-10.0f, 10.0f, -20.0f, 8, 3.0f, new Mesh("assets/jupiter.obj.mesh"), "jupiter");
        death_star = new PlanetEntity(3.0f, 0.0f, -100.0f, 60, 30.0f, new Mesh("assets/death_star.obj.mesh"), "death star"); //that's no moon...

        planetList.add(mercury); planetList.add(earth); planetList.add(moon); planetList.add(mars); planetList.add(jupiter); planetList.add(death_star);
        prev = (float)(System.nanoTime()*1E-9);

        //EXPLODE TEST
        float explode = 0.0f;
        float dexplode = 0.0f;
        
        SDL_Event ev=new SDL_Event();
        while(true){
            while(true){     
                int rv = SDL_PollEvent(ev);
                if( rv == 0 )
                    break;
                if( ev.type == SDL_QUIT )
                    System.exit(0);
                if( ev.type == SDL_KEYDOWN ){
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
            

            for (Player pl : GameController.getInstance().getPlayers()) {
                pl.update(elapsed);
            }
            
            float maxexplode = 1f;
            
            
            if( keys.contains(SDLK_x) && dexplode == 0.0f )
                dexplode = 0.5f;
            explode += dexplode * elapsed;
            if (explode > maxexplode) {
                dexplode = -dexplode;
                explode = maxexplode;
            } else if (explode < 0.0f) {
                explode = 0.0f;
                dexplode = 0.0f;
            }
            
            
            
            int cur_player = 0;
            
            UnitSquare usq = new UnitSquare();
             
            
            for (Player pl : GameController.getInstance().getPlayers()) {
                
                FBO1.bind();
                
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                
                prog.use();
                prog.setUniform("lightPos",new vec3(50,50,50) );
                pl.getCam().draw(prog);
                prog.setUniform("worldMatrix",mat4.identity());

                for (Player pl2 : GameController.getInstance().getPlayers()) {
                    pl2.render(prog);
                }


                explodeprog.use();

                explodeprog.setUniform("lightPos",new vec3(50,50,50) );
                explodeprog.setUniform("worldMatrix",mat4.identity());
                explodeprog.setUniform("projMatrix", pl.getCam().getProjMatrix());
                explodeprog.setUniform("explode",explode);
                pl.getCam().draw(explodeprog);
                for(p = 0; p < planetList.size(); p++)
                {
                    planetList.get(p).render(explodeprog);
                    
                }

                skyprog.use();
                skyprog.setUniform("eyePos", pl.mPosition);
                skyprog.setUniform("projMatrix", pl.getCam().getProjMatrix());
                skyprog.setUniform("viewMatrix", pl.getCam().getViewMatrix());
                pl.getCam().draw(skyprog);
                skybox.draw(skyprog);



                FBO1.unbind();
                
                FBO2.bind();
                
                blurprog.use();
                float width = 0;
                blurprog.setUniform("boxwidth", width);
                blurprog.setUniform("diffuse_texture", FBO1.texture);
                blurprog.setUniform("blurdelta", new vec2(1.0f,0.0f));
                usq.draw(blurprog);
                

                FBO2.unbind();
                
                
                
                ScreenSpace.setViewport(playerCount, cur_player);
                
                blurprog.setUniform("diffuse_texture",dummytex);

                blurprog.setUniform("diffuse_texture",FBO2.texture);
                blurprog.setUniform("blurdelta",new vec2(0.0f,1.0f));
                usq.draw(blurprog);
                pl.getCam().draw(blurprog);

                
                cur_player++;
            }
            
            
            if (SDL_GetMouseFocus() == win) {
                SDL_SetRelativeMouseMode(1);
            } else {
                SDL_SetRelativeMouseMode(0);
            }
                
            

            SDL_GL_SwapWindow(win);


        }//endwhile
    }//end main
}
