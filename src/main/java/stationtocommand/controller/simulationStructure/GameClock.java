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

    private ScheduledExecutorService scheduler;

    public GameClock(long startTime) {
        this.simulationStartTime = startTime;
    }

    public void start() {
        if (!running) {
            lastRealTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void stop() {
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

}
