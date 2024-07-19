package main.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class Factory {
    private static Display display;
    private static final Font font = new Font(display, "ＭＳ ゴシック", 18, SWT.NORMAL);

    public static void init(Display d) {
        display = d;
    }

    public static Label makeLabel(Shell s, String name) {
        GridData gridData = new GridData();
        gridData.horizontalAlignment = SWT.CENTER;
        gridData.widthHint = 400;
        return makeLabel(s, name, gridData);
    }

    public static Label makeLabel(Shell s, String name, GridData gd) {
        Label label = new Label(s, SWT.NULL);
        label.setLayoutData(gd);
        label.setFont(font);
        label.setText(name);
        return label;
    }

    public static Label makeLabel(Shell s, String name, int hspan) {
        GridData gridData = new GridData();
        gridData.horizontalAlignment = SWT.CENTER;
        gridData.widthHint = 400;
        gridData.horizontalSpan = hspan;
        return makeLabel(s, name, gridData);
    }

    public static Button makeButton(Shell s, String name) {
        Button button = new Button(s, SWT.NULL);
        GridData gridData = new GridData();
        gridData.horizontalAlignment = SWT.CENTER;
        gridData.widthHint = 240;
        gridData.heightHint = 60;
        button.setLayoutData(gridData);
        button.setFont(font);
        button.setText(name);
        return button;
    }

    public static Text makeTextField(Shell shell, String name, int flags) {
        final Label label = new Label(shell, SWT.NULL);
        final Text text = new Text(shell, flags);
        GridData gd = new GridData();
        gd.horizontalAlignment = SWT.CENTER;
        gd.widthHint = 240;
        gd.heightHint = 60;

        label.setText(name);
        label.setFont(font);
        text.setLayoutData(gd);
        text.setFont(font);
        return text;
    }
}
