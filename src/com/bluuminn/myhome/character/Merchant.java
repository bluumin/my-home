package com.bluuminn.myhome.character;

import com.bluuminn.myhome.etc.MyHomeUtils;
import com.bluuminn.myhome.inventory.ItemEntry;
import com.bluuminn.myhome.item.ItemStorage;
import com.bluuminn.myhome.item.StoreItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Merchant extends NPC {
    private List<StoreItem> onSaleItems = new ArrayList<>();

    private Merchant(String name, ItemStorage itemStorage) {
        super(name);
        onSaleItems = itemStorage.getStoreItems();
    }

    public static Merchant createMerchant(String name, ItemStorage itemStorage) {
        return new Merchant(name, itemStorage);
    }

    public void buyAndSell(Player player) {
        Scanner scanner = new Scanner(System.in);
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