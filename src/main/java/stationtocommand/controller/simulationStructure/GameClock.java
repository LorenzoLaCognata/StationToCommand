package stationtocommand.controller.simulationStructure;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameClock {
    private final long simulationStartTime;
    private long lastRealTime;
    private long accumulatedSimTime;
    private double timeScale = 1.0;
    private boolean running = false;

    private final PriorityQueue<ScheduledEvent> eventQueue = new PriorityQueue<>();
    private ScheduledExecutorService scheduler;

    public GameClock(long startTime) {
        this.simulationStartTime = startTime;
    }

    public void start() {
        if (!running) {
            lastRealTime = System.currentTimeMillis();
            running = true;
            scheduleEventChecker();
        }
    }

    public void pause() {
        if (running) {
            updateSimulationTime();
            running = false;
            if (scheduler != null) scheduler.shutdown();
        }
    }

    public void setTimeScale(double newScale) {
        updateSimulationTime();
        this.timeScale = newScale;
    }

    public void scheduleEvent(long eventTime, Runnable eventAction) {
        eventQueue.add(new ScheduledEvent(eventTime, eventAction));
    }

    public long getCurrentSimulationTime() {
        updateSimulationTime();
        return simulationStartTime + accumulatedSimTime;
    }

    private void updateSimulationTime() {
        if (running) {
            long now = System.currentTimeMillis();
            accumulatedSimTime += (long) ((now - lastRealTime) * timeScale);
            lastRealTime = now;
        }
    }

    public LocalDateTime getCurrentSimulationDateTime() {
        long currentSimulationTime = getCurrentSimulationTime();
        Instant instant = Instant.ofEpochMilli(currentSimulationTime);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    private void scheduleEventChecker() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            long currentSimTime = getCurrentSimulationTime();
            while (!eventQueue.isEmpty() && eventQueue.peek().getEventTime() <= currentSimTime) {
                Objects.requireNonNull(eventQueue.poll()).execute();
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }
}
