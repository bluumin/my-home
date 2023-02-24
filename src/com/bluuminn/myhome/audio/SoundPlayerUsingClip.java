package com.bluuminn.myhome.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayerUsingClip implements LineListener {

    private boolean isPlaybackCompleted;
    private AudioInputStream audioStream;
    private Clip audioClip;
    private Long clipTime;

    @Override
    public void update(LineEvent event) {
        if (LineEvent.Type.START == event.getType()) {
            System.out.println("Playback started.");
        }
        if (LineEvent.Type.STOP == event.getType()) {
            isPlaybackCompleted = true;
            System.out.println("Playback completed.");
        }
    }

    public void play(String audioFilePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        InputStream inputStream = getClass().getResourceAsStream(audioFilePath);
        audioStream = AudioSystem.getAudioInputStream(inputStream);
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);

        audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.addLineListener(this);
        audioClip.open(audioStream);
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
        audioClip.start();
    }

    public void stop() throws IOException {
        audioClip.stop();
        audioClip.close();
        audioStream.close();
    }

    public void pause() {
        clipTime = audioClip.getMicrosecondPosition();
        audioClip.stop();
    }

    public void resume() {
        if (clipTime == null) {
            System.out.println("음악 일시정지 된 적 없음");
        }
        audioClip.setMicrosecondPosition(clipTime);
        audioClip.start();
    }
}
