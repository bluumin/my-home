package com.bluuminn.myhome.harvestgame;

import com.bluuminn.myhome.area.AnimalFarm;
import com.bluuminn.myhome.area.Farm;
import com.bluuminn.myhome.area.Forest;

import java.util.Random;

public class Game {
    public static String[][] array = new String[10][15];
    private Bear bear;
    private Fish fish;
    private int itemX = 0;
    private int itemY = 0;

    public void targetSet() {
        while (itemX == 0 && itemY == 0) {
            itemX = new Random().nextInt(10);
            itemY = new Random().nextInt(15);
        }
    }


    public void set() { // 게임 초기 설정
        targetSet();
        bear = new Bear(0, 0, 1); // 처음 위치 (0, 0), 이동 거리 1.
        fish = new Fish(itemX, itemY, 1); // 처음 위치 (itemX, itemY) 이동 거리 1.
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = "_";
            }
        }
        array[bear.x][bear.y] = bear.getShape();
        array[fish.x][fish.y] = fish.getShape();
    }

    public void showArray() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }

    public void winArray() { // 이겼을 경우 보여줄 배열(플레이어가 아이템에 닿으면 아이템 자리에 플레이어 모양 출력)
        array[bear.x][bear.y] = bear.getShape();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }

    public boolean run(Farm farm, int inputVal) {
        set();
        boolean result = false;
        boolean exit = true;
        while (exit) {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println(farm.listOfItems.get(inputVal - 1).getName() + " 수확 중 . . .");
            System.out.println();
            showArray();
            exit = bear.move();
            if ((bear.collide(fish))) {
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
                System.out.println(farm.listOfItems.get(inputVal - 1).getName() + " 수확 중 . . .");
                System.out.println();
                winArray();
//                System.out.println("아이템 수확");
                exit = false;
                result = true;
            }
        }
        return result;
    }

    public boolean run(AnimalFarm animalFarm, int inputVal) {
        set();
        boolean result = false;
        boolean exit = true;
        while (exit) {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println(animalFarm.listOfItems.get(inputVal - 1).getName() + " 수확 중 . . .");
            System.out.println();
            showArray();
            exit = bear.move();
            if ((bear.collide(fish))) {
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
                System.out.println(animalFarm.listOfItems.get(inputVal - 1).getName() + " 수확 중 . . .");
                System.out.println();
                winArray();
//                System.out.println("아이템 수확");
                exit = false;
                result = true;
            }
        }
        return result;
    }

    public boolean run(Forest forest, int inputVal) {
        set();
        boolean result = false;
        boolean exit = true;
        while (exit) {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println(forest.listOfItems.get(inputVal - 1).getName() + " 수확 중 . . .");
            System.out.println();
            showArray();
            exit = bear.move();
            if ((bear.collide(fish))) {
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
                System.out.println(forest.listOfItems.get(inputVal - 1).getName() + " 수확 중 . . .");
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