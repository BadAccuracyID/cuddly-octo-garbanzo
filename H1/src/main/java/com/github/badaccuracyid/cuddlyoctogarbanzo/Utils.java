package com.github.badaccuracyid.cuddlyoctogarbanzo;

import java.io.IOException;

public class Utils {

    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name");

            Process start;
            if (os.contains("windows")) {
                ProcessBuilder processBuilder;
                processBuilder = new ProcessBuilder("cmd", "/c", "cls");
                start = processBuilder.start();
            } else {
                ProcessBuilder processBuilder;
                processBuilder = new ProcessBuilder("clear");
                start = processBuilder.inheritIO().start();
            }

            start.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printLogo() {
        String logo = "\n" +
                "                                  .......,*...                                  \n" +
                "                           ......&..........,%&&&*&.......                      \n" +
                "                         ...%.,.../(..&/,     .....,..(..,*....                 \n" +
                "                      ..#,......#*,..............   ..../%.....                 \n" +
                "                   .,/(.....(, .*....................  ...*                     \n" +
                "                  .,#,...,,. .........................  ,...% .                 \n" +
                "              ...&/((&#*,...............*&@@%...........  ,*,,....              \n" +
                "             ..(,*.... ./,.%,...... /&@@@@@ .............  .*,/...              \n" +
                "              ......./ .....*** &@@@@@&#.................. ..(,&                \n" +
                "                 (,.,....,..(@@@@@@/./@@@@@@@@@#*.......,*...,*.(               \n" +
                "                 %,,,..,,,.,.@@@@@@@#&......,@@@@@@@@@%.... .,/,%               \n" +
                "               ..%,,. ..,..,,,,../@@@@@@@@.../@@@@@#...... ..*,*(,              \n" +
                "               ..#... ..,,..,..........,,.(@@@@@ ......... ..../.,.             \n" +
                "                ./.... .. ......... .,&@@@@@%............. .*.,% /.             \n" +
                "                .  ...,#........... *@@@(.....,...........,(..#..,..            \n" +
                "                    .*. ,................,......,.......,&,,..                  \n" +
                "                     *.,.(...*......,.......,..,.. .  .&,..                     \n" +
                "                       ..,,,* . .*..,......    ...,%(...(,..                    \n" +
                "                        .,**#,*#,..(.#...,,,#&%###...*,*,...                    \n" +
                "                         . ...&&/.*&,.*&.......(#.                              \n" +
                "                                 ..*...,,..                                     \n" +
                "                                   .......                                       ";

        System.out.println(logo);
    }

}
