package com.bluuminn.myhome.item;

import com.bluuminn.myhome.inventory.ItemEntry;

import java.util.List;

public class CraftItem extends Item {

    private final ItemEntry[] requiredItems;

    private CraftItem(String name, String resource, String areaOfProduction, int level, int salePrice, int cost, int exp, ItemEntry... requiredItems) {
        super(name, resource, ItemType.CRAFTING, areaOfProduction, level, salePrice, cost, exp);
        this.requiredItems = requiredItems;
    }

    public static CraftItem of(String name, String resource, String areaOfProduction, int level, int salePrice, int cost, int exp, ItemEntry... requiredItems) {
        return new CraftItem(name, resource, areaOfProduction, level, salePrice, cost, exp, requiredItems);
    }
}