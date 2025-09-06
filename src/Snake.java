import javax.swing.*;

public class Snake {

    public static void main(String[] args){
        System.out.println("Snake started...");
        GameFrame gameFrame=new GameFrame();
        gameFrame.setTitle("snake 2.0");
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(new GamePanel());
    }
}
