package com.bluuminn.myhome.audio;

import com.bluuminn.myhome.main.GameStart;
import com.bluuminn.myhome.main.MyHomeMain;
import com.bluuminn.myhome.map.Map;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Music extends Thread {
    //public class Music implements Runnable {
    private Player player;
    private boolean isLoop;
    private File fileMain;
    private File fileMap;
    private File fileStartPoint;
    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;

    public Music(String name, boolean isLoop) {
        try {
            this.isLoop = isLoop;
            fileMain = new File(MyHomeMain.class.getResource("../audio/" + name).toURI());
            fileInputStream = new FileInputStream(fileMain);

            fileMap = new File(Map.class.getResource("../audio/" + name).toURI());
            fileInputStream = new FileInputStream(fileMap);

            fileStartPoint = new File(GameStart.class.getResource("../audio/" + name).toURI());
            fileInputStream = new FileInputStream(fileStartPoint);

            bufferedInputStream = new BufferedInputStream(fileInputStream);
            player = new Player(bufferedInputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int getTime() {
        if (player == null)
            return 0;
        return player.getPosition();
    }

    public void close(){
        isLoop = false;
        player.close();
        this.interrupt();
    }

    @Override
    public void run(){
        try{
            do{
                fileInputStream = new FileInputStream(fileMain);
                fileInputStream = new FileInputStream(fileMap);
                fileInputStream = new FileInputStream(fileStartPoint);

                bufferedInputStream = new BufferedInputStream(fileInputStream);

                player = new Player(bufferedInputStream);
                player.play();
            } while(isLoop);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}