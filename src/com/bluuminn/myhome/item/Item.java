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
    private int price;

    // 아이템 심거나 만들 때 비용
    private int cost;

    // 아이템을 수확하거나 제작하면 얻는 경험치
    private int exp;

    // 아이템을 수확하거나 제작할수 있는지 여부
    private boolean levelCK;


    public Item(String name, String productionArea, int price) {
        this.name = name;
        this.productionArea = productionArea;
        this.price = price;
    }
}