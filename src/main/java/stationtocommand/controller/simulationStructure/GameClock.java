package stationtocommand.controller.simulationStructure;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ScheduledExecutorService;

public class GameClock {
    private final long simulationStartTime;
    private long lastRealTime;
    private long accumulatedSimTime;
    private double timeScale = 30.0;
    private boolean running = false;

    public GameClock(long startTime) {
        this.simulationStartTime = startTime;
    }

    public void startGameClock() {
        if (!running) {
            lastRealTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void stopGameClock(ScheduledExecutorService schedulerService) {
        if (running) {
            updateSimulationTime();
            running = false;
            if (schedulerService != null) schedulerService.shutdown();
        }
    }

    public double getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(double newScale) {
        updateSimulationTime();
        this.timeScale = newScale;
    }

    public long getCurrentSimulationTime() {
        updateSimulationTime();
        return simulationStartTime + accumulatedSimTime;
    }

    public void updateSimulationTime() {
        if (running) {
            long now = System.currentTimeMillis();
            accumulatedSimTime += (long) ((now - lastRealTime) * timeScale);
            lastRealTime = now;
        }
    }

    public LocalDateTime getSimulationDateTime(long currentSimulationTime) {
        Instant instant = Instant.ofEpochMilli(currentSimulationTime);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
