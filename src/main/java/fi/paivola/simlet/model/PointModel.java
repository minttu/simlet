package fi.paivola.simlet.model;

import com.sun.javafx.geom.Vec2d;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * Created by juhani on 5/14/14.
 */
public abstract class PointModel extends Model {
    protected double size;
    protected Vec2d pos;
    public PointModel(String name, ScriptObjectMirror settings) {
        super(name, settings);
        size = (Double) settings.getOrDefault("size", 10);
        if(settings.containsKey("pos")) {
            ScriptObjectMirror jpos = (ScriptObjectMirror) settings.get("pos");
            pos = new Vec2d((Double) jpos.get("x"), (Double) jpos.get("y"));
        } else {
            pos = new Vec2d(0, 0);
        }
    }
}
