package com.bluuminn.myhome.character;

public class Character {
    private int inputVal;
    private String name;

    public int getInputVal() {
        return inputVal;
    }

    public String getName() {
        return this.name;
    }

    protected Character(String name) {
        this.name = name;
    }
}