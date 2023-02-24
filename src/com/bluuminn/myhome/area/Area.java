package com.bluuminn.myhome.area;

import com.bluuminn.myhome.character.Player;
import com.bluuminn.myhome.etc.ProgressBar;
import com.bluuminn.myhome.harvestgame.Game;
import com.bluuminn.myhome.inventory.ItemEntry;
import com.bluuminn.myhome.item.GrowthItem;
import com.bluuminn.myhome.timer.Timer;

import java.util.ArrayList;
import java.util.InputMismatchException;

public abstract class Area {
    private int input;
    private int inputSel;
    private int count;
    private String name;

    // 수확할 수 있는 아이템 목록 출력
    public ArrayList<GrowthItem> listOfItems = new ArrayList<>();

    protected abstract void welcome();

    public void soundPlay() {
        // TODO: 사운드 출력
//        Music harvest = new Music("harvest.mp3", false);
//        harvest.start();
    }

    public boolean print(Player player) {
        if (player.getFatigability() >= 100) {
            System.out.println("피로도가 너무 높아서 아무 것도 할 수 없어요.");
            return false;
        } else {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                   " + name + "에 도착했다.\n");
            for (int i = 0; i < listOfItems.size(); i++) {
                System.out.print(i + 1 + ". ");

                // 아이템의 레벨이 플레이어 레벨과 같거나 작으면 => 수확가능
                if (listOfItems.get(i).level <= player.level) {
                    listOfItems.get(i).levelCK = true;
                }
                if (listOfItems.get(i).levelCK) {
                    System.out.print(listOfItems.get(i).name);
                    if (!listOfItems.get(i).harvestCK) {
                        if (!listOfItems.get(i).plantCK) {
                            System.out.print(" (재배시간 : " + listOfItems.get(i).defaultTime + "초 / ");
                            System.out.print("비용 : " + listOfItems.get(i).cost + "골드)");
                        } else if (listOfItems.get(i).growingTime <= 0) {
                            System.out.print(" (수확 가능)");
                        } else {
                            System.out.print(" (재배중..)");
                        }
                    } else {
                        System.out.print(" (수확 가능)");
                    }
                } else {
                    System.out.print(listOfItems.get(i).name);
                    if (!listOfItems.get(i).harvestCK) {
                        if (!listOfItems.get(i).plantCK) {
                            System.out.print(" (재배시간 : " + listOfItems.get(i).defaultTime + "초 /");
                        } else {
                            System.out.print(" (재배중.. / ");
                        }
                    } else {
                        System.out.print("(수확 가능)");
                    }
                    System.out.print(" HOLD - LV." + listOfItems.get(i).level + " 이상)");
                }
                if (i < listOfItems.size() - 1) {
                    System.out.println();
                } else {
                    System.out.print(" ");
                }
            }
            return true;
        }
    }

    public void addInventory(Player player, ItemEntry itemEntry, Farm farm) {

        count = 0;

        boolean exit = true;
        boolean result;

        while (exit) {

            // 아이템 수확할 때 수확 가능한지 여부 체크
            // harvestCK 가 true면
            if (listOfItems.get(input - 1).harvestCK) {
                if (count == 0) {
                    System.out.println();
                    ProgressBar.loading();
                }

                // 아이템의 수확가능한 횟수가 남았으면 실행
                if (listOfItems.get(input - 1).harvestCnt > 0) {
                    Game game = new Game();
                    result = game.run(farm, input);
//                    System.out.println(result);
                    if (result) {
//                        System.out.println("result == 참 결과 실행");
                        soundPlay();
//                        Music harvest = new Music("harvest.mp3", false);
//                        harvest.start();
                        count++;
                        if (count == listOfItems.get(input - 1).harvestCnt) {
                            // 수확할 아이템 이름과 수확 가능한 횟수 출력
//                            System.out.println(listOfItems.get(inputVal - 1).itemName + " " + listOfItems.get(inputVal - 1).harvestCnt);
                            System.out.println();

                            System.out.println("    " + count + "개 획득");
                            System.out.println();
                            System.out.println("┌──────────────────────────────────────────────────┐");
                            System.out.println("           수확할 수 있는 양을 모두 수확했어요.");
                            System.out.println("          " + listOfItems.get(input - 1).name + " 획득량 : " + (count));
                            System.out.println();
                            System.out.println("               이전 메뉴로 돌아갑니다.");

                            player.inventory.addItem(itemEntry, count);

                            player.exp += listOfItems.get(input - 1).exp;

                            listOfItems.get(input - 1).harvestCK = false;
                            listOfItems.get(input - 1).plantCK = false;

                            scanner.nextLine();

                            exit = false;

                        } else {

//                            test();
//                            Music harvest = new Music("harvest.mp3", false);
//                            harvest.start();
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
                            System.out.println(listOfItems.get(input - 1).name + " 획득량 : " + count);
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
                    System.out.println("          수확할 수 있는 양을 모두 수확했어요.");

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
                        System.out.println("               아직 " + listOfItems.get(input - 1).name + " 을(를) 재배 중이에요.");
                        System.out.println("                재배가 완료되면 알려드릴게요!");
                        System.out.println("└──────────────────────────────────────────────────┘");
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
                            System.out.println("        재배 중이거나 수확 가능한 아이템이 없습니다.\n");
                            System.out.println(listOfItems.get(input - 1).name + " 을(를) 재배할까요?");
                            System.out.println();
                            System.out.println("1. 재배 하기        2. 이전 메뉴로 가기");
                            System.out.print("입력 >> ");

                            inputSel = scanner.nextInt();
                            scanner.nextLine();

                            // 재배 시작
                            switch (inputSel) {

                                case 1:
                                    System.out.println();
                                    System.out.println("┌──────────────────────────────────────────────────┐");
                                    System.out.println("                  " + listOfItems.get(input - 1).name + " 을(를) 재배합니다.");
                                    System.out.println("                재배가 완료되면 알려드릴게요!");
                                    System.out.println("└──────────────────────────────────────────────────┘");

                                    player.gold -= listOfItems.get(input - 1).cost;
                                    listOfItems.get(input - 1).plantCK = true;

//                                    CountDown cntdown = new CountDown(listOfItems.get(input - 1).growingTime);
                                    // 재배시간 임시로 0으로 설정
//                                    listOfItems.get(input - 1).growingTime = 0;

//                                    Test test = new Test();
                                    int temp = listOfItems.get(input - 1).growingTime;
//                                    Test test = new Test(temp);
                                    Thread timer = new Thread(new Timer(temp, farm, input));
                                    timer.setDaemon(true);
                                    timer.start();

//                                    System.out.println(currentThread());

//                                    System.out.println("┌──────────────────────────────────────────────────┐");
//                                    System.out.println("          계속 하시려면 아무키나 입력하세요.");
//                                    System.out.println("┌──────────────────────────────────────────────────┐");
//                                    scanner.nextLine();
                                    break;

                                case 2:
                                    break;

                                default:
                                    System.out.println("┌──────────────────────────────────────────────────┐");
                                    System.out.println("            잘못 입력했어요. 다시 입력해주세요.");

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

            // 한번 수확할 때마다 플레이어의 피로도 1씩 증가

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
}