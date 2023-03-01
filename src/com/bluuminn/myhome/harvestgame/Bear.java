package com.bluuminn.myhome.harvestgame;

import com.bluuminn.myhome.etc.MyHomeUtils;

import java.util.Scanner;

class Bear extends GameObject {
    public Bear(int startX, int startY, int distance) {
        super(startX, startY, distance);
    }

    @Override
    public boolean move() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("상(w) 하(s) 좌(a) 우(d) 종료(0)");
        System.out.print("입력 >> ");
        String direction = scanner.next();
        BearCatchesFishGame.array[x][y] = "."; // 현재 좌표에 아무것도 없게 해놓는다.

        if ("0".equals(direction)) {
            return false;
        }
        switch (direction) {
            case "a":
            case "ㅁ":
                if (y == 0)
                    y = 0;
                else
                    y -= distance;
                break;

            case "s":
            case "ㄴ":
                if (x == 9)
                    x = 9;
                else
                    x += distance;
                break;

            case "w":
            case "ㅈ":
                if (x == 0)
                    x = 0;
                else
                    x -= distance;
                break;

            case "d":
            case "ㅇ":
                if (y == 14)
                    y = 14;
                else
                    y += distance;
                break;

            default:
                System.out.println("error");
                MyHomeUtils.printLineAsCount(100);
                BearCatchesFishGame.array[x][y] = getShape(); // 바뀐 좌표에 다시 모양 입력.
        }
        return true;
    }

    @Override
    public String getShape() {
        return "8";
    }
}