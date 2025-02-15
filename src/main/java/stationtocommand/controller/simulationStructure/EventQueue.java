package stationtocommand.controller.simulationStructure;

import java.util.PriorityQueue;

public class EventQueue {

    private final PriorityQueue<ScheduledEvent> queue;

    public EventQueue() {
        this.queue = new PriorityQueue<>();
    }

    public PriorityQueue<ScheduledEvent> getQueue() {
        return queue;
    }

    public void scheduleEvent(long eventTime, Runnable eventAction) {
        queue.add(new ScheduledEvent(eventTime, eventAction));
    }

}