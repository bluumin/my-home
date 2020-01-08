package com.bluuminn.myhome.character;

import com.bluuminn.myhome.map.Store;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Merchant extends NPC {
    public Merchant(String merName) {
        super(merName);
    }

    boolean goldCk = false;
    // 아이템 한개당 단가를 임시 저장할 변수
    int singlePrice;

    // 구매할 아이템의 총 가격을 임시 저장할 변수
    int totalPrice;

    // 구매할 아이템의 이름을 임시 저장할 변
    String selItem;

    public void lines(Store store, Player player) {
        boolean exit = true;
//        boolean[] event = new boolean[store.storeItem.size()];
//        for(int i = 0; i < event.length; i++){
//            event[i] = new Random().nextBoolean();
//        }
//        int rand = new Random().nextInt(store.storeItem.size());
        while (exit) {
            try {
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("        어서오세요. 무엇을 도와드릴까요? (0. 이전으로)");
                System.out.println();
                System.out.println("              1. 아이템 구입");
                System.out.println("              2. 아이템 판매");
                System.out.println();
                System.out.print("입력 >> ");
                Scanner scanner = new Scanner(System.in);
                inputVal = scanner.nextInt();
                scanner.nextLine();

                if (inputVal == 1) {  // 아이템 구입

                    if (player.gold <= 0) {
                        System.out.println("보유한 골드가 없어 아이템을 구입할 수 없습니다.");

                    } else {

                        // 아이템 구입화면으로 이동
                        selectItem(store, player);
                    }

                } else if (inputVal == 2) {

                    // 아이템 판매화면으로 이동
                    player.sellingItem(store, player);

                } else if (inputVal == 0) {
                    exit = false;

                } else {
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("            잘못 입력했어요. 다시 입력해주세요.");
                }
            } catch (InputMismatchException e) {

                //scanner.nextLine();

                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("            잘못 입력했어요. 다시 입력해주세요.");

                continue;
            }
        }
    }

    public void storeProcedure(Store store, Player player, int itemNum, int itemQuantity) {
        // 선택한 아이템 이름을 저장
        if (store.storeItem.get(itemNum - 1).entryType.equals("일반")) {
            selItem = store.storeItem.get(itemNum - 1).item.itemName;
        } else if (store.storeItem.get(itemNum - 1).entryType.equals("생산")) {
            selItem = store.storeItem.get(itemNum - 1).growthItem.itemName;
        } else if (store.storeItem.get(itemNum - 1).entryType.equals("제작")) {
            selItem = store.storeItem.get(itemNum - 1).madeItem.itemName;
        } else if (store.storeItem.get(itemNum - 1).entryType.equals("포션")) {
            selItem = store.storeItem.get(itemNum - 1).potion.itemName;
        }

        // 한개당 가격을 저장
        if (store.storeItem.get(itemNum - 1).entryType.equals("일반")) {
            singlePrice = store.storeItem.get(itemNum - 1).item.itemPrice;
        } else if (store.storeItem.get(itemNum - 1).entryType.equals("생산")) {
            singlePrice = store.storeItem.get(itemNum - 1).growthItem.itemPrice;
        } else if (store.storeItem.get(itemNum - 1).entryType.equals("제작")) {
            singlePrice = store.storeItem.get(itemNum - 1).madeItem.itemPrice;
        } else if (store.storeItem.get(itemNum - 1).entryType.equals("포션")) {
            singlePrice = store.storeItem.get(itemNum - 1).potion.itemPrice;
        }
//        singlePrice = store.storeItem.get(itemNum - 1).itemPrice;

        // 총 가격을 저장
        totalPrice = singlePrice * itemQuantity;

        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println(selItem + " " + itemQuantity + " 개를 " + totalPrice + "골드에 구매 하시겠습니까?");
        System.out.println();
        System.out.println("1. 예        2. 아니오(판매 아이템 목록 보기)");
        System.out.println();
        System.out.print("입력 >> ");
        Scanner scanner = new Scanner(System.in);
        inputVal = scanner.nextInt();
        scanner.nextLine();

        if (inputVal == 1) {

            // 플레이어가 보유한 골드가
            // 구매하려는 아이템의 총 가격보다 많이 있으면
            if (totalPrice <= player.gold) {
                boolean ckInven = false;

                ckInven = player.inventory.addItem(store.storeItem.get(itemNum - 1), itemQuantity);

//                if (store.storeItem.get(itemNum - 1).entryType.equals("일반")){
//                    ckInven = player.inventory.addItem(store.storeItem.get(itemNum - 1), itemQuantity);
//                } else if (store.storeItem.get(itemNum - 1).entryType.equals("생산")){
//                    ckInven = player.inventory.addItem(store.storeItem.get(itemNum - 1).growthItem, itemQuantity);
//                } else if (store.storeItem.get(itemNum - 1).entryType.equals("제작")){
//                    ckInven = player.inventory.addItem(store.storeItem.get(itemNum - 1).madeItem, itemQuantity);
//                } else if (store.storeItem.get(itemNum - 1).entryType.equals("포션")){
//                    ckInven = player.inventory.addItem(store.storeItem.get(itemNum - 1).potion, itemQuantity);
//                } else{}

//                boolean ckInven = player.inventory.addItem(store.storeItem.get(itemNum - 1), itemQuantity);
                if (ckInven) {
                    player.gold -= totalPrice;
                    System.out.println();
                    System.out.println("구매 완료!\n");

                } else {
                    System.out.println("구매 실패!\n");
                }

            } else {        // 보유 골드가 가격보다 적으면
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("골드가 부족합니다.\n");
            }

            System.out.println("현재 보유한 골드 : " + player.gold);

            System.out.println("계속 하시려면 아무키나 입력하세요.");

//            scanner.nextLine(); // nextInt 엔터값 받기
            scanner.nextLine(); // 엔터 입력시 줄 올리기
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
        }

//        String enter = scanner.nextLine();
//        if (enter.length() >= 0) {
//            for (int i = 0; i < 100; i++) {
//                System.out.println();
//            }
//        }
    }

    public void selectItem(Store store, Player player) {
        boolean exit = true;

        while (exit) {
            try {

                store.storePrint(player, store);
                System.out.println();

                System.out.println("구매하실 아이템의 번호를 입력하세요. (0. 이전으로)");
                System.out.println();
                System.out.print("입력 >> ");

//                boolean numExit = true;
                int itemNum = 0;

                // 원목 작업대나 요리용 화덕이 없다면
                if (player.woodenCK == false || player.ovenCK == false) {

                    // 원목 작업대, 요리용 화덕을 모두 보유할때 까지 반복
                    while (player.woodenCK == false || player.ovenCK == false) {

                        itemNum = scanner.nextInt();
                        scanner.nextLine();

                        // 원목 작업대나 요리용 화덕이 없다면
                        if (player.woodenCK == false || player.ovenCK == false) {

                            // 원목작업대가 없다면 (요리용 화덕은 논외)
                            if (player.woodenCK == false) {

                                // 입력값이 2초과(3 ~), 0미만이면 (~ -1)
                                if (itemNum > 2 || itemNum < 0) {
                                    System.out.println("┌──────────────────────────────────────────────────┐");
                                    System.out.println("                  다시 입력해주세요");
                                    System.out.println("    구매하실 아이템의 번호를 입력하세요. (0. 이전으로)");
                                    System.out.print("입력 >> ");
                                    continue;

                                } else if (itemNum == 1 || itemNum == 2) {
                                    if (itemNum == 1) {
                                        System.out.println();
                                        System.out.println("┌──────────────────────────────────────────────────┐");
                                        System.out.println("          1. 구입하기         else. 뒤로가기");
                                        System.out.print("입력 >> ");
                                        int tmpinput = scanner.nextInt();
                                        scanner.nextLine();
                                        if (tmpinput == 1) {
                                            System.out.println();
                                            player.gold -= 100;
                                            System.out.println("┌──────────────────────────────────────────────────┐");
                                            System.out.println("                원목 작업대를 구입했어요!");
                                            player.woodenCK = true;
                                            scanner.nextLine();
                                            exit = false;
                                            continue;
//                                            return;
                                        } else {
                                            return;
                                        }
                                    } else if (itemNum == 2) {
                                        if (player.level < 4) {
                                            System.out.println("┌──────────────────────────────────────────────────┐");
                                            System.out.println("       아직 레벨이 충족되지 않아 구매하실 수 없습니다.");
                                            System.out.println();
                                            scanner.nextLine();
                                            return;
                                        } else {
                                            System.out.println();
                                            System.out.println("┌──────────────────────────────────────────────────┐");
                                            System.out.println("           1. 구입하기      else. 뒤로가기");
                                            System.out.print("입력 >> ");
                                            int tmpinput = scanner.nextInt();
                                            scanner.nextLine();
                                            if (tmpinput == 1) {
                                                System.out.println();
                                                player.gold -= 2400;
                                                System.out.println("┌──────────────────────────────────────────────────┐");
                                                System.out.println("                요리용 화덕을 구입했어요!");
                                                player.ovenCK = true;
                                                scanner.nextLine();
                                                exit = false;
//                                                continue;
                                                return;
                                            } else {
                                                return;
                                            }
                                        }
                                    }
                                } else if (itemNum == 0) {
                                    return;
                                }

                                // 원목작업대 true 일때
                            } else {
                                if (player.ovenCK == false) {
                                    if (itemNum > 1) {
                                        System.out.println("┌──────────────────────────────────────────────────┐");
                                        System.out.println("                    다시 입력해주세요");
                                        System.out.println("    구매하실 아이템의 번호를 입력하세요. (0. 이전으로)");
                                        System.out.print("입력 >> ");
                                        continue;

                                        // 화덕 선택
                                    } else if (itemNum == 1) {
                                        if (player.level < 4) {
                                            System.out.println("┌──────────────────────────────────────────────────┐");
                                            System.out.println("        아직 레벨이 충족되지 않아 구매하실 수 없습니다.");
                                            System.out.println();
                                            scanner.nextLine();
                                            return;
                                        } else {
                                            System.out.println();
                                            System.out.println("┌──────────────────────────────────────────────────┐");
                                            System.out.println("        1. 구입하기         else. 뒤로가기");
                                            System.out.print("입력 >> ");
                                            int tmpinput = scanner.nextInt();
                                            scanner.nextLine();
                                            if (tmpinput == 1) {
                                                System.out.println();
                                                player.gold -= 2400;
                                                System.out.println("┌──────────────────────────────────────────────────┐");
                                                System.out.println("                요리용 화덕을 구입했어요!");
                                                player.ovenCK = true;
                                                scanner.nextLine();
                                                exit = false;
                                                return;
                                            } else {
                                                return;
                                            }
                                        }
                                    } else if (itemNum == 0) {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    itemNum = scanner.nextInt();
                    scanner.nextLine();
                    try {

                        if (itemNum <= store.storeItem.size() && itemNum > 0) {
                            System.out.println("┌──────────────────────────────────────────────────┐");
                            if (store.storeItem.get(itemNum - 1).entryType.equals("일반")) {
                                System.out.print(store.storeItem.get(itemNum - 1).item.itemName);

                            } else if (store.storeItem.get(itemNum - 1).entryType.equals("생산")) {
                                System.out.print(store.storeItem.get(itemNum - 1).growthItem.itemName);

                            } else if (store.storeItem.get(itemNum - 1).entryType.equals("제작")) {
                                System.out.print(store.storeItem.get(itemNum - 1).madeItem.itemName);

                            } else if (store.storeItem.get(itemNum - 1).entryType.equals("포션")) {
                                System.out.print(store.storeItem.get(itemNum - 1).potion.itemName);
                            }
                            System.out.println(" 을(를) 몇 개 구매하시겠습니까? (0. 판매 아이템 목록 보기)");
                            System.out.print("입력 >> ");

                            int itemQuantity = scanner.nextInt();
                            scanner.nextLine();

                            if (itemQuantity == 0) {
                                continue;

                            } else {

                                if (itemNum == 1) {     // 양가죽 선택
                                    storeProcedure(store, player, itemNum, itemQuantity);

                                } else if (itemNum == 2) {    // 소고기 선택
                                    storeProcedure(store, player, itemNum, itemQuantity);

                                } else if (itemNum == 3) {    // 아스파라거스
                                    storeProcedure(store, player, itemNum, itemQuantity);

                                } else if (itemNum == 4) {     // 소가죽
                                    storeProcedure(store, player, itemNum, itemQuantity);

                                } else if (itemNum == 5) {     // 말가죽
                                    storeProcedure(store, player, itemNum, itemQuantity);

                                } else if (itemNum == 6) {    // 피로도 30회복 물약
                                    storeProcedure(store, player, itemNum, itemQuantity);

                                } else if (itemNum == 7) {    // 피로도 70회복 물약
                                    storeProcedure(store, player, itemNum, itemQuantity);

                                } else if (itemNum == 8) {    // 피로도 100회복 물약
                                    storeProcedure(store, player, itemNum, itemQuantity);
                                }
                            }

                        } else if (itemNum == 0) {
                            exit = false;

                        } else {
                            System.out.println("┌──────────────────────────────────────────────────┐");
                            System.out.println("            잘못 입력했어요. 다시 입력해주세요.");

                            scanner.nextLine();

                            for (int i = 0; i < 100; i++) {
                                System.out.println();
                            }
                        }

                    } catch (InputMismatchException e) {

                        System.out.println("┌──────────────────────────────────────────────────┐");
                        System.out.println("            잘못 입력했어요. 다시 입력해주세요.");

                        scanner.nextLine();

                        for (int i = 0; i < 100; i++) {
                            System.out.println();
                        }

                        continue;

                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("            잘못 입력했어요. 다시 입력해주세요.");

                scanner.nextLine();
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                continue;

            }
        }
    }

}