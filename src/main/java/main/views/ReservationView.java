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
    private static Display display;
    private static Shell shell;
    private static ScrolledComposite scrolledComposite;
    private static Table table;
    private static final Font font = new Font(display, "ＭＳ ゴシック", 18, SWT.NORMAL);

    private static ReservationModel reservationModel = ReservationModel.getInstance();
    private static ReservationController reservationController = ReservationController.getInstance();

    public void init() {
        shell = new Shell(display, SWT.TITLE | SWT.RESIZE);
        shell.setFont(font);
        shell.setLayout(new GridLayout(1, true));
        shell.setText("予約記録一覧");
        scrolledComposite = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
        table = new Table(scrolledComposite, SWT.LEFT);
        scrolledComposite.setContent(table);

        inittbl();

        final Button b1 = Factory.makeButton(shell, "OK");
        b1.addListener(SWT.Selection, e -> shell.setVisible(false));
    }

    private static final String[] colname = { "氏名", "利用開始日", "利用終了日", "部屋数" };

    private void inittbl() {
        TableColumn col;
        table.setFont(font);
        table.setHeaderVisible(true);
        table.setSize(700, 400);
        col = new TableColumn(table, SWT.LEFT);
        col.setText("id");
        col.setWidth(60);
        for (int i = 0; i < colname.length; i++) {
            col = new TableColumn(table, SWT.LEFT);
            col.setText(colname[i]);
            col.setWidth(180);
        }
        update();
    }

    public void update() {
        List<Reservation> reservations = reservationModel.getAll();
        if (reservations.size() > 0) {
            table.removeAll();
            for (Reservation reservation : reservations) {
                TableItem row = new TableItem(table, SWT.NULL);
                row.setText(Util.generate(reservation));
            }
        }
    }

    public ReservationView(Display d) {
        ReservationView.display = d;
        init();

        shell.setSize(800, 600);
        shell.setVisible(false);
    }

    private void makeAddResvDialog(Shell s) {
        Text nameField = Factory.makeTextField(s, "氏名", SWT.NULL);
        Text startDateField = Factory.makeTextField(s, "利用開始日", SWT.NULL);
        Text endDateField = Factory.makeTextField(s, "利用終了日", SWT.NULL);
        Text numberRoomsField = Factory.makeTextField(s, "部屋数", SWT.NULL);

        Button sendButton = Factory.makeButton(s, "送信");
        sendButton.addListener(SWT.Selection, e -> {
            String name = nameField.getText();
            String startDate = startDateField.getText();
            String endDate = endDateField.getText();
            String numberRooms = numberRoomsField.getText();

            Reservation reservation = new Reservation(name, startDate, endDate, numberRooms);
            reservationController.add(reservation);
            reservationController.update();
            s.dispose();
        });

        Button backButton = Factory.makeButton(s, "戻る");
        backButton.addListener(SWT.Selection, e -> s.dispose());
    }

    private void makeRemoveResvDialog(Shell s) {
        Text nameText = Factory.makeTextField(s, "氏名", SWT.NULL);
        Button b1 = Factory.makeButton(s, "送信");
        b1.addListener(SWT.Selection, e -> {
            String name = nameText.getText();
            ArrayList<Reservation> reservations = reservationController.getReservations(name);

            if (!reservations.isEmpty()) {
                final Shell subshell = new Shell(display, SWT.TITLE | SWT.CLOSE);
                subshell.setLayout(new GridLayout(2, true));
                subshell.setText("解約");
                subshell.setSize(600, 600);

                reservations.forEach(reservation -> {
                    StyledText reservationText = new StyledText(subshell, SWT.SINGLE);
                    final String text = reservation.name + ", " + reservation.startDate + ", " + reservation.endDate
                            + ", " + reservation.numberRooms;
                    final Button removeButton = Factory.makeButton(subshell, "削除");

                    reservationText.setCaret(null);
                    reservationText.setFont(font);
                    reservationText.setText(text);

                    removeButton.setData("reservation", reservation);
                    removeButton.addListener(SWT.Selection, removeEvent -> {
                        reservationController.remove((Reservation) removeButton.getData("reservation"));
                        reservationController.update();
                        reservationText.dispose();
                        removeButton.dispose();
                    });
                });

                subshell.open();

                while (!subshell.isDisposed()) {
                    if (!display.readAndDispatch()) {
                        display.sleep();
                    }
                }
            }
        });

        Button backButton = Factory.makeButton(s, "戻る");
        backButton.addListener(SWT.Selection, e -> s.dispose());
    }

    public void showAddDialog() {
        final Shell subshell = new Shell(display, SWT.TITLE | SWT.CLOSE);
        subshell.setLayout(new GridLayout(2, true));
        subshell.setText("予約");

        makeAddResvDialog(subshell);

        subshell.setSize(600, 600);
        subshell.open();
    }

    public void showRemoveDialog() {
        final Shell subshell = new Shell(display, SWT.TITLE | SWT.CLOSE);
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
