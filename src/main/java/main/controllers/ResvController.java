package main.controllers;

import main.models.Reservation;
import main.models.ResvModel;
import main.views.*;

public class ResvController {
    private static ResvController instance;

    private ResvController() {
    }

    public static ResvController getInstance() {
        if (instance == null) {
            instance = new ResvController();
        }
        return instance;
    }

    private ResvModel reservation = ResvModel.getInstance();

    private ResvView resvView;
    private RoomView roomView;
    private MessageView messageView;

    public ResvView getResvView() {
        return resvView;
    }

    public RoomView getRoomView() {
        return roomView;
    }

    public MessageView getMessageView() {
        return messageView;
    }

    public void setResvView(ResvView v) {
        resvView = v;
    }

    public void setRoomView(RoomView v) {
        roomView = v;
    }

    public void setMessageView(MessageView v) {
        messageView = v;
    }

    // 日付検査のみ
    private boolean isDateValid(Reservation r) {
        int startDate = Util.toInt(r.startDate);
        int endDate = Util.toInt(r.endDate);
        return (Util.inRange(startDate, 1, 30) &&
                Util.inRange(endDate, 1, 31) &&
                startDate < endDate);
    }

    public void add(Reservation inputReservation) {
        int numberRooms = Util.toInt(inputReservation.numberRooms);

        if (!isDateValid(inputReservation)) {
            getMessageView().display("利用開始日や利用終了日に誤りがあります");
        } else if (!reservation.isVacant(inputReservation)) {
            getMessageView().display("部屋が満室です");
        } else if (reservation.isDuplicated(inputReservation.name, inputReservation.startDate, inputReservation.endDate)) {
            getMessageView().display("予約が重複しています");
        } else {
            for (int i = 0; i < numberRooms; i++) {
                reservation.add(inputReservation);
            }
        }
    }

    public void remove(Reservation inputReservation) {
        reservation.remove(inputReservation);
    }

    public void update() {
        resvView.update();
        roomView.update();
    }
}
