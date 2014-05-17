package fi.paivola.simlet.ui;

import fi.paivola.simlet.sampler.Parameter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juhani on 17.5.2014.
 */
public class ParameterPane extends BorderPane implements Tabbable {
    private final Tab tab;
    private final List<Parameter> parameterList;
    private final ObservableList<Parameter> data;
    private TableView tableView;

    public ParameterPane() {
        tab = new Tab("Parameters");
        tab.setContent(this);
        tab.setDisable(true);
        tab.setClosable(false);
        tab.setOnSelectionChanged((e) -> {
            if (tab.isSelected()) {
                update();
            }
        });
        parameterList = new ArrayList<>();
        data = FXCollections.observableArrayList();
        update();
    }

    public void update() {
        tableView = new TableView();
        tableView.setItems(data);

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn description = new TableColumn("Description");
        description.setCellValueFactory(new PropertyValueFactory("description"));

        TableColumn min = new TableColumn("Min");
        min.setCellValueFactory(new PropertyValueFactory("min"));

        TableColumn max = new TableColumn("Max");
        max.setCellValueFactory(new PropertyValueFactory("max"));

        TableColumn valuesColumn = new TableColumn("Sample");

        int times = (data.size() > 0 ? data.get(0).getValues().size() : 0);
        for (int i = 0; i < times; i++) {
            final int run = i;
            TableColumn values = new TableColumn("" + (run + 1));
            values.setCellValueFactory(p -> {
                Parameter par = (((TableColumn.CellDataFeatures<Parameter, Double>) p).getValue());
                return new SimpleObjectProperty<String>(String.format("%1$,.2f", par.getValues().get(run)));
            });
            valuesColumn.getColumns().add(values);
        }

        tableView.getColumns().addAll(name, description, min, max, valuesColumn);

        this.setCenter(tableView);
    }

    public void addParameter(Parameter parameter) {
        parameterList.add(parameter);
        data.add(parameter);
        tab.setDisable(false);
    }

    public void clear() {
        parameterList.clear();
        tableView = null;
        data.clear();
        tab.setDisable(true);
    }

    @Override
    public Tab getTab() {
        return tab;
    }
}
