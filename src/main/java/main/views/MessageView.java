package main.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.MessageBox;

public class MessageView {
    private static Display display;
    private static Shell shell;
    private static final Font font = new Font(display, "ＭＳ ゴシック", 18, SWT.NORMAL);
    private static Text text;

    public Text getTextField() {
        return text;
    }

    public MessageView(Display d) {
        MessageView.display = d;
        init();

        shell.setSize(400, 300);
        shell.setVisible(false);
    }

    public void init() {
        shell = new Shell(display, SWT.TITLE | SWT.RESIZE);
        shell.setFont(font);
        shell.setLayout(new GridLayout(1, true));
    }

    public void append(String s) {
    }

    public void display(String message) {
        MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);

        messageBox.setText("エラー");
        messageBox.setMessage(message);
        messageBox.open();
    }

    public void close() {
        shell.dispose();
    }
}
