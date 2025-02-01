package model;

import model.actionStructure.actionModule.Action;
import model.actionStructure.actionModule.ActionManager;
import model.actionStructure.actionModule.ActionType;
import model.departmentStructure.departmentModule.Department;
import model.departmentStructure.departmentModule.DepartmentManager;
import model.departmentStructure.departmentModule.DepartmentType;
import model.equipmentStructure.equipmentModule.Equipment;
import model.equipmentStructure.equipmentModule.EquipmentManager;
import model.experienceStructure.experienceModule.ExperienceManager;
import model.locationStructure.locationModule.LocationManager;
import model.missionStructure.missionModule.Mission;
import model.missionStructure.missionModule.MissionManager;
import model.missionStructure.objectiveModule.Objective;
import model.missionStructure.objectiveModule.ObjectiveType;
import model.personStructure.civilianModule.Civilian;
import model.personStructure.civilianModule.CivilianManager;
import model.responderStructure.responderModule.Responder;
import model.responderStructure.responderModule.ResponderManager;
import model.shiftStructure.shiftModule.Shift;
import model.shiftStructure.watchModule.WatchManager;
import model.skillStructure.skillModule.Skill;
import model.skillStructure.skillModule.SkillManager;
import model.skillStructure.skillModule.SkillType;
import model.stationStructure.stationModule.Station;
import model.taskStructure.taskModule.Task;
import model.taskStructure.taskModule.TaskManager;
import model.trainingStructure.trainingModule.Training;
import model.trainingStructure.trainingModule.TrainingManager;
import model.trainingStructure.trainingModule.TrainingType;
import model.unitStructure.unitModule.Unit;
import model.unitStructure.unitTypeModule.FireUnitType;
import model.vehicleStructure.vehicleModule.Vehicle;
import model.vehicleStructure.vehicleModule.VehicleManager;

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

		Unit unit = station.getUnitManager().getUnits(FireUnitType.FIRE_TRUCK).getFirst();
		sampleMission.linkUnit(unit);

		sampleMission.linkObjective(new Objective(ObjectiveType.EVACUATE_CIVILIANS));

		System.out.println(missionManager.getMissions().getFirst() + " has objective " + missionManager.getMissions().getFirst().getObjectiveLinks().getFirst().getObjective());

		System.out.println(missionManager.getMissions().getFirst() + " assigned to " + missionManager.getMissions().getFirst().getDepartmentLinks().getFirst().getStationLinks().getFirst().getUnitLinks().getFirst().getUnit());

		Responder responder = responderManager.getResponders(unit).getFirst();
		sampleMission.linkResponder(responder);

		System.out.println(missionManager.getMissions().getFirst() + " assigned to " + missionManager.getMissions().getFirst().getDepartmentLinks().getFirst().getStationLinks().getFirst().getUnitLinks().getFirst().getResponderLinks().getFirst().getResponder());

		sampleTasks.getFirst().linkResponder(responder);

		System.out.println(sampleTasks.getFirst() + " assigned to " + responder);

		Vehicle vehicle = vehicleManager.getVehicles(unit).getFirst();
		sampleMission.linkVehicle(vehicle);

		System.out.println(missionManager.getMissions().getFirst() + " assigned to " + missionManager.getMissions().getFirst().getDepartmentLinks().getFirst().getStationLinks().getFirst().getUnitLinks().getFirst().getVehicleLinks().getFirst().getVehicle());

		Training training = trainingManager.getTraining(TrainingType.FIRST_AID);
		responderManager.getPlayer().linkTraining(training);

		System.out.println(responderManager.getPlayer() + " completes " + responderManager.getPlayer().getTrainingLinks().getFirst().getTraining());

		Skill skill = skillManager.getSkill(SkillType.SELF_DEFENSE);
		responderManager.getPlayer().linkSkill(skill);

		System.out.println(responderManager.getPlayer() + " obtains " + responderManager.getPlayer().getSkillLinks().getFirst().getSkill());

		Action action = new Action(ActionType.SETUP_PERIMETER, responderManager.getPlayer(),null,null,null,null);
		actionManager.addAction(action);

		System.out.println(actionManager.getActions().getFirst().getResponderLink().getResponder() + " performs " + actionManager.getActions().getFirst());

	}

}