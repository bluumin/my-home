package com.bluuminn.myhome.item;

public class Item {
    // 아이템 이름
    public String itemName;
    // 아이템 획득지
    String areaOfProduction;
    // 아이템 레벨. (플레이어 레벨이 아이템 레벨이 되어야 획득 가능)
    public byte level;
    // 아이템 가격 (상점 가격)
    public int itemPrice;
    // 아이템 심거나 만들 때 비용
    public int cost;
    // 아이템을 수확하거나 제작하면 얻는 경험치
    public int exp;
    // 아이템을 수확하거나 제작할수 있는지 여부
    public boolean levelCK;
    // 아이템의 타입. 일반아이템인지, 소비아이템인지
    public String type;


    public Item(String name, String areaOfProduction, int price) {
        this.itemName = name;
        this.areaOfProduction = areaOfProduction;
        this.type = "일반";
        this.itemPrice = price;
    }
}