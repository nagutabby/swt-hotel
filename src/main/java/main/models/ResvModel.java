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

    private List<Reservation> reservations = new ArrayList<Reservation>(); // 予約記録
    // TODO:部屋状態情報追加

    // 動作確認用初期値(要素数増加)
    public void init() {
        reservations.add(new Reservation("Alice", "11", "13"));
        reservations.add(new Reservation("Betty", "12", "14"));
        reservations.add(new Reservation("Carol", "13", "15"));
        reservations.add(new Reservation("Diana", "14", "16"));
        reservations.add(new Reservation("Emily", "15", "17"));
        reservations.add(new Reservation("Flora", "16", "18"));
        reservations.add(new Reservation("Helen", "17", "19"));
        reservations.add(new Reservation("Iris", "18", "20"));
    }

    public List<Reservation> getAll() {
        return reservations;
    }

    private boolean isOverlapped(int inputStartDate, int inputEndDate, int reservedStartDate, int reservedEndDate) {
        // ユーザーが入力した利用開始日が、特定の予約の利用開始日よりも前である
        if (inputStartDate < reservedStartDate) {
            // ユーザーが入力した利用終了日が特定の予約の利用開始日と同じかそれよりも前である
            if (inputEndDate <= reservedStartDate) {
                return false;
            }
            // ユーザーが入力した利用開始日が特定の予約の利用終了日と同じかそれよりも後である
        } else if (reservedEndDate <= inputStartDate) {
            return false;
        }
        return true;
    }

    public boolean isDuplicated(String inputOwner, String inputStartDateString, String inputEndDateString) {
        int inputStartDate = Util.toInt(inputStartDateString);
        int inputEndDate = Util.toInt(inputEndDateString);
        boolean isDuplicated = false;

        for (Reservation reservation : reservations) {
            if (reservation.name.equals(inputOwner)) {
                int reservedStartDate = Util.toInt(reservation.startDate);
                int reservedEndDate = Util.toInt(reservation.endDate);
                isDuplicated = isOverlapped(inputStartDate, inputEndDate, reservedStartDate, reservedEndDate);
                if (isDuplicated) {
                    break;
                }
            }
        }
        return isDuplicated;
    }

    public boolean isVacant(Reservation inputReservation) {
        int count = 0;
        for (Reservation reservation : reservations) {
            count += Util.toInt(reservation.numberRooms);
        }
        return count + Util.toInt(inputReservation.numberRooms) <= 10;
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public void remove(Reservation reservation) {
        // TODO:空室開放
        reservations.remove(reservation);
    }
}
