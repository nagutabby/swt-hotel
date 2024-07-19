package main.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import main.controllers.ReservationController;
import main.models.ReservationModel;

public class MainView {
    private static final Display display = new Display();
    private static final Font font = new Font(display, "ＭＳ ゴシック", 18, SWT.NORMAL);
    private static final Shell shell = new Shell(display, SWT.TITLE | SWT.CLOSE);
    private static final ReservationModel reservationModel = ReservationModel.getInstance();
    private static final ReservationController reservationController = ReservationController.getInstance();

    private static void init() {
        reservationModel.init();
        reservationController.setreservationView(new ReservationView(display));
        reservationController.setRoomView(new RoomView(display));
        reservationController.setMessageView(new MessageView(display));
        Factory.init(display);
        shell.setFont(font);

        shell.setText("ホテル予約システム");
        GridLayout gridLayout = new GridLayout(1, true);
        shell.setLayout(gridLayout);

        final Button addReservationButton = Factory.makeButton(shell, "予約");
        addReservationButton.addListener(SWT.Selection, e -> reservationController.getreservationView().showAddDialog());

        final Button cancelReservationButton = Factory.makeButton(shell, "解約");
        cancelReservationButton.addListener(SWT.Selection, e -> reservationController.getreservationView().showRemoveDialog());

        final Button listReservationsButton = Factory.makeButton(shell, "予約一覧表示");
        listReservationsButton.addListener(SWT.Selection, e -> reservationController.getreservationView().display());

        final Button listRoomsButton = Factory.makeButton(shell, "部屋一覧表示");
        listRoomsButton.addListener(SWT.Selection, e -> reservationController.getRoomView().display());
    }

    public static void main(String[] args) {
        init();

        shell.pack();
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
}
