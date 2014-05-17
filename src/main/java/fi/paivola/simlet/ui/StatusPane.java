package fi.paivola.simlet.ui;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by juhani on 5/14/14.
 */
public class StatusPane extends TitledPane {
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
            printFunction.print(String.valueOf((char) b));
        }
    }

    private final TextArea content;

    public StatusPane() {
        this.content = new TextArea();

        content.setFont(Font.font("Monospaced", 12));

        // Java8 <3

        PrintStream ps = new PrintStream(new Out(str -> {
            Platform.runLater(() -> {
                content.appendText(str);
            });
        }));

        System.setOut(ps);
        System.setErr(ps);

        content.setPrefRowCount(8);
        content.setEditable(false);
        this.setText("Status log");
        this.setContent(content);
    }
}
