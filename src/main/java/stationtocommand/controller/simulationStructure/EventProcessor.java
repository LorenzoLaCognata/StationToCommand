package stationtocommand.controller.simulationStructure;

import javafx.scene.media.AudioClip;
import stationtocommand.controller.Controller;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.utilsStructure.Utils;
import stationtocommand.view.dispatchStructure.MissionDepartmentView;
import stationtocommand.view.dispatchStructure.MissionView;

import java.util.List;
import java.util.Objects;

public class EventProcessor {

    // TODO: should be 30s and not 300s?
    public final long DISPATCH_DEPARTMENT_DELAY = 300000;
    public final long DISPATCH_UNIT_DELAY = 15000;

    private final EventQueue eventQueue;
    private final Controller controller;

    private final AudioClip newMissionSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/sounds/newMission.mp3")).toString());
    private final AudioClip dispatchFireSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/sounds/dispatchFire.mp3")).toString());
    private final AudioClip dispatchPoliceSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/sounds/dispatchPolice.mp3")).toString());
    // TODO: change with dispatchMedic
    private final AudioClip dispatchMedicSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/sounds/dispatchMedic.mp3")).toString());

    public EventProcessor(EventQueue eventQueue, Controller controller) {
        this.eventQueue = eventQueue;
        this.controller = controller;
    }

    public void processEvent(ScheduledEvent event) {
        if (event == null) return;

        long nextEventTime;
        ScheduledEventType nextEventType;
        Object nextEventObject;

        switch (event.eventType()) {
            case MISSION_QUEUEING:
                int interval = Utils.randomGenerator.nextInt(0,86400000);
                nextEventTime = controller.getScheduler().getCurrentSimulationTime() + interval;
                nextEventType = ScheduledEventType.MISSION_GENERATION;
                eventQueue.scheduleEvent(nextEventTime, nextEventType, null);
                break;
            case MISSION_GENERATION:
                Mission missionGenerated = controller.getModel().getMissionManager().generateMission(controller.getModel().getLocationManager());
                controller.getView().getDispatchView().addMissionView(missionGenerated, controller.getView(), controller.getView().getUtilsView());
                newMissionSound.play();
                nextEventTime = event.eventTime() + DISPATCH_DEPARTMENT_DELAY;
                nextEventType = ScheduledEventType.MISSION_DISPATCH_DEPARTMENT;
                nextEventObject = missionGenerated;
                eventQueue.scheduleEvent(nextEventTime, nextEventType, nextEventObject);
                break;
            case MISSION_DISPATCH_DEPARTMENT:
                Mission missionToDispatchToDepartment = (Mission) event.eventObject();
                if (missionToDispatchToDepartment != null) {
                    List<DepartmentType> departmentTypes = controller.getModel().getMissionManager().requiredDepartmentTypes(missionToDispatchToDepartment.getMissionType());
                    controller.getModel().getMissionManager().dispatchMissionToDepartments(missionToDispatchToDepartment);
                    MissionView missionView = controller.getView().getDispatchView().getMissionView(missionToDispatchToDepartment);
                    for (MissionDepartmentLink sampleMissionDepartmentLink : missionToDispatchToDepartment.getDepartmentLinks()) {
                        missionView.addMissionDepartmentView(sampleMissionDepartmentLink, controller.getView(), controller.getView().getUtilsView());
                    }
                    for (DepartmentType departmentType : departmentTypes) {
                        switch(departmentType) {
                            case FIRE_DEPARTMENT -> dispatchFireSound.play();
                            case POLICE_DEPARTMENT -> dispatchPoliceSound.play();
                            case MEDIC_DEPARTMENT -> dispatchMedicSound.play();
                        }
                    }
                }
                nextEventTime = event.eventTime() + DISPATCH_UNIT_DELAY;
                nextEventType = ScheduledEventType.MISSION_DISPATCH_UNIT;
                nextEventObject = event.eventObject();
                eventQueue.scheduleEvent(nextEventTime, nextEventType, nextEventObject);
                break;
            case MISSION_DISPATCH_UNIT:
                Mission missionToDispatchToUnit = (Mission) event.eventObject();
                if (missionToDispatchToUnit != null) {
                    MissionView missionView = controller.getView().getDispatchView().getMissionView(missionToDispatchToUnit);
                    for (MissionDepartmentLink missionDepartmentLink : missionToDispatchToUnit.getDepartmentLinks()) {
                        controller.getModel().getMissionManager().dispatchMissionToUnit(missionDepartmentLink);
                        MissionDepartmentView missionDepartmentView = missionView.getMissionDepartmentView(missionDepartmentLink);

                        for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
                            missionDepartmentView.addMissionStationView(missionStationLink, controller.getView(), controller.getView().getUtilsView());
                        }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown event type: " + event.eventType());
        }

    }

}