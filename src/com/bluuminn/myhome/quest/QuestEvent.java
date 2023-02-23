package com.bluuminn.myhome.quest;

import java.util.Random;

public class QuestEvent extends Thread {
    int countTime;

    private int getRand(int from, int to) {
        return (int) (Math.random() * (Math.abs(to - from) + 1)) + Math.min(from, to);
    }

    public void run() {
        while (npcQuestList.size() > 0) {
            countTime = questEvent.getRand(10, 300);
            System.out.println();
//                System.out.println(name + " " + countTime);

            for (int i = countTime; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Error: " + e);
                }
//                System.out.println(i);    // 몇초 남았는지 출력
            }
            System.out.println("\n\t\t\t\t\t\t\t\t\t\t\t\t =====================================");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + name + " 퀘스트 도착!!");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t   📜 퀘스트 리스트를 확인하세요");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t =====================================");
            int tmp = npcQuestList.size();
            int rand = new Random().nextInt(tmp);
            tmpPlayer.playerQuestList.add(npcQuestList.get(rand));
            npcQuestList.remove(rand);
        }
    }
}