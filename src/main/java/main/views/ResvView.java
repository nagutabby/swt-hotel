package main.views;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import main.controllers.ResvController;
import main.controllers.Util;
import main.models.Reservation;
import main.models.ResvModel;

public class ResvView {
    private static Display d;
    private static Shell shell;
    private static ScrolledComposite sc;
    private static Table tbl;
    private static final Font f = new Font(d, "ＭＳ ゴシック", 18, SWT.NORMAL);

    private static ResvModel rm = ResvModel.getInstance();
    private static ResvController rc = ResvController.getInstance();

    public void init() {
        shell = new Shell(d, SWT.TITLE | SWT.RESIZE);
        shell.setFont(f);
        shell.setLayout(new GridLayout(1, true));
        shell.setText("予約記録一覧");
        sc = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
        tbl = new Table(sc, SWT.LEFT); // 表示用
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
        List<Reservation> l = rm.getAll();
        if (l.size() > 0) {
            tbl.removeAll();
            int id = 0;
            for (Reservation r: l) {
                TableItem row = new TableItem(tbl, SWT.NULL);
                row.setText(Util.generate(++id, r));
            }
        }
    }

    public ResvView(Display d) {
        ResvView.d = d;
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

            Reservation r = new Reservation(name, startDate, endDate, numberRooms);
            rc.add(r);
            rc.update();
            s.dispose();
        });

        Button b2 = Factory.makeButton(s, "戻る");
        b2.addListener(SWT.Selection, e -> s.dispose());
    }

    private void makeRemoveResvDialog(Shell s) {
        Text tf0 = Factory.makeTextField(s, "id", SWT.NULL);
        Text tf1 = Factory.makeTextField(s, "name", SWT.NULL);
        Button b1 = Factory.makeButton(s, "送信");
        b1.addListener(SWT.Selection, e -> {
            Reservation r = new Reservation(tf0.getText(), tf1.getText(), "", "");
            System.out.println("remove: r = " + r);
            rc.remove(r);
            rc.update();
            s.dispose();
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
