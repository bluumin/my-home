package com.bluuminn.myhome.inventory;

import com.bluuminn.myhome.item.Item;

public class ItemEntry {

    // 아이템 객체
    private Item item;

    // 아이템 객체 하나당 갖고 있는 개수
    private int quantity;

    public ItemEntry(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}