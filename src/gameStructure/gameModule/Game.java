package gameStructure.gameModule;

import departmentStructure.departmentModule.Department;
import departmentStructure.departmentModule.DepartmentManager;
import departmentStructure.departmentModule.DepartmentType;
import locationStructure.locationModule.LocationManager;
import missionStructure.missionModule.Mission;
import missionStructure.missionModule.MissionManager;
import responderStructure.responderModule.ResponderManager;
import stationStructure.stationModule.Station;
import unitStructure.unitModule.Unit;
import unitStructure.unitTypeModule.FireUnitType;

public class Game {

	public final LocationManager locationManager;
	public final DepartmentManager departmentManager;
	public final ResponderManager responderManager;
	public final MissionManager missionManager;
/*
	public final VehicleManager vehicleManager;
	public final EquipmentManager equipmentManager;
	public final CivilianManager civilianManager;
	public final TaskManager taskManager;
 */

	public Game() {
		this.locationManager = new LocationManager();
		this.departmentManager = new DepartmentManager(this.locationManager);
		this.responderManager = new ResponderManager(this.departmentManager);
		this.missionManager = new MissionManager();
		// TODO: initialize
/*
		this.vehicleManager = new VehicleManager(this.departmentManager);
		this.equipmentManager = new EquipmentManager(this.departmentManager);
		this.civilianManager = new CivilianManager();
		this.taskManager = new TaskManager();
 */

		Mission sampleMission = missionManager.generateMission(departmentManager, locationManager);
		missionManager.addMission(sampleMission);

		Department department = departmentManager.getDepartment(DepartmentType.FIRE_DEPARTMENT);
		sampleMission.linkDepartment(department);

		Station station = department.getStationManager().getStation(1);
		sampleMission.linkStation(station);

		Unit unit = station.getUnitManager().getUnit(FireUnitType.FIRE_TRUCK);
		sampleMission.linkUnit(unit);

		System.out.println(missionManager.getMissions().getFirst() + " assigned to " + missionManager.getMissions().getFirst().getDepartmentLinks().getFirst().getStationLinks().getFirst().getUnitLinks().getFirst().getUnit());
	}

}