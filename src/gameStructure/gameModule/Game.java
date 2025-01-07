package gameStructure.gameModule;

import personStructure.civilianModule.CivilianManager;
import departmentStructure.departmentModule.DepartmentManager;
import equipmentStructure.equipmentModule.EquipmentManager;
import locationStructure.locationModule.LocationManager;
import missionStructure.missionModule.MissionManager;
import responderStructure.responderModule.ResponderManager;
import taskStructure.taskModule.TaskManager;
import vehicleStructure.vehicleModule.VehicleManager;

public class Game {

	public LocationManager locationManager;
	public DepartmentManager departmentManager;
	public ResponderManager responderManager;
	public VehicleManager vehicleManager;
	public EquipmentManager equipmentManager;
	public CivilianManager civilianManager;
	public MissionManager missionManager;
	public TaskManager taskManager;

	public Game() {
		this.locationManager = new LocationManager();
		this.departmentManager = new DepartmentManager(this.locationManager);
		this.responderManager = new ResponderManager(this.departmentManager);
		// TODO: initialize
/*
		this.vehicleManager = new VehicleManager(this.departmentManager);
		this.equipmentManager = new EquipmentManager(this.departmentManager);
		this.civilianManager = new CivilianManager();
		this.missionManager = new MissionManager();
		this.taskManager = new TaskManager();
 */
	}

}