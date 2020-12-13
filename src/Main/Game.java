package Main;

import javax.swing.JFrame;

public class Game {


        public static void main(String[] args) {
            JFrame window = new JFrame("Good Skill");
            window.setContentPane(new GamePanel());
            window.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
            window.setResizable(false);
            window.pack();
            window.setVisible(true);
        }
    }
