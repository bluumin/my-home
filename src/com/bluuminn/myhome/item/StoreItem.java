package com.bluuminn.myhome.item;

public class StoreItem extends Item {
    public StoreItem(String name, String productionArea, int price) {
        super(name, "상점", ItemType.STORE, productionArea, price);
    }
}
