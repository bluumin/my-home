package com.bluuminn.myhome.timer;

import com.bluuminn.myhome.area.AnimalFarm;

public class AnimalTimer implements Runnable {
    int n;
    int timerTemp;
    int input;
    AnimalFarm animalFarm;

    public AnimalTimer(int x, AnimalFarm animalFarm, int input) {
        this.n = x;
        this.timerTemp = x;
        this.animalFarm = animalFarm;
        this.input = input;
    }

    public void run() {
        for (int i = n; i > 0; i--) {
            try {
                Thread.sleep(1000);
                timerTemp--;
            } catch (InterruptedException e) {
                System.out.println("Error: " + e);
            }
//            System.out.println(i);    // 몇초 남았는지 출력
        }
        if (timerTemp <= 0) {
            System.out.println();
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t =====================================");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + animalFarm.animalList.get(input - 1) + " 길들이기 완료!");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t =====================================");
        }
//        farm.listOfItems.get(input - 1).harvestCK = true;
        animalFarm.listOfItems.get(input - 1).growingTime = 0;
    }
}