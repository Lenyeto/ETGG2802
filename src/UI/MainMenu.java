/*
Needs:
    play
    settings?
    exit
    buttons
    mouse movement
*/
package UI;


import JGL.JGL.*;
import static JGL.JGL.GL_COLOR_BUFFER_BIT;
import static JGL.JGL.GL_DEPTH_BUFFER_BIT;
import static JGL.JGL.glClear;
import static JSDL.JSDL.*;
import framework.Program;
import framework.Camera;
import framework.UnitSquare;



public class MainMenu
{
    int mouse_x;
    int mouse_y;
    UnitSquare us = new UnitSquare("assets/usq.obj.mesh");
    //UnitSquare us = new UnitSquare("assets/main_menu.png");
    boolean buttondown = false;

    
    public void update(SDL_Event event)
    {
        
        if( event.type == SDL_MOUSEBUTTONDOWN && buttondown == false)
            buttondown = true;
        if(event.type == SDL_MOUSEBUTTONUP && buttondown == true)
        {
            System.out.println("mouse button pressed");
            buttondown = false;
        }
    }
    
    public void render(Program prog, Camera cam)
    {
        //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        us.draw(prog);
    }
    
}
