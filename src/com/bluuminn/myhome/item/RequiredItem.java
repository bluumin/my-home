package com.bluuminn.myhome.item;

import com.bluuminn.myhome.inventory.ItemEntry;

public class RequiredItem {
    public ItemEntry itemEntry;
    public int howManyItems;

    public RequiredItem(ItemEntry item, int howMany) {
        this.itemEntry = item;
        this.howManyItems = howMany;
    }
}