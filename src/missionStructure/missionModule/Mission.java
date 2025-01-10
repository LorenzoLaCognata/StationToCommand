package missionStructure.missionModule;

import departmentStructure.departmentModule.Department;
import locationStructure.locationModule.Location;
import missionLinkStructure.missionOrganizationModule.MissionDepartmentLink;
import missionLinkStructure.missionOrganizationModule.MissionStationLink;
import stationStructure.stationModule.Station;
import unitStructure.unitModule.Unit;

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

    public void linkDepartment(Department department) {
        if (departmentLinks.stream()
                .noneMatch(item -> item.getDepartment().getDepartmentType().equals(department.getDepartmentType()))) {
            MissionDepartmentLink missionDepartmentLink = new MissionDepartmentLink(department);
            departmentLinks.add(missionDepartmentLink);
        }
    }

    public void linkStation(Station station) {
        MissionDepartmentLink missionDepartmentLink = this.departmentLinks.stream()
            .filter(item -> item.getDepartment().equals(station.getDepartment()))
            .findFirst()
            .orElse(null);

        if (missionDepartmentLink != null) {
            missionDepartmentLink.linkStation(station);
        }
    }

    public void linkUnit(Unit unit) {
        MissionDepartmentLink missionDepartmentLink = this.departmentLinks.stream()
            .filter(item -> item.getDepartment().equals(unit.getStation().getDepartment()))
            .findFirst()
            .orElse(null);

        if (missionDepartmentLink != null) {
            MissionStationLink missionStationLink = missionDepartmentLink.getStationLinks().stream()
                .filter(item -> item.getStation().equals(unit.getStation()))
                .findFirst()
                .orElse(null);

            if (missionStationLink != null) {
                missionStationLink.linkUnit(unit);
            }
        }

    }

}