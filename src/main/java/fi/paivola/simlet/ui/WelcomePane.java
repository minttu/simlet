package fi.paivola.simlet.ui;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import org.pegdown.PegDownProcessor;

import java.io.IOException;

/**
 * Created by juhani on 5/14/14.
 */
public class WelcomePane extends BorderPane {
    public WelcomePane() {
        WebView webView = new WebView();
        try {
            PegDownProcessor pegDownProcessor = new PegDownProcessor();
            webView.getEngine().loadContent(
                    Resources.toString(Resources.getResource("template.html"), Charsets.UTF_8).replace("{{html}}",
                            pegDownProcessor.markdownToHtml(Resources.toString(Resources.getResource("Welcome.md"), Charsets.UTF_8))
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setCenter(webView);
    }

    public Tab getTab() {
        Tab tab = new Tab("Welcome");
        tab.setClosable(false);
        tab.setContent(this);
        return tab;
    }
}
