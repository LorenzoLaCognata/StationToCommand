package departmentStructure.departmentModule;

import locationStructure.locationModule.LocationManager;
import shiftStructure.shiftModule.ShiftManager;
import trainingStructure.trainingModule.TrainingManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentManager {

	private final List<Department> departments = new ArrayList<>();
	private final ShiftManager shiftManager;
	private final TrainingManager trainingManager;

	public DepartmentManager(LocationManager locationManager) {
		initDepartments(locationManager);
		this.shiftManager = new ShiftManager();
		// TODO: init Trainings
		this.trainingManager = new TrainingManager();
	}

	@Override
	public String toString() {
		return departments.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public Department getDepartment(DepartmentType departmentType) {
		return departments.stream()
				.filter(item -> item.getDepartmentType().equals(departmentType))
				.findAny().
				orElse(null);
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