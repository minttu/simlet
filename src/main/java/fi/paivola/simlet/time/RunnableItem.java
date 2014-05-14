package fi.paivola.simlet.time;

import java.util.concurrent.CountDownLatch;

/**
 * Created by juhani on 5/14/14.
 */
public class RunnableItem implements Runnable {
    private final ScheduleItem scheduleItem;
    private final Scheduler scheduler;
    private final CountDownLatch latch;

    public RunnableItem(ScheduleItem scheduleItem, Scheduler scheduler, CountDownLatch latch) {
        this.scheduleItem = scheduleItem;
        this.scheduler = scheduler;
        this.latch = latch;
    }

    @Override
    public void run() {
        scheduleItem.call(scheduler);
        latch.countDown();
    }
}
