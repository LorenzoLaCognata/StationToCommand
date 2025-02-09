package model;

import model.actionStructure.Action;
import model.actionStructure.ActionManager;
import model.actionStructure.ActionType;
import model.departmentStructure.Department;
import model.departmentStructure.DepartmentManager;
import model.departmentStructure.DepartmentType;
import model.equipmentStructure.Equipment;
import model.equipmentStructure.EquipmentManager;
import model.experienceStructure.ExperienceManager;
import model.locationStructure.LocationManager;
import model.missionStructure.Mission;
import model.missionStructure.MissionManager;
import model.objectiveStructure.Objective;
import model.objectiveStructure.ObjectiveType;
import model.personStructure.Civilian;
import model.personStructure.CivilianManager;
import model.responderStructure.Responder;
import model.responderStructure.ResponderManager;
import model.shiftStructure.Shift;
import model.skillStructure.Skill;
import model.skillStructure.SkillManager;
import model.skillStructure.SkillType;
import model.stationStructure.Station;
import model.stationStructure.StationLink;
import model.taskStructure.Task;
import model.taskStructure.TaskManager;
import model.trainingStructure.Training;
import model.trainingStructure.TrainingManager;
import model.trainingStructure.TrainingType;
import model.unitStructure.Unit;
import model.unitStructure.UnitLink;
import model.unitTypeStructure.FireUnitType;
import model.vehicleStructure.Vehicle;
import model.vehicleStructure.VehicleManager;
import model.watchStructure.WatchManager;

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
							System.out.println("\t\t\t\t\t\t- " + responder);
						}
					}

					for (Vehicle vehicle : vehicleManager.getVehicles()) {
						if (vehicle.getUnitLink().getUnit().equals(unit)) {
							System.out.println("\t\t\t\t\t\t- " + vehicle);
						}
					}

					for (Equipment equipment : equipmentManager.getEquipments()) {
						if (equipment.getUnitLink().getUnit().equals(unit)) {
							System.out.println("\t\t\t\t\t\t- " + equipment);
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

		System.out.println(missionManager.getMissions().getFirst() + " generated");

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