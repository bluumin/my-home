package com.bluuminn.myhome.item;

import com.bluuminn.myhome.character.Player;

public class Potion extends Item {
    int recovery;   // 회복량

    public Potion(String name, String areaOfProduction, int price, int recov) {
        super(name, areaOfProduction, price);
        this.type = "포션";
        this.recovery = recov;
    }

    // 피로도 회복
    public void recoveryFat(Player player) {

        player.fatigability -= recovery;

        if (player.fatigability < 0) {
            player.fatigability = 0;
        }

        System.out.println("아이템을 사용했어요.");
    }
}