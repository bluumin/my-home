package com.bluuminn.myhome.item;

import com.bluuminn.myhome.character.Player;

public class Potion extends StoreItem {
    private final int recovery;   // 회복량

    public Potion(String name, String areaOfProduction, int price, int recovery) {
        super(name, ItemType.CONSUMPTION, areaOfProduction, price);
        this.recovery = recovery;
    }

    public int getRecovery() {
        return recovery;
    }
}