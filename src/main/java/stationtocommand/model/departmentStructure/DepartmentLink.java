package stationtocommand.model.departmentStructure;

public abstract class DepartmentLink {

    private final Department department;

    public DepartmentLink(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }
}