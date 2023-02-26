package com.bluuminn.myhome.timer;

import com.bluuminn.myhome.area.Farm;

public class Timer implements Runnable {
    int n;
    int timerTemp;
    int input;
    Farm farm;

    public Timer(int x, Farm farm, int input) {
        this.n = x;
        this.timerTemp = x;
        this.farm = farm;
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
        }
        if (timerTemp <= 0) {
            System.out.println();
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t =====================================");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + farm.listOfItems.get(input - 1).name + " 재배 완료!");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t =====================================");
        }

        farm.listOfItems.get(input - 1).growingPeriod = 0;
    }
}