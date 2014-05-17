package fi.paivola.simlet.ui;

import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

interface PrintFunction {
    public void print(String str);
}

class Out extends OutputStream {

    private final PrintFunction printFunction;

    public Out(PrintFunction printFunction) {
        this.printFunction = printFunction;
    }

    @Override
    public void write(int b) {
        int[] bytes = {b};
        write(bytes, 0, bytes.length);
    }

    public void write(int[] bytes, int offset, int length) {
        String s = new String(bytes, offset, length);
        printFunction.print(s);
    }
}

/**
 * Created by juhani on 5/14/14.
 */
public class StatusPane extends TitledPane {
    private final TextArea content;

    public StatusPane() {
        this.content = new TextArea();

        content.setFont(Font.font("Monospaced", 12));

        // Java8 <3
        System.setOut(new PrintStream(new Out(str -> content.appendText(str))));
        System.setErr(new PrintStream(new Out(str -> content.appendText(str))));

        content.setPrefRowCount(8);
        content.setEditable(false);
        this.setText("Status log");
        this.setContent(content);
    }
}
