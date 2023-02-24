package com.bluuminn.myhome.inventory;

import com.bluuminn.myhome.item.GrowthItem;
import com.bluuminn.myhome.item.Item;
import com.bluuminn.myhome.item.MadeItem;
import com.bluuminn.myhome.item.Potion;

public class ItemEntry {

    // 아이템 객체
    private Item item;
    private Potion potion;
    private GrowthItem growthItem;
    private MadeItem madeItem;

    // 아이템 레벨
    private int entryLevel;

    // 아이템 살수 있는지 없는지 체크
    private boolean entryLevelCK;

    // 아이템 객체 하나당 갖고 있는 개수
    private int count;

    // 아이템 타입
    private String entryType;

    // 아이템 이름
    private String entryName;

    // 아이템 가격
    private int entryPrice;

    public ItemEntry(Item item, int count) {
        this.item = item;
        this.count = count;
        this.entryType = item.type;
        this.entryName = item.name;
        this.entryPrice = item.price;
    }

    public ItemEntry(Potion potion, int count) {
        this.potion = potion;
        this.count = count;
        this.entryType = potion.type;
        this.entryName = potion.name;
        this.entryPrice = potion.price;
    }

    public ItemEntry(GrowthItem growthItem, int count) {
        this.growthItem = growthItem;
        this.count = count;
        this.entryType = growthItem.type;
        this.entryName = growthItem.name;
        this.entryPrice = growthItem.price;
    }

    public ItemEntry(MadeItem madeItem, int count) {
        this.madeItem = madeItem;
        this.count = count;
        this.entryType = madeItem.type;
        this.entryName = madeItem.name;
        this.entryPrice = madeItem.price;
    }

}