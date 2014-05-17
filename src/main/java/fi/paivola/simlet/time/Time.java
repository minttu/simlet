package fi.paivola.simlet.time;

/**
 * Created by juhani on 5/14/14.
 */
public class Time implements TimeInterface, Comparable<Time> {
    private int amount;

    public Time(int amount) {
        this.amount = Math.max(0, amount);
    }

    public Time(int times, Unit unit) {
        this(times * unit.getAmount());
    }

    public Time(Unit unit) {
        this(unit.getAmount());
    }

    public void addAmount(int amount) {
        this.amount += Math.max(0, amount);
    }

    public void addAmount(int times, Unit unit) {
        this.addAmount(times * unit.getAmount());
    }

    public void addAmount(Unit unit) {
        this.addAmount(unit.getAmount());
    }

    public Time after(int amount) {
        return new Time(this.amount + amount);
    }

    public Time after(int times, Unit unit) {
        return after(times * unit.getAmount());
    }

    public Time after(Unit unit) {
        return after(unit.getAmount());
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Time{" +
                "amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time time = (Time) o;

        if (amount != time.amount) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return amount;
    }

    @Override
    public int compareTo(Time o) {
        return this.getAmount() - o.getAmount();
    }
}
