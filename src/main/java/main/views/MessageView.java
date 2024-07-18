package main.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.MessageBox;

public class MessageView {
    private static Display d;
    private static Shell shell;
    private static final Font f = new Font(d, "ＭＳ ゴシック", 18, SWT.NORMAL);
    private static Text tf;

    public Text getTextField() {
        return tf;
    }

    public MessageView(Display d) {
        MessageView.d = d;
        init();

        shell.setSize(400, 300);
        shell.setVisible(false);
    }

    public void init() {
        shell = new Shell(d, SWT.TITLE | SWT.RESIZE);
        shell.setFont(f);
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
