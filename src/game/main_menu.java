/*
Needs:
    play
    settings?
    exit
    background img
    buttons
 */
package game;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author chutesd
 */
public abstract class main_menu implements MouseMotionListener
{
    int mouse_x;
    int mouse_y;
    
    public void mouseMoved(MouseEvent e) {
       //doesn't work yet
        System.out.println("ayyylmao");
    }

}