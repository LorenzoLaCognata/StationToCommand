package stationtocommand.controller.simulationStructure;

public record ScheduledEvent(long eventTime, ScheduledEventType eventType, Object eventObject) implements Comparable<ScheduledEvent> {

    @Override
    public int compareTo(ScheduledEvent other) {
        return Long.compare(this.eventTime, other.eventTime);
    }

}