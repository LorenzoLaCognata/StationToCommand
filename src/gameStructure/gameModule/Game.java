package gameStructure.gameModule;

import civilianStructure.civilianModule.CivilianManager;
import departmentStructure.departmentModule.DepartmentManager;
import equipmentStructure.equipmentModule.EquipmentManager;
import locationStructure.locationModule.LocationManager;
import missionStructure.missionModule.MissionManager;
import responderStructure.responderModule.ResponderManager;
import taskStructure.taskModule.TaskManager;
import vehicleStructure.vehicleModule.VehicleManager;

public class Game {

	public DepartmentManager departmentManager;
	public VehicleManager vehicleManager;
	public EquipmentManager equipmentManager;
	public ResponderManager responderManager;
	public CivilianManager civilianManager;
	public LocationManager locationManager;
	public MissionManager missionManager;
	public TaskManager taskManager;

	public Game() {
		System.out.println("Game initializing");
		this.departmentManager = new DepartmentManager();
		this.locationManager = new LocationManager(this.departmentManager);
		this.vehicleManager = new VehicleManager(this.departmentManager);
		this.equipmentManager = new EquipmentManager(this.departmentManager);
		this.responderManager = new ResponderManager(this.departmentManager);
		this.civilianManager = new CivilianManager();
		this.missionManager = new MissionManager();
		this.taskManager = new TaskManager();
		System.out.println("Game initialized successfully");
	}

}