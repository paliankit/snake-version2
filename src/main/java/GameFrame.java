package main.java;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame(){
        this.add(new GamePanel());
        this.setTitle("snake 2.0");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        //this.setLocation(-7,0);
        this.setLocationRelativeTo(null);
    }


}
