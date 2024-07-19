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

    private List<Reservation> reservations = new ArrayList<Reservation>();

    // 動作確認用初期値(要素数増加)
    public void init() {
        reservations.add(new Reservation("1", "Alice", "11", "13", "1"));
        reservations.add(new Reservation("2", "Betty", "12", "14", "1"));
        reservations.add(new Reservation("3", "Carol", "13", "15", "1"));
        reservations.add(new Reservation("4", "Diana", "14", "16", "1"));
        reservations.add(new Reservation("5", "Emily", "15", "17", "1"));
        reservations.add(new Reservation("6", "Flora", "16", "18", "1"));
        reservations.add(new Reservation("7", "Helen", "17", "19", "1"));
        reservations.add(new Reservation("8", "Alice", "18", "20", "1"));
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

    public boolean exists(String name) {
        for (Reservation reservation : reservations) {
            if (reservation.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public void remove(Reservation reservation) {
        reservations.remove(reservations.indexOf(reservation));
    }
}
