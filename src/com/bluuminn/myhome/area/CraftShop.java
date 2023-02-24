package com.bluuminn.myhome.area;

import com.bluuminn.myhome.character.Player;
import com.bluuminn.myhome.inventory.ItemEntry;
import com.bluuminn.myhome.item.MadeItem;
import com.bluuminn.myhome.item.RequiredItem;

public class CraftShop extends Area {

    String tmpItemName; // 만들 아이템 이름 임시 저장
    String tmpRequired;     // 만들 아이템의 재료 아이템 이름을 임시 저장
    String tmpComparison;   // 인벤토리에 저장된 아이템 이름 임시 저장(재료 아이템과 비교 할 용도)
    int tmpItem;    // 보유중인 아이템 개수 임시 저장
    int tmpReqNum;  // 필요한 아이템 개수 임시 저장
    int value;      // 어떤 아이템 만들지 선택
    int select;     // 제작 여부 선택(1. 제작  2.취소)
    int itemCnt;    // 아이템 제작 개수 저장
    int ckCnt;      // 아이템 충족 현황 카운트 ( 충족 개수가 필요 아이템 리스트 만큼 되면 제작 가능 )
    Boolean makingCK = false;  // 아이템 만들수 있는지 여부

    // 밀짚끈
    MadeItem strawRope = new MadeItem("밀짚끈", "원목 작업대", 100, 20);
    ItemEntry strawRopeE = new ItemEntry(strawRope, 0);

    // 네이쳐 오가닉 소파
    MadeItem natureOrganicSofa = new MadeItem("네이쳐 오가닉 소파", "원목 작업대", 600, 40);
    ItemEntry natureOrganicSofaE = new ItemEntry(natureOrganicSofa, 0);

    // 네이쳐 오가닉 테이블
    MadeItem natureOrganicTable = new MadeItem("네이쳐 오가닉 테이블", "원목 작업대", 600, 50);
    ItemEntry natureOrganicTableE = new ItemEntry(natureOrganicTable, 0);

    // 에코 바구니
    MadeItem ecoBasket = new MadeItem("에코 바구니", "원목 작업대", 400, 80);
    ItemEntry ecoBasketE = new ItemEntry(ecoBasket, 0);

    // 딸기 향초
    MadeItem candle = new MadeItem("딸기 향초", "원목 작업대", 1000, 160);
    ItemEntry candleE = new ItemEntry(candle, 0);

    public CraftShop(String name, Player player, Store store, Farm farm, AnimalFarm animalFarm) {

        this.name = name;

        // 밀짚끈
        player.madeItemList.add(strawRope);
        strawRope.requiredItem.add(new RequiredItem(farm.mealE, 1));
        strawRope.level = 1;

        // 네이쳐 오가닉 소파
        player.madeItemList.add(natureOrganicSofa);
        natureOrganicSofa.requiredItem.add(new RequiredItem(strawRopeE, 2));
        natureOrganicSofa.level = 2;

        // 네이쳐 오가닉 테이블
        player.madeItemList.add(natureOrganicTable);
        natureOrganicTable.requiredItem.add(new RequiredItem(strawRopeE, 3));
        natureOrganicTable.level = 2;

        // 에코 바구니
        player.madeItemList.add(ecoBasket);
        ecoBasket.requiredItem.add(new RequiredItem(farm.mealE, 1));
        ecoBasket.requiredItem.add(new RequiredItem(strawRopeE, 1));
        ecoBasket.level = 3;

        // 딸기 향초
        player.madeItemList.add(candle);
        candle.requiredItem.add(new RequiredItem(strawRopeE, 1));
        candle.requiredItem.add(new RequiredItem(farm.strawberryE, 1));
        candle.level = 3;

    }

