package linkStructure.organizationLinkModule;

import departmentStructure.departmentModule.Department;

public abstract class DepartmentLink {

    private Department department;

    public DepartmentLink(Department department) {
        System.out.println("DepartmentLink initializing");
        this.department = department;
        System.out.println("DepartmentLink initialized successfully");
    }

}