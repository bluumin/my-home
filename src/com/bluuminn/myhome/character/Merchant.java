package com.bluuminn.myhome.character;

import com.bluuminn.myhome.etc.MyHomeUtils;
import com.bluuminn.myhome.inventory.ItemEntry;
import com.bluuminn.myhome.item.Potion;
import com.bluuminn.myhome.item.StoreItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Merchant extends NPC {
    private final List<StoreItem> onSaleItems = new ArrayList<>();

    private Merchant(String name) {
        super(name);

        String areaName = "상점";
        StoreItem woodenWorkbench = new StoreItem("원목 작업대", areaName, 100);
        StoreItem cookingStove = new StoreItem("요리용 화덕", areaName, 2400);

        StoreItem sheepskin = new StoreItem("양가죽", areaName, 400);
        StoreItem beef = new StoreItem("소고기", areaName, 400);
        StoreItem asparagus = new StoreItem("아스파라거스", areaName, 300);
        StoreItem cowhide = new StoreItem("소가죽", areaName, 500);
        StoreItem horsehide = new StoreItem("말가죽", areaName, 500);
        Potion recovery30 = new Potion("피로도 30 회복 물약", areaName, 2000, 30);
        Potion recovery70 = new Potion("피로도 70 회복 물약", areaName, 5500, 70);
        Potion recovery100 = new Potion("피로도 100 회복 물약", areaName, 10000, 100);

        onSaleItems.add(woodenWorkbench);
        onSaleItems.add(cookingStove);
        onSaleItems.add(sheepskin);
        onSaleItems.add(beef);
        onSaleItems.add(asparagus);
        onSaleItems.add(cowhide);
        onSaleItems.add(horsehide);
        onSaleItems.add(recovery30);
        onSaleItems.add(recovery70);
        onSaleItems.add(recovery100);
    }

    public static Merchant createMerchant(String name) {
        return new Merchant(name);
    }

    public void buyAndSell(Player player, Scanner scanner) {
        while (true) {
            MyHomeUtils.printLineAsCount(100);
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("        어서오세요. 무엇을 도와드릴까요? (0. 이전으로)");
            System.out.println();
            System.out.println("              1. 아이템 구입");
            System.out.println("              2. 아이템 판매");
            System.out.println();
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
            if (input == 1) {
                if (player.getGold() <= 0) {
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("        보유한 골드가 없어 아이템을 구매할 수 없습니다.");
                    return;
                }
                userPurchases(player, scanner);
                continue;
            }
            if (input == 2) {
                player.sellItem(scanner);
            }
        }
    }

    public void userPurchases(Player player, Scanner scanner) {
        while (true) {
            showItems(player);
            System.out.println();
            System.out.println("구매하실 아이템의 번호를 입력하세요. (0. 이전으로)");
            System.out.println();
            System.out.print("입력 >> ");
            String inputValue = scanner.next();
            scanner.nextLine();
            if (!MyHomeUtils.isInteger(inputValue)) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            int input = MyHomeUtils.stringToInt(inputValue);
            if (input == 0) {
                break;
            }
            if (input > onSaleItems.size()) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }

            StoreItem item = onSaleItems.get(input);
            if (player.getLevel() < item.getLevel()) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("         아직 레벨이 충족되지 않아 구매할 수 없습니다.");
                System.out.println();
                scanner.nextLine();
                continue;
            }

            String itemName = item.getName();
            if ("원목 작업대".equals(itemName) || "요리용 화덕".equals(itemName)) {
                while (true) {
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("    " + itemName + "을(를) 구매하시겠습니까?");
                    System.out.println("            1. 구입하기           0. 뒤로가기");
                    System.out.println();
                    System.out.print("입력 >> ");
                    inputValue = scanner.next();
                    scanner.nextLine();
                    if (!MyHomeUtils.isInteger(inputValue)) {
                        MyHomeUtils.enterAgain();
                        scanner.nextLine();
                        break;
                    }
                    input = MyHomeUtils.stringToInt(inputValue);
                    if (input == 0) {
                        break;
                    }
                    if (input > 1 || input < 0) {
                        MyHomeUtils.enterAgain();
                        scanner.nextLine();
                        continue;
                    }
                    int playerGold = player.getGold();
                    int itemPrice = item.getPrice();
                    player.updateGold(playerGold - itemPrice);
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("                " + itemName + "을(를) 구입했어요!");
                    if ("원목 작업대".equals(itemName)) {
                        player.ownWoodenWorkbench();
                    }
                    if ("요리용 화덕".equals(itemName)) {
                        player.ownCookingStove();
                    }
                    break;
                }
                continue;
            }

            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("    " + itemName + " 을(를) 몇 개 구매하시겠습니까?");
            System.out.println("     (0. 이전으로)");
            System.out.println();
            System.out.print("입력 >> ");
            inputValue = scanner.next();
            scanner.nextLine();
            if (!MyHomeUtils.isInteger(inputValue)) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            int itemQuantity = MyHomeUtils.stringToInt(inputValue);
            if (itemQuantity == 0) {
                continue;
            }
            int totalPrice = item.getPrice() * itemQuantity;

            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println(item.getName() + " " + itemQuantity + " 개를");
            System.out.println(totalPrice + "골드에 구매 하시겠습니까?");
            System.out.println();
            System.out.println("1. 예        0. 아니오(이전으로 판매 아이템 목록 보기)");
            System.out.println();
            System.out.print("입력 >> ");
            inputValue = scanner.next();
            scanner.nextLine();
            if (!MyHomeUtils.isInteger(inputValue)) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            input = MyHomeUtils.stringToInt(inputValue);
            if (input == 0) {
                continue;
            }
            int playerGold = player.getGold();
            if (totalPrice > playerGold) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("                  골드가 부족합니다.");
                System.out.println();
                scanner.nextLine();
                continue;
            }
            int remainGold = playerGold - totalPrice;
            player.updateGold(remainGold);
            player.saveItem(ItemEntry.of(item, itemQuantity));
            System.out.println("구매 완료!");
            System.out.println();
            System.out.println("현재 보유 골드: " + player.getGold());
            System.out.println("계속 하시려면 아무키나 입력하세요.");
            scanner.nextLine();
            MyHomeUtils.printLineAsCount(100);
        }
    }

    private void showItems(Player player) {
        // TODO: 타임세일 해보기
//        Thread StoreTimer = new Thread(new StoreTimer(store));
//        StoreTimer.start();
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("                 판매 아이템 목록");
        System.out.println();

        for (int i = 0; i < onSaleItems.size(); i++) {
            StoreItem item = onSaleItems.get(i);
            int itemLevel = item.getLevel();
            System.out.print(i + 1 + ". " + item.getName());
            if ("원목 작업대".equals(item.getName()) && player.hasWoodenWorkbench()) {
                System.out.print(" (구입완료)");
                continue;
            }
            if ("요리용 화덕".equals(item.getName()) && player.hasCookingStove()) {
                System.out.print(" (구입완료)");
                continue;
            }
            if (player.getLevel() < itemLevel) {
                System.out.print("(HOLD - LV." + itemLevel + "이상)");
            }
            System.out.print("\n\t\t\t\t\t\t\t\t\t");
            System.out.printf("%5d" + " 골드", item.getPrice());
        }
    }
}