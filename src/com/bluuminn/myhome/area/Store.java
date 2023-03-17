package com.bluuminn.myhome.area;

import com.bluuminn.myhome.item.ItemStorage;

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

    public Store(ItemStorage itemStorage) {
        super("상점");
        // TODO: 원목 작업대 판매 가능하도록 작업하기
    }

//    public void storeTimeSale(Player player, Store store){
//        Thread StoreTimer = new Thread(new StoreTimer(store));
//        StoreTimer.start();
//        store.storeTimeSale();
//    }

    // =================================================== 레벨 1~4 (원목작업대, 요리용 화덕) 여부 체크해서 출력 수정
}