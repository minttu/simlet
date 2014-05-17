package fi.paivola.simlet.model.example;

import fi.paivola.simlet.message.Message;
import fi.paivola.simlet.model.ConnectionModel;
import fi.paivola.simlet.model.Model;
import fi.paivola.simlet.model.PointModel;
import fi.paivola.simlet.time.Scheduler;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.*;


/**
 * Created by juhani on 17.5.2014.
 */
public class Road extends ConnectionModel {

    private final List<Map.Entry<Message, Model>> shipments;
    private final double length;
    private final double speed;
    private final double travel_time;

    public Road(String name, PointModel model1, PointModel model2, ScriptObjectMirror settings) {
        super(name, settings);
        connections.add(model1);
        model1.connections.add(this);
        connections.add(model2);
        model2.connections.add(this);
        length = model1.getPos().distanceTo(model2.getPos());
        speed = getOrDefault("speed", 1);
        travel_time = length / speed;
        shipments = new ArrayList<>();
    }

    @Override
    public void handleMessage(Message message) {
        if(connections.size() != 2) {
            throw new IllegalStateException("A road is between two points. Always!");
        }
        if(connections.contains(message.getSender())) {
            int ind = connections.indexOf(message.getSender());
            Model target = connections.get((ind==0?1:0));
            shipments.add(new AbstractMap.SimpleEntry<>(message, target));
            scheduler.schedule(scheduler.getTime().after(((int) travel_time)), (sc) -> {
                if(shipments.size() < 1) {
                    return;
                }
                Map.Entry<Message, Model> first = shipments.remove(0);
                first.getValue().addMessage(first.getKey());
            });
        }
    }

    @Override
    public void registerCallbacks(Scheduler scheduler) {
        super.registerCallbacks(scheduler);
    }
}
