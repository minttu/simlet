package fi.paivola.simlet.model.example;

import fi.paivola.simlet.model.PointModel;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * Created by juhani on 5/14/14.
 */
public class Town extends PointModel {
    private double hunger;
    public Town(String name, ScriptObjectMirror settings) {
        super(name, settings);
        hunger = (Double) settings.getOrDefault("hunger", 1);
    }
}
