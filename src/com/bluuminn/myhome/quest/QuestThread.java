package com.bluuminn.myhome.quest;

import com.bluuminn.myhome.character.Player;

import java.util.List;

public class QuestThread implements Runnable {
    private QuestStorage storage;
    private Player player;

    public QuestThread(Player player) {
        this.player = player;
        storage = new QuestStorage();
    }

    @Override
    public void run() {
        int questQuantity = storage.getQuestQuantity();
        List<QuestInfo> quests = storage.getQuestInfos();
        while (player.getQuests().size() <= questQuantity) {
            int randomNumber = (int) (Math.random() * questQuantity);
            QuestInfo questInfo = quests.get(randomNumber);
            if (player.hasQuest(questInfo)) {
                continue;
            }
            System.out.println("\n\t\t\t\t\t\t\t\t\t\t\t\t =====================================");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + questInfo.getNpc().getName() + " 퀘스트 도착!!");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t   📜 퀘스트 리스트를 확인하세요");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t =====================================");
            player.addQuest(Quest.create(questInfo));
        }
    }
}