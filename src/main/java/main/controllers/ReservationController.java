package main.controllers;

import java.util.ArrayList;
import java.util.List;

import main.models.Reservation;
import main.models.ReservationModel;
import main.views.ReservationView;
import main.views.RoomView;
import main.views.MessageView;

public class ReservationController {
    private static ReservationController instance;

    private ReservationController() {
    }

    public static ReservationController getInstance() {
        if (instance == null) {
            instance = new ReservationController();
        }
        return instance;
    }

    private ReservationModel reservationModel = ReservationModel.getInstance();

    private ReservationView reservationView;
    private RoomView roomView;
    private MessageView messageView;

    public ReservationView getreservationView() {
        return reservationView;
    }

    public RoomView getRoomView() {
        return roomView;
    }

    public MessageView getMessageView() {
        return messageView;
    }

    public void setreservationView(ReservationView v) {
        reservationView = v;
    }

    public void setRoomView(RoomView v) {
        roomView = v;
    }

    public void setMessageView(MessageView v) {
        messageView = v;
    }

    private boolean isDateValid(Reservation reservation) {
        int startDate = Util.toInt(reservation.startDate);
        int endDate = Util.toInt(reservation.endDate);
        return (Util.inRange(startDate, 1, 30) &&
                Util.inRange(endDate, 1, 31) &&
                startDate < endDate);
    }

    public void add(Reservation reservation) {
        if (!isDateValid(reservation)) {
            getMessageView().display("利用開始日や利用終了日に誤りがあります");
        } else if (!reservationModel.isNumberRoomsValid(reservation)) {
            getMessageView().display("部屋数に誤りがあります");
        } else if (!reservationModel.isVacant(reservation)) {
            getMessageView().display("部屋が満室です");
        } else if (reservationModel.isDuplicated(reservation.name, reservation.startDate, reservation.endDate)) {
            getMessageView().display("予約が重複しています");
        } else {
            reservationModel.add(reservation);
        }
    }

    public void remove(Reservation reservation) {
        reservationModel.remove(reservation);
    }

    public ArrayList<Reservation> getReservations(String name) {
        List<Reservation> reservations = new ArrayList<>();
        reservations = reservationModel.getAll();
        ArrayList<Reservation> userReservations = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if (reservation.name.equals(name)) {
                userReservations.add(reservation);
            }
        }

        if (userReservations.isEmpty()) {
            getMessageView().display(name + "さんの予約がありません");
        }
        return userReservations;
    }

    public void update() {
        reservationView.update();
        roomView.update();
    }
}
