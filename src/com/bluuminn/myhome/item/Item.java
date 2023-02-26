package com.bluuminn.myhome.item;

public class Item {
    // 아이템 이름
    private String name;

    private ItemType type;

    // 아이템 획득지
    private String productionArea;

    // 아이템 레벨. (플레이어 레벨이 아이템 레벨이 되어야 획득 가능)
    private int level;

    // 아이템 가격 (상점 가격)
    private int salePrice;

    // 아이템 심거나 만들 때 비용
    private int cost;

    // 아이템을 수확하거나 제작하면 얻는 경험치
    private int exp;

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getCost() {
        return cost;
    }

    public int getExp() {
        return exp;
    }


    public Item(String name, ItemType type, String productionArea, int level, int salePrice, int cost, int exp) {
        this.name = name;
        this.type = type;
        this.productionArea = productionArea;
        this.level = level;
        this.salePrice = salePrice;
        this.cost = cost;
        this.exp = exp;
    }

    public Item(String name, ItemType type, String productionArea, int salePrice) {
        this.name = name;
        this.type = type;
        this.productionArea = productionArea;
        this.salePrice = salePrice;
    }
}