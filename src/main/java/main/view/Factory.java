package main.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

public class Factory {
    private static Display d;
    private static final Font f = new Font(d,"ＭＳ ゴシック",18,SWT.NORMAL);

    public static void init(Display di) { d = di; }

    public static Label makeLabel(Shell s, String name) {
        GridData gd = new GridData();
        gd.horizontalAlignment = SWT.CENTER;
        gd.widthHint = 400;
        return makeLabel(s, name, gd);
    }

    public static Label makeLabel(Shell s, String name, GridData gd) {
        Label l = new Label(s, SWT.NULL);
        l.setLayoutData(gd);
        l.setFont(f);
        l.setText(name);
        return l;
    }

    public static Label makeLabel(Shell s, String name, int hspan) {
        GridData gd = new GridData();
        gd.horizontalAlignment = SWT.CENTER;
        gd.widthHint = 400;
        gd.horizontalSpan = hspan;
        return makeLabel(s, name, gd);
    }

    public static Button makeButton(Shell s, String name) {
        Button b = new Button(s,SWT.NULL);
        GridData gd = new GridData();
        gd.horizontalAlignment = SWT.CENTER;
        gd.widthHint = 240;
        gd.heightHint = 60;
        b.setLayoutData(gd);
        b.setFont(f);
        b.setText(name);
        return b;
    }

    public static Text makeTextField(Shell shell, String name, int flags) {
        final Label l = new Label(shell, SWT.NULL);
        final Text tf = new Text(shell, flags);
        GridData gd = new GridData();
        gd.horizontalAlignment = SWT.CENTER;
        // TODO: pass the size as arguments
        gd.widthHint = 240;
        gd.heightHint = 60;

        l.setText(name);
        l.setFont(f);
        tf.setLayoutData(gd);
        tf.setFont(f);
        return tf;
    }
}
