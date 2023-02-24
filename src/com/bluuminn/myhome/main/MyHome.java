package com.bluuminn.myhome.main;

import com.bluuminn.myhome.area.*;
import com.bluuminn.myhome.audio.SoundPlayerUsingClip;
import com.bluuminn.myhome.character.Merchant;
import com.bluuminn.myhome.character.NPC;
import com.bluuminn.myhome.character.Player;
import com.bluuminn.myhome.etc.MyHomeConstants;
import com.bluuminn.myhome.etc.MyHomeUtils;
import com.bluuminn.myhome.etc.ProgressBar;
import com.bluuminn.myhome.inventory.ItemEntry;
import com.bluuminn.myhome.quest.Title;
import com.bluuminn.myhome.quest.Needs;
import com.bluuminn.myhome.quest.Quest;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;

public class MyHome {

    private final Farm farm;
    private final AnimalFarm animalFarm;
    private final Forest forest;
    private final Store store;
    private final CraftShop craftShop;
    private final Merchant merchant;
    private final NPC mimi;
    private final NPC tomson;

    public MyHome() {
        farm = new Farm();         // 밭 객체 추가
        animalFarm = new AnimalFarm();   // 동물 농장 객체 추가
        forest = new Forest();   // 숲 객체 추가
        store = new Store();  // 상점 객체 추가
        craftShop = new CraftShop("공방", player, store, farm, animalFarm);
        merchant = Merchant.createMerchant("로빈");
        mimi = NPC.createNPC("미미");
        tomson = NPC.createNPC("톰슨");
    }

    SoundPlayerUsingClip player = new SoundPlayerUsingClip();
    Scanner scanner = new Scanner(System.in);

    public void start() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        ProgressBar.loading();

        player.play("intro.wav");

        MyHomeUtils.printLineAsCount(100);

        System.out.println(
                "\r                              -,*.~                                   \n" +
                        "                            ;,:=~;~,                                  \n" +
                        "                             :.:=.-;                                  \n" +
                        "                          -,-!=====;-.                                \n" +
                        "                        ,-!===!::;*===:,:      ,,,*                   \n" +
                        "             :-~.    ;.-=$===*~.-,:*====;,-,,~:==:.                   \n" +
                        "              !=*-  ..;*=====*~.~,;=======-~!=====-~                  \n" +
                        "           .,;===$,,;,,-!*$=*====**=$==!-:!!=====*~.,,,~              \n" +
                        "           .-:$==$~  . .-:*-   ,$: ;$~..,.=: :=====.;=!;*!*;.-        \n" +
                        "        -~;,:====$.....,,-*=-...:  ;$.,*;.~, ;$*  !*==:     ;*.~, ,-  \n" +
                        "       .:$==:~====$!,. .. !~.~=!$:.;:~-.:!* ,:=..;==$;. ;    =!!!;*;-.\n" +
                        "  -,---.-========$==...,-:~$:,,~$:.~$$-,,;~.;$=!*,      .*!*!      -;,\n" +
                        "~-;;~--:!=$$$$==$: :!:~~;;=$$$$$$$$$$==~.-!;*=;!-     ,;**:.:-,~- .;:-\n" +
                        ",!.            ,==     =$$!        -=$$;    ,*$$=-   .!$$$$=    ~$-.! \n" +
                        ",~!;            ,*     =*,           -$!.   ,*$$$:    ,;==!.    ;*~,. \n" +
                        " -~!    ~===:   .*     ==:   -*=*-    ~!.   ,==-~*;           .;:..:!,\n" +
                        " -~!    !$$$!.  .*       ~; ~$$$$$:   ,;.   ,!     ,;!~    ~!;.    .!-\n" +
                        " -~!    !$$$!.  .*     .-!~ ,$$$$$-   ,;.   ,==:                 .;*:-\n" +
                        "  -*.    -~~,   .*.    =:     .-.     :!.   ,=$$;;*!;~-----~:;!*~-*.  \n" +
                        "  .:=,..........,=.    $$;,. .... ..,;#*,   -=$$:     .......    ~*.  \n" +
                        "   , ;=!:::::::;=#,....,:##=:-----:*##$=-.....=#*.  .:######=    =:-  \n" +
                        "       .~=########$;~-~;$#$##########$$##=!!*$#$#!,....,--,....,=:.   \n" +
                        "         ,,!#######################################$:--,,,,-,:=!,     \n" +
                        "           ,.~::;~$==;;!:;;:*:=~:=;*;::*#!;##=$;:;:;~~:;;;;:~..;      \n" +
                        "              .;;!;-*~-!:;-,*:$:-=:*##*=*-!~=;~*;;-:                  \n" +
                        "                .~~~-~!-:;!~~!!*-!***~!**;,!!-~:,;                    \n" +
                        "                     .,,,,,,,,..,,,.,,.....,..                        \n");

