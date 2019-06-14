package alex.shooter;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

class Sound {
    private static final String[][] SOUND = {{"Hit 1.wav", "Hit 2.wav", "Hit 3.wav", "Hit 4.wav", "Hit 5.wav"}, {"AR.wav"}, {"Shotgun 1.wav", "Shotgun 2.wav", "Shotgun 3.wav", "Shotgun 4.wav", "Shotgun 5.wav"}, {"Theme.wav"}};
    private static Clip clip;
    private static int soundPlaying;

    private Sound() {
    }

    public static void playSound(int id) {
        try {
            soundPlaying = id;
            File soundFile = new File(SOUND[id][new Random().nextInt(SOUND[id].length)]);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();//This plays the audio
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void stopSound(int id) {
        if (soundPlaying == id){
            clip.stop();
        }
    }
}
