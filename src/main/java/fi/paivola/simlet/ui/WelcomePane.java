package fi.paivola.simlet.ui;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

/**
 * Created by juhani on 5/14/14.
 */
public class WelcomePane extends BorderPane {
    public WelcomePane() {
        this.setCenter(new Label("Welcome!"));
    }

    public Tab getTab() {
        Tab tab = new Tab("Welcome");
        tab.setClosable(false);
        tab.setContent(this);
        return tab;
    }
}
