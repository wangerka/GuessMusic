package com.example.administrator.guessmusic.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class Const {
    public static final int ALL_WORDS_COUNTS = 24;
    public static final String[][] musics = {
            {"喜欢你","喜欢你"},
            {"套马杆","套马杆"},
            {"小苹果","小苹果"},
            {"小酒窝","小酒窝"},
            {"爱丫爱丫","爱丫爱丫"},
            {"爱的供养","爱的供养"},
            {"童话","童话"},
            {"荷塘月色","荷塘月色"}
    };

    public static char getRandomChar() {
        String str = "";
        int hightPos;
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str.charAt(0);
    }
}

