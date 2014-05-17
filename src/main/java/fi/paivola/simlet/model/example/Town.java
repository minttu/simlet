package fi.paivola.simlet.model.example;

import fi.paivola.simlet.message.Message;
import fi.paivola.simlet.message.StringMessage;
import fi.paivola.simlet.misc.Pos;
import fi.paivola.simlet.model.PointModel;
import fi.paivola.simlet.time.Scheduler;
import fi.paivola.simlet.time.Unit;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * Created by juhani on 5/14/14.
 */
public class Town extends PointModel {
    private double hunger;
    private double people;
    private double wheat;

    public Town(String name, Pos pos, double size, ScriptObjectMirror settings) {
        super(name, pos, size, settings);
        hunger = getOrDefault("hunger", 1);
        people = wheat = size * 1000;
    }

    @Override
    public void handleMessage(Message message) {
        if (message instanceof StringMessage) {
            switch (((StringMessage) message).getName()) {
                case "wheat":
                    wheat += Double.parseDouble(((StringMessage) message).getString());
                    break;
            }
        }
    }

    private void eat(Scheduler scheduler) {
        double consumption = people * hunger;
        if(consumption > wheat) {
            consumption -= wheat;
            wheat = 0;
            people -= consumption * 0.95;
            consumption = 0;
        } else {
            double extra = wheat - consumption;
            wheat -= consumption;
            people += extra * 0.05;
        }
        System.out.printf("@%d Population %f Wheat %f\n",scheduler.getTime().getAmount(), people, wheat);
        registerEat(scheduler);
    }

    private void registerEat(Scheduler scheduler) {
        scheduler.schedule(scheduler.getTime().after(Unit.DAY), (sc) -> {
            this.eat(sc);
        });
    }

    @Override
    public void registerCallbacks(Scheduler scheduler) {
        super.registerCallbacks(scheduler);
        registerEat(scheduler);
    }
}
