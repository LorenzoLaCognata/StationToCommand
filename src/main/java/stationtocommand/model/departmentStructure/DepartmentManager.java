package stationtocommand.model.departmentStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentManager {

	private final List<Department> departments = new ArrayList<>();

	public DepartmentManager() {
		initDepartments();
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

	public void initDepartments() {
		addDepartment(new Department(DepartmentType.FIRE_DEPARTMENT));
		addDepartment(new Department(DepartmentType.POLICE_DEPARTMENT));
		addDepartment(new Department(DepartmentType.MEDIC_DEPARTMENT));
	}

}