        System.out.println("\n" +
                "                       __                       \n" +
                "   ____ ___  __  __   / /_  ____  ____ ___  ___ \n" +
                "  / __ `__ \\/ / / /  / __ \\/ __ \\/ __ `__ \\/ _ \\\n" +
                " / / / / / / /_/ /  / / / / /_/ / / / / / /  __/\n" +
                "/_/ /_/ /_/\\__, /  /_/ /_/\\____/_/ /_/ /_/\\___/ \n" +
                "          /____/                                \n");

        MyHomeUtils.printLineAsCount(22);

        player.stop();
        player.play("mainMusic.wav");

        MyHomeUtils.printLineAsCount(11);

        ProgressBar.loading();

        System.out.print("\r달님이 수호해주는 마을…");

        MyHomeUtils.printLineAsCount(1);
        MyHomeUtils.delayAsMillis(2000);
        MyHomeUtils.printLineAsCount(2);

        System.out.println("그 마을의 가장 유명한 공방에");
        System.out.println("스승님 아래서 열심히 일하던 아이가 있었어요.");

        MyHomeUtils.delayAsMillis(200);
        MyHomeUtils.printLineAsCount(2);
        MyHomeUtils.delayAsMillis(500);

        ProgressBar.loading();

        String playerName;

        do {
            System.out.println("당신의 이름을 알려주세요. (2 ~ 8 글자)");
            System.out.print("입력 >> ");              // 게임 플레이어 이름 입력
            playerName = scanner.nextLine();

            System.out.println("┌──────────────────────────────────────────────────┐");
            if (playerName == null || playerName.length() < 2) {
                System.out.println("            이름이 너무 짧아요. 다시 입력해주세요.        ");
            } else if (playerName.length() > 8) {
                System.out.println("            이름이 너무 길어요. 다시 입력해주세요.        ");
            }
            System.out.println("└──────────────────────────────────────────────────┘");
        } while (playerName == null || playerName.length() < 2 || playerName.length() > 8);

        MyHomeUtils.printLineAsCount(1);
        Player player = Player.createPlayer(playerName);
        System.out.println("[ " + playerName + " ] 반가워요");

        // =================== 로딩 시나리오 ========================

        MyHomeUtils.delayAsMillis(1000);
        MyHomeUtils.printLineAsCount(2);

        System.out.println("어느 날 스승님이 한 가지 제안을 하셨어요.");

        MyHomeUtils.printLineAsCount(1);
        MyHomeUtils.delayAsMillis(2000);

        System.out.println("숲 속 어딘가에 사용하지 않는 공방을");
        System.out.println("너만의 공방으로 만들지 않겠냐고 말이에요!");

        MyHomeUtils.delayAsMillis(3000);
        MyHomeUtils.printLineAsCount(2);

        System.out.println("하지만..");

        MyHomeUtils.delayAsMillis(1500);
        MyHomeUtils.printLineAsCount(1);

        System.out.println("공방의 주인으로 인정받기 위해서는");
        System.out.println("한가지 해야 할 일이 있었어요.");

        MyHomeUtils.delayAsMillis(3000);
        MyHomeUtils.printLineAsCount(1);

        System.out.println("마을의 가장 커다란 축제인 감사제를");
        System.out.println("마을 사람들을 도와 무사히 열릴 수 있게 해야 한대요!");

        MyHomeUtils.delayAsMillis(3000);
        MyHomeUtils.printLineAsCount(1);

        System.out.println("스승님 없이 혼자서 공방을 잘 운영해 나갈 수 있을까요?");

        MyHomeUtils.printLineAsCount(1);
        MyHomeUtils.delayAsMillis(3000);

        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("                공방 운영에 도움이 될만한                  ");
        System.out.println("             초기 지원 자금 " + MyHomeConstants.INITIAL_SUPPORT_GOLD + "골드를 드릴게요.      ");
        System.out.println("└──────────────────────────────────────────────────┘");

        scanner.nextLine(); // enter 눌러야 다음 진행

        //  ================== 로딩 시나리오 끝================


