package fi.paivola.simlet.model.example;

import fi.paivola.simlet.model.PointModel;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * Created by juhani on 5/14/14.
 */
public class Field extends PointModel {
    private double wheat;
    public Field(String name, ScriptObjectMirror settings) {
        super(name, settings);
        wheat = (Double) settings.getOrDefault("wheat", 1);
    }
}
