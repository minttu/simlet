package fi.paivola.simlet.model;

import fi.paivola.simlet.misc.Pos;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * Created by juhani on 5/14/14.
 */
public abstract class PointModel extends Model {
    protected double size;
    protected Pos pos;

    public PointModel(String name, Pos pos, double size, ScriptObjectMirror settings) {
        super(name, settings);
        this.size = size;
        this.pos = pos;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }
}
