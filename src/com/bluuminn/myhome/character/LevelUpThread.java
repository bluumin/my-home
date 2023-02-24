package com.bluuminn.myhome.character;

import com.bluuminn.myhome.etc.MyHomeUtils;

public class LevelUpThread implements Runnable {
    // TODO: 캐릭터 생성 후 시작하도록
    private final Player player;

    public LevelUpThread(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        while (true) {
            MyHomeUtils.delayAsMillis(1000);
            player.levelUp();
        }
    }
}
