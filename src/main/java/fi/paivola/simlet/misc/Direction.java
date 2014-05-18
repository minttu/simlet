package fi.paivola.simlet.misc;

import java.util.Random;

/**
 * Represents a cardinal direction.
 */
public enum Direction {

    NORTH(0), N(0),
    NORTHEAST(45), NE(45),
    EAST(90), E(90),
    SOUTHEAST(135), SE(135),
    SOUTH(180), S(180),
    SOUTHWEST(225), SW(225),
    WEST(270), W(270),
    NORTHWEST(315), NW(315),
    RANDOM();

    private final int angle;

    Direction() {
        this(new Random().nextInt(360));
    }

    Direction(int angle) {
        this.angle = angle % 360;
    }

    public int differenceTo(Direction direction) {
        return (((direction.getAngle() - getAngle() + 180) % 360 + 360) % 360) - 180;
    }

    public int getAngle() {
        return angle;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "angle=" + angle +
                '}';
    }
}
