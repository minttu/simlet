package fi.paivola.simlet.message.example;

import fi.paivola.simlet.message.Message;
import fi.paivola.simlet.message.MessageBus;
import fi.paivola.simlet.misc.example.WeatherReport;
import fi.paivola.simlet.model.example.Weather;

/**
 * Created by juhani on 18.5.2014.
 */
public class WeatherMessage implements Message {

    private final WeatherReport weatherReport;
    private final Weather weather;

    public WeatherMessage(WeatherReport weatherReport, Weather weather) {
        this.weatherReport = weatherReport;
        this.weather = weather;
    }

    @Override
    public MessageBus getSender() {
        return weather;
    }

    public WeatherReport getWeatherReport() {
        return weatherReport;
    }

    @Override
    public Object getObject() {
        return weatherReport;
    }
}
