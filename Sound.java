// play sound
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[8];

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/intro.wav");
        soundURL[1] = getClass().getResource("/sound/school.wav");
        soundURL[2] = getClass().getResource("/sound/question.wav");
        soundURL[3] = getClass().getResource("/sound/slap.wav");
        soundURL[4] = getClass().getResource("/sound/door.wav");
        soundURL[5] = getClass().getResource("/sound/jumpscare.wav");
        soundURL[6] = getClass().getResource("/sound/donefor.wav");
        soundURL[7] = getClass().getResource("/sound/endgame.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            
        }
    }

    public void play() {
        clip.start();
    }

    public void loop () {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
