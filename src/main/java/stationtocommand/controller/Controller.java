package stationtocommand.controller;

import javafx.application.Platform;
import javafx.scene.control.Button;
import stationtocommand.controller.simulationStructure.GameClock;
import stationtocommand.model.Model;
import stationtocommand.model.utilsStructure.Utils;
import stationtocommand.view.View;

public class Controller {

    private final Model model;
    private final View view;
    private final GameClock clock = new GameClock(System.currentTimeMillis());

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        view.initialize(this);
        view.generateUI(model.departmentManager.getDepartments(), model.missionManager.getMissions());

        int lastMissionInterval = 0;
        for (int i=0; i<100; i++) {
            int newInterval = Utils.randomGenerator.nextInt(1000,10000);
            lastMissionInterval = lastMissionInterval + newInterval;
            clock.scheduleEvent(System.currentTimeMillis() + lastMissionInterval, () -> {
                model.missionManager.generateMission(model.departmentManager, model.locationManager);
                Platform.runLater(() -> {
                    Button dispatchButton = view.getDispatchButton();
                    if (view.getBreadCrumbBar().getSelectedCrumb() != null && view.getBreadCrumbBar().getSelectedCrumb().getValue().equals(dispatchButton.getText())) {
                        view.dispatchButtonHandler(dispatchButton, model.missionManager.getMissions());
                        view.getGridPane().requestLayout();
                    }
                });
                System.out.println("New mission generated on " + clock.getCurrentSimulationDateTime());
            });
        }

        clock.start();
    }

}
