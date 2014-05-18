package fi.paivola.simlet.misc.example;

import fi.paivola.simlet.misc.Direction;

/**
 * Created by juhani on 18.5.2014.
 */
public class WeatherReport {
    private final double warmth;
    private final double cloudy;
    private final Direction direction;
    private final double wind_speed;

    public WeatherReport(double wind_speed, Direction direction, double cloudy, double warmth) {
        this.wind_speed = wind_speed;
        this.direction = direction;
        this.cloudy = cloudy;
        this.warmth = warmth;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getCloudy() {
        return cloudy;
    }

    public double getWarmth() {
        return warmth;
    }
}
