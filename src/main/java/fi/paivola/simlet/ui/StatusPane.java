package fi.paivola.simlet.ui;

import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;

/**
 * Created by juhani on 5/14/14.
 */
public class StatusPane extends TitledPane {
    private final TextArea content;

    public StatusPane() {
        this.content = new TextArea();
        content.setPrefRowCount(8);
        content.setEditable(false);
        this.setText("Status log");
        this.setContent(content);
    }

    public void log(Object... str) {
        StringBuilder sb = new StringBuilder();
        for (Object s : str) {
            sb.append(s);
        }
        sb.append("\n");
        content.appendText(sb.toString());
        content.setScrollTop(99999);
    }

}
