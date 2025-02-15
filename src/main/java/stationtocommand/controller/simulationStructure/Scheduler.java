package stationtocommand.controller.simulationStructure;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private final GameClock gameClock;
    private final EventQueue eventQueue;
    private final ScheduledExecutorService schedulerService;

    public Scheduler(GameClock gameClock, EventQueue eventQueue) {
        this.gameClock = gameClock;
        this.eventQueue = eventQueue;
        this.schedulerService = Executors.newSingleThreadScheduledExecutor();
    }

    public ScheduledExecutorService getSchedulerService() {
        return schedulerService;
    }

    public void scheduleEventChecker() {
        schedulerService.scheduleAtFixedRate(() -> {
            long currentSimTime = gameClock.getCurrentSimulationTime();
            while (!eventQueue.getQueue().isEmpty() && eventQueue.getQueue().peek().getEventTime() <= currentSimTime) {
                Objects.requireNonNull(eventQueue.getQueue().poll()).execute();
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }
}
