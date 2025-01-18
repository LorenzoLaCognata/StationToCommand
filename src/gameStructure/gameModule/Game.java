package gameStructure.gameModule;

import departmentStructure.departmentModule.Department;
import departmentStructure.departmentModule.DepartmentManager;
import departmentStructure.departmentModule.DepartmentType;
import equipmentStructure.equipmentModule.Equipment;
import equipmentStructure.equipmentModule.EquipmentManager;
import locationStructure.locationModule.LocationManager;
import missionStructure.missionModule.Mission;
import missionStructure.missionModule.MissionManager;
import responderStructure.responderModule.Responder;
import responderStructure.responderModule.ResponderManager;
import stationStructure.stationModule.Station;
import taskStructure.taskModule.Task;
import taskStructure.taskModule.TaskManager;
import unitStructure.unitModule.Unit;
import unitStructure.unitTypeModule.FireUnitType;
import vehicleStructure.vehicleModule.Vehicle;
import vehicleStructure.vehicleModule.VehicleManager;

import java.util.List;

public class Game {

	public final LocationManager locationManager;
	public final DepartmentManager departmentManager;
	public final ResponderManager responderManager;
	public final MissionManager missionManager;
	public final VehicleManager vehicleManager;
	public final EquipmentManager equipmentManager;
	public final TaskManager taskManager;
/*
	public final CivilianManager civilianManager;
 */

	public Game() {
		this.locationManager = new LocationManager();
		this.departmentManager = new DepartmentManager(this.locationManager);
		this.responderManager = new ResponderManager(this.departmentManager);
		this.missionManager = new MissionManager();
		this.vehicleManager = new VehicleManager(this.departmentManager);
		this.equipmentManager = new EquipmentManager(this.departmentManager);
		this.taskManager = new TaskManager();
/*
		// TODO: initialize
		this.civilianManager = new CivilianManager();
 */

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

				}
			}
		}

		Mission sampleMission = missionManager.generateMission(departmentManager, locationManager);
		missionManager.addMission(sampleMission);

		System.out.println(missionManager.getMissions().getFirst() + " generated");

		List<Task> sampleTasks = taskManager.generateTasks(sampleMission);

		for (Task sampleTask : sampleTasks) {
			System.out.println("Mission task: " + sampleTask);
		}

		Department department = departmentManager.getDepartment(DepartmentType.FIRE_DEPARTMENT);
		sampleMission.linkDepartment(department);

		Station station = department.getStationManager().getStation(1);
		sampleMission.linkStation(station);

		Unit unit = station.getUnitManager().getUnit(FireUnitType.FIRE_TRUCK);
		sampleMission.linkUnit(unit);

		System.out.println(missionManager.getMissions().getFirst() + " assigned to " + missionManager.getMissions().getFirst().getDepartmentLinks().getFirst().getStationLinks().getFirst().getUnitLinks().getFirst().getUnit());

		Responder responder = responderManager.getResponder(unit);
		sampleMission.linkResponder(responder);

		System.out.println(missionManager.getMissions().getFirst() + " assigned to " + missionManager.getMissions().getFirst().getDepartmentLinks().getFirst().getStationLinks().getFirst().getUnitLinks().getFirst().getResponderLinks().getFirst().getResponder());

		sampleTasks.getFirst().linkResponder(responder);

		System.out.println(sampleTasks.getFirst() + " assigned to " + responder);

		Vehicle vehicle = vehicleManager.getVehicle(unit);
		sampleMission.linkVehicle(vehicle);

		System.out.println(missionManager.getMissions().getFirst() + " assigned to " + missionManager.getMissions().getFirst().getDepartmentLinks().getFirst().getStationLinks().getFirst().getUnitLinks().getFirst().getVehicleLinks().getFirst().getVehicle());

	}

}