package stationtocommand.controller.simulationStructure;

import java.util.PriorityQueue;

public class EventQueue {

    private final PriorityQueue<ScheduledEvent> eventQueue;

    public EventQueue() {
        this.eventQueue = new PriorityQueue<>();
    }

    public PriorityQueue<ScheduledEvent> getEventQueue() {
        return eventQueue;
    }

    public void scheduleEvent(long eventTime, ScheduledEventType eventType, Object eventObject) {
        eventQueue.add(new ScheduledEvent(eventTime, eventType, eventObject));
    }

}