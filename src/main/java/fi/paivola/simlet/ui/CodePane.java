package fi.paivola.simlet.ui;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Consumer;

/**
 * Created by juhani on 5/14/14.
 */
public class CodePane extends BorderPane {
    private final WebView webView;
    private final ToolBar toolBar;
    private final Label fileLabel;
    private Tab tab;
    private String code;
    private File file;

    public CodePane() {
        this.webView = new WebView();
        this.webView.setContextMenuEnabled(false);
        this.fileLabel = new Label("");
        setCode("");
        this.setCenter(webView);

        this.toolBar = new ToolBar();
        toolBar.getItems().add(new Button("Save"));
        toolBar.getItems().add(fileLabel);
        this.setTop(toolBar);
    }

    public void restore() {
        try {
            this.webView.getEngine().loadContent(
                    Resources.toString(Resources.getResource("editor.html"), Charsets.UTF_8).replace("{{code}}", code)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCode() {
        return (String) this.webView.getEngine().executeScript("editor.getValue()");
    }

    public void setCode(String code) {
        this.code = code;
        restore();
    }

    public void setFile(File file) {
        this.file = file;
        try {
            final StringBuilder sb = new StringBuilder();
            new BufferedReader(new FileReader(file)).lines().forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    sb.append(s).append("\n");
                }
            });
            fileLabel.setText(file.getAbsolutePath());
            setCode(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(tab != null) {
            tab.setDisable(false);
        }
    }

    public void closeFile() {
        this.file = null;
        this.tab.setDisable(true);
    }

    public Tab getTab() {
        tab = new Tab("Scenario editor");
        tab.setContent(this);
        if(file == null) {
            tab.setDisable(true);
        }
        return tab;
    }
}
