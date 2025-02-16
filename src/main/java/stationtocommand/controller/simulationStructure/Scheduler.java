package stationtocommand.controller.simulationStructure;

import stationtocommand.controller.Controller;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private final GameClock gameClock;
    private final EventQueue eventQueue;
    private final ScheduledExecutorService schedulerService;
    private final EventProcessor eventProcessor;

    public Scheduler(Controller controller) {
        this.gameClock = new GameClock(System.currentTimeMillis());
        this.eventQueue = new EventQueue();
        this.schedulerService = Executors.newSingleThreadScheduledExecutor();
        this.eventProcessor = new EventProcessor(eventQueue, controller);
    }

    public ScheduledExecutorService getSchedulerService() {
        return schedulerService;
    }

    public void scheduleEventChecker() {
        schedulerService.scheduleAtFixedRate(() -> {
            long currentSimTime = gameClock.getCurrentSimulationTime();
            while (!eventQueue.getEventQueue().isEmpty() && eventQueue.getEventQueue().peek().eventTime() <= currentSimTime) {
                ScheduledEvent event = eventQueue.getEventQueue().poll();
                if (event != null) {eventProcessor.processEvent(event);}
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    public void scheduleEvent(long eventTime, ScheduledEventType eventType, Object eventObject) {
        eventQueue.scheduleEvent(eventTime, eventType, eventObject);
    }

    public void startGameClock() {
        gameClock.startGameClock();
    }

    public void stopGameClock() {
        gameClock.stopGameClock(schedulerService);
    }

    public long getCurrentSimulationTime() {
        return gameClock.getCurrentSimulationTime();
    }

    private void updateSimulationTime() {
        gameClock.updateSimulationTime();
    }

    public LocalDateTime getSimulationDateTime(long currentSimulationTime) {
        return gameClock.getSimulationDateTime(currentSimulationTime);
    }

}