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

    private ResvModel model = ResvModel.getInstance();

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
    private boolean valid(Reservation r) {
        int ckin = Util.toInt(r.ckin);
        int ckout = Util.toInt(r.ckout);
        return (Util.inRange(ckin, 1, 20) &&
                Util.inRange(ckout - 1, 1, 20) &&
                ckin < ckout);
    }

    private boolean overlapped(int ckin1, int ckout1, int ckin2, int ckout2) {
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

    // 重複検査
    public void add(Reservation r) {
        Reservation r2;
        if (!valid(r)) {
            messageView.append(r + " is invalid");
            return;
        }
        if ((r2 = model.checkDuplicate(r.owner, r.ckin, r.ckout)) != null) {
            messageView.append(r + " is duplicated with " + r2);
            return;
        }
        model.add(r);
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
