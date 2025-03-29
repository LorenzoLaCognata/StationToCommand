package stationtocommand.controller.simulationStructure;

import javafx.application.Platform;
import javafx.scene.control.Label;
import stationtocommand.controller.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
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

    public void scheduleEventChecker(Label gameClockLabel, DateTimeFormatter dateFormat) {
        schedulerService.scheduleAtFixedRate(() -> {
            long currentSimTime = gameClock.getCurrentSimulationTime();
            Platform.runLater(() -> {
                gameClockLabel.setText(getSimulationDateTime(gameClock.getCurrentSimulationTime()).format(dateFormat));
                gameClockLabel.requestLayout();
            });
            while (!eventQueue.getEventQueue().isEmpty() && Objects.requireNonNull(eventQueue.getEventQueue().peek()).eventTime() <= currentSimTime) {
                ScheduledEvent event = eventQueue.getEventQueue().poll();
                if (event != null) {eventProcessor.processEvent(event);}
            }
        }, 0, 750, TimeUnit.MILLISECONDS);
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

    public double getTimeScale() {
        return gameClock.getTimeScale();
    }

    public void setTimeScale(double newScale) {
        gameClock.setTimeScale(newScale);
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