package stationtocommand.controller;

import stationtocommand.controller.simulationStructure.ScheduledEventType;
import stationtocommand.controller.simulationStructure.Scheduler;
import stationtocommand.model.Model;
import stationtocommand.model.actionStructure.Action;
import stationtocommand.model.actionStructure.ActionType;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.locationStructure.LocationManager;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.objectiveStructure.Objective;
import stationtocommand.model.objectiveStructure.ObjectiveType;
import stationtocommand.model.personStructure.Civilian;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.shiftStructure.Shift;
import stationtocommand.model.skillStructure.Skill;
import stationtocommand.model.skillStructure.SkillType;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.taskStructure.Task;
import stationtocommand.model.trainingStructure.Training;
import stationtocommand.model.trainingStructure.TrainingType;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitLink;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.utilsStructure.Utils;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.View;

import java.util.List;

public class Controller {

    private final Model model;
    private final View view;
    private final Scheduler scheduler = new Scheduler(this);

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        view.initialize(this);
        view.generateUI(model.getDepartmentManager().getDepartments(), model.getMissionManager().getMissions());
        scheduler.scheduleEventChecker();
        modelSampleInitialization();
        missionGenerator();
    }

    public Model getModel() {
        return model;
    }

    public View getView() {
        return view;
    }

    public void modelSampleInitialization() {

        for (Department department : model.getDepartmentManager().getDepartments()) {
            department.getShiftManager().initShifts(department, model.getWatchManager(), model.getResponderManager());
        }

        for (Department department : model.getDepartmentManager().getDepartments()) {
            System.out.println("\n" + department);

            for (Station station : department.getStations()) {
                System.out.println("\n\t\t- " + station);

                for (Unit unit : station.getUnits()) {
                    System.out.println("\t\t\t\t- " + unit);

                    for (Responder responder : model.getResponderManager().getResponders()) {
                        if (responder.getUnitLink().getUnit().equals(unit)) {
                            // TODO: temporary random movement to visualize responders differently on map
                            float randomLatitude = Utils.randomGenerator.nextFloat(LocationManager.MIN_LATITUDE, LocationManager.MAX_LATITUDE);
                            float randomLongitude = Utils.randomGenerator.nextFloat(LocationManager.MIN_LONGITUDE, LocationManager.MAX_LONGITUDE);
                            Location location  = new Location(randomLatitude, randomLongitude);
                            responder.setLocation(location);
                            System.out.println("\t\t\t\t\t\t- " + responder);
                        }
                    }

                    for (Shift shift : department.getShiftManager().getShifts(unit)) {
                        System.out.println("\t\t\t\t\t\t- " + shift);
                    }

                }
            }
        }

        Mission sampleMission = model.getMissionManager().generateMission(model.getDepartmentManager(), model.getLocationManager());

        List<Task> sampleTasks = model.getTaskManager().generateTasks(sampleMission);

        for (Task sampleTask : sampleTasks) {
            System.out.println("\t- " + sampleTask);
        }

        Civilian civilian = new Civilian(sampleMission.getLocation());
        model.getCivilianManager().addCivilian(civilian);

        sampleMission.linkCivilian(civilian);

        System.out.println("\t- " + sampleMission.getCivilianLinks().getFirst().getCivilian());

        Department department = model.getDepartmentManager().getDepartment(DepartmentType.FIRE_DEPARTMENT);
        sampleMission.linkDepartment(department);

        Station station = department.getStationManager().getStation(1);
        sampleMission.linkStation(station);

        sampleMission.linkUnit(station.getUnitManager().getUnits(FireUnitType.FIRE_ENGINE).getFirst());
        sampleMission.linkUnit(station.getUnitManager().getUnits(FireUnitType.FIRE_TRUCK).getFirst());

        sampleMission.linkObjective(new Objective(ObjectiveType.EVACUATE_CIVILIANS));

        System.out.println(sampleMission + " has objective " + sampleMission.getObjectiveLinks().getFirst().getObjective());

        List<Unit> sampleMissionUnits = sampleMission.getDepartmentLinks().stream()
                .flatMap(item -> item.getStationLinks().stream())
                .flatMap(item -> item.getUnitLinks().stream())
                .map(UnitLink::getUnit)
                .toList();

        for (Unit missionUnit : sampleMissionUnits) {

            for (Responder responder : missionUnit.getResponders()) {
                sampleMission.linkResponder(responder);
                System.out.println(sampleMission + " assigned to " + responder);

                sampleTasks.getFirst().linkResponder(responder);
                System.out.println(sampleTasks.getFirst() + " assigned to " + responder);
            }

            Vehicle vehicle = model.getVehicleManager().getVehicles(missionUnit).getFirst();
            sampleMission.linkVehicle(vehicle);
            System.out.println(sampleMission + " assigned to " + vehicle);
        }


        Training training = model.getTrainingManager().getTraining(TrainingType.FIRST_AID);
        model.getResponderManager().getPlayer().linkTraining(training);

        System.out.println(model.getResponderManager().getPlayer() + " completes " + model.getResponderManager().getPlayer().getTrainingLinks().getFirst().getTraining());

        Skill skill = model.getSkillManager().getSkill(SkillType.SELF_DEFENSE);
        model.getResponderManager().getPlayer().linkSkill(skill);

        System.out.println(model.getResponderManager().getPlayer() + " obtains " + model.getResponderManager().getPlayer().getSkillLinks().getFirst().getSkill());

        Action action = new Action(ActionType.SETUP_PERIMETER, model.getResponderManager().getPlayer(), null, null, null, null);
        model.getActionManager().addAction(action);

        System.out.println(model.getActionManager().getActions().getFirst().getResponderLink().getResponder() + " performs " + model.getActionManager().getActions().getFirst());

    }

    private void missionGenerator() {
        for (int i=0; i<100; i++) {
            scheduler.scheduleEvent(System.currentTimeMillis(), ScheduledEventType.MISSION_QUEUEING, null);
        }
        scheduler.startGameClock();
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

}