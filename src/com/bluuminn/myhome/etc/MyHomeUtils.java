package com.bluuminn.myhome.etc;

public class MyHomeUtils {
    public static void printLineAsCount(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println();
        }
    }

    public static void sleepAsMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
