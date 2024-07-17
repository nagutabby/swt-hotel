package main.control;

import java.util.Arrays;

import main.model.Reservation;

// staticメソッドのみ
public class Util {
    public static  int toInt(String s) {
        int v=0;
        try {
            v = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            v = -1;
        }
        return v;
    }

    // 範囲検査:l,uは正, 数字以外はvは-1なので失敗
    public static  boolean inRange(int v, int l, int u) {
        return  l <= v  && v <= u;
    }

    private static String[] sv = new String[5];
    public static String[] generate(int id, Reservation r) {
        Arrays.fill(sv, "");
        sv[0] = String.format("%d", id);
        sv[1] = r.owner;
        sv[2] = r.ckin;
        sv[3] = r.ckout;
        sv[4] = r.num;
        return sv;
    }
}
