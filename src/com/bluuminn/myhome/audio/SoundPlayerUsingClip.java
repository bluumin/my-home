package com.bluuminn.myhome.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayerUsingClip implements LineListener {

    boolean isPlaybackCompleted;

    @Override
    public void update(LineEvent event) {
//        if (LineEvent.Type.START == event.getType()) {
//            System.out.println("Playback started.");
//        }
        if (LineEvent.Type.STOP == event.getType()) {
            isPlaybackCompleted = true;
            System.out.println("Playback completed.");
        }
    }




    public void play(String audioFilePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        InputStream inputStream = getClass().getResourceAsStream(audioFilePath);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);

        Clip audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.addLineListener(this);
        audioClip.open(audioStream);
        audioClip.start();
        audioClip.close();
        audioStream.close();
    }
}
