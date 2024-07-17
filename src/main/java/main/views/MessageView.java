package main.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MessageView {
    private static Display d;
    private static Shell shell;
    private static final Font f = new Font(d, "ＭＳ ゴシック", 18, SWT.NORMAL);
    // private static ScrolledComposite sc;
    private static Text tf;

    public Text getTextField() {
        return tf;
    }

    public MessageView(Display d) {
        this.d = d;
        init();

        shell.setSize(400, 300);
        shell.setVisible(false);
    }

    public void init() {
        shell = new Shell(d, SWT.TITLE | SWT.RESIZE);
        shell.setFont(f);
        shell.setLayout(new GridLayout(1, true));
        shell.setText("メッセージ");

        tf = Factory.makeTextField(shell, "", SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        final Button b1 = Factory.makeButton(shell, "OK");
        b1.addListener(SWT.Selection, e -> shell.setVisible(false));
    }

    public void append(String s) {
        tf.setText(tf.getText() + s + "\n");
    }

    public void display() {
        shell.setVisible(true);
    }

    public void close() {
        shell.dispose();
    }
}
