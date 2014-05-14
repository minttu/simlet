package fi.paivola.simlet.time;

/**
 * Created by juhani on 5/14/14.
 */
public interface ScheduleItem {
    public void call(Scheduler scheduler);
}
