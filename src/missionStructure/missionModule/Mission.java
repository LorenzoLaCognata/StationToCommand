package missionStructure.missionModule;

import departmentStructure.departmentModule.Department;
import locationStructure.locationModule.Location;
import missionLinkStructure.missionOrganizationModule.MissionDepartmentLink;

import java.util.ArrayList;
import java.util.List;

public class Mission {

    private final MissionType missionType;
    private final Location location;
    private final List<MissionDepartmentLink> departmentLinks;

    public Mission(MissionType missionType, Location location) {
        System.out.println("Mission initializing");
        this.missionType = missionType;
        this.location = location;
        this.departmentLinks = new ArrayList<>();
        System.out.println("Mission initialized successfully");
    }

    @Override
    public String toString() {
        return "[MISSION] " + this.missionType + " (" + this.location + ")";
    }

    public MissionType getMissionType() {
        return missionType;
    }

    public Location getLocation() {
        return location;
    }

    public List<MissionDepartmentLink> getDepartmentLinks() {
        return departmentLinks;
    }

    public MissionDepartmentLink linkDepartment(Department department) {
        if (departmentLinks.stream()
                .noneMatch(item -> item.getDepartment().getDepartmentType().equals(department.getDepartmentType()))) {
            MissionDepartmentLink missionDepartmentLink = new MissionDepartmentLink(department);
            departmentLinks.add(missionDepartmentLink);
            return missionDepartmentLink;
        }
        else {
            return null;
        }
    }

}