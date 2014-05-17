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
        people = size * 1000;
        wheat = people;
    }

    @Override
    public void handleMessage(Message message) {
        if (message instanceof StringMessage) {
            switch (((StringMessage) message).getName()) {
                case "wheat":
                    System.out.println("Town got a wheat shipment");
                    wheat += Double.parseDouble(((StringMessage) message).getString());
                    break;
            }
        }
    }

    private void eat(Scheduler scheduler) {
        double newwheat = wheat - people * hunger;
        double newpeople = people - newwheat;
        System.out.printf("P %f->%f W %f->%f\n", people, newpeople, wheat, newwheat);
        people = newpeople;
        wheat = newwheat;
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
