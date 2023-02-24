package com.bluuminn.myhome.etc;

public class MyHomeUtils {
    public static void printLineAsCount(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println();
        }
    }

    public static void delayAsMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignore) {
        }
    }

    public static boolean isInteger(String str) {
        return str != null && str.matches("^\\d+$");
    }
}
