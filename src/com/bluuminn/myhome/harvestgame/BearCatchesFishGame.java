package com.bluuminn.myhome.harvestgame;

import com.bluuminn.myhome.area.AnimalFarm;
import com.bluuminn.myhome.area.Forest;
import com.bluuminn.myhome.etc.MyHomeUtils;
import com.bluuminn.myhome.item.Item;

import java.util.Arrays;
import java.util.Random;

public class BearCatchesFishGame {
    public static String[][] array = new String[10][15];
    private Bear bear;
    private Fish fish;
    private int itemX = 0;
    private int itemY = 0;

    public void setTarget() {
        while (itemX == 0 && itemY == 0) {
            itemX = new Random().nextInt(10);
            itemY = new Random().nextInt(15);
        }
    }


    public void set() { // 게임 초기 설정
        setTarget();
        bear = new Bear(0, 0, 1); // 처음 위치 (0, 0), 이동 거리 1.
        fish = new Fish(itemX, itemY, 1); // 처음 위치 (itemX, itemY) 이동 거리 1.
        for (String[] strings : array) {
            Arrays.fill(strings, "_");
        }
        array[bear.x][bear.y] = bear.getShape();
        array[fish.x][fish.y] = fish.getShape();
    }

    private void showArray() {
        for (String[] strings : array) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
    }

    private void winArray() { // 이겼을 경우 보여줄 배열(플레이어가 아이템에 닿으면 아이템 자리에 플레이어 모양 출력)
        array[bear.x][bear.y] = bear.getShape();
        for (String[] strings : array) {
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
            System.out.println(item.getName() + " 수확 중 . . .");
            System.out.println();
            if (bear.collide(fish)) {
                winArray();
                break;
            }
            showArray();
            bear.move();
        }
    }

    public boolean start(AnimalFarm animalFarm, int inputVal) {
        set();
        boolean result = false;
        boolean exit = true;
        while (exit) {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println(animalFarm.items.get(inputVal - 1).getName() + " 수확 중 . . .");
            System.out.println();
            showArray();
            exit = bear.move();
            if ((bear.collide(fish))) {
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
                System.out.println(animalFarm.items.get(inputVal - 1).getName() + " 수확 중 . . .");
                System.out.println();
                winArray();
//                System.out.println("아이템 수확");
                exit = false;
                result = true;
            }
        }
        return result;
    }

    public boolean start(Forest forest, int inputVal) {
        set();
        boolean result = false;
        boolean exit = true;
        while (exit) {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println(forest.items.get(inputVal - 1).getName() + " 수확 중 . . .");
            System.out.println();
            showArray();
            exit = bear.move();
            if ((bear.collide(fish))) {
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
                System.out.println(forest.items.get(inputVal - 1).getName() + " 수확 중 . . .");
                System.out.println();
                winArray();
//                System.out.println("아이템 수확");
                exit = false;
                result = true;
            }
        }
        return result;
    }
}