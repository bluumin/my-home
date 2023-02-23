package com.bluuminn.myhome.audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class Music extends Thread {
    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;

    @Override
    public void run() {
        try {
            do {
                fileInputStream = new FileInputStream(fileMain);
                fileInputStream = new FileInputStream(fileMap);
                fileInputStream = new FileInputStream(fileStartPoint);

                bufferedInputStream = new BufferedInputStream(fileInputStream);

                player = new Player(bufferedInputStream);
                player.play();
            } while (isLoop);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}