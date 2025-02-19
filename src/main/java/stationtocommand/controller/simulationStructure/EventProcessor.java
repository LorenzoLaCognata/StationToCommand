package stationtocommand.controller.simulationStructure;

import stationtocommand.controller.Controller;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.utilsStructure.Utils;

public class EventProcessor {

    // TODO: final value should be 30s
    public final int DISPATCH_DEPARTMENT_DELAY = 10000;
    public final int DISPATCH_STATION_DELAY = 2000;
    public final int DISPATCH_UNIT_DELAY = 1000;

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
                int interval = Utils.randomGenerator.nextInt(0,86400000);
                nextEventTime = System.currentTimeMillis() + interval;
                nextEventType = ScheduledEventType.MISSION_GENERATION;
                eventQueue.scheduleEvent(nextEventTime, nextEventType, null);
                break;
            case MISSION_GENERATION:
                Mission missionGenerated = controller.getModel().getMissionManager().generateMission(controller.getModel().getLocationManager());
                nextEventTime = event.eventTime() + DISPATCH_DEPARTMENT_DELAY;
                nextEventType = ScheduledEventType.MISSION_DISPATCH_DEPARTMENT;
                nextEventObject = missionGenerated;
                eventQueue.scheduleEvent(nextEventTime, nextEventType, nextEventObject);
                break;
            case MISSION_DISPATCH_DEPARTMENT:
                Mission missionDispatchedDepartment = (Mission) event.eventObject();
                if (missionDispatchedDepartment != null) {
                    controller.getModel().getMissionManager().dispatchMissionToDepartment(missionDispatchedDepartment);
                }
                nextEventTime = event.eventTime() + DISPATCH_STATION_DELAY;
                nextEventType = ScheduledEventType.MISSION_DISPATCH_STATION;
                nextEventObject = event.eventObject();
                eventQueue.scheduleEvent(nextEventTime, nextEventType, nextEventObject);
                break;
            case MISSION_DISPATCH_STATION:
                Mission missionDispatchedStation = (Mission) event.eventObject();
                if (missionDispatchedStation != null) {
                    for (MissionDepartmentLink missionDepartmentLink : missionDispatchedStation.getDepartmentLinks()) {
                        controller.getModel().getMissionManager().dispatchMissionToStation(missionDepartmentLink);
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
                        for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
                            controller.getModel().getMissionManager().dispatchMissionToUnit(missionStationLink);
                        }
                    }
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