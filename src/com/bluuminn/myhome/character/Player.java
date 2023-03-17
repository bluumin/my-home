package com.bluuminn.myhome.character;

import com.bluuminn.myhome.etc.MyHomeConstants;
import com.bluuminn.myhome.etc.MyHomeUtils;
import com.bluuminn.myhome.inventory.Inventory;
import com.bluuminn.myhome.inventory.ItemEntry;
import com.bluuminn.myhome.item.CraftItem;
import com.bluuminn.myhome.item.ItemType;
import com.bluuminn.myhome.item.Potion;
import com.bluuminn.myhome.quest.Quest;
import com.bluuminn.myhome.quest.QuestInfo;
import com.bluuminn.myhome.quest.Title;
import com.bluuminn.myhome.quest.TitleStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player extends Character {
    private int restCount;
    // 퀘스트 완료 횟수 체크 = 업적 달성용
    private int questCompletedCount;
    // 아이템 제작 완료 횟수 체크 = 업적 달성용
    private int craftingCount;
    private boolean isResting;
    private int exp;                // 경험치
    private int maxExp;             // 레벨당 최대 경험치
    private int level;             // 플레이어 레벨
    private int fatigability;      // 피로도
    private int gold;               // 돈(마이홈의 화폐 단위)
    private boolean hasWoodenWorkbench;       // 원목 작업대 구입 여부
    private boolean hasCookingStove;         // 요리용 화덕 구입 여부
    private Inventory inventory;
    private List<Quest> quests;
    private List<Title> titles;

    private Player(String name) {
        super(name);
        this.level = 1;
        this.exp = 0;
        this.maxExp = 25;
        this.restCount = 5;
        this.gold = MyHomeConstants.INITIAL_SUPPORT_GOLD;
        this.fatigability = 0;   // 피로도
        this.hasWoodenWorkbench = false;
        this.hasCookingStove = false;
        this.isResting = false;

        this.questCompletedCount = 0;
        this.craftingCount = 0;

        this.quests = new ArrayList<>();
        this.inventory = new Inventory();
        this.titles = getTitles();
    }

    private List<Title> getTitles() {
        this.titles = new ArrayList<>();
        TitleStorage.TITLE_INFOS.stream().map(Title::of).forEach(titles::add);
        return titles;
    }

    public static Player createPlayer(String name) {
        return new Player(name);
    }

    public int getFatigability() {
        return fatigability;
    }

    public void updateFatigability(int fatigability) {
        if (fatigability < 0) {
            this.fatigability = 0;
            return;
        }
        if (fatigability > 100) {
            this.fatigability = 100;
            return;
        }
        this.fatigability = fatigability;
    }

    public int getExp() {
        return exp;
    }

    public void updateExp(int exp) {
        this.exp = exp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public int getRestCount() {
        return restCount;
    }

    public void resetRestCount() {
        this.restCount = 5;
    }

    public boolean isResting() {
        return isResting;
    }

    private void rest() {
        this.isResting = true;
    }

    private void rested() {
        this.isResting = false;
    }

    private void reduceRestCountBy1() {
        this.restCount--;
    }

    public int getLevel() {
        return level;
    }

    public int getGold() {
        return gold;
    }

    public int getCraftingCount() {
        return craftingCount;
    }

    private void increaseCraftingCountBy1() {
        this.craftingCount++;
    }

    public int getQuestCompletedCount() {
        return questCompletedCount;
    }

    private void increaseQuestCompletedCountBy1() {
        this.questCompletedCount++;
    }

    public boolean hasWoodenWorkbench() {
        return hasWoodenWorkbench;
    }

    public void ownWoodenWorkbench() {
        this.hasWoodenWorkbench = true;
    }

    public void ownCookingStove() {
        this.hasCookingStove = true;
    }

    public boolean hasCookingStove() {
        return hasCookingStove;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void addQuest(Quest quest) {
        this.quests.add(quest);
    }

    public void replaceInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void levelUp() {
        if (this.exp < this.maxExp) {
            return;
        }

        this.exp -= this.maxExp;
        this.level += 1;
        payGold();
        increaseMaxExp();
        System.out.println("\n" +
                "\t\t    __    _______    __________       __  ______     __\n" +
                "\t\t   / /   / ____/ |  / / ____/ /      / / / / __ \\   / /\n" +
                "\t\t  / /   / __/  | | / / __/ / /      / / / / /_/ /  / / \n" +
                "\t\t / /___/ /___  | |/ / /___/ /___   / /_/ / ____/  /_/  \n" +
                "\t\t/_____/_____/  |___/_____/_____/   \\____/_/      (_)   \n" +
                "\t\t                                                       \n");

    }

    private void payGold() {
        if (this.level <= 5) {
            this.updateGold(this.level + 3000);
        }
    }

    private void increaseMaxExp() {
        this.maxExp *= this.level < 3 ? 1.1 : 1.3;
    }

    public void updateGold(int gold) {
        this.gold = gold;
    }

    public boolean needToRest() {
        return this.fatigability >= 100;
    }

    public void achieveTitle() {
        if (getQuestCompletedCount() >= 3) {
            System.out.println();
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                   업적을 달성했어요!");
            this.titles.get(0).achieved();
        }

        if (getCraftingCount() >= 10) {
            System.out.println();
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                   업적을 달성했어요!");
            this.titles.get(1).achieved();
        }
    }

    public void willRest(Scanner scanner) {
        while (true) {
            if (this.isResting) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("                  휴식 모드를 종료합니다.");
                System.out.println("        피로도가 10 감소했습니다. (현재피로도: " + getFatigability() + ")");
                System.out.println();
                rested();
                updateFatigability(this.fatigability - 10);
                scanner.nextLine();
                break;
            }

            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("         " + getName() + "! 피로가 많이 쌓이셨나보군요.");
            System.out.println("            휴식 모드로 전환할까요? (1000G/1회)");
            System.out.println("                 남은 횟수: " + getRestCount());
            System.out.println();
            System.out.println("1. 휴식 취하기    0. 이전 단계로");
            System.out.print("입력 >> ");

            String inputValue = scanner.next();
            scanner.nextLine();
            if (!MyHomeUtils.isInteger(inputValue)) {
                MyHomeUtils.enterAgain();
                continue;
            }

            int input = MyHomeUtils.stringToInt(inputValue);
            if (input == 0) {
                break;
            }
            if (input > 1 || input < 0) {
                MyHomeUtils.enterAgain();
                continue;
            }
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                휴식 모드로 전환합니다.");
            rest();
            updateGold(this.gold - 1000);
            reduceRestCountBy1();
            scanner.nextLine();
            break;
        }
    }

    public void showQuests(Scanner scanner) {
        while (true) {
            if (quests.isEmpty()) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("              진행 중인 퀘스트가 없습니다.");
                System.out.println("                이전 메뉴로 돌아갑니다.");
                scanner.nextLine();
                return;
            }

            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                    퀘스트 리스트 ");

            for (int i = 0; i < quests.size(); i++) {
                Quest quest = quests.get(i);
                System.out.println(i + 1 + ". " + quest.getQuestName());
            }
            System.out.println();
            System.out.println("퀘스트 번호를 입력하면 자세한 정보를 확인할 수 있습니다. (0. 이전으로)");
            System.out.print("입력 >> ");
            String inputValue = scanner.next();
            if (!MyHomeUtils.isInteger(inputValue)) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            int input = MyHomeUtils.stringToInt(inputValue);
            if (input < 0 || input > quests.size()) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            if (input == 0) {
                break;
            }
            Quest quest = quests.get(input);
            showQuestInfo(quest, scanner);
        }
    }

    public void showQuestInfo(Quest quest, Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                   퀘스트 상세 정보");
            System.out.println();
            String npcName = quest.getNpc().getName();
            System.out.print(npcName + ": ");

            // 퀘스트 내용 말하는 효과
            QuestInfo info = quest.getInfo();
            for (char c : info.getScript()) {
                System.out.print(c);
                MyHomeUtils.delayAsMillis((int) (Math.random() * 80) + 40);
            }

            // 퀘스트 이름 출력
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("[ " + info.getName() + " ]");
            System.out.println();

            // 퀘스트 필요한 아이템 출력
            System.out.println("< 필요 아이템 >");
            System.out.println();

            List<ItemEntry> requiredCraftItems = info.getRequiredCraftItems();
            for (int i = 0; i < requiredCraftItems.size(); i++) {
                ItemEntry craftItem = requiredCraftItems.get(i);
                System.out.println(i + 1 + ". " + craftItem.getItemName() + " " + craftItem.getQuantity() + "개 보유하기");
            }

            System.out.println();

            List<ItemEntry> requiredDeliveryItems = info.getRequiredDeliveryItems();
            for (int i = 0; i < requiredDeliveryItems.size(); i++) {
                ItemEntry deliveryItem = requiredDeliveryItems.get(i);
                System.out.println(i + 1 + ". " + deliveryItem.getItemName() + " " + deliveryItem.getQuantity() + "개 납품하기 (아이템 회수)");
            }

            System.out.println();
            System.out.println("< 보상 >");

            if (info.getRewardExp() > 0) {
                System.out.println("경험치: " + info.getRewardExp());
            }
            if (info.getRewardGold() > 0) {
                System.out.println("골드: " + info.getRewardGold());
            }
            List<ItemEntry> rewardItems = info.getRewardItems();
            for (int i = 0; i < rewardItems.size(); i++) {
                ItemEntry rewardItem = rewardItems.get(i);
                System.out.println(i + 1 + ". " + rewardItem.getItemName() + " " + rewardItem.getQuantity() + "개");
            }

            System.out.println();
            System.out.println();
            System.out.println("1. 퀘스트 완료하기       0. 이전으로");
            String inputValue = scanner.next();
            if (!MyHomeUtils.isInteger(inputValue)) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            int input = MyHomeUtils.stringToInt(inputValue);
            if (input < 0 || input > 1) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            if (input == 0) {
                break;
            }
            if (!isAllCollected(info)) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("              아직 필요한 아이템이 부족해요.");
                System.out.println("                이전 단계로 돌아갑니다.");
                scanner.nextLine();
                break;
            }

            if (!quest.complete(this)) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("        인벤토리에 보상 아이템을 수령할 공간이 없어요.");
                System.out.println("            인벤토리 확인 후 다시 시도해주세요.");
                System.out.println("                이전 단계로 돌아갑니다.");
                scanner.nextLine();
                continue;
            }

            System.out.println();
            System.out.print(npcName + ": ");
            for (char c : info.getEndingScript()) {
                System.out.print(c);
                MyHomeUtils.delayAsMillis((int) (Math.random() * 80) + 40);
            }

            System.out.println();
            System.out.println();
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("        [ " + info.getName() + " ] 퀘스트 완료!");
            increaseQuestCompletedCountBy1();
            scanner.nextLine();
        }
    }

    public boolean isAllCollected(QuestInfo info) {
        if (inventory.isEmpty()) {
            return false;
        }

        if (!isCollected(info.getRequiredCraftItems())) {
            return false;
        }
        if (!isCollected(info.getRequiredDeliveryItems())) {
            return false;
        }
        return true;
    }

    private boolean isCollected(List<ItemEntry> requiredItems) {
        for (ItemEntry craftItem : requiredItems) {
            ItemEntry inventoryItem = inventory.find(craftItem);
            if (inventoryItem == null) {
                return false;
            }
            if (craftItem.getQuantity() > inventoryItem.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    public void craft(CraftItem craftItem, int craftCount) {
        String itemName = craftItem.getName();
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("            " + itemName + " 을(를) 제작합니다.");

        // TODO: 로딩 스레드
        // TODO: 제작 중 동영상 스레드
        Inventory playerInventory = new Inventory(getInventory());
        try {
            for (ItemEntry requiredItem : craftItem.getRequiredItems()) {
                int requiredQuantity = requiredItem.getQuantity();

                ItemEntry inventoryItem = getItem(requiredItem);
                int inventoryItemQuantity = inventoryItem.getQuantity();
                inventoryItemQuantity -= (requiredQuantity * craftCount);
                inventoryItem.updateQuantity(inventoryItemQuantity);

                if (inventoryItemQuantity <= 0) {
                    inventory.remove(inventoryItem);
                }
            }

            inventory.add(ItemEntry.of(craftItem, craftCount));

            System.out.println();
            System.out.println();
            increaseCraftingCountBy1();
            updateFatigability(getFatigability() + 7);
            updateExp(getExp() + craftItem.getExp());

            System.out.println(itemName + " 제작 완료!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            replaceInventory(playerInventory);
        }
    }

    public void showInfo(Scanner scanner) {
        while (true) {
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("            플레이어 [ " + getName() + " ] 정보");
            System.out.println();
            System.out.println("    레벨: " + getLevel());
            System.out.println("    경험치: " + getExp() + " / " + getMaxExp());
            System.out.println("    피로도: " + (isResting ? "회복 중.." : getFatigability()));
            System.out.println();
            System.out.println("    골드 : " + getGold());
            System.out.println();
            System.out.println("1. 업적 확인하기       0. 메인 메뉴로");
            System.out.print("입력 >> ");
            String inputValue = scanner.next();
            if (!MyHomeUtils.isInteger(inputValue)) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            int input = MyHomeUtils.stringToInt(inputValue);
            if (input > 1 || input < 0) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            if (input == 0) {
                break;
            }

            // 업적 보기
            int titleQty = this.titles.size();
            while (true) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("                    업적 리스트");
                System.out.println();
                for (int i = 0; i < titleQty; i++) {
                    String isAchieved = this.titles.get(i).isAchieved() ? "달성" : "미달성";

                    System.out.print("    " + (i + 1) + ". ");
                    System.out.printf("%-12s", this.titles.get(i).getInfo().getName());
                    System.out.println("\t\t" + isAchieved);
                }

                System.out.println();
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("                   업적 달성 조건 보기");
                System.out.println("       조건을 확인하고 싶은 업적의 번호를 입력해주세요.  (0. 이전으로)");
                System.out.print("입력 >> ");

                inputValue = scanner.next();
                scanner.nextLine();
                if (!MyHomeUtils.isInteger(inputValue)) {
                    MyHomeUtils.enterAgain();
                    scanner.nextLine();
                    continue;
                }

                input = MyHomeUtils.stringToInt(inputValue);
                if (input < 0 || input > titleQty) {
                    MyHomeUtils.enterAgain();
                    scanner.nextLine();
                    continue;
                }

                if (input == 0) {
                    System.out.println();
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("                플레이어 정보로 돌아갑니다.");
                    scanner.nextLine();
                    break;
                }

                System.out.println();
                System.out.println(input + ". " + this.titles.get(input - 1).getInfo().getName());
                System.out.println();
                System.out.println("업적달성조건 : " + this.titles.get(input - 1).getInfo().getCondition());
            }
        }
    }

    public void sellItem(Scanner scanner) {
        while (true) {
            if (inventory.isEmpty()) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("            판매 할 수 있는 아이템이 없습니다.");
                System.out.println("                이전 메뉴로 돌아갑니다.");
                return;
            }

            showInventory();
            System.out.println();
            System.out.println("판매하고 싶은 아이템의 번호를 입력하세요. (0. 이전으로)");
            System.out.println();
            System.out.print("입력 >> ");
            String inputValue = scanner.next();
            scanner.nextLine();
            if (!MyHomeUtils.isInteger(inputValue)) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            int input = Integer.parseInt(inputValue);
            if (input == 0) {
                break;
            }
            if (input < 0 || input > inventory.getNumberOfItems()) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }

            // 선택한 번호가 범위 내에 있으면 (범위 : 인벤토리 1번 ~ 마지막번)
            while (true) {
                List<ItemEntry> items = inventory.getItems();
                ItemEntry item = items.get(input);
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("  " + item.getItemName() + " 을(를) 몇 개 판매 하시겠습니까? (0. 이전으로)");
                System.out.println();
                System.out.print("입력 >> ");
                inputValue = scanner.next();
                scanner.nextLine();
                if (!MyHomeUtils.isInteger(inputValue)) {
                    MyHomeUtils.enterAgain();
                    scanner.nextLine();
                    continue;
                }
                int salesQuantity = Integer.parseInt(inputValue);
                if (salesQuantity == 0) {
                    return;
                }
                if (salesQuantity < 0 || item.getQuantity() < salesQuantity) {
                    MyHomeUtils.enterAgain();
                    scanner.nextLine();
                    continue;
                }

                // 선택한 아이템 이름을 저장
                String itemName = item.getItemName();

                // 한개당 가격을 저장
                int pricePerItem = item.getItemSalePrice();

                // 총 가격을 저장
                // 구매할 아이템의 총 가격을 임시 저장할 변수
                int totalPrice = pricePerItem * salesQuantity;

                // 인벤토리에 있는 아이템의 개수 저장
                int itemQuantity = item.getQuantity();

                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("   " + itemName + " " + salesQuantity + "개를 " + totalPrice + "골드에 판매 하시겠습니까?");
                System.out.println();
                System.out.println("1. 예        0. 아니오(이전으로)");
                System.out.println();
                System.out.print("입력 >> ");
                inputValue = scanner.next();
                scanner.nextLine();
                if (!MyHomeUtils.isInteger(inputValue)) {
                    MyHomeUtils.enterAgain();
                    scanner.nextLine();
                    continue;
                }
                input = Integer.parseInt(inputValue);
                if (input == 0) {
                    break;
                }
                if (input > 1 || input < 0) {
                    MyHomeUtils.enterAgain();
                    scanner.nextLine();
                    continue;
                }

                if (salesQuantity > item.getQuantity()) {
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("               아이템의 개수가 부족합니다.");
                    System.out.println();
                    System.out.println("      현재 보유한 " + itemName + " 개수: " + itemQuantity + "개");
                    continue;
                }

                int remainQuantity = itemQuantity - salesQuantity;
                item.updateQuantity(remainQuantity);
                if (remainQuantity <= 0) {
                    inventory.remove(item);
                }
                updateGold(this.gold + totalPrice);
                System.out.println();
                System.out.println("판매 완료!");
                System.out.println("현재 보유 골드: " + this.gold);
                break;
            }
        }
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("               인벤토리가 비었습니다.");
            return;
        }
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("                  인벤토리 리스트");
        System.out.println();
        List<ItemEntry> inventoryItems = inventory.getItems();
        for (int i = 0; i < inventory.getNumberOfItems(); i++) {
            ItemEntry item = inventoryItems.get(i);
            System.out.print(i + 1 + ". ");
            System.out.println(item.getItemName() + " (" + item.getItemSalePrice() + "골드)");
            System.out.println("\t\t\t\t\t\t\t\t" + item.getQuantity() + "개");
        }
    }

    public void showInventory(Scanner scanner) {
        while (true) {
            if (inventory.isEmpty()) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("                  인벤토리가 비어있어요.");
                System.out.println("           아무 키나 입력하면 메인 메뉴로 돌아가요.");
                System.out.println("└──────────────────────────────────────────────────┘");
                scanner.nextLine();
                MyHomeUtils.printLineAsCount(100);
            }

            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                   인벤토리 리스트");
            System.out.println();
            List<ItemEntry> inventoryItems = inventory.getItems();
            for (int i = 0; i < inventory.getNumberOfItems(); i++) {
                ItemEntry item = inventoryItems.get(i);
                System.out.print(i + 1 + ". " + item.getItemName());
                System.out.println();
                System.out.println("\t\t\t\t\t\t" + item.getQuantity() + "개");
            }

            System.out.println();
            System.out.println();
            System.out.println("확인하고 싶은 아이템의 번호를 입력하세요. (0. 이전으로)");
            System.out.print("입력 >> ");

            String inputValue = scanner.next();
            if (!MyHomeUtils.isInteger(inputValue)) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            int input = MyHomeUtils.stringToInt(inputValue);
            if (input < 0 || input > inventory.getNumberOfItems()) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            if (input == 0) {
                break;
            }

            ItemEntry item = inventoryItems.get(input);
            if (item.getItemType() == ItemType.CONSUMPTION) {
                while (true) {
                    System.out.println();
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.print("        [ " + item.getItemName() + " ] 을(를) ");
                    System.out.println("                 사용하시겠습니까?");
                    System.out.println();
                    System.out.println("1. 사용        0. 이전으로");
                    System.out.print("입력 >> ");

                    inputValue = scanner.next();
                    if (!MyHomeUtils.isInteger(inputValue)) {
                        MyHomeUtils.enterAgain();
                        scanner.nextLine();
                        continue;
                    }
                    input = MyHomeUtils.stringToInt(inputValue);
                    if (input < 0 || input > 1) {
                        MyHomeUtils.enterAgain();
                        scanner.nextLine();
                        continue;
                    }
                    if (input == 0) {
                        break;
                    }

                    drink(item);
                    if (item.getQuantity() <= 0) {
                        inventory.remove(item);
                        break;
                    }
                }
                continue;
            }
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("        [ " + item.getItemName() + "] 아이템 정보");
            System.out.println();
            System.out.println("  타입: " + item.getItemType().getName());
            System.out.println("  생산아이템: " + item.getItem().getResource());
            System.out.println("  생산구역: " + item.getItem().getProductionArea());
            System.out.println();
            System.out.print("계속 하시려면 아무키나 입력하세요.");
            scanner.nextLine();
        }
    }

    private void drink(ItemEntry item) {
        if (item.getItemType() != ItemType.CONSUMPTION) {
            return;
        }
        item.updateQuantity(item.getQuantity() - 1);
        Potion potion = (Potion) item.getItem();
        int recovery = potion.getRecovery();
        updateFatigability(getFatigability() - recovery);
        System.out.println(getName() + " 아이템을 사용해서 피로도가 " + recovery + " 만큼 감소했어요!");
    }

    public void saveItem(ItemEntry item) {
        Inventory playerInventory = new Inventory(getInventory());
        try {
            inventory.add(item);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            replaceInventory(playerInventory);
        }
    }

    public ItemEntry getItem(ItemEntry item) {
        return inventory.find(item);
    }

    public boolean hasQuest(QuestInfo questInfo) {
        for (Quest quest : quests) {
            if (quest.getInfo().equals(questInfo)) {
                return true;
            }
        }
        return false;
    }
}