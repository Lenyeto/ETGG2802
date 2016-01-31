/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 *
 * @author William
 */
public class launcher {
    public launcher() {
        JFrame _frame = new JFrame("Launcher");
        _frame.setVisible(true);
        _frame.setSize(1920, 1080);
        _frame.setLocation(0, 0);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        menuCanvas _panel = new menuCanvas();
        _panel.setSize(20, 20);
        _panel.setLocation(0, 0);
        _frame.add(_panel);
        
        JButton _start_game = new JButton("Start Game");
        //_start_game.setSize(2, 2);
        //_start_game.setPreferredSize(new Dimension(40, 40));
        //_start_game.setLocation(0, 0);
        _start_game.addActionListener(_panel);
        _frame.add(_start_game);
        
    }
    
    public static void main(String[] args) {
        new launcher();
    }
}

class menuCanvas extends JPanel implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        
        String[] s = new String[2];
        
        s[0] = "1";
        s[1] = "1920x1080";
        topFrame.setVisible(false);
        main game = new main();
        game.main(s);
        topFrame.setVisible(true);
        
        
    }
    
    public void paint(Graphics g) {
        
    }
}