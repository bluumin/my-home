package com.bluuminn.myhome.area;

import com.bluuminn.myhome.character.Player;
import com.bluuminn.myhome.inventory.ItemEntry;
import com.bluuminn.myhome.item.GrowthItem;

import java.util.InputMismatchException;

public class Farm extends Area {

    // 밀
    GrowthItem meal = new GrowthItem("밀", "밭", 90);
    ItemEntry mealE = new ItemEntry(meal, 0);

    // 딸기
    GrowthItem strawberry = new GrowthItem("딸기", "밭", 120);
    ItemEntry strawberryE = new ItemEntry(strawberry, 0);

    // 토마토
    GrowthItem tomato = new GrowthItem("토마토", "밭", 150);
    ItemEntry tomatoE = new ItemEntry(tomato, 0);

    // 솜
    GrowthItem cotton = new GrowthItem("솜", "밭", 180);
    ItemEntry cottonE = new ItemEntry(cotton, 0);

    // 감자
    GrowthItem potato = new GrowthItem("감자", "밭", 200);
    ItemEntry potatoE = new ItemEntry(potato, 0);

    // 옥수수
    GrowthItem corn = new GrowthItem("옥수수", "밭", 220);
    ItemEntry cornE = new ItemEntry(corn, 0);


    public Farm() {
        name = "밭";

        // 밀
        listOfItems.add(meal);
        meal.level = 1;
        meal.exp = 30;
        meal.cost = 50;
        meal.growingPeriod = 10;
        meal.defaultTime = 10;


        // 딸기
        listOfItems.add(strawberry);
        strawberry.level = 3;
        strawberry.exp = 6;
        strawberry.cost = 140;
        strawberry.growingPeriod = 15;
        strawberry.defaultTime = 15;


        // 토마토
        listOfItems.add(tomato);
        tomato.level = 6;
        tomato.exp = 31;
        tomato.cost = 300;
        tomato.growingPeriod = 10;
        tomato.defaultTime = 10;


        // 솜
        listOfItems.add(cotton);
        cotton.level = 10;
        cotton.exp = 99;
        cotton.cost = 540;
        cotton.growingPeriod = 15;
        cotton.defaultTime = 15;


        // 감자
        listOfItems.add(potato);
        potato.level = 16;
        potato.exp = 339;
        potato.cost = 940;
        potato.growingPeriod = 10;
        potato.defaultTime = 10;


        // 옥수수
        listOfItems.add(corn);
        corn.level = 20;
        corn.exp = 756;
        corn.cost = 1240;
        corn.growingPeriod = 15;
        corn.defaultTime = 15;

    }

    public void getFarmItem(Player player) {
        while (true) {
            boolean printCK = print(player);
            if (!printCK) {
                return;
            }
            System.out.println("\n\n수확하고 싶은 작물의 번호를 입력하세요. (0. 이전 단계로)");
            System.out.print("입력 >> ");

            try {
                input = scanner.nextInt();
                scanner.nextLine();

                if (input == 0) {
                    return;
                }

                if (input > 0 && listOfItems.size() >= input) {

                    switch (input) {
                        case 1:
                            // 밀
                            if (listOfItems.get(input - 1).levelCK) {
                                addInventory(player, mealE, farm);
                            } else {
                                System.out.println("┌──────────────────────────────────────────────────┐");
                                System.out.println("    플레이어의 레벨이 충족되지 않아 아직 획득 할 수 없습니다.");
                                scanner.nextLine();
                            }
                            break;

                        case 2:
                            // 딸기
                            if (listOfItems.get(input - 1).levelCK) {
                                addInventory(player, strawberryE, farm);
                            } else {
                                System.out.println("┌──────────────────────────────────────────────────┐");
                                System.out.println("    플레이어의 레벨이 충족되지 않아 아직 획득 할 수 없습니다.");
                                scanner.nextLine();
                            }
                            break;

                        case 3:
                            // 토마토
                            if (listOfItems.get(input - 1).levelCK) {
                                addInventory(player, tomatoE, farm);
                            } else {
                                System.out.println("┌──────────────────────────────────────────────────┐");
                                System.out.println("    플레이어의 레벨이 충족되지 않아 아직 획득 할 수 없습니다.");
                                scanner.nextLine();
                            }
                            break;

                        case 4:
                            // 솜
                            if (listOfItems.get(input - 1).levelCK) {
                                addInventory(player, cottonE, farm);
                            } else {
                                System.out.println("┌──────────────────────────────────────────────────┐");
                                System.out.println("    플레이어의 레벨이 충족되지 않아 아직 획득 할 수 없습니다.");
                                scanner.nextLine();
                            }
                            break;

                        case 5:
                            // 감자
                            if (listOfItems.get(input - 1).levelCK) {
                                addInventory(player, potatoE, farm);
                            } else {
                                System.out.println("┌──────────────────────────────────────────────────┐");
                                System.out.println("    플레이어의 레벨이 충족되지 않아 아직 획득 할 수 없습니다.");
                                scanner.nextLine();
                            }
                            break;

                        case 6:
                            // 옥수수
                            if (listOfItems.get(input - 1).levelCK) {
                                addInventory(player, cornE, farm);
                            } else {
                                System.out.println("┌──────────────────────────────────────────────────┐");
                                System.out.println("    플레이어의 레벨이 충족되지 않아 아직 획득 할 수 없습니다.");
                                scanner.nextLine();
                            }
                            break;
                    }

                } else {
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("            잘못 입력했어요. 다시 입력해주세요.");
                    scanner.nextLine();
                }

            } catch (InputMismatchException e) {

//                scanner.nextLine();

                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("            잘못 입력했어요. 다시 입력해주세요.");
                scanner.nextLine();
            }
        }
    }

    @Override
    protected void welcome() {

    }
}