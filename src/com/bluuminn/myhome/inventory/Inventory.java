package com.bluuminn.myhome.inventory;

import java.util.ArrayList;

public class Inventory {

    // 인벤토리가 담을 수 있는 총 개수
    private static final int MAX_NUMBER_OF_ITEMS = 10;

    // 인벤토리에 들어있는 아이템 목록
    private ArrayList<ItemEntry> items;

    public Inventory() {
        items = new ArrayList<>(MAX_NUMBER_OF_ITEMS);
    }

    public ArrayList<ItemEntry> getItems() {
        return items;
    }

    public int getNumberOfItems() {
        return items.size();
    }

    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }

    public void add(ItemEntry entry) {
        int index = indexOf(entry);
        if (index >= 0) {
            ItemEntry item = items.get(index);
            item.addQuantity(entry.getQuantity());
            return;
        }
        // 인벤토리에 아이템이 없으면 추가 등록
        if (isFull()) {
            System.out.println("인벤토리가 가득 차서 더 이상 아이템을 담을 수 없어요.");
            return;
        }
        // 빈 자리에 추가
        for (int i = 0; i < MAX_NUMBER_OF_ITEMS; i++) {
            if (items.get(i) != null) {
                continue;
            }
            items.set(i, entry);
            break;
        }
    }

    // 아이템 개수 감소(0이면 제거)
    // 해당하는 칸의 아이템의 개수를 cnt만큼 감소시킨다
    public void remove(ItemEntry item) {
        items.remove(item);
    }

    public int indexOf(ItemEntry entry) {
        for (int i = 0; i < MAX_NUMBER_OF_ITEMS; i++) {
            ItemEntry item = items.get(i);
            if (item != null && item.getItemName().equals(entry.getItemName())) {
                return i;
            }
        }
        return -1;
    }

    public boolean isFull() {
        return items.size() >= MAX_NUMBER_OF_ITEMS;
    }

    public int getItemQuantity(int index) {
        ItemEntry entry = items.get(index);
        if (entry != null) {
            return entry.getQuantity();
        }
        return 0;
    }

    public ItemEntry find(ItemEntry item) {
        int index = indexOf(item);
        if (index < 0) {
            return null;
        }
        return items.get(index);
    }
}