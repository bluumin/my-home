package com.bluuminn.myhome.inventory;

import java.util.ArrayList;

public class Inventory {

    // 인벤토리가 담을 수 있는 총 개수
    private static final int MAX_ITEMS = 10;


    // 인벤토리에 들어있는 아이템 목록
    private ArrayList<ItemEntry> itemList;


    // 인벤토리에 들어있는 아이템 수
    private int items;

    public Inventory() {
        itemList = new ArrayList<>(MAX_ITEMS);
        for (int i = 0; i < MAX_ITEMS; i++) {
            itemList.add(null);
        }
        items = 0;
    }

    public boolean addItem(ItemEntry entry, int cnt) {
        // 해당 아이템이 있는지 검사
        int index = findItem(entry);
        if (index < 0) { // 아이템이 없다면

            // 인벤토리에 빈자리가 있는지 검사
            boolean ck = ckInventory();

            // 인벤토리가 꽉찼으면 (ckInventory() == true -> 꽉참을 의미)
            if (ck) {

                System.out.println("인벤토리가 가득 차서 더 이상 아이템을 담을 수 없어요.");
                return false;

            } else {
                // 빈 자리에 추가
                for (int i = 0; i < MAX_ITEMS; i++) {
                    if (itemList.get(i) == null) {
                        if (entry.entryType.equals("일반")) {
                            itemList.set(i, new ItemEntry(entry.item, cnt));

                        } else if (entry.entryType.equals("생산")) {
                            itemList.set(i, new ItemEntry(entry.growthItem, cnt));

                        } else if (entry.entryType.equals("제작")) {
                            itemList.set(i, new ItemEntry(entry.madeItem, cnt));

                        } else if (entry.entryType.equals("포션")) {
                            itemList.set(i, new ItemEntry(entry.potion, cnt));
                        } else {
                        }

                        items++;
                        break;
                    }
                }
            }
        } else {    // 아이템이 있다면
            itemList.get(index).count += cnt; // 수량을 cnt만큼 증가
        }
        return true;
    }


//    public boolean addItem(ItemEntry entry, int cnt) {
//
//        // 인벤토리에 빈자리가 있는지 검사
//        boolean ck = ckInventory();
//
//        // 인벤토리가 꽉찼으면 (ckInventory() == true -> 꽉참을 의미)
//        if (ck) {
//
//            System.out.println("인벤토리가 가득 차서 더 이상 아이템을 담을 수 없어요.");
//            return false;
//
//            // 인벤토리에 빈자리가 있으면
//        } else {
//            // 해당 아이템이 있는지 검사
//            int index = findItem(entry);
//            if (index < 0) { // 아이템이 없다면
//                // 빈 자리에 추가
//                for (int i = 0; i < MAX_ITEMS; i++) {
//                    if (itemList.get(i) == null) {
//                        if (entry.entryType.equals("일반")) {
//                            itemList.set(i, new ItemEntry(entry.item, cnt));
//
//                        } else if (entry.entryType.equals("생산")) {
//                            itemList.set(i, new ItemEntry(entry.growthItem, cnt));
//
//                        } else if (entry.entryType.equals("제작")) {
//                            itemList.set(i, new ItemEntry(entry.madeItem, cnt));
//
//                        } else if (entry.entryType.equals("포션")) {
//                            itemList.set(i, new ItemEntry(entry.potion, cnt));
//                        } else {
//                        }
//
//                        items++;
//                        break;
//                    }
//                }
//            } else {    // 아이템이 있다면
//                itemList.get(index).count += cnt; // 수량을 cnt만큼 증가
//            }
//            return true;
//        }
//    }


    //============================== 아이템 개수 감소(0이면 제거) =================================
    // 해당하는 칸의 아이템의 개수를 cnt만큼 감소시킨다
    public boolean removeItem(int index, int cnt) {
        ItemEntry entry = itemList.get(index);
        if (entry != null) {
            itemList.get(index).count -= cnt;
            if (entry.count <= 0) {
                itemList.remove(index);
                itemList.add(null);
                items--;
            }
            return true;
        }
        return false;
    }

    public int findItem(ItemEntry item) {
        for (int i = 0; i < MAX_ITEMS; i++) {
            if (itemList.get(i) != null && itemList.get(i).entryName.equals(item.entryName)) {
                return i;
            }
        }
        return -1;
    }


    // ================== 인벤토리 가득 차면 알리기 =================
    // 인벤토리 칸이 다 차면 알리기
    public boolean ckInventory() {
        if (itemList.get(MAX_ITEMS - 1) == null) {
            return false;   // 빈자리 있음
        } else {
            return true;    // 빈자리 없음. 인벤토리 꽉참
        }
    }

    // ======================= 아이템 객체 반환 ======================
    // 해당하는 인벤토리 칸의 아이템 객체를 반환한다
    public ItemEntry getItem(int index) {
        ItemEntry entry = itemList.get(index);
        if (entry != null) {
            return entry;
        }
        return null;
    }

    public int getItemIndex(ItemEntry itemEntry) {
        for (int i = 0; i < MAX_ITEMS; i++) {
            if (itemList.get(i) != null && itemList.get(i).entryName.equals(itemEntry.entryName)) {
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
        ItemEntry entry = itemList.get(index);
        if (entry != null) {
            return entry.count;
        }
        return -1;
    }


    // ================ 인벤토리에 들어있는 전체 아이템 객체 수 반환 =================
    // 인벤토리에 들어가있는 아이템 객체의 개수를 반환한다
    public int getAvailableItems() {
        return items;
    }
}