package stationtocommand.controller;

import javafx.application.Platform;
import javafx.scene.control.Button;
import stationtocommand.controller.simulationStructure.EventQueue;
import stationtocommand.controller.simulationStructure.GameClock;
import stationtocommand.controller.simulationStructure.Scheduler;
import stationtocommand.model.Model;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.utilsStructure.Utils;
import stationtocommand.view.View;

public class Controller {

    public final int DISPATCH_DELAY = 30000;

    private final Model model;
    private final View view;
    private final GameClock gameClock = new GameClock(System.currentTimeMillis());
    private final EventQueue eventQueue = new EventQueue();
    private final Scheduler scheduler = new Scheduler(gameClock, eventQueue);

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        view.initialize(this);
        view.generateUI(model.departmentManager.getDepartments(), model.missionManager.getMissions());
        scheduler.scheduleEventChecker();

        int lastMissionInterval = 0;
        for (int i=0; i<100; i++) {
            int newInterval = Utils.randomGenerator.nextInt(1000,10000);
            lastMissionInterval = lastMissionInterval + newInterval;
            long eventTime = System.currentTimeMillis() + lastMissionInterval;
            eventQueue.scheduleEvent(eventTime, () -> {
                Mission mission = model.missionManager.generateMission(model.departmentManager, model.locationManager);
                Platform.runLater(() -> {
                    Button dispatchButton = view.getDispatchButton();
                    if (view.getBreadCrumbBar().getSelectedCrumb() != null && view.getBreadCrumbBar().getSelectedCrumb().getValue().equals(dispatchButton.getText())) {
                        view.dispatchButtonHandler(dispatchButton, model.missionManager.getMissions());
                        view.getGridPane().requestLayout();
                    }
                });
                System.out.println("New mission " + mission + " generated on " + gameClock.getCurrentSimulationDateTime());

                eventQueue.scheduleEvent(eventTime + DISPATCH_DELAY, () -> {
                    model.missionManager.dispatchMission(mission);
                    System.out.println("Mission " + mission + " dispatched on " + gameClock.getCurrentSimulationDateTime());
                });

            });
        }

        gameClock.start();
    }

    public GameClock getGameClock() {
        return gameClock;
    }
}
