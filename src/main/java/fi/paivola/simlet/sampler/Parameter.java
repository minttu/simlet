package fi.paivola.simlet.sampler;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juhani on 5/14/14.
 */
public class Parameter {
    private final String name;
    private final String description;
    private final double min;
    private final double max;
    private final List<Double> values;

    public Parameter(String name, String description, double min, double max) {
        this.name = name;
        this.description = description;
        this.min = min;
        this.max = max;
        this.values = new ArrayList<Double>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public void addValue(double d) {
        this.values.add(d);
    }

    public List<Double> getValues() {
        return values;
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(description);
    }

    public DoubleProperty minProperty() {
        return new SimpleDoubleProperty(min);
    }

    public DoubleProperty maxProperty() {
        return new SimpleDoubleProperty(max);
    }

    public ListProperty<Double> valuesProperty() {
        SimpleListProperty<Double> dd = new SimpleListProperty<>();
        dd.addAll(values);
        return dd;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", values=" + values +
                '}';
    }
}
