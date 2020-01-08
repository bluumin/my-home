package com.bluuminn.myhome.inventory;

import com.bluuminn.myhome.item.GrowthItem;
import com.bluuminn.myhome.item.Item;
import com.bluuminn.myhome.item.MadeItem;
import com.bluuminn.myhome.item.Potion;

public class ItemEntry {

    // 아이템 객체
    public Item item;
    public Potion potion;
    public GrowthItem growthItem;
    public MadeItem madeItem;

    // 아이템 레벨
    public int entryLevel;

    // 아이템 살수 있는지 없는지 체크
    public boolean entryLevelCK;

    // 아이템 객체 하나당 갖고 있는 개수
    public int count;

    // 아이템 타입
    public String entryType;

    // 아이템 이름
    public String entryName;

    // 아이템 가격
    public int entryPrice;

    public ItemEntry(Item item, int count) {
        this.item = item;
        this.count = count;
        this.entryType = item.type;
        this.entryName = item.itemName;
        this.entryPrice = item.itemPrice;
    }

    public ItemEntry(Potion potion, int count) {
        this.potion = potion;
        this.count = count;
        this.entryType = potion.type;
        this.entryName = potion.itemName;
        this.entryPrice = potion.itemPrice;
    }

    public ItemEntry(GrowthItem growthItem, int count) {
        this.growthItem = growthItem;
        this.count = count;
        this.entryType = growthItem.type;
        this.entryName = growthItem.itemName;
        this.entryPrice = growthItem.itemPrice;
    }

    public ItemEntry(MadeItem madeItem, int count) {
        this.madeItem = madeItem;
        this.count = count;
        this.entryType = madeItem.type;
        this.entryName = madeItem.itemName;
        this.entryPrice = madeItem.itemPrice;

    }

}