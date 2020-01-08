package com.bluuminn.myhome.item;

public class GrowthItem extends Item {
    // 아이템 수확 가능 여부
    public boolean harvestCK;
    // 아이템 심었는지 여부
    public boolean plantCK;
    // 다 자라는데 걸리는 시간
    public int growingTime;
    // 다 자라는데 걸리는 시간 기본 값
    public int defaultTime;
    // 한번에 획득할 수 있는 횟수 카운트
    public byte harvestCnt;

    public GrowthItem(String name, String areaOfProduction, int price) {
        super(name, areaOfProduction, price);
        this.harvestCK = false;
        this.plantCK = false;
        this.levelCK = false;
        this.harvestCnt = 3;
        this.type = "생산";
    }
}