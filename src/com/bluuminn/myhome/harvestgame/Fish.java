package com.bluuminn.myhome.harvestgame;

class Fish extends GameObject {
    public Fish(int startX, int startY, int distance) {
        super(startX, startY, distance);
    }

    public boolean move() {
        return true;
    }

    @Override
    public String getShape() {
        return "♣";
    }
}