package gameStructure.gameModule;

import departmentStructure.departmentModule.Department;
import departmentStructure.departmentModule.DepartmentType;
import linkStructure.organizationLinkModule.DepartmentLink;
import missionLinkStructure.missionOrganizationModule.MissionDepartmentLink;
import missionLinkStructure.missionOrganizationModule.MissionStationLink;
import missionLinkStructure.missionOrganizationModule.MissionUnitLink;
import missionStructure.missionModule.Mission;
import missionStructure.missionModule.MissionType;
import personStructure.civilianModule.CivilianManager;
import departmentStructure.departmentModule.DepartmentManager;
import equipmentStructure.equipmentModule.EquipmentManager;
import locationStructure.locationModule.LocationManager;
import missionStructure.missionModule.MissionManager;
import responderStructure.responderModule.ResponderManager;
import stationStructure.stationModule.Station;
import taskStructure.taskModule.TaskManager;
import unitStructure.unitModule.Unit;
import unitStructure.unitTypeModule.FireUnitType;
import unitStructure.unitTypeModule.UnitType;
import vehicleStructure.vehicleModule.VehicleManager;

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

		Mission sampleMission = new Mission(MissionType.STRUCTURE_FIRE, locationManager.generateLocation());
		Department department = departmentManager.getDepartment(DepartmentType.FIRE_DEPARTMENT);
		MissionDepartmentLink missionDepartmentLink = sampleMission.linkDepartment(department);
		Station station = department.getStationManager().getStation(1);
		MissionStationLink missionStationLink = missionDepartmentLink.linkStation(station);
		Unit unit = station.getUnitManager().getUnit(FireUnitType.FIRE_TRUCK);
		MissionUnitLink missionUnitLink = missionStationLink.linkUnit(unit);
		missionManager.addMission(sampleMission);
		System.out.println(sampleMission + " assigned to " + sampleMission.getDepartmentLinks().getFirst().getStationLinks().getFirst().getUnitLinks().getFirst().getUnit());
	}

}