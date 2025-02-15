package stationtocommand.controller.simulationStructure;

public class ScheduledEvent implements Comparable<ScheduledEvent> {
    private final long eventTime;
    private final Runnable action;

    public ScheduledEvent(long eventTime, Runnable action) {
        this.eventTime = eventTime;
        this.action = action;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void execute() {
        action.run();
    }

    @Override
    public int compareTo(ScheduledEvent other) {
        return Long.compare(this.eventTime, other.eventTime);
    }
}