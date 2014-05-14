package fi.paivola.simlet.time;

/**
 * Created by juhani on 5/14/14.
 */
public enum Unit implements TimeInterface {
    MINUTE(1),
    HOUR(60),
    DAY(1440),
    WEEK(10080);

    private final int amount;

    Unit(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }
}
