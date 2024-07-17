package main.models;

import java.util.List;
import java.util.ArrayList;

import main.controllers.Util;

public class ResvModel {
    private static ResvModel instance;

    private ResvModel() {
    }

    public static ResvModel getInstance() {
        if (instance == null)
            instance = new ResvModel();
        return instance;
    }

    private List<Reservation> resvs = new ArrayList<Reservation>(); // 予約記録
    // TODO:部屋状態情報追加

    // 動作確認用初期値(要素数増加)
    public void init() {
        resvs.add(new Reservation("Alice", "11", "13"));
        resvs.add(new Reservation("Betty", "12", "14"));
        resvs.add(new Reservation("Carol", "13", "15"));
        resvs.add(new Reservation("Diana", "14", "16"));
        resvs.add(new Reservation("Emily", "15", "17"));
        resvs.add(new Reservation("Flora", "16", "18"));
        resvs.add(new Reservation("Helen", "17", "19"));
        resvs.add(new Reservation("Iris", "18", "20"));
    }

    // TODO: use DB
    public List<Reservation> getAll() {
        return resvs;
    }

    private boolean overlapped(
            int ckin1, int ckout1, int ckin2, int ckout2) {
        if (ckin1 > ckin2) {
            int t;
            t = ckin2;
            ckin2 = ckin1;
            ckin1 = t;
            t = ckout2;
            ckout2 = ckout1;
            ckout1 = t;
        }
        return (ckin2 < ckout1);
    }

//    private Reservation duplicated(Reservation r1) {
//        int ckin1 = Util.toInt(r1.ckin);
//        int ckout1 = Util.toInt(r1.ckout);
//        for (var r2 : resvs) {
//            if (r1.owner.equals(r2.owner)) {
//                int ckin2 = Util.toInt(r2.ckin);
//                int ckout2 = Util.toInt(r2.ckout);
//                if (overlapped(ckin1, ckout1, ckin2, ckout2)) {
//                    return r2;
//                }
//            }
//        }
//        return null;
//    }

    public Reservation checkDuplicate(String o, String ci, String co) {
        int ckin1 = Util.toInt(ci);
        int ckout1 = Util.toInt(co);
        for (Reservation r : resvs) {
            if (r.owner.equals(o)) {
                int ckin2 = Util.toInt(r.ckin);
                int ckout2 = Util.toInt(r.ckout);
                if (overlapped(ckin1, ckout1, ckin2, ckout2)) {
                    return r;
                }
            }
        }
        return null;
    }

    private boolean isVacancy(Reservation r) {
        int c = 0;
        for (Reservation r2 : resvs) {
            c += Util.toInt(r2.num);
        }
        return c + Util.toInt(r.num) <= 10;
    }

    public void add(Reservation r) {
        if (isVacancy(r)) {
            resvs.add(r);
        } else {
            System.err.println("no vacancy for " + r);
            return;
            // TODO: show error message in messageView
        }
    }

    public void remove(Reservation r) {
        // TODO:空室開放
        resvs.remove(r);
    }
}
