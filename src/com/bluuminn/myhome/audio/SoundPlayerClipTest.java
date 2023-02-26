package com.bluuminn.myhome.audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class SoundPlayerClipTest {

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        SoundPlayerUsingClip clip = new SoundPlayerUsingClip();
        clip.play("mainMusic.wav");
        Thread.sleep(5000);
        clip.pause();
        Thread.sleep(2000);
        clip.resume();
    }
}
