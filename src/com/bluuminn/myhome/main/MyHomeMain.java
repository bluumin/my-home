package com.bluuminn.myhome.main;

import com.bluuminn.myhome.audio.Music;
import com.bluuminn.myhome.etc.ProgressBar;

public class MyHomeMain {
    public static void main(String[] args) {

        GameStart gamestart = new GameStart();

        ProgressBar progressBar = new ProgressBar();

        progressBar.loading();
        // Runnuble
//        Thread intro = new Thread(new Music("intro.mp3", false));

        // Thread
        Music intro = new Music("intro.mp3", false);
        intro.start();
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }

        System.out.println("\r                              -,*.~                                   \n" +
                "                            ;,:=~;~,                                  \n" +
                "                             :.:=.-;                                  \n" +
                "                          -,-!=====;-.                                \n" +
                "                        ,-!===!::;*===:,:      ,,,*                   \n" +
                "             :-~.    ;.-=$===*~.-,:*====;,-,,~:==:.                   \n" +
                "              !=*-  ..;*=====*~.~,;=======-~!=====-~                  \n" +
                "           .,;===$,,;,,-!*$=*====**=$==!-:!!=====*~.,,,~              \n" +
                "           .-:$==$~  . .-:*-   ,$: ;$~..,.=: :=====.;=!;*!*;.-        \n" +
                "        -~;,:====$.....,,-*=-...:  ;$.,*;.~, ;$*  !*==:     ;*.~, ,-  \n" +
                "       .:$==:~====$!,. .. !~.~=!$:.;:~-.:!* ,:=..;==$;. ;    =!!!;*;-.\n" +
                "  -,---.-========$==...,-:~$:,,~$:.~$$-,,;~.;$=!*,      .*!*!      -;,\n" +
                "~-;;~--:!=$$$$==$: :!:~~;;=$$$$$$$$$$==~.-!;*=;!-     ,;**:.:-,~- .;:-\n" +
                ",!.            ,==     =$$!        -=$$;    ,*$$=-   .!$$$$=    ~$-.! \n" +
                ",~!;            ,*     =*,           -$!.   ,*$$$:    ,;==!.    ;*~,. \n" +
                " -~!    ~===:   .*     ==:   -*=*-    ~!.   ,==-~*;           .;:..:!,\n" +
                " -~!    !$$$!.  .*       ~; ~$$$$$:   ,;.   ,!     ,;!~    ~!;.    .!-\n" +
                " -~!    !$$$!.  .*     .-!~ ,$$$$$-   ,;.   ,==:                 .;*:-\n" +
                "  -*.    -~~,   .*.    =:     .-.     :!.   ,=$$;;*!;~-----~:;!*~-*.  \n" +
                "  .:=,..........,=.    $$;,. .... ..,;#*,   -=$$:     .......    ~*.  \n" +
                "   , ;=!:::::::;=#,....,:##=:-----:*##$=-.....=#*.  .:######=    =:-  \n" +
                "       .~=########$;~-~;$#$##########$$##=!!*$#$#!,....,--,....,=:.   \n" +
                "         ,,!#######################################$:--,,,,-,:=!,     \n" +
                "           ,.~::;~$==;;!:;;:*:=~:=;*;::*#!;##=$;:;:;~~:;;;;:~..;      \n" +
                "              .;;!;-*~-!:;-,*:$:-=:*##*=*-!~=;~*;;-:                  \n" +
                "                .~~~-~!-:;!~~!!*-!***~!**;,!!-~:,;                    \n" +
                "                     .,,,,,,,,..,,,.,,.....,..                        \n");

        System.out.println("\n" +
                "                       __                       \n" +
                "   ____ ___  __  __   / /_  ____  ____ ___  ___ \n" +
                "  / __ `__ \\/ / / /  / __ \\/ __ \\/ __ `__ \\/ _ \\\n" +
                " / / / / / / /_/ /  / / / / /_/ / / / / / /  __/\n" +
                "/_/ /_/ /_/\\__, /  /_/ /_/\\____/_/ /_/ /_/\\___/ \n" +
                "          /____/                                \n");

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        try {
            intro.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Runnuble
//        Thread mainMusic = new Thread(new Music("mainMusic.mp3", true));

        // Thread
        Music mainMusic = new Music("mainMusic.mp3", true);
        mainMusic.setDaemon(true);
        mainMusic.start();

        gamestart.startPoint();
    }
}
