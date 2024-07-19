package main.views;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import main.controllers.Util;
import main.models.Reservation;
import main.models.ReservationModel;

public class RoomView {
    private static Display display;
    private static final Font font = new Font(display, "ＭＳ ゴシック", 18, SWT.NORMAL);
    private static Shell shell;
    private static ScrolledComposite scrolledComposite;
    private static Table table;
    private static final int N = 31; // 日数
    private static final int D = 1; // 開始日

    private static ReservationModel reservationModel = ReservationModel.getInstance();

    public void init() {
        shell = new Shell(display, SWT.TITLE | SWT.RESIZE);
        shell.setFont(font);
        shell.setLayout(new GridLayout(1, true));
        shell.setText("部屋状態一覧");
        scrolledComposite = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
        table = new Table(scrolledComposite, SWT.LEFT); // 表示用
        scrolledComposite.setContent(table);

        initTable();

        final Button b1 = Factory.makeButton(shell, "OK");
        b1.addListener(SWT.Selection, e -> shell.setVisible(false));
    }

    private void initTable() {
        TableColumn col;
        table.setFont(font);
        table.setHeaderVisible(true);
        table.setSize(1320, 500);
        col = new TableColumn(table, SWT.LEFT);
        col.setText("Room");
        col.setWidth(120);
        for (int i = 0; i < N; i++) {
            col = new TableColumn(table, SWT.LEFT);
            col.setText(String.format("%d", D + i));
            col.setWidth(120);
        }
        update();
    }

    private String toStr(int v) {
        return v + "";
    }

    private static String[][] buf = new String[10][N];

    private void makeBuffer() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < N; x++) {
                buf[y][x] = "";
            }
        }

        int c = 0;
        for (Reservation reservation : reservationModel.getAll()) {
            int startDate = Util.toInt(reservation.startDate) - D;
            int endDate = Util.toInt(reservation.endDate) - D;
            for (int i = startDate; i < endDate; i++) {
                if (Util.inRange(i, 0, N - 1)) {
                    buf[c][i] = reservation.name;
                }
            }
            if (++c >= 10) {
                break;
            }
        }
    }

    // 1行分の表示内容
    private String[] makeRow(int y) {
        String[] sv = new String[N + 1];
        Arrays.fill(sv, "");
        sv[0] = toStr(101 + y);
        for (int x = 0; x < N - 1; x++)
            sv[x + 1] = buf[y][x];
        return sv;
    }

    public void update() {
        makeBuffer();
        table.removeAll();
        for (int y = 0; y < 10; y++) {
            TableItem row = new TableItem(table, SWT.NULL);
            row.setText(makeRow(y));
        }
    }

    public RoomView(Display d) {
        RoomView.display = d;
        init();
        shell.setSize(1320, 800);
        shell.setVisible(false);
    }

    public void display() {
        shell.setVisible(true);
    }

    public void close() {
        shell.dispose();
    }
}
