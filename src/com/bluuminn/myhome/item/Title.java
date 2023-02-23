package com.bluuminn.myhome.item;

public class Title {
    private String titleName;
    private String achievementConditions;
    private boolean obtainCK;

    public Title(String name) {
        this.titleName = name;
        this.obtainCK = false;
    }
}