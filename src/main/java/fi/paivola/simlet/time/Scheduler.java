package fi.paivola.simlet.time;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by juhani on 5/14/14.
 */
public class Scheduler implements Runnable, TimeInterface {
    private final TreeMap<Time, List<ScheduleItem>> schedule;
    private final Map<Time, List<ScheduleItem>> buffer;
    private final ExecutorService executorService;
    private final Time current;
    private final Time max;

    public Scheduler(Time max) {
        this.schedule = new TreeMap<Time, List<ScheduleItem>>();
        this.buffer = new HashMap<Time, List<ScheduleItem>>();
        this.executorService = Executors.newFixedThreadPool(2);
        this.current = new Time(0);
        this.max = max;
    }

    public synchronized void schedule(Time time, ScheduleItem scheduleItem) {
        List<ScheduleItem> tmp = buffer.get(time);
        if (tmp == null) {
            tmp = new ArrayList<ScheduleItem>();
            tmp.add(scheduleItem);
            buffer.put(time, tmp);
        } else {
            tmp.add(scheduleItem);
        }
    }

    public boolean step() {
        schedule.putAll(buffer);
        buffer.clear();
        if (schedule.isEmpty()) {
            return false;
        }
        current.addAmount(schedule.firstKey().getAmount() - current.getAmount());
        System.out.println(current);
        if (current.getAmount() > max.getAmount()) {
            return false;
        }
        //CountDownLatch latch = new CountDownLatch(schedule.firstEntry().getValue().size());
        for (ScheduleItem si : schedule.firstEntry().getValue()) {
            //executorService.execute(new RunnableItem(si, this, latch));
            si.call(this);
        }
        /*try {
            latch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }*/
        schedule.remove(schedule.firstKey());
        return true;
    }

    @Override
    public void run() {
        while (step()) {

        }
    }

    @Override
    public int getAmount() {
        return current.getAmount();
    }

    public Time getTime() {
        return current;
    }
}
