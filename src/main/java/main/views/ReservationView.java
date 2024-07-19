package main.views;

import java.util.List;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import main.controllers.ReservationController;
import main.controllers.Util;
import main.models.Reservation;
import main.models.ReservationModel;

public class ReservationView {
    private static Display d;
    private static Shell shell;
    private static ScrolledComposite sc;
    private static Table tbl;
    private static final Font f = new Font(d, "ＭＳ ゴシック", 18, SWT.NORMAL);

    private static ReservationModel resvModel = ReservationModel.getInstance();
    private static ReservationController resvController = ReservationController.getInstance();

    public void init() {
        shell = new Shell(d, SWT.TITLE | SWT.RESIZE);
        shell.setFont(f);
        shell.setLayout(new GridLayout(1, true));
        shell.setText("予約記録一覧");
        sc = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
        tbl = new Table(sc, SWT.LEFT);
        sc.setContent(tbl);

        inittbl();

        final Button b1 = Factory.makeButton(shell, "OK");
        b1.addListener(SWT.Selection, e -> shell.setVisible(false));
    }

    private static final String[] colname = { "氏名", "利用開始日", "利用終了日", "部屋数" };

    private void inittbl() {
        TableColumn col;
        tbl.setFont(f);
        tbl.setHeaderVisible(true);
        tbl.setSize(700, 400);
        col = new TableColumn(tbl, SWT.LEFT);
        col.setText("id");
        col.setWidth(60);
        for (int i = 0; i < colname.length; i++) {
            col = new TableColumn(tbl, SWT.LEFT);
            col.setText(colname[i]);
            col.setWidth(180);
        }
        update();
    }

    public void update() {
        List<Reservation> reservations = resvModel.getAll();
        if (reservations.size() > 0) {
            tbl.removeAll();
            for (Reservation reservation: reservations) {
                TableItem row = new TableItem(tbl, SWT.NULL);
                row.setText(Util.generate(reservation));
            }
        }
    }

    public ReservationView(Display d) {
        ReservationView.d = d;
        init();

        shell.setSize(800, 600);
        shell.setVisible(false);
    }

    private void makeAddResvDialog(Shell s) {
        Text nameField = Factory.makeTextField(s, "氏名", SWT.NULL);
        Text startDateField = Factory.makeTextField(s, "利用開始日", SWT.NULL);
        Text endDateField = Factory.makeTextField(s, "利用終了日", SWT.NULL);
        Text numberRoomsField = Factory.makeTextField(s, "部屋数", SWT.NULL);

        Button b1 = Factory.makeButton(s, "送信");
        b1.addListener(SWT.Selection, e -> {
            String name = nameField.getText();
            String startDate = startDateField.getText();
            String endDate = endDateField.getText();
            String numberRooms = numberRoomsField.getText();

            Reservation reservation = new Reservation(name, startDate, endDate, numberRooms);
            resvController.add(reservation);
            resvController.update();
            s.dispose();
        });

        Button b2 = Factory.makeButton(s, "戻る");
        b2.addListener(SWT.Selection, e -> s.dispose());
    }

    private void makeRemoveResvDialog(Shell s) {
        Text tf1 = Factory.makeTextField(s, "氏名", SWT.NULL);
        Button b1 = Factory.makeButton(s, "送信");
        b1.addListener(SWT.Selection, e -> {
            String name = tf1.getText();
            if (!resvModel.exists(name)) {
                resvController.getMessageView().display(name + "さんの予約がありません");
            } else {
                ArrayList<Reservation> reservations = resvController.getReservations(name);
                final Shell subshell = new Shell(d, SWT.TITLE | SWT.CLOSE);
                subshell.setLayout(new GridLayout(2, true));
                subshell.setText("解約");
                subshell.setSize(600, 600);
                final Font f = new Font(d, "ＭＳ ゴシック", 18, SWT.NORMAL);
                reservations.forEach(reservation -> {
                    StyledText reservationText = new StyledText(subshell, SWT.SINGLE);
                    reservationText.setCaret(null);
                    reservationText.setFont(f);
                    String text = reservation.name + ", " + reservation.startDate + ", " + reservation.endDate + ", " + reservation.numberRooms;
                    reservationText.setText(text);
                    final Button removeButton = Factory.makeButton(subshell, "削除");
                    removeButton.setData("reservation", reservation);
                    removeButton.addListener(SWT.Selection, removeEvent -> {
                        resvModel.remove((Reservation)removeButton.getData("reservation"));
                        resvController.update();
                        reservationText.dispose();
                        removeButton.dispose();
                    });
                });

                subshell.open();
                while (!subshell.isDisposed()) {
                    if (!d.readAndDispatch()) {
                        d.sleep();
                    }
                }
            }

        });

        Button b2 = Factory.makeButton(s, "戻る");
        b2.addListener(SWT.Selection, e -> s.dispose());
    }

    public void showAddDialog() {
        final Shell subshell = new Shell(d, SWT.TITLE | SWT.CLOSE);
        subshell.setLayout(new GridLayout(2, true));
        subshell.setText("予約");

        makeAddResvDialog(subshell);

        subshell.setSize(600, 600);
        subshell.open();
    }

    public void showRemoveDialog() {
        final Shell subshell = new Shell(d, SWT.TITLE | SWT.CLOSE);
        subshell.setLayout(new GridLayout(2, true));
        subshell.setText("解約");

        makeRemoveResvDialog(subshell);

        subshell.setSize(600, 600);
        subshell.open();
    }

    public void display() {
        shell.setVisible(true);
    }

    public void close() {
        shell.dispose();
    }
}
