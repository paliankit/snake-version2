import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class SoundPOC {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, URISyntaxException, InterruptedException {
       // File soundFile=new File("/appleEaten.wav");
//        File soundFile=new File(getClass().getResource("/appleEaten.wav"));
        SoundPOC soundPOC=new SoundPOC();
        File soundFile=soundPOC.getSoundFile();
        AudioInputStream inputStream= AudioSystem.getAudioInputStream(soundFile);

        Clip clip=AudioSystem.getClip();
        clip.open(inputStream);
        clip.start();
        //while (clip.isRunning()) {
            Thread.sleep(5000);
        //}
        clip.close();

    }

    public File getSoundFile() throws URISyntaxException {
        URL soundURL = getClass().getResource("/appleEaten.wav");
        File soundFile=new File(soundURL.toURI());
        return soundFile;
    }
}
