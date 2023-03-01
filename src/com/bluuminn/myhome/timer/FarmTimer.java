package com.bluuminn.myhome.timer;

import com.bluuminn.myhome.item.GrowthItem;

public class FarmTimer implements Runnable {
    private final GrowthItem item;

    public FarmTimer(GrowthItem item) {
        this.item = item;
    }

    public void run() {
        int growingPeriod = item.getGrowingPeriod();
        int countNumber = growingPeriod;
        for (int i = 0; i < growingPeriod; i++) {
            try {
                Thread.sleep(1000);
                countNumber--;
            } catch (InterruptedException ignore) {
            }
        }
        if (countNumber <= 0) {
            System.out.println();
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t =====================================");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + item.getName() + " 재배 완료!");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t =====================================");
        }
        item.harvestable();
    }
}