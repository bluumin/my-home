package com.bluuminn.myhome.area;

public abstract class Area {
    private final String name;

    public Area(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void playSound() {
        // TODO: 사운드 출력
//        Music harvest = new Music("harvest.mp3", false);
//        harvest.start();
    }
}