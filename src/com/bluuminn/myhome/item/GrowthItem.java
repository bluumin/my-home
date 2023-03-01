package com.bluuminn.myhome.item;

public class GrowthItem extends Item {
    // 아이템 수확 가능 여부
    private boolean isHarvestable;

    // 아이템 심었는지 여부
    private boolean isPlanted;

    // 다 자라는데 걸리는 시간
    private int growingPeriod;

    // 한번에 획득할 수 있는 횟수 카운트
    private int harvestCount;

    private int harvestRemainQuantity;

    public GrowthItem(String name, String productionArea, int level, int salePrice, int cost, int exp, int growingPeriod) {
        super(name, ItemType.CULTIVATE, productionArea, level, salePrice, cost, exp);
        this.isHarvestable = false;
        this.isPlanted = false;
        this.growingPeriod = growingPeriod;
        this.harvestCount = 3;
        this.harvestRemainQuantity = harvestCount;
    }

    public boolean isHarvestable() {
        return isHarvestable;
    }

    public void harvestable() {
        this.isHarvestable = true;
    }

    public void initHarvestable() {
        this.isHarvestable = false;
        this.harvestRemainQuantity = harvestCount;
    }

    public boolean isPlanted() {
        return isPlanted;
    }

    public void plant() {
        this.isPlanted = true;
    }

    public void initPlanted() {
        this.isPlanted = false;
    }

    public int getGrowingPeriod() {
        return growingPeriod;
    }

    public int getHarvestCount() {
        return harvestCount;
    }

    public int getHarvestRemainQuantity() {
        return harvestRemainQuantity;
    }

    public void decreaseHarvestRemainQuantityBy1() {
        this.harvestRemainQuantity--;
    }

    public boolean isPlantable(int playerLevel) {
        return playerLevel >= getLevel();
    }
}