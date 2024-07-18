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
    private boolean isValid(Reservation r) {
        int ckin = Util.toInt(r.startDate);
        int ckout = Util.toInt(r.endDate);
        return (Util.inRange(ckin, 1, 20) &&
                Util.inRange(ckout - 1, 1, 20) &&
                ckin < ckout);
    }

    // 重複検査
    public void add(Reservation inputReservation) {
        int numberRooms = Util.toInt(inputReservation.numberRooms);

        if (!isValid(inputReservation)) {
            messageView.append(inputReservation + " is invalid");
        } else if (!reservation.isVacant(inputReservation)) {
            System.err.println("no vacancy for " + reservation);
            // TODO: show error message in messageView
        } else if (reservation.isDuplicated(inputReservation.name, inputReservation.startDate, inputReservation.endDate)) {
            messageView.append("reservation is duplicated");
        } else {
            for (int i = 0; i < numberRooms; i++) {
                reservation.add(inputReservation);
            }
        }
    }

    public void remove(Reservation r) {
        // TODO: 検査
        // model.remove(r);
    }

    // TODO: リストの走査
    public void update() {
        resvView.update();
        roomView.update();
    }
}
