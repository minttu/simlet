package fi.paivola.simlet.model;

import fi.paivola.simlet.message.Message;
import fi.paivola.simlet.message.MessageBus;
import fi.paivola.simlet.time.ScheduleItem;
import fi.paivola.simlet.time.Scheduler;
import fi.paivola.simlet.time.Unit;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juhani on 5/14/14.
 */
public abstract class Model extends MessageBus implements ScheduleItem {
    private final String name;
    private final List<Model> connections;
    protected final ScriptObjectMirror settings;
    private int runs;

    public Model(String name, ScriptObjectMirror settings) {
        this.name = name;
        this.connections = new ArrayList<Model>();
        this.settings = settings;
        this.runs = 0;
    }

    @Override
    public void call(Scheduler scheduler) {
        updateMessages();
        for (Message message : getAllMessages()) {
            System.out.println(name + ": \"" + message.getObject().toString());
        }
        runs++;
        System.out.println(name + ": \"" + runs + "\"");
        if (runs < 100) {
            scheduler.schedule(scheduler.getTime().after(Unit.DAY), this);
        }
    }

}
