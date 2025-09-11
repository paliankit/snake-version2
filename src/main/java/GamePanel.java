package main.java;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
    char direction='R';
    boolean running=false;
    int bodyParts=5;
    int applesEaten;
    Random random;
    Timer timer;
    static final int DELAY=75;
    String level;
    Image cherry =new ImageIcon(getClass().getResource("/cherry.png")).getImage();
    int gameOver=0;
    Map<String,Integer> levelCounter =new HashMap<String,Integer>();

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
        timer.start();
        levelCounter.put("I",1);
        levelCounter.put("II",1);
        levelCounter.put("III",1);
        levelCounter.put("IV",1);
    }

    public void newApple(){
        appleX=random.nextInt(WIDTH/UNIT)*UNIT;  // to have complete overlap of apple with snake
        appleY=random.nextInt(HEIGHT/UNIT)*UNIT;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(running){
           move();
            try {
                checkCollisions();
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        try {
            draw(g);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics g) throws UnsupportedAudioFileException, LineUnavailableException, URISyntaxException, IOException {
        if(running) {
            g.setColor(Color.red);
            //g.drawOval(appleX, appleY, UNIT, UNIT);
            g.drawImage(cherry,appleX, appleY, UNIT, UNIT,null);

            g.setColor(Color.red);
            g.drawString("Score: "+ applesEaten,260,25);
            g.drawString("Level: "+ level,200,25);

            g.setColor(Color.green);
            if(applesEaten<2){
                level="I";
                for (int i = 0; i < bodyParts; i++) {
                    g.drawRect(x[i], y[i], UNIT, UNIT);
                }
            }else if(applesEaten<4){
                level="II";
                levelUpSound();
                for (int i = 0; i < bodyParts; i++) {
                    g.fillRect(x[i], y[i], UNIT, UNIT);
                }
            }else if(applesEaten<6){
                level="III";
                levelUpSound();
                for (int i = 0; i < bodyParts; i++) {
                    g.drawRoundRect(x[i], y[i], UNIT, UNIT,10,10);
                }
            }else{
                level="IV";
                levelUpSound();
                for (int i = 0; i < bodyParts; i++) {
                    g.fillRoundRect(x[i], y[i], UNIT, UNIT,10,10);
                }
            }
        }else{
            g.setColor(Color.red);
            gameOver++;
            gameOverSound();
            g.setFont(new Font(null, Font.PLAIN,14));
            g.drawString("Game Over" ,250,300);
            g.drawString("Score: "+ applesEaten,250,325);
            g.drawString("Please press enter key to restart",250,350);
        }

    }

    public void move(){
        if(running){
            for(int i=bodyParts;i>0;i--){
                x[i]=x[i-1];
                y[i]=y[i-1];
            }
            switch(direction){
                case 'R':
                    x[0]=x[0]+UNIT;
                    break;
                case 'L':
                    x[0]=x[0]-UNIT;
                    break;
                case 'U':
                    y[0]=y[0]-UNIT;
                    break;
                case 'D':
                    y[0]=y[0]+UNIT;
                    break;
            }
        }
    }

    public void checkCollisions() throws UnsupportedAudioFileException, LineUnavailableException, URISyntaxException, IOException {

        if(x[0]==appleX && y[0]==appleY){
            applesEaten++;
            bodyParts++;
            newApple();
            repaint();
            appleEatenSound();
        }

//        if(x[0]>WIDTH || y[0]<0 || y[0]>HEIGHT || x[0]<0){
//            running=false;
//        }

        // now collision is not possible, teleportation is

        if(x[0]>WIDTH){
            x[0]=0;
        }
        if(x[0]<0){
            x[0]=600;
        }
        if(y[0]<0){
            y[0]=600;
        }
        if(y[0]>600){
            y[0]=0;
        }

        for(int i=1;i<bodyParts;i++){
            if(x[0]==x[i] && y[0]==y[i]){
                running=false;
                break;
            }
        }
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
                // for reset/restart after game over
                case KeyEvent.VK_ENTER:
                    x=new int[WIDTH/UNIT];
                    y=new int[HEIGHT/UNIT];
                    direction='R';
                    applesEaten=0;
                    bodyParts=5;
                    running=true;
                    repaint();
                    break;
            }
        }
    }

    public void appleEatenSound() throws URISyntaxException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        URL soundURL = getClass().getResource("/appleEaten.wav");
        File soundFile=new File(soundURL.toURI());
        AudioInputStream inputStream= AudioSystem.getAudioInputStream(soundFile);

        Clip clip=AudioSystem.getClip();
        clip.open(inputStream);
        clip.start();
    }

    public void gameOverSound() throws URISyntaxException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(gameOver==1){ // to output the sound only once, avoid looping of sound
            URL soundURL = getClass().getResource("/gameOver.wav");
            File soundFile=new File(soundURL.toURI());
            AudioInputStream inputStream= AudioSystem.getAudioInputStream(soundFile);

            Clip clip=AudioSystem.getClip();
            clip.open(inputStream);
            clip.start();
        }
    }

    public void levelUpSound() throws URISyntaxException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(levelCounter.get(level)==1) { // so that this sound is played only once when level is up
            URL soundURL = getClass().getResource("/levelUp.wav");
            File soundFile = new File(soundURL.toURI());
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.start();
            levelCounter.put(level, levelCounter.get(level)+1);
        }
    }

}
