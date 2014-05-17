package fi.paivola.simlet;

/**
 * Created by juhani on 5/13/14.
 */

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import fi.paivola.simlet.model.Model;
import fi.paivola.simlet.model.example.Town;
import fi.paivola.simlet.runner.Configure;
import fi.paivola.simlet.time.Scheduler;
import fi.paivola.simlet.time.Time;
import fi.paivola.simlet.time.Unit;
import fi.paivola.simlet.ui.CodePane;
import fi.paivola.simlet.ui.ParameterPane;
import fi.paivola.simlet.ui.StatusPane;
import fi.paivola.simlet.ui.WelcomePane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {

        // Main pane
        BorderPane root = new BorderPane();

        // Other panes
        // final StatusPane statusPane = new StatusPane();
        final WelcomePane welcomePane = new WelcomePane();
        final ParameterPane parameterPane = new ParameterPane();
        final ProgressBar progressBar = new ProgressBar();
        final CodePane codePane = new CodePane(progressBar);
        final MenuBar menuBar = new MenuBar();
        final TabPane tabPane = new TabPane();

        progressBar.setPrefWidth(9999);

        Configure.parameterPane = parameterPane;

        // Menu
        Menu menuFile = new Menu("File");
        menuBar.getMenus().add(menuFile);

        MenuItem itemOpen = new MenuItem("Open");
        menuFile.getItems().add(itemOpen);
        itemOpen.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JavaScript (*.js)", "*.js");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                codePane.setFile(file);
                tabPane.getSelectionModel().select(1);
            }
        });

        MenuItem itemClose = new MenuItem("Close");
        menuFile.getItems().add(itemClose);
        itemClose.setOnAction(event -> {
            codePane.closeFile();
            parameterPane.clear();
            tabPane.getSelectionModel().select(0);
        });


        MenuItem itemExit = new MenuItem("Exit");
        menuFile.getItems().add(itemExit);
        itemExit.setOnAction(event -> primaryStage.close());


        // Tabs
        tabPane.setSide(Side.BOTTOM);
        tabPane.getTabs().add(welcomePane.getTab());
        tabPane.getTabs().add(codePane.getTab());
        tabPane.getTabs().add(parameterPane.getTab());

        root.setTop(menuBar);
        root.setCenter(tabPane);
        root.setBottom(progressBar);
        // root.setBottom(statusPane);

        primaryStage.setTitle("SIMLet");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.show();
    }
}
