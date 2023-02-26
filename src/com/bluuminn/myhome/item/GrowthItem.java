package com.bluuminn.myhome.item;

public class GrowthItem extends Item {
    // 아이템 수확 가능 여부
    private boolean isHarvestable;

    // 아이템 심었는지 여부
    private boolean isPlanted;

    // 다 자라는데 걸리는 시간
    private int growingPeriod;

    // 다 자라는데 걸리는 시간 기본 값
    private int defaultTime;

    // 한번에 획득할 수 있는 횟수 카운트
    private byte harvestCount;

    public GrowthItem(String name, String areaOfProduction, int level, int salePrice, int cost, int exp) {
        super(name, ItemType.CULTIVATE, areaOfProduction, level, salePrice, cost, exp);
        this.isHarvestable = false;
        this.isPlanted = false;
        this.harvestCount = 3;
    }

    public boolean isHarvestable() {
        return isHarvestable;
    }

    public boolean isPlanted() {
        return isPlanted;
    }
}