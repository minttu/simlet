package fi.paivola.simlet;

/**
 * Created by juhani on 5/13/14.
 */

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import fi.paivola.simlet.model.Model;
import fi.paivola.simlet.time.Scheduler;
import fi.paivola.simlet.time.Time;
import fi.paivola.simlet.time.Unit;
import fi.paivola.simlet.ui.CodePane;
import fi.paivola.simlet.ui.StatusPane;
import fi.paivola.simlet.ui.WelcomePane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.MenuBar;
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

        /*Model m = new Model("asd");
        Model n = new Model("dsa");
        Scheduler scheduler = new Scheduler();
        scheduler.schedule(new Time(Unit.DAY), m);
        scheduler.schedule(new Time(24, Unit.HOUR), n);
        Thread thread = new Thread(scheduler);
        thread.start();*/

        // Main pane
        BorderPane root = new BorderPane();

        // Other panes
        final WelcomePane welcomePane = new WelcomePane();
        final CodePane codePane = new CodePane();
        final StatusPane statusPane = new StatusPane();
        final MenuBar menuBar = new MenuBar();
        final TabPane tabPane = new TabPane();

        // Menu
        Menu menuFile = new Menu("File");
        menuBar.getMenus().add(menuFile);

        MenuItem itemOpen = new MenuItem("Open");
        menuFile.getItems().add(itemOpen);
        itemOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JavaScript (*.js)", "*.js");
                fileChooser.getExtensionFilters().add(extFilter);

                File file = fileChooser.showOpenDialog(null);
                if(file != null) {
                    codePane.setFile(file);
                    tabPane.getSelectionModel().select(1);
                }
            }
        });

        MenuItem itemClose = new MenuItem("Close");
        menuFile.getItems().add(itemClose);
        itemClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                codePane.closeFile();
                tabPane.getSelectionModel().select(0);
            }
        });


        MenuItem itemExit = new MenuItem("Exit");
        menuFile.getItems().add(itemExit);
        itemExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });


        // Tabs
        tabPane.setSide(Side.BOTTOM);
        tabPane.getTabs().add(welcomePane.getTab());
        tabPane.getTabs().add(codePane.getTab());

        root.setTop(menuBar);
        root.setCenter(tabPane);
        root.setBottom(statusPane);

        for (int i = 0; i < 100; i++) statusPane.log("hai: ", i);

        primaryStage.setTitle("SIMLet");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.show();
    }
}
