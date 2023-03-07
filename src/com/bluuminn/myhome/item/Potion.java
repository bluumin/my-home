package com.bluuminn.myhome.item;

import com.bluuminn.myhome.character.Player;

public class Potion extends StoreItem {
    private final int recovery;   // 회복량

    public Potion(String name, String areaOfProduction, int price, int recovery) {
        super(name, areaOfProduction, price);
        this.recovery = recovery;
    }

    public void calculateRecoveryAmount(Player player) {
        int fatigability = player.getFatigability();
        fatigability -= recovery;

        if (fatigability < 0) {
            fatigability = 0;
        }
        player.updateFatigability(fatigability);
        System.out.println(getName() + " 아이템을 사용했어요.");
    }
}