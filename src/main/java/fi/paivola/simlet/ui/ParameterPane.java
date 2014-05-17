package fi.paivola.simlet.ui;

import fi.paivola.simlet.sampler.Parameter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juhani on 17.5.2014.
 */
public class ParameterPane extends BorderPane implements Tabbable {
    private final Tab tab;
    private final List<Parameter> parameterList;
    

    public ParameterPane() {
        tab = new Tab("Parameters");
        tab.setContent(this);
        tab.setDisable(true);
        tab.setClosable(false);
        parameterList = new ArrayList<>();
    }

    public void addParameter(Parameter parameter) {
        parameterList.add(parameter);
        tab.setDisable(false);
    }

    public void clear() {
        parameterList.clear();
        tab.setDisable(true);
    }

    @Override
    public Tab getTab() {
        return tab;
    }
}
