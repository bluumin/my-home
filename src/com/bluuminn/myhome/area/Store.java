package com.bluuminn.myhome.area;

import com.bluuminn.myhome.character.Merchant;
import com.bluuminn.myhome.item.Potion;
import com.bluuminn.myhome.item.StoreItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Store extends Area {

    private final Merchant merchant = Merchant.createMerchant("로빈");

    private final List<StoreItem> onSaleItems = new ArrayList<>();

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

    public Store() {
        super("상점");
        String areaName = getName();

        // TODO: 원목 작업대 판매 가능하도록 작업하기
        // Item wooden = new Item("원목 작업대", "상점", 100);

        // 양가죽
        StoreItem sheepskin = new StoreItem("양가죽", areaName, 400);
        // 소고기
        StoreItem beef = new StoreItem("소고기", areaName, 400);
        // 아스파라거스
        StoreItem asparagus = new StoreItem("아스파라거스", areaName, 300);
        // 소가죽
        StoreItem cowhide = new StoreItem("소가죽", areaName, 500);
        // 말가죽
        StoreItem horsehide = new StoreItem("말가죽", areaName, 500);
        // 피로도 30 회복 물약
        Potion recovery30 = new Potion("피로도 30 회복 물약", areaName, 2000, 30);
        // 피로도 70 회복 물약
        Potion recovery70 = new Potion("피로도 70 회복 물약", areaName, 5500, 70);
        // 피로도 100 회복 물약
        Potion recovery100 = new Potion("피로도 100 회복 물약", areaName, 10000, 100);

        onSaleItems.add(sheepskin);
        onSaleItems.add(beef);
        onSaleItems.add(asparagus);
        onSaleItems.add(cowhide);
        onSaleItems.add(horsehide);
        onSaleItems.add(recovery30);
        onSaleItems.add(recovery70);
        onSaleItems.add(recovery100);
    }

//    public void storeTimeSale(Player player, Store store){
//        Thread StoreTimer = new Thread(new StoreTimer(store));
//        StoreTimer.start();
//        store.storeTimeSale();
//    }

    // =================================================== 레벨 1~4 (원목작업대, 요리용 화덕) 여부 체크해서 출력 수정
}