    //    @Override
    public void printCraftMenu(Player player) {
        boolean exit = true;

        while (exit) {
            makingCK = false;
            int one = 0;    // 아이템 하나당 만들 수 있는 개수
            int min = 999;    // 최대로 만들 수 있는 개수
            int madeCnt = 0;    // 제작에 필요한 재료 아이템 보유 현황 체크
            ckCnt = 0;
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("         어떤 아이템을 제작할까요? (0. 메인 메뉴로)");
            System.out.println();
            for (int i = 0; i < player.madeItemList.size(); i++) {
                if (player.madeItemList.get(i).level <= player.level) {
                    player.madeItemList.get(i).levelCK = true;
                }
                tmpItemName = player.madeItemList.get(i).name;
                System.out.print(i + 1 + ". ");
                if (player.madeItemList.get(i).levelCK) {
                    System.out.println(tmpItemName);
                } else {
                    System.out.print(tmpItemName);
                    System.out.println(" (HOLD - LV. " + player.madeItemList.get(i).level + "이상)");
                }
            }
            System.out.println();
            System.out.print("입력 >> ");
            value = scanner.nextInt();
            scanner.nextLine();
            if (value == 0) {
                exit = false;
                continue;
            } else if (value > player.madeItemList.size()) {
                System.out.println("번호를 다시 입력해주세요.");
                continue;
            }

            if (!player.madeItemList.get(value - 1).levelCK) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("    플레이어의 레벨이 충족되지 않아 아직 제작 할 수 없습니다.");
                scanner.nextLine();
                continue;
            } else {
                boolean ckck = true;
                while (ckck) {

                    tmpItemName = player.madeItemList.get(value - 1).name;

                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("              " + tmpItemName + " 제작");
                    System.out.println();
                    System.out.println("  필요한 재료 아이템\t\t\t 보유 개수  /  필요 개수");
                    System.out.println();

                    for (int i = 0; i < player.madeItemList.get(value - 1).requiredItem.size(); i++) {
                        // 필요 아이템 이름 출력
                        System.out.print(i + 1 + ". ");
                        tmpRequired = player.madeItemList.get(value - 1).requiredItem.get(i).itemEntry.entryName;
                        System.out.print(tmpRequired);
                        System.out.print("\t\t\t\t\t\t   ");

                        // 같은 아이템 찾았는지 여부
                        boolean findCK = true;

                        // 필요한 아이템을 몇개 보유했는지 출력
                        // 인벤토리가 비어있으면 개수 보유 개수 0 출력
                        if (player.inventory.getAvailableItems() <= 0) {
                            System.out.print("0 / ");

                            // 제작에 필요한 아이템 개수 임시 저장
                            tmpReqNum = player.madeItemList.get(value - 1).requiredItem.get(i).howManyItems;

                            // 인벤토리에 아이템이 하나라도 있으면
                        } else {
//                System.out.print("test1");
                            for (int j = 0; j < player.inventory.getAvailableItems(); j++) {
                                ItemEntry testEntry = player.inventory.getItem(j);

                                if (testEntry != null) {
//                    System.out.print("test2");

                                    // 검사 중인 인벤토리 아이템 이름 임시 저장
                                    tmpComparison = player.inventory.getItemName(testEntry);

                                    // 제작에 필요한 아이템 개수 임시 저장
                                    tmpReqNum = player.madeItemList.get(value - 1).requiredItem.get(i).howManyItems;

//                    System.out.print("test3");
//
//                            System.out.print(tmpRequired + " ");
//                            System.out.print(tmpComparison + " ");

                                    // 필요한 아이템 이름과 인벤토리 아이템 이름이 같으면
                                    if (tmpComparison.equals(tmpRequired)) {

                                        // 인벤토리에 있는 아이템 개수를 가져옴
                                        tmpItem = player.inventory.getItemCount(j);
                                        System.out.print(tmpItem + " / ");

                                        // 인벤토리에 있는 재료 아이템 개수가 제작에 필요한 개수보다 같거나 많으면
                                        if (tmpItem >= tmpReqNum) {
                                            ckCnt++;
                                        }

                                        findCK = true;
                                        break;

                                    }
                                }
                                findCK = false;
                            }
                            if (!findCK) {
                                System.out.print("0 / ");
                            }

                        }

                        // 제작에 필요한 아이템 개수 출력
                        System.out.println(tmpReqNum);

                        // tmpItem - 보유중인 아이템 개수
                        // tmpReqNum - 필요한 아이템 개수
                        if (tmpItem == 0) {

                        } else {
                            one = tmpItem / tmpReqNum;
                            if (one >= 1) {
                                //madeCnt++;
                                if (min > one) {
                                    min = one;
                                }
                            }
                        }
                    }

//                System.out.println(ckCnt);
//                System.out.println(player.madeItemList.get(value - 1).requiredItem.size());

                    if (ckCnt == player.madeItemList.get(value - 1).requiredItem.size()) {
                        makingCK = true;
                    }


                    System.out.println("\n");
//        System.out.println(tmpName + " 을(를) 제작 할까요?");
                    System.out.println("1. 제작하기         else. 뒤로가기(제작 리스트 보기)");
                    System.out.print("입력 >> ");
                    select = scanner.nextInt();
                    scanner.nextLine();
                    boolean makingExit = true;
                    while (makingExit) {
                        if (makingCK) {
                            if (select == 1) {
                                System.out.print("몇 개 제작하시겠어요?");
                                System.out.println(" (최대 제작 가능 개수 : " + min + ")");
                                System.out.print("입력 >> ");
                                itemCnt = scanner.nextInt();
                                scanner.nextLine();
                                if (itemCnt == 0) {
                                    makingExit = false;
                                    continue;
                                } else if (itemCnt > min) {
                                    System.out.println();
                                    System.out.println("┌──────────────────────────────────────────────────┐");
                                    System.out.println("  최대 제작 개수는 " + min + " 개 입니다. 다시 입력해주세요.");
                                    continue;
                                } else if (itemCnt <= min) {
                                    switch (value) {
                                        case 1: // 밀짚끈
                                            player.makingItem(player, tmpItemName, strawRopeE, itemCnt, value);
                                            player.exp += strawRope.exp;
                                            break;
                                        case 2: // 네이쳐 오가닉 소파
                                            player.makingItem(player, tmpItemName, natureOrganicSofaE, itemCnt, value);
                                            player.exp += natureOrganicSofa.exp;
                                            break;
                                        case 3: // 네이쳐 오가닉 테이블
                                            player.makingItem(player, tmpItemName, natureOrganicTableE, itemCnt, value);
                                            player.exp += natureOrganicTable.exp;
                                            break;
                                        case 4: // 에코 바구니
                                            player.makingItem(player, tmpItemName, ecoBasketE, itemCnt, value);
                                            player.exp += ecoBasket.exp;
                                            break;
                                        case 5:
                                            player.makingItem(player, tmpItemName, candleE, itemCnt, value);
                                            player.exp += candle.exp;
                                            break;
                                        default:
                                            System.out.println("┌──────────────────────────────────────────────────┐");
                                            System.out.println("              잘못 입력했어요. 다시 입력해주세요.");
                                            continue;
                                    }


                                    makingExit = false;
                                    scanner.nextLine();
                                    continue;
                                }
                            } else {
                                // 뒤로가기
                                makingExit = false;
                                ckck = false;
                                continue;
                            }

                        } else {
                            if (select == 1) {
                                System.out.println("┌──────────────────────────────────────────────────┐");
                                System.out.println("            아이템을 제작할 재료가 부족해요.");
                                makingExit = false;
                                ckck = false;
                                scanner.nextLine();
                                for (int i = 0; i < 100; i++) {
                                    System.out.println();
                                }
                                continue;
                            } else {
                                // 뒤로가기
                                makingExit = false;
                                ckck = false;
                                continue;
                            }
                        }
                    }
                    ckCnt = 0;
                }
            }
        }
    }   // PrintCraftMenu 종료

}