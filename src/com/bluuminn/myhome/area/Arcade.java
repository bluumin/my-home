package com.bluuminn.myhome.area;

import com.bluuminn.myhome.character.Player;
import com.bluuminn.myhome.etc.MyHomeUtils;

import java.util.Random;
import java.util.Scanner;

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
                    numberBaseball(player);
                    break;
                case 2:
                    rockScissorPaper(player);
                    break;
                default:
                    return;
            }
            break;
        }
    }

    // ============================ 가위바위보 - 미니게임 ===============================
    private void rockScissorPaper(Player player) {
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
    private void numberBaseball(Player player) {
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
}
