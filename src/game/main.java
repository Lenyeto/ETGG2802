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
import JSDL.JSDL;
import java.util.List;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Controller;
import org.lwjgl.LWJGLException;
import java.util.ArrayList;
import Entity.Bullet;
import Entity.PlanetEntity;
import framework.Utility;

public class main{
    
    
    public static void main(String[] args){
        
        
        long controller = 0;
        
        char playerCount = 0;
        
        
        
        if (args.length >= 1) {
            playerCount = (char) Integer.parseInt(args[0]);
        }
        
        
        
        
        long controllers[] = new long[playerCount];
        for (long i : controllers) {
            i = 0;
        }
        
        SDL_Init(SDL_INIT_GAMECONTROLLER);
        
        for (long j : controllers) {
            for (int i = 0; i < SDL_NumJoysticks(); ++i) {
                if (SDL_IsGameController(i) > 0) {
                    controller = SDL_GameControllerOpen(i);
                    if (controller > 0) {
                        
                    } else {
                        System.out.println("Coult not open game controller");
                    }
                    for (int k = 0; k < controllers.length; k++) {
                        if (controllers[k] == 0) {
                            controllers[k] = controller;
                            break;
                        }
                    }
                    System.out.println(controller);
                }
            }   
        }
        
        for (long i : controllers) {
            System.out.println(i);
        }
        
        
        
        //controller pressed/not pressed variables
        int Start = 0;
        int AButton = 0;
        int RightBumper = 0;
        int LeftBumper = 0;
        //precision based controls
        float rotate_forward = 0.0f;
        float move_sideways = 0.0f;
        float rotate_vertical = 0.0f;
        float rotate_horizontal = 0.0f;
        float move_forward = 0.0f;
                
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
                    System.out.println("GL message: "+message);
                    //if( severity == GL_DEBUG_SEVERITY_HIGH )
                    //    System.exit(1);
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
        float bulletTime = 0.0f;
        Set<Integer> keys = new TreeSet<>();
        Camera cam;
        Program prog;
        Program skyprog;
        Program blurprog;
        Program glowprog;
        Program stitchprog;
        //Program bumpprog;
        Program explodeprog;
        float prev;
        Framebuffer fbo1;
        Framebuffer fbo2;
        Framebuffer[][] fbos = new Framebuffer[playerCount][2];
        
        
        
        
        
        
        
        
        
        
        Player players[] = new Player[playerCount];
        List<PlanetEntity> planetList = new ArrayList<>();
        List<Bullet> bulletList = new ArrayList<>();
        PlanetEntity mercury;
        PlanetEntity earth;
        PlanetEntity moon;
        PlanetEntity mars;
        PlanetEntity death_star;
        PlanetEntity jupiter;
        Texture2D dummytex = new SolidTexture(GL_UNSIGNED_BYTE,0,0,0,0);   
        String[] s = { "assets/skybox/stars_lf.jpg", "assets/skybox/stars_rt.jpg", "assets/skybox/stars_up.jpg",
            "assets/skybox/stars_dn.jpg","assets/skybox/stars_fr.jpg", "assets/skybox/stars_bk.jpg"};
        
        fbo1 = new Framebuffer(screenWidth,screenHeight);
        fbo2 = new Framebuffer(screenWidth,screenHeight);

        if (playerCount == 1) {
            fbos[0][0] = new Framebuffer(screenWidth, screenHeight);
            fbos[0][1] = new Framebuffer(screenWidth, screenHeight);
        } else if (playerCount == 2) {
            fbos[0][0] = new Framebuffer(screenWidth, screenHeight/2);
            fbos[0][1] = new Framebuffer(screenWidth, screenHeight/2);
            
            fbos[1][0] = new Framebuffer(screenWidth, screenHeight/2);
            fbos[1][1] = new Framebuffer(screenWidth, screenHeight/2);
        } else if (playerCount == 3) {
            fbos[0][0] = new Framebuffer(screenWidth/2, screenHeight/2);
            fbos[0][1] = new Framebuffer(screenWidth/2, screenHeight/2);
            
            fbos[1][0] = new Framebuffer(screenWidth/2, screenHeight/2);
            fbos[1][1] = new Framebuffer(screenWidth/2, screenHeight/2);
            
            fbos[2][0] = new Framebuffer(screenWidth, screenHeight/2);
            fbos[2][1] = new Framebuffer(screenWidth, screenHeight/2);
        } else {
            fbos[0][0] = new Framebuffer(screenWidth/2, screenHeight/2);
            fbos[0][1] = new Framebuffer(screenWidth/2, screenHeight/2);
            
            fbos[1][0] = new Framebuffer(screenWidth/2, screenHeight/2);
            fbos[1][1] = new Framebuffer(screenWidth/2, screenHeight/2);
            
            fbos[2][0] = new Framebuffer(screenWidth/2, screenHeight/2);
            fbos[2][1] = new Framebuffer(screenWidth/2, screenHeight/2);
            
            fbos[3][0] = new Framebuffer(screenWidth/2, screenHeight/2);
            fbos[3][1] = new Framebuffer(screenWidth/2, screenHeight/2);
        }
        
        
        
        prog = new Program("vs.txt","fs.txt");
        skyprog = new Program("skyvs.txt","skyfs.txt");
        
        blurprog = new Program("blurvs.txt","blurfs.txt");
        //bumpprog = new Program("bumpvs.txt", "bumpfs.txt");
        explodeprog = new Program("explodevs.txt", "explodegs.txt", "explodefs.txt");
        //glowprog = new Program("glowvs.txt","glowfs.txt");
        stitchprog = new Program("stitchvs.txt", "stitchfs.txt");
        skyprog.use();
        SkyBox skybox = new SkyBox(s, skyprog);
        //assets/tetraship.obj.mesh
        //assets/tie_fighter/Creature.obj.mesh
        
        players[0] = new Player(0, 0, 0, "assets/tie_fighter/Creature.obj.mesh", SDLK_w, SDLK_s, SDLK_a, SDLK_d, SDLK_SPACE, SDLK_RIGHT, SDLK_LEFT, SDLK_UP, SDLK_DOWN, screenWidth, screenHeight);
        players[0].setScale(0.01f);
        if (playerCount == 2) {
            players[1] = new Player(1, 0, 0, "assets/tie_fighter/Creature.obj.mesh", SDLK_w, SDLK_s, SDLK_a, SDLK_d, SDLK_SPACE, SDLK_RIGHT, SDLK_LEFT, SDLK_UP, SDLK_DOWN, screenWidth, screenHeight);
            players[1].setScale(0.01f);
        }
        if (playerCount == 3) {
            players[1] = new Player(1, 0, 0, "assets/tie_fighter/Creature.obj.mesh", SDLK_w, SDLK_s, SDLK_a, SDLK_d, SDLK_SPACE, SDLK_RIGHT, SDLK_LEFT, SDLK_UP, SDLK_DOWN, screenWidth, screenHeight);
            players[1].setScale(0.01f);
            
            players[2] = new Player(0, 1, 0, "assets/tie_fighter/Creature.obj.mesh", SDLK_w, SDLK_s, SDLK_a, SDLK_d, SDLK_SPACE, SDLK_RIGHT, SDLK_LEFT, SDLK_UP, SDLK_DOWN, screenWidth, screenHeight);
            players[2].setScale(0.01f);
        }
        if (playerCount == 4) {
            players[1] = new Player(1, 0, 0, "assets/tie_fighter/Creature.obj.mesh", SDLK_w, SDLK_s, SDLK_a, SDLK_d, SDLK_SPACE, SDLK_RIGHT, SDLK_LEFT, SDLK_UP, SDLK_DOWN, screenWidth, screenHeight);
            players[1].setScale(0.01f);
            
            players[2] = new Player(0, 1, 0, "assets/tie_fighter/Creature.obj.mesh", SDLK_w, SDLK_s, SDLK_a, SDLK_d, SDLK_SPACE, SDLK_RIGHT, SDLK_LEFT, SDLK_UP, SDLK_DOWN, screenWidth, screenHeight);
            players[2].setScale(0.01f);
            
            players[3] = new Player(0, 0, 1, "assets/tie_fighter/Creature.obj.mesh", SDLK_w, SDLK_s, SDLK_a, SDLK_d, SDLK_SPACE, SDLK_RIGHT, SDLK_LEFT, SDLK_UP, SDLK_DOWN, screenWidth, screenHeight);
            players[3].setScale(0.01f);
        }
        players[0].setController(controllers[0]);
        if (playerCount == 2) {
            players[1].setController(controllers[1]);
        }
        if (playerCount == 3) {
            players[1].setController(controllers[1]);
            players[2].setController(controllers[2]);
        }
        if (playerCount == 4) {
            players[1].setController(controllers[1]);
            players[2].setController(controllers[2]);
            players[3].setController(controllers[3]);
        }
        
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
                //assigns values to the controller buttons
                
                Start = SDL_GameControllerGetButton(controller, SDL_CONTROLLER_BUTTON_START);
                AButton = SDL_GameControllerGetButton(controller, SDL_CONTROLLER_BUTTON_A);
                LeftBumper = SDL_GameControllerGetButton(controller, SDL_CONTROLLER_BUTTON_LEFTSHOULDER);
                RightBumper = SDL_GameControllerGetButton(controller, SDL_CONTROLLER_BUTTON_RIGHTSHOULDER);
                
                
                if (Start > 0)
                    System.exit(0);
                                
                int rv = SDL_PollEvent(ev);
                if( rv == 0 )
                    break;
                //System.out.println("Event "+ev.type);
                if( ev.type == SDL_QUIT )
                    System.exit(0);
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
            
            bulletTime += elapsed;
            
            if( keys.contains(SDLK_w ))
                rotate_forward = -1.0f;
            else if (keys.contains(SDLK_s))
                rotate_forward = 1.0f;
            else
                rotate_forward = 0.0f;
            //LeftBumper = keys.contains(SDLK_a);
            //RightBumper = keys.contains(SDLK_d);
            if (keys.contains(SDLK_q))
                move_sideways = -1.0f;
            else if (keys.contains(SDLK_e))
                move_sideways = 1.0f;
            else
                move_sideways = 0.0f;
            if (keys.contains(SDLK_LSHIFT))
                move_forward = 1.0f;
            else
                move_forward = 0.0f;
            if (keys.contains(SDLK_SPACE))
            {
                /*
                if (bulletTime >= 0.3f){
                    Bullet b = new Bullet(player);
                    bulletList.add(b);
                    bulletTime = 0.0f;
                }
                */
            }
                
            
            
            //player.update(move_forward, rotate_forward, move_sideways, LeftBumper, RightBumper, elapsed);
            
            for (Player pl : players) {
                pl.update(move_forward, SDL_GameControllerGetAxis(pl.getController(), SDL_CONTROLLER_AXIS_LEFTY)/8000, SDL_GameControllerGetAxis(pl.getController(), SDL_CONTROLLER_AXIS_LEFTX) / 8000, SDL_GameControllerGetButton(pl.getController(), SDL_CONTROLLER_BUTTON_LEFTSHOULDER), SDL_GameControllerGetButton(pl.getController(), SDL_CONTROLLER_BUTTON_RIGHTSHOULDER), elapsed);
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
            
            
            
            
            UnitSquare usq = new UnitSquare();
            
            int cur_player = 0;
            
            for (Framebuffer[] fb : fbos) {
                
                //the fbo stuff is for later...
                fb[0].bind();

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                prog.use();
                prog.setUniform("lightPos",new vec3(50,50,50) );
                players[cur_player].getCam().draw(prog);
                prog.setUniform("worldMatrix",mat4.identity());

                //players[0].render(prog);
                for (Player pl : players) {
                    pl.render(prog);
                }

                for (i = 0; i < bulletList.size(); i++)
                {
                    bulletList.get(i).update(players[0],elapsed);
                    bulletList.get(i).render(prog);
                    if (!bulletList.get(i).isActive)
                        bulletList.remove(bulletList.get(i));
                }


                explodeprog.use();

                explodeprog.setUniform("lightPos",new vec3(50,50,50) );
                explodeprog.setUniform("worldMatrix",mat4.identity());
                explodeprog.setUniform("projMatrix", players[cur_player].getCam().getProjMatrix());
                explodeprog.setUniform("explode",explode);
                players[cur_player].getCam().draw(explodeprog);
                for(p = 0; p < planetList.size(); p++)
                {
                    planetList.get(p).render(explodeprog);
                    for (i = 0; i < bulletList.size(); i++)
                    {
                        if(Utility.Collision(planetList.get(p).mPosition, planetList.get(p).size, bulletList.get(i).mPosition, bulletList.get(i).size))
                        {
                            if (!planetList.get(p).mName.equals("death star"))
                            {
                                bulletList.remove(bulletList.get(i));
                                planetList.get(p).mHealth--;
                                if(planetList.get(p).mHealth <= 0 && dexplode == 0.0f)
                                    dexplode = 0.5f;
                                    //planetList.remove(planetList.get(p));
                            }
                        }
                    }
                }

                skyprog.use();
                skyprog.setUniform("eyePos", players[cur_player].mPosition);
                skyprog.setUniform("projMatrix", players[cur_player].getCam().getProjMatrix());
                skyprog.setUniform("viewMatrix", players[cur_player].getCam().getViewMatrix());
                players[cur_player].getCam().draw(skyprog);
                skybox.draw(skyprog);


                //bumpprog.use();
                //bumpprog.setUniform("lightvec",new vec3(50,50,50) );
                //bumpprog.setUniform("CAMERA_POSITION", player.mPosition);
                //player.getCam().draw(bumpprog);
                //planetList.get(1).render(bumpprog);




                fb[0].unbind();


                fb[1].bind();

                blurprog.use();
                //float ayy = (int)player.getSpeed()>>4;
                float ayy = 0;


                blurprog.setUniform("boxwidth",ayy);  
                blurprog.setUniform("diffuse_texture",fb[1].texture);
                blurprog.setUniform("blurdelta",new vec2(1.0f,0.0f));
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                usq.draw(blurprog);


                fb[1].unbind();



                blurprog.setUniform("diffuse_texture",dummytex);

                blurprog.setUniform("diffuse_texture",fb[1].texture);
                blurprog.setUniform("blurdelta",new vec2(0.0f,1.0f));
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                usq.draw(blurprog);
                players[cur_player].getCam().draw(blurprog);

                
                cur_player++;
            }
            
            stitchprog.use();
            stitchprog.setUniform("diffuse_one", fbos[0][0].texture);
            if (playerCount == 2) {
                stitchprog.setUniform("diffuse_two", fbos[1][0].texture);
            } else if (playerCount == 3) {
                stitchprog.setUniform("diffuse_two", fbos[1][0].texture);
                stitchprog.setUniform("diffuse_three", fbos[2][0].texture);
            } else if (playerCount == 4) {
                stitchprog.setUniform("diffuse_two", fbos[1][0].texture);
                stitchprog.setUniform("diffuse_three", fbos[2][0].texture);
                stitchprog.setUniform("diffuse_four", fbos[3][0].texture);
            }
            stitchprog.setUniform("num_screens", playerCount);
            stitchprog.setUniform("screenWidth", screenWidth);
            stitchprog.setUniform("screenHeight", screenHeight);
            
            usq.draw(stitchprog);
            
            
            if (SDL_GetMouseFocus() == win) {
                SDL_SetRelativeMouseMode(1);
            } else {
                SDL_SetRelativeMouseMode(0);
            }
                
            

            SDL_GL_SwapWindow(win);


        }//endwhile
    }//end main
}
