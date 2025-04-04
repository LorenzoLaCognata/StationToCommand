package stationtocommand.controller.simulationStructure;

import javafx.scene.media.AudioClip;
import stationtocommand.controller.Controller;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.utilsStructure.Utils;

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

        if (event.eventObject() != null) {
            System.out.println(controller.getScheduler().getSimulationDateTime(event.eventTime()) + ": " + event.eventType() + " - " + event.eventObject());
        }
        else {
            System.out.println(controller.getScheduler().getSimulationDateTime(event.eventTime()) + ": " + event.eventType());
        }

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
                newMissionSound.play();
                nextEventTime = event.eventTime() + DISPATCH_DEPARTMENT_DELAY;
                nextEventType = ScheduledEventType.MISSION_DISPATCH_DEPARTMENT;
                nextEventObject = missionGenerated;
                eventQueue.scheduleEvent(nextEventTime, nextEventType, nextEventObject);
                break;
            case MISSION_DISPATCH_DEPARTMENT:
                Mission missionDispatchedDepartment = (Mission) event.eventObject();
                if (missionDispatchedDepartment != null) {
                    List<DepartmentType> departmentTypes = controller.getModel().getMissionManager().requiredDepartmentTypes(missionDispatchedDepartment.getMissionType());
                    controller.getModel().getMissionManager().dispatchMissionToDepartment(missionDispatchedDepartment);
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
                Mission missionDispatchedUnit = (Mission) event.eventObject();
                if (missionDispatchedUnit != null) {
                    for (MissionDepartmentLink missionDepartmentLink : missionDispatchedUnit.getDepartmentLinks()) {
                        controller.getModel().getMissionManager().dispatchMissionToUnit(missionDepartmentLink);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown event type: " + event.eventType());
        }

    }

}