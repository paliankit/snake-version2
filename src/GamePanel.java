import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int HEIGHT=600;
    static final int WIDTH=600;
    static final int UNIT=25;
    static final int TOTAL_UNITS=(HEIGHT*WIDTH)/UNIT;
    int[] x=new int[TOTAL_UNITS];
    int[] y=new int[TOTAL_UNITS];
    int appleX;
    int appleY;
    char direction='L';
    boolean running=false;
    int bodyParts=5;
    int applesEaten;
    Random random;
    Timer timer;
    static final int DELAY=75;

    public GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple();
        running=true;
        timer=new Timer(DELAY,this);
    }

    public void newApple(){
        appleX=random.nextInt(600);
        appleY=random.nextInt(600);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        // draw the apple
        // 1. set the color as red
        // 2. draw oval
        g.setColor(Color.red);
        g.drawOval(appleX,appleY,UNIT,UNIT);

        //draw the snake

    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='R'){
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L'){
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D'){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U'){
                        direction='D';
                    }
                    break;
            }
        }
    }

}
