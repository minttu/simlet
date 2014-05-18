package fi.paivola.simlet.model.example;

import fi.paivola.simlet.message.Message;
import fi.paivola.simlet.message.example.WeatherMessage;
import fi.paivola.simlet.misc.Direction;
import fi.paivola.simlet.misc.example.WeatherReport;
import fi.paivola.simlet.model.Model;
import fi.paivola.simlet.time.Scheduler;
import fi.paivola.simlet.time.Unit;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.Random;

/**
 * Created by juhani on 18.5.2014.
 */
public class Weather extends Model {
    public Weather(String name, ScriptObjectMirror settings) {
        super(name, settings);
    }

    public void weather(Scheduler scheduler) {
        // Literally random weather
        // ToDo: actual weather
        Random random = new Random();
        WeatherReport weatherReport = new WeatherReport(random.nextDouble() * 9 + 4,
                Direction.RANDOM,
                random.nextDouble() / 2 + 0.25,
                random.nextDouble() * 30);
        WeatherMessage weatherMessage = new WeatherMessage(weatherReport, this);
        for (Model model : connections) {
            model.addMessage(weatherMessage);
        }
    }

    @Override
    public void handleMessage(Message message) {

    }

    @Override
    public void registerCallbacks(Scheduler scheduler) {
        super.registerCallbacks(scheduler);
        scheduler.every(Unit.DAY, this::weather);
    }
}
