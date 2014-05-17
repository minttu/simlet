package fi.paivola.simlet.model;

import fi.paivola.simlet.message.Message;
import fi.paivola.simlet.message.MessageBus;
import fi.paivola.simlet.time.ScheduleItem;
import fi.paivola.simlet.time.Scheduler;
import fi.paivola.simlet.time.Time;
import fi.paivola.simlet.time.Unit;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juhani on 5/14/14.
 */
public abstract class Model extends MessageBus {
    private final String name;
    public final List<Model> connections;
    protected final ScriptObjectMirror settings;
    protected Scheduler scheduler;

    public Model(String name, ScriptObjectMirror settings) {
        this.name = name;
        this.connections = new ArrayList<Model>();
        this.settings = settings;
    }

    public double getOrDefault(String name, double def) {
        Object real = settings.get(name);
        return (real == null ? def : (double) real);
    }

    @Override
    public synchronized void addMessage(Message message) {
        super.addMessage(message);
        updateMessages();
    }

    @Override
    public void updateMessages() {
        super.updateMessages();
        getAllMessages().forEach(this::handleMessage);
    }

    public abstract void handleMessage(Message message);

    public void registerCallbacks(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
