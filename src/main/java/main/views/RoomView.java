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
    private static Display d;
    private static final Font f = new Font(d, "ＭＳ ゴシック", 18, SWT.NORMAL);
    private static Shell shell;
    private static ScrolledComposite sc;
    private static Table tbl;
    private static final int N = 31; // 日数
    private static final int D = 1; // 開始日

    private static ReservationModel resvModel = ReservationModel.getInstance();

    public void init() {
        shell = new Shell(d, SWT.TITLE | SWT.RESIZE);
        shell.setFont(f);
        shell.setLayout(new GridLayout(1, true));
        shell.setText("部屋状態一覧");
        sc = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
        tbl = new Table(sc, SWT.LEFT); // 表示用
        sc.setContent(tbl);

        inittbl();

        final Button b1 = Factory.makeButton(shell, "OK");
        b1.addListener(SWT.Selection, e -> shell.setVisible(false));
    }

    private void inittbl() {
        TableColumn col;
        tbl.setFont(f);
        tbl.setHeaderVisible(true);
        tbl.setSize(1320, 500);
        col = new TableColumn(tbl, SWT.LEFT);
        col.setText("Room");
        col.setWidth(120);
        for (int i = 0; i < N; i++) {
            col = new TableColumn(tbl, SWT.LEFT);
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
        // clear
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < N; x++) {
                buf[y][x] = "";
            }
        }

        int c = 0;
        for (Reservation reservation : resvModel.getAll()) {
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
        tbl.removeAll();
        for (int y = 0; y < 10; y++) {
            TableItem row = new TableItem(tbl, SWT.NULL);
            row.setText(makeRow(y));
        }
    }

    public RoomView(Display d) {
        RoomView.d = d;
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
