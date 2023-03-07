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
import java.util.Random;
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

    private final boolean isResting;

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

    public boolean isResting() {
        return isResting;
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

    // ============================= 미니 게임 ==============================
    public void miniGame() {
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println();
        System.out.println("                   휴식 및 미니 게임                    ");
        System.out.println();
        System.out.println();
        if (isResting) {
            System.out.println("                   1. 🚫 휴식 끝내기                         ");
        } else {
            System.out.println("                1. 🛌 휴식 취하기 (" + Math.abs(restCount - 5) + "/5)");
//            System.out.println("                 (남은 횟수 : "+restCount);
        }
        System.out.println("                2. 🎮 미니 게임 하러가기                    ");
        System.out.println("             else. 메인 메뉴로 이동                     ");
        System.out.println();
        System.out.println("└──────────────────────────────────────────────────┘");
        System.out.print("입력 >> ");
        inputVal = scanner.nextInt();
        scanner.nextLine();
        switch (inputVal) {
            case 1:  //  휴식 취하기
                if (restCount < 0) {
                    System.out.println("휴식 모드 횟수를 모두 사용했어요.");
                    scanner.nextLine();
                } else {
                    rest();
                }
                break;
            case 2:  // 미니게임
//                numberBaseball(player);
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("                 🎮 미니 게임 리스트");
                System.out.println();
                System.out.println("                  1. ⚾︎ 숫자 야구");
                System.out.println("                  2. ✌️ 가위바위보");
//                System.out.println("                  3. 🔢 홀 짝 ( Coming Soon )");
                System.out.println("                 else. 돌아가기         ");
                System.out.println("└──────────────────────────────────────────────────┘");
                System.out.print("입력 >> ");
                int select = scanner.nextInt();
                scanner.nextLine();
                switch (select) {
                    case 1:
                        numberBaseball(player);
                        break;
                    case 2:
                        rockscissorpaper(player);
                        break;
//                    case 3:
//                        break;
                    default:
                        return;
//
                }
                break;

            default:

        }
    }

    // ============================ 가위바위보 - 미니게임 ===============================
    public void rockscissorpaper(Player player) {
        // 가위 - 1
        // 바위 - 2
        // 보 - 3

        int lose = 0, win = 0, draw = 0;

        boolean outExit = true;
        while (outExit) {
            int com = 0, user = 0;
            String userInput = null;
            String comVal = null;
            String tmpUser = null;

            Random random = new Random();
            com = random.nextInt(3) + 1;
            if (com == 1) {
                comVal = "✌️";
            } else if (com == 2) {
                comVal = "✊";
            } else if (com == 3) {
                comVal = "✋";
            }


            System.out.println();
            System.out.println("\r ✌️ - 가위 or 1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\r ✊️ - 바위 or 2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\r ✋️ - 보 or 3");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean exit = true;

            while (exit) {
                System.out.println();
                System.out.print("입력 >> ");
                userInput = scanner.nextLine();

                if (userInput.equals("가위") || userInput.equals("1")) {
                    user = 1;
                    tmpUser = "✌️";
                    exit = false;

                } else if (userInput.equals("바위") || userInput.equals("2")) {
                    user = 2;
                    tmpUser = "✊";
                    exit = false;

                } else if (userInput.equals("보") || userInput.equals("3")) {
                    user = 3;
                    tmpUser = "✋";
                    exit = false;

                } else if (userInput.equals("0")) {
                    return;
//                    exit = false;

                } else {
                    System.out.println("잘못 입력했어요. 다시 입력해주세요.");
                    continue;
                }
            }
            System.out.println();
            System.out.println("👤(Player)       🖥(Computer)");
            System.out.println();
            System.out.println("    " + tmpUser + "       🆚       " + comVal);
            System.out.println();

            switch (user) {
                case 1:
                    if (com == 1) {
                        System.out.println("✊ 비겼어요!");
                        draw++;
                        System.out.println();
                    } else if (com == 2) {
                        System.out.println("🙁 아쉽지만 졌네요.");
                        lose++;
                        System.out.println();
                    } else if (com == 3) {
                        System.out.println("👏 이겼어요!");
                        win++;
                        System.out.println();
                    }
                    break;
                case 2:
                    if (com == 1) {
                        System.out.println("👏 이겼어요!");
                        win++;
                        System.out.println();
                    } else if (com == 2) {
                        System.out.println("✊ 비겼어요!");
                        draw++;
                        System.out.println();
                    } else if (com == 3) {
                        System.out.println("🙁 아쉽지만 졌네요.");
                        lose++;
                        System.out.println();
                    }
                    break;
                case 3:
                    if (com == 1) {
                        System.out.println("🙁 아쉽지만 졌네요.");
                        lose++;
                        System.out.println();
                    } else if (com == 2) {
                        System.out.println("👏 이겼어요!");
                        win++;
                        System.out.println();
                    } else if (com == 3) {
                        System.out.println("✊ 비겼어요!");
                        draw++;
                        System.out.println();
                    }
                    break;
            }

            System.out.println("1. 다시 하기   2. 그만 하기");
            int tmp = scanner.nextInt();
            scanner.nextLine();

            if (tmp == 1) {
                continue;
            } else {
                System.out.print("이긴 횟수의 3배 만큼 피로도가 회복됩니다.");
                System.out.println("(이긴 횟수 : " + win + ")");
                player.fatigability -= win * 3;
                if (player.fatigability < 0) {
                    player.fatigability = 0;
                }
                System.out.println();
                scanner.nextLine();
                outExit = false;
            }
        }
    }

    // ================================ 숫자 야구 - 미니 게임 =============================
    public void numberBaseball(Player player) {
        System.out.println();
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("                   ⚾︎ 숫자 야구!");
        System.out.println();
        System.out.println("        1. 설명 보기            else. 바로 하기");
        System.out.print("입력 >> ");
        int exCk = scanner.nextInt();
        scanner.nextLine();

        if (exCk == 1) {
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                        숫자 야구");
            System.out.println();
            System.out.println("1. 컴퓨터가 랜덤으로 3자리 숫자를 설정함 (각 자리 숫자는 1~9사이, 겹치지 않음)");
            System.out.println("2. 사용자가 값을 입력하여 그 숫자를 맞춘다");
            System.out.println("3. 입력 숫자와 자리가 맞을 경우 Strike");
            System.out.println("4. 입력 숫자가 포함되지만 자리 위치는 틀렸을 경우 Ball");
            System.out.println("5. 입력 숫자, 자리가 모두 틀리면 OUT");
            System.out.println("6. 사용자가 맞출때까지 진행 (중간 종료 가능)");
            System.out.println("7. 턴 횟수에 따라 피로도가 회복 됩니다.");
            System.out.println();
            System.out.println("ex. COM : 5 7 8");
            System.out.println("    User: 1 2 5   => OUT");
            System.out.println("    User: 1 7 2   => 1 Strike");
            System.out.println("    User: 1 2 7   => 1 Ball");
            System.out.println("    User: 5 2 7   => 1 Strike 1 Ball");
            System.out.println("    User: 5 7 8   => 1 Strike 1 Ball");
            System.out.println();
            System.out.println("게임을 시작하려면 아무키나 입력하세요");
            scanner.nextLine();
        }

        System.out.println();
//        System.out.println("몇자리 수 야구를 하시겠어요? (3 ~ 9) >> ");
        int strike = 0;
        int ball = 0;
//        int inNum = scanner.nextInt();
//        scanner.nextLine();
        int[] computer = new int[3]; //컴퓨터가 정한 값
        int[] user = new int[3]; //유저가 정한 값

        Random random = new Random();


        //여기서부터 컴퓨터가 정할 값
        //중복방지.
        //random.nextInt(10); 이것은 1~9사이에 랜덤

        //computer 3중에 1번째가 0이면 랜덤 값 넣음
        while (computer[0] == 0) {
            computer[0] = random.nextInt(10);
        }

        //computer 3중에 1번째와 2번째가 중복되거나,
        //2번째가 0이라면 랜덤 값 넣음
        while (computer[0] == computer[1] || computer[1] == 0) {
            computer[1] = random.nextInt(10);
        }

        //computer 3중에 1번째와 3번째가 중복되거나,
        //2번째와 3번째가 중복되거나,
        //3번째가 0이라면 랜덤 값 넣음
        while (computer[0] == computer[2] || computer[1] == computer[2] || computer[2] == 0) {
            computer[2] = random.nextInt(10);
        }


        //유저가 정한 값

        //입력 s 변수 선언
        Scanner s = new Scanner(System.in);

        int cnt = 0;

        while (strike < 3) { //스트라이크 3이 될 때 까지 무한 루프
            cnt++;
            //3번 반복하여 유저 입력 받음.
            for (int i = 0; i < user.length; i++) {
                System.out.print("\n" + (i + 1) + "번째 수: ");
                user[i] = s.nextInt();

                //만약 10이상이거나, 0이하면 오류 출력
                if (user[i] == 0) {
                    System.out.println("게임을 종료합니다.");
                    scanner.nextLine();
                    return;
                }

                while (user[i] >= 10 || user[i] < 0) {
                    System.out.println("error: 1~9사이에 입력 하세요.");
                    System.out.print(i + 1 + "번째 수: ");
                    user[i] = s.nextInt();
                }
            }

            //여기서 결과 체크

            //strike, ball 체크
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    //컴퓨터 정한 값과
                    //유저가 정한 값을 같으면
                    if (computer[i] == user[j]) {
                        //computer i번째와
                        //user j번째가 같으면
                        //스트라이크!
                        if (i == j) {
                            strike += 1;
                        } else {  //아니면 볼!
                            ball += 1;
                        }
                    }
                }
            }

            //만약 strike, ball 아무도 못맞췄다면
            if (strike == 0 && ball == 0) {
                System.out.println("\n 🚫 OUT!! \n");
            } else if (strike == 3) { //만약 스트라이크 3개라면
                System.out.println("\n 👍 3 Strike !!");
                System.out.println("게임에서 승리했습니다. (턴 수 : " + cnt + ")");
                System.out.println();
                player.fatigability -= 100 / cnt;
                restCount = 5;
                System.out.println("휴식 모드 횟수가 초기화 되었습니다.");
                scanner.nextLine();
            } else {
                System.out.println("\n" + strike + " Strike  /  " + ball + " Ball\n");
                strike = 0;
                ball = 0;
            }
        }
    }

    // ========================== 플레이어 휴식 ============================
    public void rest() {
        // 휴식중이 아닐 때
        if (!isResting) {
            System.out.println(getName() + " ! 피로가 많이 쌓이셨나보군요.");
            System.out.println("휴식 모드로 전환할까요? (1회 - 1000골드)");
            System.out.println();
            System.out.println("1. 휴식 취하기    else. 이전 단계로");

            int select = scanner.nextInt();
            scanner.nextLine();

            if (select == 1) {
                System.out.println("휴식 모드로 전환합니다.");

                player.isResting = true;
                player.gold -= 1000;
                restCount--;

                scanner.nextLine();
            }

        } else {
            System.out.println();
            System.out.println("휴식 모드를 종료합니다.");
            System.out.println();
            player.isResting = false;
            player.fatigability -= 10;
            if (player.fatigability <= 0) {
                player.fatigability = 0;
            }

            scanner.nextLine();

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