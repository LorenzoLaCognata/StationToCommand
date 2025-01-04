package departmentStructure.departmentModule;

import rankStructure.rankModule.RankManager;
import shiftStructure.shiftModule.ShiftManager;
import trainingStructure.trainingModule.TrainingManager;

import java.util.ArrayList;
import java.util.List;

public class DepartmentManager {

	private List<Department> departments = new ArrayList<>();
	private ShiftManager shiftManager;
	private TrainingManager trainingManager;
	private RankManager rankManager;

	public DepartmentManager() {
		System.out.println("DepartmentManager initializing");
		initDepartments();
		this.shiftManager = new ShiftManager();
		this.trainingManager = new TrainingManager();
		this.rankManager = new RankManager();
		System.out.println("DepartmentManager initialized successfully");
	}

	public Department getDepartment(DepartmentType departmentType) {
		// TODO
		return departments.get(0);
	}

	public void addDepartment(Department department) {
		this.departments.add(department);
	}

	public void initDepartments() {
		addDepartment(new Department(DepartmentType.FireDepartment));
		addDepartment(new Department(DepartmentType.PoliceDepartment));
		addDepartment(new Department(DepartmentType.MedicDepartment));
	}

}