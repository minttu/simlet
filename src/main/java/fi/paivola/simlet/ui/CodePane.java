package fi.paivola.simlet.ui;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
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
    private final Button saveButton;
    private final Button evalButton;

    public CodePane() {
        this.webView = new WebView();
        this.webView.setContextMenuEnabled(false);
        this.fileLabel = new Label("");
        setCode("");
        this.setCenter(webView);

        webView.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.S) {
                save();
            }
        });

        saveButton = new Button("Save");
        saveButton.setOnAction(event -> save());
        evalButton = new Button("Eval");
        evalButton.setOnAction(event -> eval());

        this.toolBar = new ToolBar();
        toolBar.getItems().add(saveButton);
        toolBar.getItems().add(evalButton);
        toolBar.getItems().add(fileLabel);
        this.setTop(toolBar);
    }

    public void eval() {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        ///Thread thread = new Thread(() -> {
        // System.out.println("start"); // !!!!
        try {
            engine.eval(getCode());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        // System.out.println("end");
        //});
        // thread.setDaemon(true);
        //thread.start();
    }

    public void save() {
        setCode(getCode());
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(getCode());
            bufferedWriter.close();
            System.out.println("Saved file \"" + file.getAbsolutePath() + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (tab != null) {
            tab.setDisable(false);
        }
        System.out.println("Opened file \"" + file.getAbsolutePath() + "\"");
    }

    public void closeFile() {
        String fpath = file.getAbsolutePath();
        this.file = null;
        this.tab.setDisable(true);
        System.out.println("Closed file \"" + fpath + "\"");
    }

    public Tab getTab() {
        tab = new Tab("Scenario editor");
        tab.setContent(this);
        tab.setClosable(false);
        if (file == null) {
            tab.setDisable(true);
        }
        return tab;
    }
}
