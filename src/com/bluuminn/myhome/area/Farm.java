package com.bluuminn.myhome.area;

import com.bluuminn.myhome.character.Player;
import com.bluuminn.myhome.etc.MyHomeUtils;
import com.bluuminn.myhome.harvestgame.BearCatchesFishGame;
import com.bluuminn.myhome.inventory.ItemEntry;
import com.bluuminn.myhome.item.GrowthItem;
import com.bluuminn.myhome.timer.FarmTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Farm extends Area {
    private final List<GrowthItem> items = new ArrayList<>();

    public Farm() {
        super("밭");
        String areaName = getName();
        GrowthItem meal = new GrowthItem("밀", areaName, 1, 90, 50, 30, 10);
        GrowthItem strawberry = new GrowthItem("딸기", areaName, 3, 120, 140, 6, 15);
        GrowthItem tomato = new GrowthItem("토마토", areaName, 6, 150, 300, 31, 10);
        GrowthItem cotton = new GrowthItem("솜", areaName, 10, 180, 540, 99, 15);
        GrowthItem potato = new GrowthItem("감자", areaName, 16, 200, 940, 339, 10);
        GrowthItem corn = new GrowthItem("옥수수", areaName, 20, 220, 1240, 756, 15);
        items.add(meal);
        items.add(strawberry);
        items.add(tomato);
        items.add(cotton);
        items.add(potato);
        items.add(corn);
    }

    public void cultivate(Player player, Scanner scanner) {
        while (true) {
            MyHomeUtils.printLineAsCount(100);
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                   " + getName() + "에 도착했다.\n");

            int playerLevel = player.getLevel();
            for (int i = 0; i < items.size(); i++) {
                GrowthItem item = items.get(i);
                System.out.print((i + 1) + ". " + item.getName());

                // 아이템의 레벨이 플레이어 레벨과 같거나 작으면 => 재배가능
                if (!item.isPlantable(playerLevel)) {
                    System.out.println(" HOLD - LV." + item.getLevel() + " 이상)");
                    continue;
                }
                if (!item.isPlanted()) {
                    System.out.println(" (재배시간: " + item.getGrowingPeriod() + "초 / 비용: " + item.getCost() + "골드)");
                    continue;
                }
                if (item.getGrowingPeriod() <= 0) {
                    System.out.println(" (수확 가능)");
                    continue;
                }
                System.out.print(" (재배중..)");
            }

            MyHomeUtils.printLineAsCount(2);
            System.out.println("수확하고 싶은 작물의 번호를 입력하세요. (0. 이전 단계로)");
            System.out.print("입력 >> ");
            String inputValue = scanner.next();
            scanner.nextLine();
            if (!MyHomeUtils.isInteger(inputValue)) {
                MyHomeUtils.enterAgain();
            }
            int input = MyHomeUtils.stringToInt(inputValue);
            if (input == 0) {
                return;
            }
            if (input >= items.size() || input < 0) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }

            while (true) {
                GrowthItem item = items.get(input - 1);
                // 레벨이 안되면 아무것도 못한다고 알려주기
                if (!item.isPlantable(playerLevel)) {
                    printNotPlantable();
                    scanner.nextLine();
                    continue;
                }
                // 아무것도 안한 상태면 재배시작하기
                if (!item.isPlanted()) {
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("        재배 중이거나 수확 가능한 아이템이 없습니다.");
                    System.out.println();
                    System.out.println(item.getName() + " 을(를) 재배할까요?");
                    System.out.println();
                    System.out.println("1. 재배 하기        0. 이전 메뉴로 가기");
                    System.out.print("입력 >> ");
                    String subInputValue = scanner.next();
                    if (!MyHomeUtils.isInteger(subInputValue)) {
                        MyHomeUtils.enterAgain();
                        scanner.nextLine();
                        continue;
                    }
                    int subInput = MyHomeUtils.stringToInt(subInputValue);
                    if (subInput < 0 || 1 < subInput) {
                        MyHomeUtils.enterAgain();
                        scanner.nextLine();
                        continue;
                    }
                    if (player.getGold() < item.getCost()) {
                        System.out.println("┌──────────────────────────────────────────────────┐");
                        System.out.println("                   골드가 부족합니다.");
                        scanner.nextLine();
                        continue;
                    }

                    int playerGold = player.getGold();
                    int plantCost = item.getCost();
                    int remainGold = playerGold - plantCost;
                    if (remainGold < 0) {
                        remainGold = 0;
                    }
                    player.updateGold(remainGold);
                    item.plant();
                    new Thread(new FarmTimer(item)).start();
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("                  " + item.getName() + " 을(를) 재배합니다.");
                    System.out.println("                재배가 완료되면 알려드릴게요!");
                    System.out.println("└──────────────────────────────────────────────────┘");
                    break;
                }

                // 기다리기
                if (!item.isHarvestable()) {
                    System.out.println();
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("               아직 " + item.getName() + " 을(를) 재배 중이에요.");
                    System.out.println("                재배가 완료되면 알려드릴게요!");
                    System.out.println("└──────────────────────────────────────────────────┘");
                    break;
                }
                // 수확가능 한 양을 모두 수확했을 때
                if (item.getHarvestRemainQuantity() < 1) {
                    item.initHarvestable();
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("          수확할 수 있는 양을 모두 수확했어요.");
                    System.out.println("          " + item.getName() + " 획득량: " + item.getHarvestCount());
                    System.out.println();
                    System.out.println("               이전 메뉴로 돌아갑니다.");
                    scanner.nextLine();
                    break;
                }
                // 수확하기
                new BearCatchesFishGame().start(item);
                playSound();
                System.out.println();
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("               " + item.getName() + " 1개 획득!");
                player.saveItem(ItemEntry.createItemEntry(item, 1));
                item.decreaseHarvestRemainQuantityBy1();
                int exp = player.getExp() + item.getExp();
                player.updateExp(exp);
                int fatigability = player.getFatigability() + 15;
                if (fatigability > 100) {
                    fatigability = 100;
                }
                player.updateFatigability(fatigability);
                scanner.nextLine();
                MyHomeUtils.printLineAsCount(100);
            }
        }
    }
}