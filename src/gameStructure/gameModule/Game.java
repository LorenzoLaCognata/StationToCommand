package gameStructure.gameModule;

import departmentStructure.departmentModule.Department;
import departmentStructure.departmentModule.DepartmentManager;
import departmentStructure.departmentModule.DepartmentType;
import equipmentStructure.equipmentModule.Equipment;
import equipmentStructure.equipmentModule.EquipmentManager;
import experienceStructure.experienceModule.ExperienceManager;
import locationStructure.locationModule.LocationManager;
import missionStructure.missionModule.Mission;
import missionStructure.missionModule.MissionManager;
import personStructure.civilianModule.Civilian;
import personStructure.civilianModule.CivilianManager;
import responderStructure.responderModule.Responder;
import responderStructure.responderModule.ResponderManager;
import shiftStructure.shiftModule.Shift;
import shiftStructure.watchModule.WatchManager;
import skillStructure.skillModule.Skill;
import skillStructure.skillModule.SkillManager;
import skillStructure.skillModule.SkillType;
import stationStructure.stationModule.Station;
import taskStructure.taskModule.Task;
import taskStructure.taskModule.TaskManager;
import trainingStructure.trainingModule.Training;
import trainingStructure.trainingModule.TrainingManager;
import trainingStructure.trainingModule.TrainingType;
import unitStructure.unitModule.Unit;
import unitStructure.unitTypeModule.FireUnitType;
import vehicleStructure.vehicleModule.Vehicle;
import vehicleStructure.vehicleModule.VehicleManager;

import java.util.List;

public class Game {

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

	public Game() {
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
		missionManager.addMission(sampleMission);

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
		training.linkResponder(responderManager.getPlayer());

		System.out.println(training.getResponderLinks().getFirst().getResponder() + " completes " + training);

		Skill skill = skillManager.getSkill(SkillType.SELF_DEFENSE);
		skill.linkResponder(responderManager.getPlayer());

		System.out.println(skill.getResponderLinks().getFirst().getResponder() + " obtains " + skill);

	}

}