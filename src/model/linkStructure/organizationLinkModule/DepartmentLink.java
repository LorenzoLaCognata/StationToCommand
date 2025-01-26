package model.linkStructure.organizationLinkModule;

import model.departmentStructure.departmentModule.Department;

public abstract class DepartmentLink {

    private final Department department;

    public DepartmentLink(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }
}