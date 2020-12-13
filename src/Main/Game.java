package Main;

import javax.swing.*;
import java.net.URL;

public class Game {

        // main method to run game
        public static void main(String[] args) {
            // creates a JFrame Window iw
            JFrame window = new JFrame("Good Skill");
            // sets content in pane of Game Panel
            window.setContentPane(new GamePanel());
            // allows game window to be closed
            window.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
            // makes window un resizable
            window.setResizable(false);
            // sizes the frame so that all its contents are at or above their preferred sizes
            window.pack();
            // changes default icon to custom icon
            URL url = Game.class.getResource("/Sprites/Player/icon.png");
            ImageIcon imgicon = new ImageIcon(url);
            window.setIconImage(imgicon.getImage());
            // sets window to visible
            window.setVisible(true);
        }
    }
