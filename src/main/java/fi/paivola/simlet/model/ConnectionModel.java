package fi.paivola.simlet.model;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * Created by juhani on 16.5.2014.
 */
public abstract class ConnectionModel extends Model {

    public ConnectionModel(String name, ScriptObjectMirror settings) {
        super(name, settings);
    }

}
