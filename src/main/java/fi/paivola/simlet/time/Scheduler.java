package fi.paivola.simlet.time;

import fi.paivola.simlet.model.Model;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by juhani on 5/14/14.
 */
public class Scheduler implements Runnable, TimeInterface {
    private final TreeMap<TimeInterface, List<ScheduleItem>> schedule;
    private final Map<TimeInterface, List<ScheduleItem>> buffer;
    private final ExecutorService executorService;
    private final Time current;
    private final Time max;

    public Scheduler(Time max) {
        this.schedule = new TreeMap<>();
        this.buffer = new HashMap<>();
        this.executorService = Executors.newFixedThreadPool(2);
        this.current = new Time(0);
        this.max = max;
    }

    public synchronized void after(TimeInterface time, ScheduleItem scheduleItem) {
        schedule(current.after(time), scheduleItem);
    }

    public synchronized void every(TimeInterface time, ScheduleItem scheduleItem) {
        schedule(current.after(time), sc -> {
            scheduleItem.call(sc);
            sc.every(time, scheduleItem);
        });
    }

    public synchronized void schedule(TimeInterface time, ScheduleItem scheduleItem) {
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
        // System.out.println(current);
        if (current.getAmount() > max.getAmount()) {
            return false;
        }
        for (ScheduleItem si : schedule.firstEntry().getValue()) {
            si.call(this);
        }
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
