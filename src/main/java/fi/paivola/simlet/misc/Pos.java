package fi.paivola.simlet.misc;

/**
 * Created by juhani on 14.5.2014.
 */
public class Pos {
    private final Double x;
    private final Double y;

    public Pos(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public double distanceTo(Pos other) {
        return Math.sqrt(Math.pow(other.getX() - this.getX(), 2) + Math.pow(other.getY() - this.getY(), 2));
    }
}
