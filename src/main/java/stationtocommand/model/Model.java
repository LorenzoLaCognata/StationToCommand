package stationtocommand.model;

import stationtocommand.model.actionStructure.ActionManager;
import stationtocommand.model.departmentStructure.DepartmentManager;
import stationtocommand.model.equipmentStructure.EquipmentManager;
import stationtocommand.model.experienceStructure.ExperienceManager;
import stationtocommand.model.locationStructure.LocationManager;
import stationtocommand.model.missionStructure.MissionManager;
import stationtocommand.model.personStructure.CivilianManager;
import stationtocommand.model.responderStructure.ResponderManager;
import stationtocommand.model.skillStructure.SkillManager;
import stationtocommand.model.taskStructure.TaskManager;
import stationtocommand.model.trainingStructure.TrainingManager;
import stationtocommand.model.vehicleStructure.VehicleManager;
import stationtocommand.model.watchStructure.WatchManager;

public class Model {

	private final LocationManager locationManager;
	private final DepartmentManager departmentManager;
	private final WatchManager watchManager;
	private final ResponderManager responderManager;
	private final MissionManager missionManager;
	private final VehicleManager vehicleManager;
	private final EquipmentManager equipmentManager;
	private final CivilianManager civilianManager;
	private final ExperienceManager experienceManager;
	private final TrainingManager trainingManager;
	private final SkillManager skillManager;
	private final TaskManager taskManager;
	private final ActionManager actionManager;

	public Model() {
		locationManager = new LocationManager();
		watchManager = new WatchManager();
		departmentManager = new DepartmentManager();
		responderManager = new ResponderManager(departmentManager);
		missionManager = new MissionManager(departmentManager);
		vehicleManager = new VehicleManager(departmentManager);
		equipmentManager = new EquipmentManager(departmentManager);
		civilianManager = new CivilianManager();
		experienceManager = new ExperienceManager();
		trainingManager = new TrainingManager(experienceManager);
		skillManager = new SkillManager(experienceManager, trainingManager);
		taskManager = new TaskManager();
		actionManager = new ActionManager();
	}

	public LocationManager getLocationManager() {
		return locationManager;
	}

	public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}

	public WatchManager getWatchManager() {
		return watchManager;
	}

	public ResponderManager getResponderManager() {
		return responderManager;
	}

	public MissionManager getMissionManager() {
		return missionManager;
	}

	public VehicleManager getVehicleManager() {
		return vehicleManager;
	}

	public EquipmentManager getEquipmentManager() {
		return equipmentManager;
	}

	public CivilianManager getCivilianManager() {
		return civilianManager;
	}

	public ExperienceManager getExperienceManager() {
		return experienceManager;
	}

	public TrainingManager getTrainingManager() {
		return trainingManager;
	}

	public SkillManager getSkillManager() {
		return skillManager;
	}

	public TaskManager getTaskManager() {
		return taskManager;
	}

	public ActionManager getActionManager() {
		return actionManager;
	}
}