package model.departmentStructure;

import model.locationStructure.LocationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentManager {

	private final List<Department> departments = new ArrayList<>();

	public DepartmentManager(LocationManager locationManager) {
		initDepartments(locationManager);
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