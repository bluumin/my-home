package com.bluuminn.myhome.character;

import com.bluuminn.myhome.area.CraftShop;
import com.bluuminn.myhome.etc.MyHomeConstants;
import com.bluuminn.myhome.etc.MyHomeUtils;
import com.bluuminn.myhome.inventory.Inventory;
import com.bluuminn.myhome.inventory.ItemEntry;
import com.bluuminn.myhome.item.CraftItem;
import com.bluuminn.myhome.quest.Quest;
import com.bluuminn.myhome.quest.Title;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player extends Character {
    /**
     * 수확하기
     * 재배하기
     * 아이템 제작하기
     */

    private int restCount = 5;

    private Quest tmpQuest = null;

    // 퀘스트 완료 횟수 체크 = 업적 달성용
    private int questCompletedCount;

    // 아이템 제작 완료 횟수 체크 = 업적 달성용
    private int craftingCount;

    // 퀘스트 리스트 체크 용
    private boolean ckck;

    private boolean isResting;

    // 칭호 리스트
    private final ArrayList<Title> titles = new ArrayList<>();

    // 제작 아이템 목록 리스트
    private final ArrayList<CraftItem> craftItemList = new ArrayList<>();

    // 퀘스트 리스트
    private final ArrayList<Quest> playerQuestList = new ArrayList<>();

    // 아이템 한개당 단가를 임시 저장할 변수
    private int singlePrice;

    // 구매할 아이템의 총 가격을 임시 저장할 변수
    private int totalPrice;

    // 구매할 아이템의 이름을 임시 저장할 변수
    private String selItem;

    private int exp;                // 경험치
    private int maxExp;             // 레벨당 최대 경험치
    private int level;             // 플레이어 레벨
    private int fatigability;      // 피로도
    private int gold;               // 돈(마이홈의 화폐 단위)
    private boolean hasWoodenWorkbench;       // 원목 작업대 구입 여부
    private boolean hasCookingStove;         // 요리용 화덕 구입 여부
    private final Inventory inventory = new Inventory();

    private Player(String name) {
        super(name);
        this.level = 1;
        this.exp = 0;
        this.maxExp = 25;
        this.gold = MyHomeConstants.INITIAL_SUPPORT_GOLD;
        this.fatigability = 0;   // 피로도
        this.hasWoodenWorkbench = false;
        this.hasCookingStove = false;
        this.isResting = false;
        this.questCompletedCount = 0;
        this.craftingCount = 0;
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
        if (this.questCompletedCount >= 3) {
            System.out.println();
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                   업적을 달성했어요!");
            this.titles.get(0).achieved();
        }

        if (this.craftingCount >= 10) {
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

    // ========================= 퀘스트 리스트 체크 ==============================
    public boolean ckQuest(Player player, Quest tmpQuest) {
        for (int i = 0; i < player.playerQuestList.size(); i++) {
            // 인벤토리에 있는 아이템 이름과 아이템 이름이 겹칠경우에는 추가하면 안됨
            if (player.playerQuestList.get(i).questName.equals(tmpQuest.questName)) {
                return false;
            }
        }
        // 겹치는 이름이 없을 경우 추가 가능
        return true;
    }

    // ============================ 플레이어 퀘스트 리스트 확인 ==============================
    public void viewQuestList(Player player, NPC mimi) {
//        System.out.println(mimi.tmpPlayer.playerQuestList.size());
        if (NPC.tmpPlayer.playerQuestList.size() >= 0) {
//            System.out.println("test1");
            for (int i = 0; i < NPC.tmpPlayer.playerQuestList.size(); i++) {
//                System.out.println("test2");
                tmpQuest = NPC.tmpPlayer.playerQuestList.get(i);
                if (player.playerQuestList.size() <= 0) {
                    player.playerQuestList.add(tmpQuest);
                } else {
                    ckck = player.ckQuest(player, tmpQuest);
                    if (ckck) {
                        player.playerQuestList.add(tmpQuest);
                    }
                }
            }
        }

        if (player.fatigability >= 100) {
            System.out.println("피로도가 너무 높아서 아무 것도 할 수 없어요.");
            scanner.nextLine();
        } else {
            if (player.playerQuestList.size() <= 0) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("            진행 중인 퀘스트가 없습니다.");
                scanner.nextLine();
            } else {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("                  퀘스트 리스트 ");

                for (int i = 0; i < player.playerQuestList.size(); i++) {
                    System.out.print(i + 1 + ". ");
                    System.out.println(player.playerQuestList.get(i).questName);
                }
                System.out.println();
                System.out.print("입력 >> ");
                inputVal = scanner.nextInt();
                scanner.nextLine();

                // 입력값이 플레이어 퀘스트 리스트 사이즈 값보다 작거나 같으면 선택값의 퀘스트를 보여줌
                if (inputVal <= player.playerQuestList.size() && inputVal > 0) {
                    player.questInfo(player, inputVal, mimi);
                } else if (inputVal == 0) {
                }
            }
        }
    }

    // =================== 퀘스트 정보 보기 ==================

    public void questInfo(Player player, int inputVal, NPC mimi) {
        System.out.println();

        // 퀘스트 낸 npc 이름 출력  ==> ex) 미미 : ~~~~~
        System.out.print(playerQuestList.get(inputVal - 1).npcName + " : ");

        // 퀘스트 내용 말하는 효과
        for (int i = 0; i < playerQuestList.get(inputVal - 1).questContent.length; i++) {
            System.out.print(playerQuestList.get(inputVal - 1).questContent[i]);
            try {
                int slept = (int) (Math.random() * 80) + 40;
//                System.out.println(slept);
                Thread.sleep(slept);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        scanner.nextLine();


        // 퀘스트 이름 출력
        System.out.println("== 퀘스트 이름 ==");
        System.out.println(playerQuestList.get(inputVal - 1).questName);
        System.out.println();

        // 퀘스트 필요한 아이템 출력
        System.out.println("== 필요 아이템 ==");
        for (int i = 0; i < playerQuestList.get(inputVal - 1).needs.size(); i++) {
            System.out.print(i + 1 + ". ");
            System.out.print(playerQuestList.get(inputVal - 1).needs.get(i).needItem);
            System.out.print(" " + playerQuestList.get(inputVal - 1).needs.get(i).needItemCnt + "개");
            if (!playerQuestList.get(inputVal - 1).deliverCK) {
                System.out.println(" 보유하기");
            } else {
                System.out.println(" (아이템 회수)\n");
            }
            System.out.println();
        }

        System.out.println("== 보상 ==");
        // 퀘스트 보상 경험치가 있다면 출력
        if (playerQuestList.get(inputVal - 1).payExp > 0) {
            System.out.print("경험치 : ");
            System.out.println(playerQuestList.get(inputVal - 1).payExp);
        }

        // 퀘스트 보상 골드가 있다면 출력
        if (playerQuestList.get(inputVal - 1).payForGold > 0) {
            System.out.print("골드 : ");
            System.out.println(playerQuestList.get(inputVal - 1).payForGold);
        }

        // 퀘스트 보상 아이템이 있다면 출력
        if (playerQuestList.get(inputVal - 1).payItem != null) {
            System.out.print("아이템 : ");
            System.out.print(playerQuestList.get(inputVal - 1).payItem.entryName);
            System.out.println(" " + playerQuestList.get(inputVal - 1).payItemCount + "개");
        }
        System.out.println();
        System.out.println("1. 퀘스트 완료       else. 돌아가기");
        int sel = scanner.nextInt();
        scanner.nextLine();
        if (sel == 1) {
            // 완료여부 체크. true = 완료 false = 미완료
            boolean ckCom = questCompleteCK(player, inputVal);
            if (ckCom) {
                // 퀘스트가 반환형이면 퀘스트 아이템은 플레이어의 인벤토리에서 삭제해야함
                if (playerQuestList.get(inputVal - 1).deliverCK) {
//                    System.out.println("test 1");
                    for (int i = 0; i < playerQuestList.get(inputVal - 1).needs.size(); i++) {
//                        System.out.println("test 2");
                        String tmpitemname = playerQuestList.get(inputVal - 1).needs.get(i).needItem;
                        int tmpitemcnt = playerQuestList.get(inputVal - 1).needs.get(i).needItemCnt;
                        for (int j = 0; j < player.inventory.getAvailableItems(); j++) {
//                            System.out.println("test 3");
                            if (tmpitemname.equals(player.inventory.getItemName(player.inventory.getItem(j)))) {
                                player.inventory.remove(j, tmpitemcnt);
//                                System.out.println("test 4");
                            }
                        }
                    }
                }
                // 퀘스트 보상 경험치가 있다면 추가
                if (playerQuestList.get(inputVal - 1).payExp > 0) {
                    player.exp += playerQuestList.get(inputVal - 1).payExp;
                }

                // 퀘스트 보상 골드가 있다면 추가
                if (playerQuestList.get(inputVal - 1).payForGold > 0) {
                    player.gold += playerQuestList.get(inputVal - 1).payForGold;
                }

                // 퀘스트 보상 아이템이 있다면 추가
                if (playerQuestList.get(inputVal - 1).payItem != null) {
                    player.inventory.add(playerQuestList.get(inputVal - 1).payItem, playerQuestList.get(inputVal - 1).payItemCount);
                }

                // 퀘스트 낸 npc 이름 출력  ==> ex) 미미 : ~~~~~
                System.out.print(playerQuestList.get(inputVal - 1).npcName + " : ");
                // 퀘스트 내용 말하는 효과
                for (int i = 0; i < playerQuestList.get(inputVal - 1).questEnding.length; i++) {
                    System.out.print(playerQuestList.get(inputVal - 1).questEnding[i]);
                    try {
                        int slept = (int) (Math.random() * 80) + 40;
//                System.out.println(slept);
                        Thread.sleep(slept);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println();
                System.out.println();
                System.out.println("퀘스트 완료!");
                player.questCompletedCount += 1;
                NPC.tmpPlayer.playerQuestList.remove(inputVal - 1);
                playerQuestList.remove(inputVal - 1);
                scanner.nextLine();
            }
        } else {

        }

    }

    // ======================== 퀘스트 조건 맞는지 확인 ====================
    //      퀘스트 필요 아이템과 플레이어의 인벤토리를 비교해서 플레이어 인벤토리 안에
    //  퀘스트 필요 개수만큼 해당 아이템이 있으면 완료 표시. deliverCK 여부에 따라
    // 퀘스트 아이템 회수할것인지 말것인지 구현

    public boolean questCompleteCK(Player player, int inputVal) {
        // 만약 하나의 퀘스트가 여러 아이템이 필요한 상황일 때
        // 해당 퀘스트 needs 리스트의 개수만큼 카운트가 돼야 퀘스트 성공여부 판단할수 있음
        // 그때 카운트 할 용도의 변수
        int ckCnt = 0;

        // 플레이어의 인벤토리가 비어있으면 false 반환 => 완료되지 않음
        if (player.inventory.getAvailableItems() <= 0) {
            System.out.println("아직 필요한 아이템이 부족해요.");
            scanner.nextLine();
            return false;
        }
        // 플레이어가 인벤토리에 갖고 있는 개수만큼 반복
        for (int i = 0; i < player.inventory.getAvailableItems(); i++) {
            // 플레이어 인벤토리의 i번째 아이템 이름을 임시 저장
            String pItemName = player.inventory.getItemName(player.inventory.getItem(i));
            // 플레이어 인벤토리의 i번째 아이템의 개수를 임시 저장
            int pItemCnt = player.inventory.getItemCount(i);

            // 플레이어의 퀘스트리스트의 필요한 아이템 리스트 크기만큼 반복
            for (int j = 0; j < playerQuestList.get(inputVal - 1).needs.size(); j++) {
                // 플레이어가 선택한 퀘스트에 필요한 j번째 아이템 이름을 임시 저장
                String needItemName = playerQuestList.get(inputVal - 1).needs.get(j).needItem;

                // 플레이어가 선택한 퀘스트에 필요한 j번째 아이템 개수를 임시 저장
                int needItemCnt = playerQuestList.get(inputVal - 1).needs.get(j).needItemCnt;

                // 필요한 아이템의 이름과 플레이어 인벤토리 내의 이름이 일치하면서
                // 필요한 개수보다 플레이어가 보유한 아이템의 개수가 더 많은지 검사
                if (needItemName.equals(pItemName) && needItemCnt <= pItemCnt) {
                    ckCnt++;
                }
            }
        }

        // ckCnt가 퀘스트리스트 needs 개수랑 같으면 => 조건이 만족 됐다면
        if (ckCnt == playerQuestList.get(inputVal - 1).needs.size()) {
            playerQuestList.get(inputVal - 1).completeCK = true;
            // 체크용 프린트
//            System.out.println(playerQuestList.get(inputVal - 1).completeCK);
            return true;
        } else {
            // 체크용 프린트
//            System.out.println(playerQuestList.get(inputVal - 1).completeCK);
            System.out.print(playerQuestList.get(inputVal - 1).npcName + " : ");
            System.out.println("아직 필요한 아이템이 부족해요.");
            scanner.nextLine();
            return false;
        }

    }


    // ======================= 제작 공방으로 이동 ============================
    public void goToCraftShop(CraftShop craftShop, Player player) {

        if (player.fatigability >= 100) {
            System.out.println("피로도가 너무 높아서 아무 것도 할 수 없어요.");
            scanner.nextLine();
        } else {
            if (!player.hasWoodenWorkbench) {
                System.out.println("감사제를 준비하려면 원목 작업대가 필요해요.");
                System.out.println("상점에서 원목 작업대를 구입하세요.");
                System.out.println();
                scanner.nextLine();
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                progressBar.loading();
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("                판매 아이템 목록\n");
                System.out.print("      1. 원목 작업대\n\t\t\t\t\t\t\t\t\t");
                System.out.printf("%5d" + " 골드", 100);
                System.out.println("\n");
                System.out.println("1. 구입하기     else. 뒤로가기");
                System.out.print("입력 >> ");
                inputVal = scanner.nextInt();
                scanner.nextLine();
                if (inputVal == 1) {
                    System.out.println();
                    player.gold -= 100;
                    System.out.println("원목 작업대를 구입했어요!");
                    player.hasWoodenWorkbench = true;
                    scanner.nextLine();
                } else {
                    return;
                }
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
            }
            craftShop.printCraftMenu(player);
        }
    }


    // ============================== 아이템 제작 =============================

    // itemName - 어떤 아이템을 제작하는지 출력할 용도
    // itemEntry - 인벤토리 검사 비교용
    // cnt - 몇개 만들 건지
    // requiCnt - 필요한 재료 아이템 개수
    // tmpInven - 인벤토리에 있는 재료 아이템 개수
    // value - 제작할 아이템의 메뉴 번호(리스트 값 꺼내오기 위함)
    public void makingItem(Player player, String itemName, ItemEntry itemEntry, int cnt, int value) {
        int requiCnt;
        // 제작하기
        System.out.println(itemName + " 을(를) 제작합니다.");
        // 로딩 스레드
        // 제작 중 동영상 스레드
        inventory.add(itemEntry, cnt);
        System.out.println();
        System.out.println();

        // 제작 완료되면 제작에 사용된 아이템 개수만큼 플레이어의 인벤토리의 아이템 개수 차감
        for (int i = 0; i < craftItemList.get(value - 1).requiredItems.size(); i++) {
            ItemEntry testEntry = craftItemList.get(value - 1).requiredItems.get(i).item;
            requiCnt = craftItemList.get(value - 1).requiredItems.get(i).getQuantity();
            String name = inventory.getItemName(testEntry);

            for (int j = 0; j < inventory.getAvailableItems(); j++) {
                String tmptmp = inventory.getItemName(inventory.getItem(j));

                if (name.equals(tmptmp)) {
                    inventory.remove(inventory.getItemIndex(testEntry), requiCnt * cnt);
                }
            }
        }

        player.craftingCount++;
        player.fatigability += 7;
        if (player.fatigability >= 100) {
            player.fatigability = 100;
        }
        System.out.println(itemName + "제작 완료!");
        System.out.println();
        scanner.nextLine();
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
            System.out.println("1. 업적 확인하기     else. 메인 메뉴로");
            System.out.print("입력 >> ");
            String inputValue = scanner.next();
            if (!MyHomeUtils.isInteger(inputValue) || Integer.parseInt(inputValue) != 1) {
                return;
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
                    System.out.printf("%-12s", this.titles.get(i).getName());
                    System.out.println("\t\t" + isAchieved);
                }

                System.out.println();
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("                  업적 달성 조건 보기.");
                System.out.println("       조건을 확인하고 싶은 업적의 번호를 입력해주세요.");
                System.out.print("입력 (0. 돌아가기) >> ");

                inputValue = scanner.next();
                scanner.nextLine();
                if (!MyHomeUtils.isInteger(inputValue)) {
                    MyHomeUtils.enterAgain();
                    scanner.nextLine();
                    continue;
                }

                int inputVal = Integer.parseInt(inputValue);
                if (inputVal < 0 || inputVal > titleQty) {
                    MyHomeUtils.enterAgain();
                    scanner.nextLine();
                    continue;
                }

                if (inputVal == 0) {
                    System.out.println();
                    System.out.println("플레이어 정보로 돌아갑니다.");
                    scanner.nextLine();
                    break;
                }

                System.out.println();
                System.out.println(inputVal + ". " + this.titles.get(inputVal - 1).getName());
                System.out.println();
                System.out.println("업적달성조건 : " + this.titles.get(inputVal - 1).getCondition());
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

            ArrayList<ItemEntry> items = inventory.getItems();
            showInventory(items);
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
                totalPrice = pricePerItem * salesQuantity;

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

    public void showInventory(ArrayList<ItemEntry> items) {
        if (inventory.isEmpty()) {
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("               인벤토리가 비었습니다.");
            return;
        }
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("                  인벤토리 리스트");
        System.out.println();
        for (int i = 0; i < inventory.getNumberOfItems(); i++) {
            ItemEntry item = items.get(i);
            System.out.print(i + 1 + ". ");
            System.out.println(item.getItemName() + " (" + item.getItemSalePrice() + "골드)");
            System.out.println("\t\t\t\t\t\t\t\t" + item.getQuantity() + "개");
        }
    }

    public void viewInventory(Player player) {
        boolean exit = true;
        while (exit) {

            if (inventory.getAvailableItems() <= 0) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("                 인벤토리가 비어있어요.");
                System.out.println("          아무키나 입력하면 메인 메뉴로 돌아가요.");
                System.out.println("└──────────────────────────────────────────────────┘");

                scanner.nextLine();
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                exit = false;

            } else {
                try {
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("                   인벤토리 리스트\n");
                    for (int i = 0; i < inventory.getAvailableItems(); i++) {
                        System.out.print(i + 1 + ". ");
                        System.out.print(inventory.getItemName(inventory.getItem(i)) + "\n" + "\t\t\t\t\t\t");
                        System.out.println(inventory.getItemCount(i) + " 개");
                    }

//                    System.out.println("인벤토리 아이템 개수 "+inventory.getAvailableItems());
                    System.out.println("\n\n확인하고 싶은 아이템의 번호를 입력하세요. (0. 메인 메뉴로)");
                    System.out.print("입력 >> ");

                    inputVal = scanner.nextInt();
                    scanner.nextLine();

                    if (inputVal <= inventory.getAvailableItems() && inputVal > 0) {

//                        if (inventory.getItem(inputVal - 1).type.equals("소비")) {
                        if (inventory.getItem(inputVal - 1).entryType.equals("포션")) {

//                            System.out.print("소비 아이템 : ");
//                            System.out.println(inventory.getItem(inputVal - 1).itemName);
                            boolean innerExit = true;
                            while (innerExit) {
                                try {
                                    System.out.println();
                                    System.out.println("┌──────────────────────────────────────────────────┐");
                                    System.out.print("[ " + inventory.getItem(inputVal - 1).potion.name + " ] 을(를) ");
                                    System.out.println("사용하시겠습니까?");
                                    System.out.println();
                                    System.out.println("1. 사용       2. 돌아가기(인벤토리 리스트 보기)");
                                    System.out.print("입력 >> ");

                                    int inputSel = scanner.nextInt();
                                    scanner.nextLine();

                                    if (inputSel == 1) {
                                        inventory.getItem(inputVal - 1).potion.calculateRecoveryAmount(player);
                                        player.inventory.remove(inputVal - 1, 1);
                                        innerExit = false;

                                    } else {
                                        innerExit = false;
                                    }

                                } catch (InputMismatchException e) {

                                    scanner.nextLine();

                                    System.out.println("┌──────────────────────────────────────────────────┐");
                                    System.out.println("            잘못 입력했어요. 다시 입력해주세요.");

                                    continue;
                                }

                            }
                        } else {
                            if (inventory.getItem(inputVal - 1).entryType.equals("생산")) {
                                System.out.print(inventory.getItem(inputVal - 1).growthItem.type + " 아이템 : ");
                                System.out.println(inventory.getItem(inputVal - 1).growthItem.name);

                            } else if (inventory.getItem(inputVal - 1).entryType.equals("일반")) {
                                System.out.print(inventory.getItem(inputVal - 1).item.type + " 아이템 : ");
                                System.out.println(inventory.getItem(inputVal - 1).item.name);

                            } else if (inventory.getItem(inputVal - 1).entryType.equals("제작")) {
                                System.out.print(inventory.getItem(inputVal - 1).madeItem.type + " 아이템 : ");
                                System.out.println(inventory.getItem(inputVal - 1).madeItem.name);
                            }
                        }

                        System.out.print("계속 하시려면 아무키나 입력하세요.");
                        scanner.nextLine();

                    } else if (inputVal == 0) {
                        exit = false;

                    } else {

                        System.out.println("┌──────────────────────────────────────────────────┐");
                        System.out.println("            잘못 입력했어요. 다시 입력해주세요.");
                        continue;

                    }
                } catch (InputMismatchException e) {

                    scanner.nextLine();

                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("            잘못 입력했어요. 다시 입력해주세요.");
                }
            }
        }
    }

    public void saveItem(ItemEntry item) {
        inventory.add(item);
    }
}