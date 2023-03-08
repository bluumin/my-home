package com.bluuminn.myhome.item;

import com.bluuminn.myhome.inventory.ItemEntry;

import java.util.ArrayList;
import java.util.List;

public class ItemStorage {
    /**
     * 동물농장 아이템
     */
    public static final GrowthItem MILK = new GrowthItem("우유", "젖소", "동물농장", 4, 90, 40, 8, 10);
    public static final GrowthItem WOOL = new GrowthItem("양털", "양", "동물농장", 12, 120, 120, 20, 15);
    public static final GrowthItem EGG = new GrowthItem("달걀", "닭", "동물농장", 12, 150, 160, 30, 10);
    public static final GrowthItem HONEY = new GrowthItem("꿀", "벌통", "동물농장", 12, 180, 120, 32, 15);
    public static final GrowthItem RABBIT_FUR = new GrowthItem("토끼털", "토끼", "동물농장", 12, 200, 200, 50, 10);


    /**
     * 밭 아이템
     */
    public static final GrowthItem MEAL = new GrowthItem("밀", "밀", "밭", 1, 90, 50, 30, 10);
    public static final GrowthItem STRAWBERRY = new GrowthItem("딸기", "딸기", "밭", 3, 120, 140, 6, 15);
    public static final GrowthItem TOMATO = new GrowthItem("토마토", "토마토", "밭", 6, 150, 300, 31, 10);
    public static final GrowthItem COTTON = new GrowthItem("솜", "솜", "밭", 10, 180, 540, 99, 15);
    public static final GrowthItem POTATO = new GrowthItem("감자", "감자", "밭", 16, 200, 940, 339, 10);
    public static final GrowthItem CORN = new GrowthItem("옥수수", "옥수수", "밭", 20, 220, 1240, 756, 15);


    /**
     * 숲 아이템
     */
    public static final GrowthItem FIR = new GrowthItem("전나무목재", "전나무", "숲", 3, 300, 70, 20, 10);
    public static final GrowthItem APPLE = new GrowthItem("사과", "사과나무", "숲", 7, 400, 180, 24, 10);
    public static final GrowthItem ORANGE = new GrowthItem("오렌지", "오렌지나무", "숲", 10, 500, 220, 24, 10);
    public static final GrowthItem MAPLE = new GrowthItem("단풍나무목재", "단풍나무", "숲", 12, 800, 300, 32, 15);

    /**
     * 공방 아이템
     */
    public static final CraftItem STRAW_ROPE = CraftItem.of("밀짚끈", "원목 작업대", "공방", 1, 160, 100, 20, ItemEntry.of(ItemStorage.MEAL, 1));

    public static final CraftItem NATURE_ORGANIC_SOFA = CraftItem.of("네이쳐 오가닉 소파", "원목 작업대", "공방", 2, 600, 400, 40, ItemEntry.of(STRAW_ROPE, 2));

    public static final CraftItem NATURE_ORGANIC_TABLE = CraftItem.of("네이쳐 오가닉 테이블", "원목 작업대", "공방", 2, 650, 420, 50, ItemEntry.of(ItemStorage.STRAW_ROPE, 3));

    public static final CraftItem ECO_BASKET = CraftItem.of("에코 바구니", "원목 작업대", "공방", 3, 400, 230, 80, ItemEntry.of(ItemStorage.MEAL, 1), ItemEntry.of(ItemStorage.STRAW_ROPE, 1));

    public static final CraftItem CANDLE = CraftItem.of("딸기 향초", "원목 작업대", "공방", 3, 1000, 650, 160, ItemEntry.of(ItemStorage.STRAW_ROPE, 1), ItemEntry.of(ItemStorage.STRAW_ROPE, 1));

    public final List<GrowthItem> farmItems = new ArrayList<>();
    public final List<GrowthItem> animalFarmItems = new ArrayList<>();
    public final List<GrowthItem> forestItems = new ArrayList<>();
    public final List<CraftItem> craftItems = new ArrayList<>();

    public ItemStorage() {
        farmItems.add(MEAL);
        farmItems.add(STRAWBERRY);
        farmItems.add(TOMATO);
        farmItems.add(COTTON);
        farmItems.add(POTATO);
        farmItems.add(CORN);

        animalFarmItems.add(MILK);
        animalFarmItems.add(WOOL);
        animalFarmItems.add(EGG);
        animalFarmItems.add(HONEY);
        animalFarmItems.add(RABBIT_FUR);

        forestItems.add(FIR);
        forestItems.add(APPLE);
        forestItems.add(ORANGE);
        forestItems.add(MAPLE);

        craftItems.add(STRAW_ROPE);
        craftItems.add(NATURE_ORGANIC_SOFA);
        craftItems.add(NATURE_ORGANIC_TABLE);
        craftItems.add(ECO_BASKET);
        craftItems.add(CANDLE);
    }

    public List<GrowthItem> getFarmItems() {
        return new ArrayList<>(farmItems);
    }

    public List<GrowthItem> getAnimalFarmItems() {
        return new ArrayList<>(animalFarmItems);
    }

    public List<GrowthItem> getForestItems() {
        return new ArrayList<>(forestItems);
    }

    public List<CraftItem> getCraftItems() {
        return new ArrayList<>(craftItems);
    }
}
