
package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import framework.GameController;
import java.io.IOException;

public class launcher implements ActionListener{
    
    
    private final JButton _start_game;
    private final JComboBox _resolution_selection;
    private final JComboBox _player_count_selection;
    private final JComboBox _windowed_option_selection;
    private final JComboBox _type_option_selection;
    
    private final JTextField _server_ip_field;
    
    private final JFrame _frame;
    private final menuCanvas _panel;
    private final String[] resolutionChoices = {"640x640", "1280x720", "1920x1080", "2160x1440", "3840x2160"};
    private final String[] playerChoices = {"1", "2", "3", "4"};
    private final String[] windowedOptions = {"Windowed", "Fullscreen", "Fullscreen 2"};
    private final String[] typeOption = {"Offline", "Online - Client", "Online - Server"};
    
    
    public launcher() {
        _frame = new JFrame("Launcher");
        _frame.setVisible(true);
        _frame.setSize(220, 400);
        _frame.setLocation(0, 0);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setResizable(false);
        
        _panel = new menuCanvas();
        _panel.setSize(20, 20);
        _panel.setLocation(0, 0);
        _frame.add(_panel);
        
        _start_game = new JButton("Start Game");
        _start_game.setSize(200, 20);
        //_start_game.setPreferredSize(new Dimension(40, 40));
        //_start_game.setLocation(0, 0);
        _start_game.setVerticalAlignment(JLabel.CENTER);
        _start_game.setHorizontalAlignment(JLabel.CENTER);
        _start_game.addActionListener(_panel);
        
        JLabel _resolution_label = new JLabel("Resolution");
        _resolution_label.setSize(200, 20);
        _resolution_label.setLocation(0, 20);
        
        JLabel _playercount_label = new JLabel("Player Count");
        _playercount_label.setSize(200, 20);
        _playercount_label.setLocation(0, 40);
        
        JLabel _windowed_option_label = new JLabel("Window Option");
        _windowed_option_label.setSize(200, 20);
        _windowed_option_label.setLocation(0, 60);
        
        JLabel _type_option_label = new JLabel("Program");
        _type_option_label.setSize(200, 20);
        _type_option_label.setSize(0, 80);
        
        JLabel _server_ip_label = new JLabel("Server IP");
        _server_ip_label.setSize(200, 20);
        _server_ip_label.setLocation(0, 100);
        
        JLabel _blank = new JLabel("");
        
        _resolution_selection = new JComboBox(resolutionChoices);
        _resolution_selection.setSelectedIndex(2);
        _resolution_selection.addActionListener(this);
        _resolution_selection.setSize(130, 20);
        _resolution_selection.setLocation(70, 20);
        
        _player_count_selection = new JComboBox(playerChoices);
        _player_count_selection.setSelectedIndex(0);
        _player_count_selection.addActionListener(this);
        _player_count_selection.setSize(100, 20);
        _player_count_selection.setLocation(100, 40);
        
        _windowed_option_selection = new JComboBox(windowedOptions);
        _windowed_option_selection.setSelectedIndex(0);
        _windowed_option_selection.addActionListener(this);
        _windowed_option_selection.setSize(100, 20);
        _windowed_option_selection.setLocation(100, 60);
        
        _type_option_selection = new JComboBox(typeOption);
        _type_option_selection.setSelectedIndex(0);
        _type_option_selection.addActionListener(this);
        _type_option_selection.setSize(100, 20);
        _type_option_selection.setLocation(100, 80);
        
        _server_ip_field = new JTextField("127.0.0.1:1337");
        _server_ip_field.addActionListener(this);
        _server_ip_field.setSize(100, 20);
        _server_ip_field.setLocation(100, 100);
        
        _frame.add(_start_game);
        _frame.add(_resolution_selection);
        _frame.add(_resolution_label);
        _frame.add(_player_count_selection);
        _frame.add(_playercount_label);
        _frame.add(_windowed_option_selection);
        _frame.add(_windowed_option_label);
        _frame.add(_type_option_label);
        _frame.add(_type_option_selection);
        _frame.add(_server_ip_label);
        _frame.add(_server_ip_field);
        _frame.add(_blank);
        
        
        
        
        _frame.repaint();
    }
    
    public static void main(String[] args) {
        new launcher();
    }

    public void actionPerformed(ActionEvent ae) {
        _panel.setRes((String) _resolution_selection.getSelectedItem());
        _panel.setPlayerCount((String) _player_count_selection.getSelectedItem());
        _panel.setWindowedOption((String) _windowed_option_selection.getSelectedItem());
        _panel.setProgram((String) _type_option_selection.getSelectedItem());
        _panel.setIP((String)_server_ip_field.getText());
    }
}

class menuCanvas extends JPanel implements ActionListener {
    
    public menuCanvas() {
        super(new BorderLayout());
    }
    
    private String resolution = "1920x1080";
    private String playerCount = "1";
    private String windowedOption = "Windowed";
    private String program = "Offline";
    private String ip_port = "127.0.0.1:1337";
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        
        
        if (program.equals("Offline")) {
            String[] s = new String[3];
            s[0] = playerCount;
            s[1] = resolution;
            s[2] = windowedOption;
            topFrame.setVisible(false);
            String[] tmp = resolution.split("x");
            int screenWidth = 512; 
            int screenHeight = 512;
            if (tmp.length >= 2) {
                screenWidth = Integer.parseInt(tmp[0]);
                screenHeight = Integer.parseInt(tmp[1]);
            }
            GameController.getInstance().setPlayerCount(Integer.parseInt(playerCount));
            GameController.getInstance().setResolution(screenWidth, screenHeight);

            SinglePlayer game = new SinglePlayer();
            game.main(s);
        }
        
        if (program.equals("Online - Client")) {
            String[] s = new String[4];
            s[0] = playerCount;
            s[1] = resolution;
            s[2] = windowedOption;
            s[3] = ip_port;
            String[] tmp = resolution.split("x");
            int screenWidth = 512; 
            int screenHeight = 512;
            if (tmp.length >= 2) {
                screenWidth = Integer.parseInt(tmp[0]);
                screenHeight = Integer.parseInt(tmp[1]);
            }
            GameController.getInstance().setPlayerCount(Integer.parseInt(playerCount));
            GameController.getInstance().setResolution(screenWidth, screenHeight);

            topFrame.setVisible(false);
            MultiPlayer game = new MultiPlayer();
            try {
                game.main(s);
            } catch (IOException e) {
                
            }
        }
        
        if (program.equals("Online - Server")) {
            Server s = new Server();
            String[] str = new String[1];
            try {
                s.main(str);
            } catch (IOException e) {
                
            }
        }
        
        
        topFrame.setVisible(true);
        
        
    }
    
    public void setRes(String s) {
        resolution = s;
    }
    
    public void setPlayerCount(String s) {
        playerCount = s;
    }
    
    public void setWindowedOption(String s) {
        windowedOption = s;
    }
    
    public void setProgram(String s) {
        program = s;
    }
    
    public void paint(Graphics g) {
        
    }

    void setIP(String string) {
        ip_port = string;
    }
}