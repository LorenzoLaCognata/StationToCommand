package missionStructure.missionModule;

import departmentStructure.departmentModule.Department;
import locationStructure.locationModule.Location;
import linkStructure.organizationLinkModule.DepartmentLink;

import java.util.ArrayList;
import java.util.List;

public class Mission {

    private Location location;
    private List<DepartmentLink> departmentLinks;

    public Mission(Location location) {
        System.out.println("Mission initializing");
        this.location = location;
        departmentLinks = new ArrayList<>();
        System.out.println("Mission initialized successfully");
    }

    public void linkDepartment(Department department) {
        // TODO
    }

}