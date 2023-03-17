package com.bluuminn.myhome.harvestgame;

class Fish extends MiniGamePiece {
    public Fish(int x, int y) {
        super(x, y, 0);
    }

    @Override
    public void move(String[][] board) {
    }

    @Override
    public String getShape() {
        return "🐟";
    }
}