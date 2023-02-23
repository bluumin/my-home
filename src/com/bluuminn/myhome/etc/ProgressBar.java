package com.bluuminn.myhome.etc;

import java.util.ArrayList;
import java.util.Random;

public class ProgressBar {
    public static ArrayList<String> tip = new ArrayList<>();

    static {
        tip.add("플레이어 레벨 5까지는 레벨이 오를 때마다 3000골드를 드려요!");
        tip.add("아이템 제작을 시작하려면 원목 작업대가 꼭 필요해요.");
        tip.add("내 정보 보기에서 업적 정보를 확인할 수 있어요.");
        tip.add("피로도가 100이 되면 어떤 작업도 할 수 없어요. 피로도를 잘 관리해 주세요.");
        tip.add("상점에서 피로도 회복 물약을 구입할 수 있어요.");
        tip.add("게임 속 미니 게임을 즐겨보세요! 피로도를 회복할 수 있습니다.");
        tip.add("휴식 모드로 진입하면 피로도가 회복됩니다.");
    }

    public static void loading() {
        int rand = new Random().nextInt(tip.size());
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
        System.out.println("\t\t⚠️ TIP ! " + tip.get(rand));
        System.out.println();
        System.out.print("\t\t\t\t\tLoading........... ");
        int count = 1;
        for (int j = 1; j < 30; j++) {
            System.out.print("\r");
            if (count == 1) {
                System.out.print("\t\t\t\t\tLoading........... 🌑");
                count++;
            } else if (count == 2) {
                System.out.print("\t\t\t\t\tLoading........... 🌒");
                count++;
            } else if (count == 3) {
                System.out.print("\t\t\t\t\tLoading........... 🌓");
                count++;
            } else if (count == 4) {
                System.out.print("\t\t\t\t\tLoading........... 🌔");
                count++;
            } else if (count == 5) {
                System.out.print("\t\t\t\t\tLoading........... 🌕");
                count++;
            } else if (count == 6) {
                System.out.print("\t\t\t\t\tLoading........... 🌖");
                count++;
            } else if (count == 7) {
                System.out.print("\t\t\t\t\tLoading........... 🌗");
                count++;
            } else if (count == 8) {
                System.out.print("\t\t\t\t\tLoading........... 🌘");
                count++;
            } else {
                count = 1;
            }
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }
}
