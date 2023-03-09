package com.bluuminn.myhome.area;

import com.bluuminn.myhome.character.Player;
import com.bluuminn.myhome.etc.MyHomeUtils;

import java.util.*;

public class Arcade extends Area {
    public Arcade() {
        super("오락실");
    }

    public void showGames(Player player, Scanner scanner) {
        while (true) {
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                 🎮 미니 게임 리스트");
            System.out.println("└──────────────────────────────────────────────────┘");
            System.out.println();
            System.out.println("                  1. ⚾ 숫자야구");
            System.out.println("                  2. ✌️ 가위바위보");
            System.out.println("                  0. 돌아가기");
//                System.out.println("                  3. 🔢 홀 짝 (Coming Soon)");
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
            if (input > 2 || input < 0) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            if (input == 0) {
                break;
            }
            switch (input) {
                case 1:
                    numberBaseball(player, scanner);
                    break;
                case 2:
                    rockScissorPaper(player, scanner);
                    break;
                default:
                    return;
            }
            break;
        }
    }

    private void rockScissorPaper(Player player, Scanner scanner) {
        while (true) {
            MyHomeUtils.printLineAsCount(100);
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                   ✌️ 가위 바위 보");
            System.out.println();
            System.out.println("                    1. 게임 시작");
            System.out.println("                    2. 설명 보기");
            System.out.println();
            System.out.println("                    0. 이전으로");

            String inputValue = scanner.next();
            if (!MyHomeUtils.isInteger(inputValue)) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            int input = MyHomeUtils.stringToInt(inputValue);
            if (input > 2 || input < 0) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            if (input == 0) {
                break;
            }
            if (input == 2) {
                descRockScissorPaper();
                scanner.nextLine();
                continue;
            }

            int wins = playRockScissorPaper(player, scanner);
            System.out.print("이긴 횟수의 3배 만큼 피로도가 회복됩니다.");
            System.out.println("(이긴 횟수: " + wins + ")");
            player.updateFatigability(player.getFatigability() - (wins * 3));
            System.out.println();
            scanner.nextLine();
        }
    }

