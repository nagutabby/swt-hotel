package main.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import main.controllers.ResvController;
import main.models.ResvModel;

public class MainView {
    private static final Display d = new Display();
    private static final Font f = new Font(d, "ＭＳ ゴシック", 18, SWT.NORMAL);
    private static final Shell shell = new Shell(d, SWT.TITLE | SWT.CLOSE);
    private static final ResvModel rm = ResvModel.getInstance();
    private static final ResvController rc = ResvController.getInstance();

    private static void init() {
        rm.init();
        rc.setResvView(new ResvView(d)); // rc.attach
        rc.setRoomView(new RoomView(d)); // rc.attach
        rc.setMessageView(new MessageView(d));

        Factory.init(d);

        shell.setText("ホテル予約システム");
        GridLayout l = new GridLayout(1, true);
        shell.setLayout(l);

        final Label l1 = Factory.makeLabel(shell, "ようこそ");

        final Button b1 = Factory.makeButton(shell, "予約");
        b1.addListener(SWT.Selection, e -> rc.getResvView().showAddDialog());

        final Button b2 = Factory.makeButton(shell, "解約");
        b2.addListener(SWT.Selection, e -> rc.getResvView().showRemoveDialog());

        final Button b3 = Factory.makeButton(shell, "予約一覧表示");
        b3.addListener(SWT.Selection, e -> rc.getResvView().display());

        final Button b4 = Factory.makeButton(shell, "部屋一覧表示");
        b4.addListener(SWT.Selection, e -> rc.getRoomView().display());

        final Button b5 = Factory.makeButton(shell, "メッセージ");
        b5.addListener(SWT.Selection, e -> rc.getMessageView().display());
    }

    public static void main(String[] args) {
        init();

        shell.pack();
        shell.open();

        while (!shell.isDisposed()) {
            if (!d.readAndDispatch()) {
                d.sleep();
            }
        }
        d.dispose();
    }
}
