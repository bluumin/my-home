package com.bluuminn.myhome.item;

import com.bluuminn.myhome.inventory.ItemEntry;

import java.util.ArrayList;

public class CraftItem extends Item {

    public ArrayList<ItemEntry> requiredItems = new ArrayList<>();

    public CraftItem(String name, String areaOfProduction, int level, int salePrice, int cost, int exp) {
        super(name, ItemType.CRAFTING, areaOfProduction, level, salePrice, cost, exp);
    }
}