    private int playRockScissorPaper(Player player, Scanner scanner) {
        int win = 0;
        while (true) {
            MyHomeUtils.printLineAsCount(100);
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                   ✌️ 가위 바위 보");
            System.out.println();
            System.out.println("                   게임을 시작합니다.");
            MyHomeUtils.delayAsMillis(1000);

            String computerValue = "";
            String computerValueEmoji = null;
            String userInputEmoji;

            int computer = (int) (Math.random() * 3) + 1;
            if (computer == 1) {
                computerValue = "가위";
                computerValueEmoji = "✌️";
            }
            if (computer == 2) {
                computerValue = "바위";
                computerValueEmoji = "✊";
            }
            if (computer == 3) {
                computerValue = "보";
                computerValueEmoji = "✋";
            }

            System.out.println();
            System.out.println("\r ✌️: 가위(1)");
            MyHomeUtils.delayAsMillis(1000);

            System.out.println("\r ✊️: 바위(2)");
            MyHomeUtils.delayAsMillis(1000);

            System.out.println("\r ✋️: 보(3)");
            MyHomeUtils.delayAsMillis(1000);

            String userValue;
            while (true) {
                System.out.println();
                System.out.println("[ 가위(1) 바위(2) 보(3) ]");
                System.out.print("입력 >> ");
                String userInput = scanner.nextLine();
                if ("가위".equals(userInput) || "1".equals(userInput)) {
                    userValue = "가위";
                    userInputEmoji = "✌️";
                    break;
                }
                if ("바위".equals(userInput) || "2".equals(userInput)) {
                    userValue = "바위";
                    userInputEmoji = "✊";
                    break;
                }
                if ("보".equals(userInput) || "3".equals(userInput)) {
                    userValue = "보";
                    userInputEmoji = "✋";
                    break;
                }
                System.out.println("잘못 입력했어요. 다시 입력해주세요.");
            }
            System.out.println();
            System.out.println("👤(" + player.getName() + ")       🖥(Computer)");
            System.out.println();
            System.out.println("    " + userInputEmoji + "       🆚       " + computerValueEmoji);
            System.out.println();

            switch (userValue) {
                case "가위":
                    if ("가위".equals(computerValue)) {
                        System.out.println("✊ 비겼어요!");
                        System.out.println();
                    }
                    if ("바위".equals(computerValue)) {
                        System.out.println("🙁 아쉽지만 졌어요.");
                        System.out.println();
                    }
                    if ("보".equals(computerValue)) {
                        win++;
                        System.out.println("👏 이겼어요!");
                        System.out.println();
                    }
                    break;
                case "바위":
                    if ("가위".equals(computerValue)) {
                        win++;
                        System.out.println("👏 이겼어요!");
                        System.out.println();
                    }
                    if ("바위".equals(computerValue)) {
                        System.out.println("✊ 비겼어요!");
                        System.out.println();
                    }
                    if ("보".equals(computerValue)) {
                        System.out.println("🙁 아쉽지만 졌어요.");
                        System.out.println();
                    }
                    break;
                case "보":
                    if ("가위".equals(computerValue)) {
                        System.out.println("🙁 아쉽지만 졌어요.");
                    }
                    if ("바위".equals(computerValue)) {
                        win++;
                        System.out.println("👏 이겼어요!");
                    }
                    if ("보".equals(computerValue)) {
                        System.out.println("✊ 비겼어요!");
                    }
                    break;
            }

            System.out.println();
            System.out.println("1. 다시 하기      0. 그만 하기");
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
            }
            if (input == 0) {
                break;
            }
        }
        return win;
    }

    private void descRockScissorPaper() {
        MyHomeUtils.printLineAsCount(100);
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("                   ✌️ 가위 바위 보");
        System.out.println();
        System.out.println("  컴퓨터와 가위바위보 게임을 진행합니다.");
        System.out.println("  진행 방법은 다음과 같습니다.");
        System.out.println();
        System.out.println("  ============================= ");
        System.out.println("    [가위(1) 바위(2) 보(3)]");
        System.out.println("    입력 >> ");
        System.out.println("  ============================= ");
        System.out.println("  위와 같은 입력 화면이 나타나면 가위 / 바위 / 보 중에 하나를 입력합니다.");
        System.out.println("  한글 대신 옆에 적힌 숫자를 입력할 수 있습니다.");
        System.out.println();
        System.out.println("  [입력 예시]");
        System.out.println("  입력 >> 가위      ⭕️");
        System.out.println("  입력 >> 바위      ⭕️");
        System.out.println("  입력 >> 보       ⭕️");
        System.out.println("  입력 >> 1        ⭕");
        System.out.println("  입력 >> 2        ⭕");
        System.out.println("  입력 >> 3        ⭕");
        System.out.println("  입력 >> 가위가위   ❌");
        System.out.println("  입력 >> 2222     ❌");
        System.out.println("  입력 >> 보보      ❌");
        System.out.println("  아무 키나 입력하면 이전 단계로 돌아갑니다.");
    }

    private void numberBaseball(Player player, Scanner scanner) {
        while (true) {
            MyHomeUtils.printLineAsCount(100);
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                    ⚾ 숫자 야구");
            System.out.println();
            System.out.println("                    1. 게임 시작");
            System.out.println("                    2. 설명 보기");
            System.out.println();
            System.out.println("                    0. 이전으로");
            System.out.println();
            System.out.print("입력 >> ");
            String inputValue = scanner.next();
            if (!MyHomeUtils.isInteger(inputValue)) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            int input = MyHomeUtils.stringToInt(inputValue);
            if (input == 0) {
                break;
            }
            if (input == 2) {
                descNumberBaseball();
                scanner.nextLine();
                continue;
            }
            if (input > 2 || input < 0) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }

            int roundNumber = playNumberBaseball(scanner);
            player.updateFatigability(player.getFatigability() - (100 / roundNumber));
            player.resetRestCount();
            System.out.println("휴식 가능한 횟수가 초기화 되었습니다.");
        }
    }

    private int playNumberBaseball(Scanner scanner) {
        while (true) {
            System.out.println("┌──────────────────────────────────────────────────┐");
            System.out.println("                    ⚾ 숫자 야구");
            System.out.println();
            System.out.println("                   게임을 시작합니다.");
            System.out.println();
            System.out.println();
            /*
            TODO: 난이도 선택하게 해서 피로도만 깎거나 휴식 횟수도 깎거나.. 하면 좋을 듯
            System.out.print("몇자리 수 야구를 하시겠어요? 3~5 사이의 숫자를 입력해주세요.");
            System.out.print("입력 >> ");
            String inputValue = scanner.next();
            if (!MyHomeUtils.isInteger(inputValue)) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
            int numberOfDigit = MyHomeUtils.stringToInt(inputValue);
            if (numberOfDigit < 3 || numberOfDigit > 5) {
                MyHomeUtils.enterAgain();
                scanner.nextLine();
                continue;
            }
             */
            int numberOfDigit = 3;
            Set<Integer> computerSet = new HashSet<>();
            for (int i = 0; computerSet.size() < numberOfDigit; i++) {
                int num = (int) (Math.random() * 9) + 1;
                computerSet.add(num);
            }

            List<Integer> computer = new ArrayList<>(computerSet);
            List<Integer> user = new ArrayList<>(numberOfDigit);

            int strike = 0, ball;
            int roundNumber = 0;
            while (strike < numberOfDigit) {
                roundNumber++;
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("                ⚾ Round " + roundNumber);
                strike = 0;
                ball = 0;
                List<Integer> tempUser = new ArrayList<>(user);
                //유저 입력
                int number = 1;
                while (user.size() < numberOfDigit) {
                    System.out.println();
                    System.out.print(number + "번째 수: ");
                    String userInputValue = scanner.next();
                    if (!MyHomeUtils.isInteger(userInputValue)) {
                        System.out.println("1~9 사이의 숫자만 입력해주세요.");
                        scanner.nextLine();
                        continue;
                    }
                    int userInput = MyHomeUtils.stringToInt(userInputValue);
                    if (userInput == 0) {
                        return roundNumber;
                    }
                    if (userInput < 1 || userInput > 9) {
                        System.out.println("1~9 사이의 숫자만 입력해주세요.");
                        scanner.nextLine();
                        continue;
                    }
                    user.add(userInput);
                    number++;
                }

                // user - computer 했을 때
                tempUser.removeAll(computer);

                // user.size == 0: 일단 숫자는 다 맞음
                if (tempUser.size() == 0) {
                    // 자리까지 다 맞는지 확인
                    for (int i = 0; i < computer.size(); i++) {
                        Integer computerNumber = computer.get(i);
                        Integer userNumber = user.get(i);
                        if (Objects.equals(computerNumber, userNumber)) {
                            strike++;
                        } else {
                            ball++;
                        }
                    }
                    if (strike == numberOfDigit) {
                        System.out.println();
                        System.out.println("게임에서 승리했습니다. (턴 수: " + roundNumber + ")");
                        System.out.println();
                        return roundNumber;
                    }
                    if (strike < numberOfDigit) {
                        System.out.println();
                        System.out.println(strike + " Strike  /  " + ball + " Ball");
                        System.out.println();
                        continue;
                    }
                }

                // user 개수가 그대로라면 out
                if (tempUser.size() == user.size()) {
                    System.out.println();
                    System.out.println(" 🚫 OUT!!");
                    System.out.println();
                    continue;
                }

                // 숫자가 일부만 포함되어 있다면
                for (int i = 0; i < user.size(); i++) {
                    Integer userNumber = user.get(i);
                    int computerIndex = computer.indexOf(userNumber);
                    if (computerIndex < 0) {
                        continue;
                    }
                    // index 이용해서 strike / ball 구분
                    if (i == computerIndex) {
                        strike++;
                    } else {
                        ball++;
                    }
                    System.out.println();
                    System.out.println(strike + " Strike  /  " + ball + " Ball");
                    System.out.println();
                }
            }
        }
    }

    private void descNumberBaseball() {
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("                    ⚾ 숫자 야구");
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
        System.out.println("  아무 키나 입력하면 이전 단계로 돌아갑니다.");
    }
}
