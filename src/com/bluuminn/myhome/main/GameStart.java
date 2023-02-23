package com.bluuminn.myhome.main;

import com.bluuminn.myhome.character.Merchant;
import com.bluuminn.myhome.character.NPC;
import com.bluuminn.myhome.character.Player;
import com.bluuminn.myhome.etc.MyHomeConstants;
import com.bluuminn.myhome.etc.MyHomeUtils;
import com.bluuminn.myhome.etc.ProgressBar;
import com.bluuminn.myhome.inventory.ItemEntry;
import com.bluuminn.myhome.item.Title;
import com.bluuminn.myhome.area.*;
import com.bluuminn.myhome.quest.Needs;
import com.bluuminn.myhome.quest.Quest;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameStart {

    public void startPoint() {
        int inputVal;      // 유저 입력값 처리
        MyHomeUtils.printLineAsCount(11);

        ProgressBar.loading();

        System.out.print("\r달님이 수호해주는 마을…\n");

        MyHomeUtils.sleepAsMillis(2000);

        MyHomeUtils.printLineAsCount(2);

        System.out.println("그 마을의 가장 유명한 공방에");
        System.out.println("스승님 아래서 열심히 일하던 아이가 있었어요.");

        MyHomeUtils.sleepAsMillis(200);

        try {
            System.out.println("\n");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ProgressBar.loading();

        Scanner scanner = new Scanner(System.in);
        String playerName;

        do {
            System.out.println("당신의 이름을 알려주세요. (2 ~ 8 글자)");
            System.out.print("입력 >> ");              // 게임 플레이어 이름 입력
            playerName = scanner.nextLine();

            if (playerName == null || playerName.length() < 2) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("            이름이 너무 짧아요. 다시 입력해주세요.        ");
                System.out.println("└──────────────────────────────────────────────────┘");
            } else if (playerName.length() > 8) {
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("            이름이 너무 길어요. 다시 입력해주세요.        ");
                System.out.println("└──────────────────────────────────────────────────┘");
            }
        } while (playerName == null || playerName.length() < 2 || playerName.length() > 8);

        System.out.println();
        Player player = new Player();
        System.out.println("[ " + player.name + " ] 반가워요");

        // =================== 로딩 시나리오 ========================

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println("어느 날 스승님이 한 가지 제안을 하셨어요.\n");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("숲 속 어딘가에 사용하지 않는 공방을\n" +
                "너만의 공방으로 만들지 않겠냐고 말이에요!");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n하지만..");
        MyHomeUtils.sleepAsMillis(1500);

        System.out.println("\n공방의 주인으로 인정받기 위해서는\n" +
                "한가지 해야 할 일이 있었어요.");

        MyHomeUtils.sleepAsMillis(3000);

        System.out.println("\n마을의 가장 커다란 축제인 감사제를\n" +
                "마을 사람들을 도와 무사히 열릴 수 있게 해야 한대요!");

        MyHomeUtils.sleepAsMillis(3000);

        System.out.println("\n스승님 없이 혼자서 공방을 잘 운영해 나갈 수 있을까요?\n");

        MyHomeUtils.sleepAsMillis(3000);

        //  ================== 로딩 시나리오 끝================


        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("                공방 운영에 도움이 될만한                  ");
        System.out.println("             초기 지원 자금 " + MyHomeConstants.INITIAL_SUPPORT_GOLD + "골드를 드릴게요.      ");
        System.out.println("└──────────────────────────────────────────────────┘");

        scanner.nextLine();


        // ============================= 클래스 선언 ==================================
        Farm farm = new Farm();         // 밭 객체 추가
        AnimalFarm animalFarm = new AnimalFarm();   // 동물 농장 객체 추가
        Forest forest = new Forest();   // 숲 객체 추가
        Store store = new Store();  // 상점 객체 추가
        CraftShop craftShop = new CraftShop("공방", player, store, farm, animalFarm);
        Merchant merchant = new Merchant("로빈");     // 상인 NPC 추가
        NPC mimi = new NPC("미미");       // NPC 미미 추가
        NPC tomson = new NPC("톰슨");     // NPC 촌장 톰슨

        // ============================= 업적 타이틀 추가 ==============================
        Title title0 = new Title("공방의 새 주인");
        player.title.add(title0);
        title0.achievementConditions = "퀘스트 3회 완료 시";

        Title title1 = new Title("원목 공예 장인");
        player.title.add(title1);
        title1.achievementConditions = "아이템 제작 10회 이상";

        // ============================= 퀘스트 생성 =============================
        Quest quest1 = new Quest("밀짚을 이용한 끈!", "미미", false);
        quest1.questContent = ("수확한 밀로 끈을 만들어봐요!").toCharArray();
        quest1.payForGold = 1000;
        quest1.payExp = 40;
        quest1.payItem = new ItemEntry(animalFarm.milk, 0);
        quest1.payItemCount = 1;
        quest1.needs.add(new Needs("밀짚끈", 4));
        quest1.questEnding = ("생각보다 튼튼해 보여요. 처음이라 걱정했는데 잘하고 계시는데요?\n앞으로도 이렇게만 해주세요!").toCharArray();

        Quest quest2 = new Quest("밀짚끈으로 소파를?", "미미", false);
        quest2.questContent = ("만들어 놓은 밀짚끈으로 소파를 만들어볼까요?").toCharArray();
        quest2.payForGold = 980;
        quest2.payExp = 45;
        quest2.needs.add(new Needs("네이쳐 오가닉 소파", 1));
        quest2.questEnding = ("우와! 정말 좋아 보여요! 저도 나중에 부탁해도 될까요?").toCharArray();

        Quest quest3 = new Quest("톰슨 할아버지의 첫 주문!", "톰슨", true);
        quest3.questContent = ("자네가 만든 의자 괜찮더군! 작업실에 의자가 필요한데.. 괜찮으면 만들어주지 않겠나?").toCharArray();
        quest3.payForGold = 2000;
        quest3.payExp = 45;
        quest3.needs.add(new Needs("네이쳐 오가닉 소파", 1));
        quest3.questEnding = ("흠, 제법 하는구먼.. 밀짚도 튼튼하게 엮여있고..\n그렇다곤 해도 아직 초보자니 마음을 놓지 말게나!").toCharArray();

        // ====================== 각 NPC 퀘스트 리스트에 추가 =========================
        mimi.npcQuestList.add(quest1);
        mimi.npcQuestList.add(quest2);
        tomson.npcQuestList.add(quest3);

        mimi.questEvent.start();
        tomson.questEvent.start();

        player.playerLvUp();

        outer:
        while (true) {
            try {
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                if (!player.restCK) {
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("                     Main Menu                      ");
                    System.out.println("└──────────────────────────────────────────────────┘");
                    System.out.println();
                    System.out.println("               1. 👤 내 정보 보기           ");
                    System.out.println("               2. 🍓 재료 수확하기           ");
                    System.out.println("               3. 🔨 아이템 제작            ");
                    System.out.println("               4. 📦 인벤토리 보기           ");
                    System.out.println("               5. 📜 퀘스트 리스트 보기       ");
                    System.out.println("               6. 💰 상점                  ");
                    System.out.println("               7. 🎮 휴식 & 미니 게임        ");    // 휴식 취하기, 미니게임(숫자야구, 가위바위보, 홀짝)
                    System.out.println();
//                    System.out.println("               9. ⚙️ 설정");
                    System.out.println("               0. 🔚 게임 종료               ");
                    System.out.println();
                    System.out.print("입력 >> ");

                    inputVal = scanner.nextInt();
                    scanner.nextLine();


                    switch (inputVal) {
                        case 0:
                            System.exit(0);
                            break outer;

                        case 1:     // 플레이어 정보 보기
                            player.playerInfoPrint(player);
                            break;

                        case 2:     // 재료 수확하러 하기
                            ProgressBar.loading();
                            player.viewMapList(player, animalFarm, farm, forest);
                            break;

                        case 3:     // 아이템 제작
                            ProgressBar.loading();
                            player.goToCraftShop(craftShop, player);
                            break;

                        case 4:     // 인벤토리 확인
                            player.viewInventory(player);
                            break;

                        case 5:     // 퀘스트 리스트 확인
                            player.viewQuestList(player, mimi);
                            break;

                        case 6:     // 상점
                            ProgressBar.loading();
                            player.goToStore(player, merchant, store);
                            break;

                        case 7:     // 미니게임
                            ProgressBar.loading();
                            player.miniGame(player);
                            break;

                        default:
                            System.out.println();
                            System.out.println("           잘못 입력했어요. 다시 입력해주세요.");
                            continue;

                    }   // switch 종료

                    // player.restCK == true  (플레이어가 휴식 중이면)
                } else {
                    System.out.println("┌──────────────────────────────────────────────────┐");
                    System.out.println("                     Main Menu                      ");
                    System.out.println("└──────────────────────────────────────────────────┘");
                    System.out.println();
                    System.out.println("               1. 👤 내 정보 보기           ");
                    System.out.println("               2. 🍓 재료 수확하기 (휴식중)           ");
                    System.out.println("               3. 🔨 아이템 제작 (휴식중)            ");
                    System.out.println("               4. 📦 인벤토리 보기           ");
                    System.out.println("               5. 📜 퀘스트 리스트 보기 (휴식중)       ");
                    System.out.println("               6. 💰 상점                  ");
                    System.out.println("               7. 🎮 휴식 & 미니 게임        ");    // 휴식 취하기, 미니게임(숫자야구, 가위바위보, 홀짝)
                    System.out.println();
//                System.out.println("    9. 설정");
                    System.out.println("               0. 🔚 게임 종료               ");
                    System.out.println();
                    System.out.print("입력 >> ");

                    inputVal = scanner.nextInt();
                    scanner.nextLine();

                    switch (inputVal) {
                        case 0:
                            System.exit(0);
                            break outer;

                        case 1:     // 플레이어 정보 보기
                            player.playerInfoPrint(player);
                            break;

                        case 2:     // 재료 수확하러 하기 (휴식)
                            restPrint();
//                            player.viewMapList(player, animal, farm);
                            break;

                        case 3:     // 아이템 제작 (휴식)
                            restPrint();
//                            player.goToCraftShop(craftShop, player);
                            break;

                        case 4:     // 인벤토리 확인
                            player.viewInventory(player);
                            break;

                        case 5:     // 퀘스트 리스트 확인 (휴식)
                            restPrint();
//                            player.viewQuestList(player, mimi);
                            break;

                        case 6:     // 상점
                            ProgressBar.loading();
                            player.goToStore(player, merchant, store);
                            break;

                        case 7:     // 미니게임
                            ProgressBar.loading();
                            player.miniGame(player);
                            break;

                        default:
                            System.out.println();
                            System.out.println("           잘못 입력했어요. 다시 입력해주세요.");
                            continue;

                    }   // switch 종료
                }
            } catch (InputMismatchException e) {

                scanner.nextLine();

                System.out.println();
                System.out.println("           잘못 입력했어요. 다시 입력해주세요.");

                continue;
            }
        }   // outer while 종료
    }

    public void restPrint() {
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("                지금은 휴식 중이에요.");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void tipPrint() {

    }
}