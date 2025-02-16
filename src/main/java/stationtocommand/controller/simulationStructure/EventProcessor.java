package stationtocommand.controller.simulationStructure;

import javafx.application.Platform;
import javafx.scene.control.Button;
import stationtocommand.controller.Controller;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.utilsStructure.Utils;

public class EventProcessor {

    // TODO: final value should be 30s
    //public final int DISPATCH_DELAY = 30000;
    public final int DISPATCH_DELAY = 10000;

    private final EventQueue eventQueue;
    private final Controller controller;

    public EventProcessor(EventQueue eventQueue, Controller controller) {
        this.eventQueue = eventQueue;
        this.controller = controller;
    }

    public void processEvent(ScheduledEvent event) {
        if (event == null) return;

        if (event.eventObject() != null) {
            System.out.println(controller.getScheduler().getSimulationDateTime(event.eventTime()) + ": " + event.eventType() + " - " + event.eventObject());
        }
        else {
            System.out.println(controller.getScheduler().getSimulationDateTime(event.eventTime()) + ": " + event.eventType());
        }

        long nextEventTime = 0;
        ScheduledEventType nextEventType = null;
        Object nextEventObject = null;

        switch (event.eventType()) {
            case MISSION_QUEUEING:
                int interval = Utils.randomGenerator.nextInt(0,1000000);
                nextEventTime = System.currentTimeMillis() + interval;
                nextEventType = ScheduledEventType.MISSION_GENERATION;
                eventQueue.scheduleEvent(nextEventTime, nextEventType, null);
                break;
            case MISSION_GENERATION:
                Mission missionGenerated = controller.getModel().getMissionManager().generateMission(controller.getModel().getDepartmentManager(), controller.getModel().getLocationManager());
                Platform.runLater(() -> {
                    if (controller.getView().getBreadCrumbBar().getSelectedCrumb() != null && controller.getView().getBreadCrumbBar().getSelectedCrumb().getValue().equals(controller.getView().getDispatchButton().getText())) {
                        controller.getView().dispatchButtonHandler(controller.getView().getDispatchButton(), controller.getModel().getMissionManager().getMissions());
                        controller.getView().getGridPane().requestLayout();
                    }
                });
                nextEventTime = event.eventTime() + DISPATCH_DELAY;
                nextEventType = ScheduledEventType.MISSION_DISPATCH_DEPARTMENT;
                nextEventObject = missionGenerated;
                eventQueue.scheduleEvent(nextEventTime, nextEventType, nextEventObject);
                break;
            case MISSION_DISPATCH_DEPARTMENT:
                Mission missionDispatched = (Mission) event.eventObject();
                if (missionDispatched != null) {
                    controller.getModel().getMissionManager().dispatchMission(missionDispatched);
                    Platform.runLater(() -> {
                        if (controller.getView().getBreadCrumbBar().getSelectedCrumb() != null) {
                            if (controller.getView().getBreadCrumbBar().getSelectedCrumb().getValue().equals(controller.getView().getDispatchButton().getText())) {
                                controller.getView().dispatchButtonHandler(controller.getView().getDispatchButton(), controller.getModel().getMissionManager().getMissions());
                                controller.getView().getGridPane().requestLayout();
                            } else if (controller.getView().getBreadCrumbBar().getSelectedCrumb().getValue().equals(missionDispatched)) {
                                // TODO: trigger refresh of show sidebar etc. also when other pages are selected...
                                controller.getView().getGridPane().requestLayout();
                            }
                        }
                    });
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown event type: " + event.eventType());
        }

        if (nextEventTime != 0) {
            if (nextEventObject != null) {
                System.out.println("\tNext event => " + controller.getScheduler().getSimulationDateTime(nextEventTime) + ": " + nextEventType + " - " + nextEventObject);
            }
            else {
                System.out.println("\tNext event => " + controller.getScheduler().getSimulationDateTime(nextEventTime) + ": " + nextEventType);
            }
        }
    }

}