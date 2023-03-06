package com.bluuminn.myhome.area;

public abstract class Area {
    private final String name;

    public Area(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void playSound() {
        // TODO: 사운드 출력
//        Music harvest = new Music("harvest.mp3", false);
//        harvest.start();
    }

    protected static void printNotPlantable() {
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("    플레이어의 레벨이 충족되지 않아 아직 획득 할 수 없습니다.");
    }
}