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

    public void add(ItemEntry entry) {
        int index = find(entry);
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

    //============================== 아이템 개수 감소(0이면 제거) =================================
    // 해당하는 칸의 아이템의 개수를 cnt만큼 감소시킨다
    public void remove(int index, int cnt) {
        ItemEntry entry = items.get(index);
        if (entry != null) {
            items.get(index).quantity -= cnt;
            if (entry.quantity <= 0) {
                items.remove(index);
                items.add(null);
                items--;
            }
            return true;
        }
        return false;
    }

    public int find(ItemEntry entry) {
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

    // ======================= 아이템 객체 반환 ======================
    // 해당하는 인벤토리 칸의 아이템 객체를 반환한다
    public ItemEntry getItem(int index) {
        ItemEntry entry = items.get(index);
        if (entry != null) {
            return entry;
        }
        return null;
    }

    public int getItemIndex(ItemEntry itemEntry) {
        for (int i = 0; i < MAX_NUMBER_OF_ITEMS; i++) {
            if (items.get(i) != null && items.get(i).entryName.equals(itemEntry.entryName)) {
                return i;
            }
        }
        return -1;
    }

    // ==================== 아이템 이름 반환 ===================
    // 해당하는 아이템의 이름을 반환한다
    public String getItemName(ItemEntry item) {
        if (item.entryType.equals("일반")) {
            return item.item.name;
        } else if (item.entryType.equals("생산")) {
            return item.growthItem.name;
        } else if (item.entryType.equals("제작")) {
            return item.madeItem.name;
        } else if (item.entryType.equals("포션")) {
            return item.potion.name;
        } else {
            return null;
        }
    }

    // ==================== 아이템 가격 반환 ===================
    // 해당하는 아이템의 가격을 반환한다
    public int getItemPrice(ItemEntry item) {

        if (item.entryType.equals("일반")) {
            return item.item.price;
        } else if (item.entryType.equals("생산")) {
            return item.growthItem.price;
        } else if (item.entryType.equals("제작")) {
            return item.madeItem.price;
        } else if (item.entryType.equals("포션")) {
            return item.potion.price;
        } else {
            return -1;
        }
    }


    // ============ 해당 인벤토리 한 칸에 들어있는 아이템 개수 반환 ==========
    // 해당하는 칸의 아이템의 개수를 반환한다
    public int getItemCount(int index) {
        ItemEntry entry = items.get(index);
        if (entry != null) {
            return entry.quantity;
        }
        return -1;
    }


    // ================ 인벤토리에 들어있는 전체 아이템 객체 수 반환 =================
    // 인벤토리에 들어가있는 아이템 객체의 개수를 반환한다
    public int getAvailableItems() {
        return items;
    }
}