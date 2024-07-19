package main.controllers;

import java.util.Arrays;

import main.models.Reservation;

// staticメソッドのみ
public class Util {
    public static int toInt(String s) {
        int v = 0;
        try {
            v = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            v = -1;
        }
        return v;
    }

    // 範囲検査: l, uは正、数字以外はvは-1なので失敗
    public static boolean inRange(int v, int l, int u) {
        return l <= v && v <= u;
    }

    private static String[] sv = new String[5];

    public static String[] generate(Reservation r) {
        Arrays.fill(sv, "");
        sv[0] = r.id;
        sv[1] = r.name;
        sv[2] = r.startDate;
        sv[3] = r.endDate;
        sv[4] = r.numberRooms;
        return sv;
    }
}