        // ============================= 클래스 선언 ==================================
        Farm farm = new Farm();         // 밭 객체 추가
        AnimalFarm animalFarm = new AnimalFarm();   // 동물 농장 객체 추가
        Forest forest = new Forest();   // 숲 객체 추가
        Store store = new Store();  // 상점 객체 추가
        CraftShop craftShop = new CraftShop("공방", player, store, farm, animalFarm);
        Merchant merchant = Merchant.createMerchant("로빈");
        NPC mimi = NPC.createNPC("미미");
        NPC tomson = NPC.createNPC("톰슨");

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
        boolean isResting = player.isResting();

        while (true) {
            showMenus(isResting);

            String inputValue = scanner.next();
            if (!MyHomeUtils.isInteger(inputValue)) {
                enterAgain();
                continue;
            }
            switch (Integer.parseInt(inputValue)) {
                case 0:
                    System.exit(0);
                    break;

                case 1:     // 플레이어 정보 보기
                    player.showInfo();
                    break;

                case 2:     // 재료 수확하러 하기
                    if (!isResting) {
                        ProgressBar.loading();
                        showAreas(player, animalFarm, farm, forest);
                    } else {
                        printToRest();
                    }
                    break;

                case 3:     // 아이템 제작
                    if (!isResting) {
                        ProgressBar.loading();
                        player.goToCraftShop(craftShop, player);
                    } else {
                        printToRest();
                    }
                    break;

                case 4:     // 퀘스트 리스트 확인
                    if (!isResting) {
                        player.viewQuestList(player, mimi);
                    } else {
                        printToRest();
                    }
                    break;

                case 5:     // 인벤토리 확인
                    player.viewInventory(player);
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
                    enterAgain();
            }
        }
    }

    private static void enterAgain() {
        MyHomeUtils.printLineAsCount(1);
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("           잘못 입력했어요. 다시 입력해주세요.");
    }

    private static void showMenus(boolean isResting) {
        MyHomeUtils.printLineAsCount(100);
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("                     Main Menu                      ");
        System.out.println("└──────────────────────────────────────────────────┘");
        System.out.println();
        System.out.println("               1. 👤 내 정보 보기           ");
        if (!isResting) {
            System.out.println("               2. 🍓 재료 수확하기           ");
            System.out.println("               3. 🔨 아이템 제작            ");
            System.out.println("               4. 📜 퀘스트 리스트 보기       ");
        } else {
            System.out.println("               2. 🍓 재료 수확하기 (휴식중)           ");
            System.out.println("               3. 🔨 아이템 제작 (휴식중)            ");
            System.out.println("               4. 📜 퀘스트 리스트 보기 (휴식중)       ");
        }
        System.out.println("               5. 📦 인벤토리 보기           ");
        System.out.println("               6. 💰 상점                  ");
        System.out.println("               7. 🎮 휴식 & 미니 게임        ");
        System.out.println();
//                    System.out.println("               9. ⚙️ 설정");
        System.out.println("               0. 🔚 게임 종료               ");
        System.out.println();
        System.out.print("입력 >> ");
    }

    public void printToRest() {
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("                지금은 휴식 중이에요.");
        MyHomeUtils.printLineAsCount(1);
        scanner.nextLine(); // enter 눌러야 다음 진행
    }

    private void showAreas(Player player, AnimalFarm animalFarm, Farm farm, Forest forest) {
        boolean exit = true;
        if (player.fatigability >= 100) {
            System.out.println("피로도가 너무 높아서 아무 것도 할 수 없어요.");
            scanner.nextLine();
        } else {
            while (exit) {
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("           재료를 수확하러 왔다. 어디로 갈까?");
                System.out.println();
                System.out.println("   1. 밭     2. 동물농장     3. 숲     else. 이전으로");
                System.out.println("└──────────────────────────────────────────────────┘");
                System.out.println();
                System.out.print("입력 >> ");
                Scanner scanner = new Scanner(System.in);
                inputVal = scanner.nextInt();
                scanner.nextLine();

                switch (inputVal) {
                    case 1: // 밭으로 이동
                        farm.getFarmItem(player, farm);
                        break;
                    case 2: // 동물농장으로 이동
                        animalFarm.getAnimalFarmItem(player, animalFarm);
                        break;
                    case 3: // 숲으로 이동
                        forest.getForestItem(player, forest);
                        break;
                    default:
                        return;
                }   // switch 종료
            }
        }
    }
}