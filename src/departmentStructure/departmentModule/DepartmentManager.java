package departmentStructure.departmentModule;

import locationStructure.locationModule.LocationManager;
import rankStructure.rankModule.RankManager;
import shiftStructure.shiftModule.ShiftManager;
import trainingStructure.trainingModule.TrainingManager;

import java.util.ArrayList;
import java.util.List;

public class DepartmentManager {

	private final List<Department> departments = new ArrayList<>();
	private final ShiftManager shiftManager;
	private final TrainingManager trainingManager;
	private final RankManager rankManager;

	public DepartmentManager(LocationManager locationManager) {
		System.out.println("DepartmentManager initializing");
		initDepartments(locationManager);
		this.shiftManager = new ShiftManager();
		// TODO: init Shifts
		this.trainingManager = new TrainingManager();
		// TODO: init Trainings
		this.rankManager = new RankManager();
		// TODO: init Ranks
		System.out.println("DepartmentManager initialized successfully");
	}

	@Override
	public String toString() {
		return departments.toString();
	}

	public Department getDepartment(DepartmentType departmentType) {
		return departments.stream()
				.filter(item -> item.getDepartmentType().equals(departmentType))
				.findFirst()
				.orElse(null);
	}

	public void addDepartment(Department department) {
		this.departments.add(department);
	}

	public void initDepartments(LocationManager locationManager) {
		addDepartment(new Department(DepartmentType.FIRE_DEPARTMENT, locationManager));
		addDepartment(new Department(DepartmentType.POLICE_DEPARTMENT, locationManager));
		addDepartment(new Department(DepartmentType.MEDIC_DEPARTMENT, locationManager));
	}

}