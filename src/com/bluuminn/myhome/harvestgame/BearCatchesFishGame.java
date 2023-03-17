package com.bluuminn.myhome.harvestgame;

import com.bluuminn.myhome.etc.MyHomeUtils;
import com.bluuminn.myhome.item.Item;

import java.util.Arrays;
import java.util.Random;

public class BearCatchesFishGame {
    private final String[][] board = new String[10][15];
    private Bear bear;
    private Fish fish;

    public Fish getPositionedFish() {
        int x = 0, y = 0;
        while (x == 0 && y == 0) {
            x = new Random().nextInt(10);
            y = new Random().nextInt(15);
        }
        return new Fish(x, y);
    }


    public void set() { // 게임 초기 설정
        bear = new Bear(0, 0);
        fish = getPositionedFish();
        for (String[] strings : board) {
            Arrays.fill(strings, "_");
        }
        board[bear.getX()][bear.getY()] = bear.getShape();
        board[fish.getX()][fish.getY()] = fish.getShape();
    }

    private void showArray() {
        for (String[] strings : board) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
    }

    private void winArray() { // 이겼을 경우 보여줄 배열(플레이어가 아이템에 닿으면 아이템 자리에 플레이어 모양 출력)
        board[bear.getX()][bear.getY()] = bear.getShape();
        for (String[] strings : board) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
    }

    public void start(Item item) {
        set();
        while (true) {
            MyHomeUtils.printLineAsCount(100);
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println("        " + item.getName() + " 수확중 ...");
            System.out.println();
            if (bear.collide(fish)) {
                winArray();
                break;
            }
            showArray();
            bear.move(board);
        }
    }
}