package com.bluuminn.myhome.timer;

import com.bluuminn.myhome.area.Forest;

public class ForestTimer implements Runnable {
    int n;
    int timerTemp;
    int input;
    Forest forest;

    public ForestTimer(int x, Forest forest, int input) {
        this.n = x;
        this.timerTemp = x;
        this.forest = forest;
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
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + forest.listOfItems.get(input - 1).itemName + " 기르기 완료!");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t =====================================");
        }
        forest.listOfItems.get(input - 1).growingTime = 0;
    }
}