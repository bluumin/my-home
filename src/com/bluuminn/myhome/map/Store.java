package com.bluuminn.myhome.map;

import com.bluuminn.myhome.character.Player;
import com.bluuminn.myhome.inventory.ItemEntry;
import com.bluuminn.myhome.item.Item;
import com.bluuminn.myhome.item.Potion;

import java.util.ArrayList;
import java.util.Random;

public class Store extends Area {
    boolean[] event;
    int rand;

    class StoreTimer implements Runnable {

        Store storeTimer;

        public void rand() {
            storeTimer = new Store();

            event = new boolean[storeTimer.storeItem.size()];
            for (int i = 0; i < event.length; i++) {
                event[i] = new Random().nextBoolean();
            }
            rand = new Random().nextInt(storeTimer.storeItem.size());
        }

        public StoreTimer(Store store) {
//        this.n = x;
//        this.timerTemp = x;
            this.storeTimer = store;
//        this.input = input;
        }

        public void run() {
            rand();
//        for (int i = n; i > 0; i--) {
//            try {
//                Thread.sleep(1000);
//                timerTemp--;
//            } catch (InterruptedException e) {
//                System.out.println("Error: " + e);
//            }
////            System.out.println(i);    // 몇초 남았는지 출력
//        }
            if (event[rand]) {
                System.out.println();
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t =====================================");
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t" + storeTimer.storeItem.get(rand).entryName + " 30% 할인 이벤트! ");
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t =====================================");

                storeTimer.storeItem.get(rand).entryPrice *= 0.7;
            }
        }
    }

    public ArrayList<ItemEntry> storeItem = new ArrayList<ItemEntry>();

//    // 원목 작업대
//    Item wooden = new Item("원목 작업대", "상점", 100);
//    ItemEntry woodenE = new ItemEntry(wooden, 0);

    // 양가죽
    Item sheepskin = new Item("양가죽", "상점", 400);
    ItemEntry sheepskinE = new ItemEntry(sheepskin, 0);

    // 소고기
    Item beef = new Item("소고기", "상점", 400);
    ItemEntry beefE = new ItemEntry(beef, 0);

    // 아스파라거스
    Item asparagus = new Item("아스파라거스", "상점", 300);
    ItemEntry asparagusE = new ItemEntry(asparagus, 0);

    // 소가죽
    Item cowhide = new Item("소가죽", "상점", 500);
    ItemEntry cowhideE = new ItemEntry(cowhide, 0);

    // 말가죽
    Item horsehide = new Item("말가죽", "상점", 500);
    ItemEntry horsehideE = new ItemEntry(horsehide, 0);

    // 피로도 30 회복 물약
    Potion recovery30 = new Potion("피로도 30 회복 물약", "상점", 2000, 30);
    ItemEntry recovery30E = new ItemEntry(recovery30, 0);

    // 피로도 70 회복 물약
    Potion recovery70 = new Potion("피로도 70 회복 물약", "상점", 5500, 70);
    ItemEntry recovery70E = new ItemEntry(recovery70, 0);

    // 피로도 100 회복 물약
    Potion recovery100 = new Potion("피로도 100 회복 물약", "상점", 10000, 100);
    ItemEntry recovery100E = new ItemEntry(recovery100, 0);

    public Store() {

        // 양가죽
        storeItem.add(sheepskinE);
        sheepskin.itemPrice = 400;

        // 소고기
        storeItem.add(beefE);
        beef.itemPrice = 400;

        // 아스파라거스
        storeItem.add(asparagusE);
        asparagus.itemPrice = 300;

        // 소가죽
        storeItem.add(cowhideE);
        cowhide.itemPrice = 500;

        // 말가죽
        storeItem.add(horsehideE);
        horsehide.itemPrice = 500;

        // 피로도 30 회복
        storeItem.add(recovery30E);
        recovery30.itemPrice = 2000;

        // 피로도 70 회복
        storeItem.add(recovery70E);
        recovery70.itemPrice = 5500;

        // 피로도 100 회복
        storeItem.add(recovery100E);
        recovery100.itemPrice = 10000;

    }

//    public void storeTimeSale(Player player, Store store){
//        Thread StoreTimer = new Thread(new StoreTimer(store));
//        StoreTimer.start();
//        store.storeTimeSale();
//    }

    // =================================================== 레벨 1~4 (원목작업대, 요리용 화덕) 여부 체크해서 출력 수정
    public void storePrint(Player player, Store store) {

//        Thread StoreTimer = new Thread(new StoreTimer(store));
//        StoreTimer.start();
//        System.out.println();
//        System.out.println(storeTimer.);
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("                 판매 아이템 목록\n");
        if (!player.woodenCK || !player.ovenCK) {
            if (player.woodenCK && !player.ovenCK) {
                if (player.level < 4) {
                    System.out.print("1. 요리용 화덕 (HOLD - LV.4 이상)\n\t\t\t\t\t\t\t\t\t");
                } else {
                    System.out.print("1. 요리용 화덕\n\t\t\t\t\t\t\t\t\t");
                }
                System.out.printf("%5d" + " 골드", 2400);
                System.out.println();
            } else {
                System.out.print("1. 원목 작업대\n\t\t\t\t\t\t\t\t\t");
                System.out.printf("%5d" + " 골드", 100);
                System.out.println();
                if (player.level < 4) {
                    System.out.print("2. 요리용 화덕 (HOLD - LV.4 이상)\n\t\t\t\t\t\t\t\t\t");
                } else {
                    System.out.print("2. 요리용 화덕\n\t\t\t\t\t\t\t\t\t");
                }
                System.out.printf("%5d" + " 골드", 2400);
                System.out.println();


            }
        } else {
            for (int i = 0; i < storeItem.size(); i++) {
                System.out.print(i + 1 + ". ");
                System.out.print(storeItem.get(i).entryName + "\n" + "\t\t\t\t\t\t\t");
                System.out.printf("%5d" + " 골드", storeItem.get(i).entryPrice);

                if (i < storeItem.size()) {
                    System.out.println();
                } else {
                    System.out.print(" ");
                }
            }
        }
    }
}