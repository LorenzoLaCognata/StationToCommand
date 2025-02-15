package stationtocommand.model;

import stationtocommand.model.actionStructure.Action;
import stationtocommand.model.actionStructure.ActionManager;
import stationtocommand.model.actionStructure.ActionType;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentManager;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.equipmentStructure.Equipment;
import stationtocommand.model.equipmentStructure.EquipmentManager;
import stationtocommand.model.experienceStructure.ExperienceManager;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.locationStructure.LocationManager;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.missionStructure.MissionManager;
import stationtocommand.model.objectiveStructure.Objective;
import stationtocommand.model.objectiveStructure.ObjectiveType;
import stationtocommand.model.personStructure.Civilian;
import stationtocommand.model.personStructure.CivilianManager;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderManager;
import stationtocommand.model.shiftStructure.Shift;
import stationtocommand.model.skillStructure.Skill;
import stationtocommand.model.skillStructure.SkillManager;
import stationtocommand.model.skillStructure.SkillType;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationLink;
import stationtocommand.model.taskStructure.Task;
import stationtocommand.model.taskStructure.TaskManager;
import stationtocommand.model.trainingStructure.Training;
import stationtocommand.model.trainingStructure.TrainingManager;
import stationtocommand.model.trainingStructure.TrainingType;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitLink;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.utilsStructure.Utils;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleManager;
import stationtocommand.model.watchStructure.WatchManager;

import java.util.List;

public class Model {

	public final LocationManager locationManager;
	public final DepartmentManager departmentManager;
	public final WatchManager watchManager;
	public final ResponderManager responderManager;
	public final MissionManager missionManager;
	public final VehicleManager vehicleManager;
	public final EquipmentManager equipmentManager;
	public final CivilianManager civilianManager;
	public final ExperienceManager experienceManager;
	public final TrainingManager trainingManager;
	public final SkillManager skillManager;
	public final TaskManager taskManager;
	public final ActionManager actionManager;

	public Model() {
		locationManager = new LocationManager();
		watchManager = new WatchManager();
		departmentManager = new DepartmentManager(locationManager);
		responderManager = new ResponderManager(departmentManager);
		missionManager = new MissionManager();
		vehicleManager = new VehicleManager(departmentManager);
		equipmentManager = new EquipmentManager(departmentManager);
		civilianManager = new CivilianManager();
		experienceManager = new ExperienceManager();
		trainingManager = new TrainingManager(experienceManager);
		skillManager = new SkillManager(experienceManager, trainingManager);
		taskManager = new TaskManager();
		actionManager = new ActionManager();

		for (Department department : departmentManager.getDepartments()) {
			department.getShiftManager().initShifts(department, watchManager, responderManager);
		}

		for (Department department : departmentManager.getDepartments()) {
			System.out.println("\n" + department);

			for (Station station : department.getStations()) {
				System.out.println("\n\t\t- " + station);

				for (Unit unit : station.getUnits()) {
					System.out.println("\t\t\t\t- " + unit);

					for (Responder responder : responderManager.getResponders()) {
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

		Mission sampleMission = missionManager.generateMission(departmentManager, locationManager);
		for (int i=0; i<4; i++) {
			missionManager.generateMission(departmentManager, locationManager);
		}

		List<Task> sampleTasks = taskManager.generateTasks(sampleMission);

		for (Task sampleTask : sampleTasks) {
			System.out.println("\t- " + sampleTask);
		}

		Civilian civilian = new Civilian(sampleMission.getLocation());
		civilianManager.addCivilian(civilian);

		sampleMission.linkCivilian(civilian);

		System.out.println("\t- " + sampleMission.getCivilianLinks().getFirst().getCivilian());

		Department department = departmentManager.getDepartment(DepartmentType.FIRE_DEPARTMENT);
		sampleMission.linkDepartment(department);

		Station station = department.getStationManager().getStation(1);
		sampleMission.linkStation(station);

		sampleMission.linkUnit(station.getUnitManager().getUnits(FireUnitType.FIRE_ENGINE).getFirst());
		sampleMission.linkUnit(station.getUnitManager().getUnits(FireUnitType.FIRE_TRUCK).getFirst());

		sampleMission.linkObjective(new Objective(ObjectiveType.EVACUATE_CIVILIANS));

		System.out.println(sampleMission + " has objective " + sampleMission.getObjectiveLinks().getFirst().getObjective());

		List<Station> sampleMissionStations = sampleMission.getDepartmentLinks().stream()
				.flatMap(item -> item.getStationLinks().stream())
				.map(StationLink::getStation)
				.toList();

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

			Vehicle vehicle = vehicleManager.getVehicles(missionUnit).getFirst();
			sampleMission.linkVehicle(vehicle);
			System.out.println(sampleMission + " assigned to " + vehicle);
		}


		Training training = trainingManager.getTraining(TrainingType.FIRST_AID);
		responderManager.getPlayer().linkTraining(training);

		System.out.println(responderManager.getPlayer() + " completes " + responderManager.getPlayer().getTrainingLinks().getFirst().getTraining());

		Skill skill = skillManager.getSkill(SkillType.SELF_DEFENSE);
		responderManager.getPlayer().linkSkill(skill);

		System.out.println(responderManager.getPlayer() + " obtains " + responderManager.getPlayer().getSkillLinks().getFirst().getSkill());

		Action action = new Action(ActionType.SETUP_PERIMETER, responderManager.getPlayer(), null, null, null, null);
		actionManager.addAction(action);

		System.out.println(actionManager.getActions().getFirst().getResponderLink().getResponder() + " performs " + actionManager.getActions().getFirst());

	}

}