package com.bluuminn.myhome.harvestgame;

import com.bluuminn.myhome.area.AnimalFarm;
import com.bluuminn.myhome.area.Farm;
import com.bluuminn.myhome.area.Forest;

import java.util.Random;

public class Game { // 전체적인 게임 진행
    public static String array[][] = new String[10][15];
    Bear bear;
    Fish fish;
    int itemX = 0;
    int itemY = 0;

    public void targetSet() {
        while (itemX == 0 && itemY == 0) {
//            if (itemX == 0 && itemY == 0) {
            itemX = new Random().nextInt(10);
            itemY = new Random().nextInt(15);
//            }
        }
    }


    public void Set() { // 게임 초기 설정
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

    public void ShowArray() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }

    public void WinArray() { // 이겼을 경우 보여줄 배열(플레이어가 아이템에 닿으면 아이템 자리에 플레이어 모양 출력)
        array[bear.x][bear.y] = bear.getShape();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }

    public boolean Run(Farm farm, int inputVal) {
        Set();
        boolean result = false;
        boolean exit = true;
        while (exit) {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println(farm.listOfItems.get(inputVal - 1).itemName + " 수확 중 . . .");
            System.out.println();
            ShowArray();
            exit = bear.move();
            if ((bear.collide(fish)) == true) {
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
                System.out.println(farm.listOfItems.get(inputVal - 1).itemName + " 수확 중 . . .");
                System.out.println();
                WinArray();
//                System.out.println("아이템 수확");
                exit = false;
                result = true;
            }
        }
        return result;
    }
    public boolean Run(AnimalFarm animalFarm, int inputVal) {
        Set();
        boolean result = false;
        boolean exit = true;
        while (exit) {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println(animalFarm.listOfItems.get(inputVal - 1).itemName + " 수확 중 . . .");
            System.out.println();
            ShowArray();
            exit = bear.move();
            if ((bear.collide(fish)) == true) {
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
                System.out.println(animalFarm.listOfItems.get(inputVal - 1).itemName + " 수확 중 . . .");
                System.out.println();
                WinArray();
//                System.out.println("아이템 수확");
                exit = false;
                result = true;
            }
        }
        return result;
    }
    public boolean Run(Forest forest, int inputVal) {
        Set();
        boolean result = false;
        boolean exit = true;
        while (exit) {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println(forest.listOfItems.get(inputVal - 1).itemName + " 수확 중 . . .");
            System.out.println();
            ShowArray();
            exit = bear.move();
            if ((bear.collide(fish)) == true) {
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = =");
                System.out.println(forest.listOfItems.get(inputVal - 1).itemName + " 수확 중 . . .");
                System.out.println();
                WinArray();
//                System.out.println("아이템 수확");
                exit = false;
                result = true;
            }
        }
        return result;
    }
}