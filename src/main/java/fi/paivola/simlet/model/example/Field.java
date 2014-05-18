package fi.paivola.simlet.model.example;

import fi.paivola.simlet.message.Message;
import fi.paivola.simlet.message.StringMessage;
import fi.paivola.simlet.misc.Pos;
import fi.paivola.simlet.model.Model;
import fi.paivola.simlet.model.PointModel;
import fi.paivola.simlet.time.Scheduler;
import fi.paivola.simlet.time.Time;
import fi.paivola.simlet.time.Unit;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.Random;

/**
 * Created by juhani on 5/14/14.
 */
public class Field extends PointModel {
    private double wheat;

    public Field(String name, Pos pos, double size, ScriptObjectMirror settings) {
        super(name, pos, size, settings);
        wheat = getOrDefault("wheat", 1);
    }

    @Override
    public void handleMessage(Message message) {

    }

    private void harvest(Scheduler scheduler) {
        double got = new Random().nextDouble() * 1000 * size * wheat;
        System.out.printf("@%d Harvested %f\n", scheduler.getTime().getAmount(), got);
        double each = got / connections.size();
        for (Model model : connections) {
            model.addMessage(new StringMessage("wheat", "" + (each), this));
        }
    }

    @Override
    public void registerCallbacks(Scheduler scheduler) {
        super.registerCallbacks(scheduler);
        // Every day at 7:00
        scheduler.after(new Time(7, Unit.HOUR), sc -> scheduler.every(Unit.DAY, this::harvest));
    }
}
