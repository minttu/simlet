package fi.paivola.simlet.ui;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import fi.paivola.simlet.runner.Configure;
import fi.paivola.simlet.runner.ConfigureFactory;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import java.io.*;
import java.util.function.Consumer;

/**
 * Created by juhani on 5/14/14.
 */
public class CodePane extends BorderPane implements Tabbable {
    private final WebView webView;
    private final ToolBar toolBar;
    private final Label fileLabel;
    private final ProgressBar progressBar;
    private Tab tab;
    private String code;
    private File file;
    private final Button saveButton;
    private final Button parametersButton;
    private final Button runButton;

    public CodePane(ProgressBar progressBar) {
        this.progressBar = progressBar;
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
        parametersButton = new Button("Parameters");
        parametersButton.setOnAction(event -> resolveParameters());
        runButton = new Button("Run");
        runButton.setOnAction(event -> runSimulation());

        this.toolBar = new ToolBar();
        toolBar.getItems().addAll(saveButton, parametersButton, runButton, fileLabel);
        this.setTop(toolBar);
    }

    public void runSimulation() {
        final String src = getCode();
        Task task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                Configure configure = new ConfigureFactory(src).create();
                updateProgress(0, 1);
                while (configure.run_once_next()) {
                    updateProgress(configure.getPercentage(), 1);
                }
                updateProgress(1, 1);
                return 0;
            }
        };
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
        });
        progressBar.progressProperty().bind(task.progressProperty());
        thread.setDaemon(true);
        thread.start();
    }

    public void resolveParameters() {
        final String src = getCode();
        Thread thread = new Thread(new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                new ConfigureFactory(src).create().updateParameterPane();
                return 0;
            }
        });

        thread.setUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
        });
        thread.setDaemon(true);
        thread.start();
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
