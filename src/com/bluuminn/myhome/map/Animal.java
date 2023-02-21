package com.bluuminn.myhome.map;

import com.bluuminn.myhome.character.Player;
import com.bluuminn.myhome.harvestgame.Game;
import com.bluuminn.myhome.inventory.ItemEntry;
import com.bluuminn.myhome.item.GrowthItem;
import com.bluuminn.myhome.timer.AnimalTimer;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Animal extends Area {

    public ArrayList<String> animalList = new ArrayList<String>();

    // 우유
    public GrowthItem milk = new GrowthItem("우유", "동물 농장", 90);
    ItemEntry milkE = new ItemEntry(milk, 0);

    // 양털
    GrowthItem wool = new GrowthItem("양털", "동물 농장", 120);
    ItemEntry woolE = new ItemEntry(wool, 0);

    // 달걀
    GrowthItem egg = new GrowthItem("달걀", "동물 농장", 150);
    ItemEntry eggE = new ItemEntry(egg, 0);

    // 꿀
    GrowthItem honey = new GrowthItem("꿀", "동물 농장", 180);
    ItemEntry honeyE = new ItemEntry(honey, 0);

    // 토끼털
    GrowthItem rabbitFur = new GrowthItem("토끼털", "동물 농장", 200);
    ItemEntry rabbitFurE = new ItemEntry(rabbitFur, 0);


    public Animal() {

        name = "동물 농장";
        animalList.add("젖소");
        animalList.add("양");
        animalList.add("닭");
        animalList.add("벌통");
        animalList.add("토끼");

        listOfItems.add(milk);
        listOfItems.add(wool);
        listOfItems.add(egg);
        listOfItems.add(honey);
        listOfItems.add(rabbitFur);

        milk.level = 4;
        wool.level = 12;
        egg.level = 12;
        honey.level = 12;
        rabbitFur.level = 12;

        milk.exp = 8;
        wool.exp = 20;
        egg.exp = 30;
        honey.exp = 32;
        rabbitFur.exp = 50;

        milk.cost = 40;
        wool.cost = 120;
        egg.cost = 160;
        honey.cost = 120;
        rabbitFur.cost = 200;

        milk.growingTime = 10;
        wool.growingTime = 15;
        egg.growingTime = 10;
        honey.growingTime = 15;
        rabbitFur.growingTime = 10;

        milk.defaultTime = 10;
        wool.defaultTime = 15;
        egg.defaultTime = 10;
        honey.defaultTime = 15;
        rabbitFur.defaultTime = 10;


//        listOfItems.add(strawberry);
//        strawberry.level = 3;
//        strawberry.exp = 6;
//        strawberry.cost = 140;
//        strawberry.growingTime = 30;
//        strawberry.defaultTime = 30;


    }

    // 서브 메뉴 출력
//    @Override
    public int animalSubMenu() {
        if (listOfItems.get(input - 1).harvestCK) {
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("    " + animalList.get(input - 1) + " 로(으로)부터 " + listOfItems.get(input - 1).itemName + " 얻기");
            System.out.println();
            System.out.println("    1. " + listOfItems.get(input - 1).itemName + " 얻기        2. 그만하기");
            System.out.print("입력 >> ");

            return 1;

        } else {
            return -1;
        }
    }


    public void addInventory(Player player, ItemEntry itemEntry, Animal animal) {
        count = 0;

        boolean exit = true;
        boolean result;

        while (exit) {

            // 아이템 수확할 때 수확 가능한지 여부 체크
            // harvestCK 가 true면
            if (listOfItems.get(input - 1).harvestCK) {
                if (count == 0) {
                    System.out.print("아무키나 입력하면 ");
                    System.out.println(listOfItems.get(input - 1).itemName + " 을(를) 수확하러 이동해요");

                    scanner.nextLine();
                }

                // 아이템의 수확가능한 횟수가 남았으면 실행
                if (listOfItems.get(input - 1).harvestCnt > 0) {
                    Game game = new Game();
                    result = game.Run(animal, input);
//                    System.out.println(result);
                    if (result) {
//                        System.out.println("result == 참 결과 실행");
                        soundPlay();
                        count++;
                        if (count == listOfItems.get(input - 1).harvestCnt) {
                            // 수확할 아이템 이름과 수확 가능한 횟수 출력
//                            System.out.println(listOfItems.get(inputVal - 1).itemName + " " + listOfItems.get(inputVal - 1).harvestCnt);
                            System.out.println();

                            System.out.println("    " + count + "개 획득");
                            System.out.println("┌──────────────────────────────────────────────────┐");
                            System.out.println("         수확할 수 있는 양을 모두 수확했어요.");
                            System.out.println(listOfItems.get(input - 1).itemName + " 획득량 : " + (count));
                            System.out.println();
                            System.out.println("이전 메뉴로 돌아갑니다.");

                            player.inventory.addItem(itemEntry, count);

                            player.exp += listOfItems.get(input - 1).exp;

                            listOfItems.get(input - 1).harvestCK = false;
                            listOfItems.get(input - 1).plantCK = false;

                            exit = false;

                        } else {

//                            test();
                            soundPlay();
                            System.out.println();
                            System.out.println("    " + count + "개 획득");
                            player.exp += listOfItems.get(input - 1).exp;
                        }

                    } else {
//                        System.out.println("result == 거짓 결과 실행");
                        if (count <= 0) {
                            exit = false;
                            break;

                        } else {
                            listOfItems.get(input - 1).harvestCnt -= count;
//                            System.out.println(listOfItems.get(inputVal - 1).itemName + " " + listOfItems.get(inputVal - 1).harvestCnt);
                            System.out.println("┌──────────────────────────────────────────────────┐");
                            System.out.println("        " + listOfItems.get(input - 1).itemName + " 획득량 : " + count);
                            player.inventory.addItem(itemEntry, count);
                            if (listOfItems.get(input - 1).harvestCnt <= 0) {
                                listOfItems.get(input - 1).harvestCK = false;
                            }
                            exit = false;
                            break;
                        }
                    }
                } else {
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("             수확할 수 있는 양을 모두 수확했어요.");

                    listOfItems.get(input - 1).harvestCK = false;
                    scanner.nextLine();
                    exit = false;
                }

                //  harvestCK(수확가능여부) 가 false일 경우
                //      => 수확할 수 있는 아이템이 없는 경우
            } else {

                //  plantCK(재배 시작 여부) 를 검사

                //  plantCK 가 true일 경우
                //  남은 재배 시간 기다리기
                //  추가 할 수 있으면 남은 시간 카운트를 gui로 보여주기

                if (listOfItems.get(input - 1).plantCK) {

                    System.out.println();
//                    System.out.println("시간 기능 구현 해야 함");
//                    System.out.println("시간 기능 구현 다 됐다는 가정 하에 실행 중");


                    // growingTime(재배시간)이 0이면
                    if (listOfItems.get(input - 1).growingTime <= 0) {

                        // harvestCK (수확가능여부)를 true로 바꿔줌
                        listOfItems.get(input - 1).harvestCK = true;

                        // 초기화 과정....
                        listOfItems.get(input - 1).harvestCnt = 3;

                        // 재배시간 초기화
                        listOfItems.get(input - 1).growingTime = listOfItems.get(input - 1).defaultTime;

                    } else {
                        System.out.println();
                        System.out.println("┌──────────────────────────────────────────────────┐");
                        System.out.println("            아직 " + animalList.get(input - 1) + " 을(를) 길들이는 중이에요.");
                        System.out.println("        " + animalList.get(input - 1) + " 이(가)" + " 길들여지면 알려드릴게요!");
                        System.out.println("└──────────────────────────────────────────────────┘");
                        System.out.println();
                        scanner.nextLine();
                        exit = false;
                        continue;
                    }

                    // plantCK 가 false일 경우
                    // 재배를 시작하거나 이전 메뉴로 이동하기
                } else {
                    int harvestFlag = 0;

                    while (harvestFlag == 0) {

                        try {
                            harvestFlag = 1;
                            System.out.println("┌──────────────────────────────────────────────────┐");
                            System.out.println("   동물을 길들이는 중이거나 수확 가능한 아이템이 없습니다.\n");
                            System.out.println(animalList.get(input - 1) + "을(를) 길들이면 " + listOfItems.get(input - 1).itemName + "을 얻을 수 있습니다.");
                            System.out.println(animalList.get(input - 1) + " 을(를) 길들일까요?");
                            System.out.println();
                            System.out.println("1." + animalList.get(input - 1) + " 길들이기        else. 이전 메뉴로 가기");
                            System.out.print("입력 >> ");

                            inputSel = scanner.nextInt();
                            scanner.nextLine();

                            // 재배 시작
                            switch (inputSel) {

                                case 1:
                                    System.out.println();
                                    System.out.println("┌──────────────────────────────────────────────────┐");
                                    System.out.println("                " + animalList.get(input - 1) + " 을(를) 길들입니다.");
                                    System.out.println("        " + animalList.get(input - 1) + " 이(가)" + " 길들여지면 알려드릴게요!");
                                    System.out.println("└──────────────────────────────────────────────────┘");
                                    System.out.println();

                                    player.gold = listOfItems.get(input - 1).cost;
                                    listOfItems.get(input - 1).plantCK = true;

//                                    CountDown cntdown = new CountDown(listOfItems.get(input - 1).growingTime);
                                    // 재배시간 임시로 0으로 설정
//                                    listOfItems.get(input - 1).growingTime = 0;

//                                    Test test = new Test();
                                    int temp = listOfItems.get(input - 1).growingTime;
//                                    Test test = new Test(temp);
                                    Thread animalTimer = new Thread(new AnimalTimer(temp, animal, input));
                                    animalTimer.setDaemon(true);
                                    animalTimer.start();

//                                    System.out.println(currentThread());

//                                    System.out.println("┌──────────────────────────────────────────────────┐");
//                                    System.out.println("          계속 하시려면 아무키나 입력하세요.");
//                                    System.out.println("└──────────────────────────────────────────────────┘");
                                    scanner.nextLine();
                                    break;

                                case 2:
                                    break;

                                default:
                                    return;

                            }

                            exit = false;

                        } catch (InputMismatchException e) {

                            harvestFlag = 0;

                            scanner.nextLine();

                            System.out.println("┌──────────────────────────────────────────────────┐");
                            System.out.println("            잘못 입력했어요. 다시 입력해주세요.");

                            continue;

                        }
                    }
                }
            }

            // 한번 수확할 때마다 플레이어의 피로도 15씩 증가

            if (count >= 1) {
                player.fatigability += 15;

                if (player.fatigability >= 100) {
                    player.fatigability = 100;
                }

//                player.levelUP();
            }

            scanner.nextLine();
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
        }
    }

    public void getAnimalFarmItem(Player player, Animal animal) {
        while (true) {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("            " + name + "에 도착했다.\n");

            // 동물 - 획득 아이템 출력
            for (int i = 0; i < animalList.size(); i++) {

                // 아이템의 레벨이 플레이어 레벨과 같거나 작으면 => 수확가능
                if (listOfItems.get(i).level <= player.level) {
                    listOfItems.get(i).levelCK = true;
                }

                System.out.print(i + 1 + ". ");

                // 레벨이 충족돼서 재배 및 수확이 가능 하다면
                if (listOfItems.get(i).levelCK) {

                    // 동물이름 - 수확아이템 출력
                    System.out.print(animalList.get(i) + " - " + listOfItems.get(i).itemName);

                    // 수확가능 여부가 false 라면 => 수확 불가능 상태라면
                    if (listOfItems.get(i).harvestCK == false) {
                        // 농작물을 심었는지 확인함.
                        // 농작물 심었는지 여부가 false라면 => 농작물을 안심었다면
                        if (listOfItems.get(i).plantCK == false) {
                            // 재배시간과 비용을 출력함
                            System.out.print(" (재배시간 : " + listOfItems.get(i).defaultTime + "초 / ");
                            System.out.print("비용 : " + listOfItems.get(i).cost + "골드)");

                            // 농작물을 심었고
                            // 농작물 자라는 시간이 0이라면 => 농작물이 다 자랐다면
                        } else if (listOfItems.get(i).growingTime <= 0) {

                            // 수확가능으로 출력함
                            System.out.print(" (수확 가능)");

                            // 농작물을 심었고 자라는 시간이 남아있다면 재배중 출력함
                        } else {
                            System.out.print(" (재배중..)");
                        }

                        // 수확가능여부가 true라면 수확가능 출력함
                    } else {
                        System.out.print(" (수확 가능)");
                    }

                    // 레벨이 충족되지 않았다면
                } else {
                    System.out.print(animalList.get(i) + " - " + listOfItems.get(i).itemName);
                    System.out.print(" (재배시간 : " + listOfItems.get(i).defaultTime + "초 /");
                    System.out.print(" HOLD - LV." + listOfItems.get(i).level + " 이상)");
                    // 수확가능 여부 체크
                    // 수확가능 여부 false => 수확 불가
//                    if (listOfItems.get(i).harvestCK == false) {
//
//                        // 농작물 심었는지 여부 체크
//                        // 농작물 심었는지 여부 false => 농작물 안심음
//                        if (listOfItems.get(i).plantCK == false) {
//                            // 재배시간 몇초인지 띄워줌
//                            System.out.print(" (재배시간 : " + listOfItems.get(i).defaultTime + "초 /");
//                        }
////                        else {
////                            System.out.print(" (재배중.. / ");
////                        }
//                    } else {
//                        System.out.print("(수확 가능)");
//                    }

                }

//                System.out.print(i + 1 + ". ");             // 출력할 수 있는 아이템 목록 순서 출력
//                if (listOfItems.get(i).level <= player.level) {
//                    listOfItems.get(i).levelCK = true;
//                    // 동물 - 획득 아이템 출력
//                    System.out.print(animalList.get(i) + " - " + listOfItems.get(i).itemName);
//                } else {
//                    System.out.print(animalList.get(i) + " - " + listOfItems.get(i).itemName);
//                    System.out.print(" (HOLD - LV." + listOfItems.get(i).level + " 이상)");
//                }
                if (i < listOfItems.size() - 1) {       // 다음 리스트가 존재하면
                    System.out.println();               // 줄바꿔줌
                } else {                                // 다음 리스트가 없으면
                    System.out.print(" ");              // 공백출력
                }
            }

            System.out.println("\n\n얻고 싶은 재료의 번호를 입력하세요. (0. 이전 단계로)");
            System.out.print("입력 >> ");
//            inputVal = scanner.nextLine();
            input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) {
                break;
            } else if (input > 0 && listOfItems.size() >= input) {

                switch (input) {
                    case 1:
                        // 우유
                        if (listOfItems.get(input - 1).levelCK) {
                            addInventory(player, milkE, animal);
                        } else {
                            System.out.println("┌──────────────────────────────────────────────────┐");
                            System.out.println("    플레이어의 레벨이 충족되지 않아 아직 획득 할 수 없습니다.");
                            scanner.nextLine();
                        }
                        break;

                    case 2:
                        // 양털
                        if (listOfItems.get(input - 1).levelCK) {
                            addInventory(player, woolE, animal);
                        } else {
                            System.out.println("┌──────────────────────────────────────────────────┐");
                            System.out.println("    플레이어의 레벨이 충족되지 않아 아직 획득 할 수 없습니다.");
                            scanner.nextLine();
                        }
                        break;

                    case 3:
                        // 달걀
                        if (listOfItems.get(input - 1).levelCK) {
                            addInventory(player, eggE, animal);
                        } else {
                            System.out.println("┌──────────────────────────────────────────────────┐");
                            System.out.println("    플레이어의 레벨이 충족되지 않아 아직 획득 할 수 없습니다.");
                            scanner.nextLine();
                        }
                        break;

                    case 4:
                        // 꿀
                        if (listOfItems.get(input - 1).levelCK) {
                            addInventory(player, honeyE, animal);
                        } else {
                            System.out.println("┌──────────────────────────────────────────────────┐");
                            System.out.println("    플레이어의 레벨이 충족되지 않아 아직 획득 할 수 없습니다.");
                            scanner.nextLine();
                        }
                        break;

                    case 5:
                        // 토끼털
                        if (listOfItems.get(input - 1).levelCK) {
                            addInventory(player, rabbitFurE, animal);
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
        } // while 종료
    }
}
