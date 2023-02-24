package com.bluuminn.myhome.quest;

import com.bluuminn.myhome.character.Player;
import com.bluuminn.myhome.etc.MyHomeUtils;

public class AchieveTitleThread implements Runnable {

    // TODO: 캐릭터 생성 후 시작하도록
    private final Player player;

    public AchieveTitleThread(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        while (true) {
            MyHomeUtils.delayAsMillis(1000);
            player.achieveTitle();
        }
    }
}
