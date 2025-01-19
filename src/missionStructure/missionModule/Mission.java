package missionStructure.missionModule;

import departmentStructure.departmentModule.Department;
import locationStructure.locationModule.Location;
import missionLinkStructure.missionLinkModule.MissionCivilianLink;
import missionLinkStructure.missionOrganizationModule.MissionDepartmentLink;
import missionLinkStructure.missionOrganizationModule.MissionStationLink;
import missionLinkStructure.missionOrganizationModule.MissionUnitLink;
import personStructure.civilianModule.Civilian;
import responderStructure.responderModule.Responder;
import stationStructure.stationModule.Station;
import unitStructure.unitModule.Unit;
import vehicleStructure.vehicleModule.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Mission {

    private final MissionType missionType;
    private final Location location;
    private final List<MissionDepartmentLink> departmentLinks;
    private final List<MissionCivilianLink> civilianLinks;

    public Mission(MissionType missionType, Location location) {
        this.missionType = missionType;
        this.location = location;
        this.departmentLinks = new ArrayList<>();
        this.civilianLinks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "[MISSION] " + this.missionType;
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

    public List<MissionCivilianLink> getCivilianLinks() {
        return civilianLinks;
    }

    public void linkDepartment(Department department) {
        if (departmentLinks.stream()
                .noneMatch(item -> item.getDepartment().equals(department))) {
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

    public void linkCivilian(Civilian civilian) {
        if (civilianLinks.stream()
                .noneMatch(item -> item.getCivilian().equals(civilian))) {
            MissionCivilianLink missionCivilianLink = new MissionCivilianLink(civilian);
            civilianLinks.add(missionCivilianLink);
        }
    }

    public void linkResponder(Responder responder) {
        MissionDepartmentLink missionDepartmentLink = this.departmentLinks.stream()
                .filter(item -> item.getDepartment().equals(responder.getUnitLink().getUnit().getStation().getDepartment()))
                .findFirst()
                .orElse(null);

        if (missionDepartmentLink != null) {
            MissionStationLink missionStationLink = missionDepartmentLink.getStationLinks().stream()
                    .filter(item -> item.getStation().equals(responder.getUnitLink().getUnit().getStation()))
                    .findFirst()
                    .orElse(null);

            if (missionStationLink != null) {
                MissionUnitLink missionUnitLink = missionStationLink.getUnitLinks().stream()
                        .filter(item -> item.getUnit().equals(responder.getUnitLink().getUnit()))
                        .findFirst()
                        .orElse(null);

                if (missionUnitLink != null) {
                    missionUnitLink.linkResponder(responder);
                }
            }
        }
    }

    public void linkVehicle(Vehicle vehicle) {
        MissionDepartmentLink missionDepartmentLink = this.departmentLinks.stream()
                .filter(item -> item.getDepartment().equals(vehicle.getUnitLink().getUnit().getStation().getDepartment()))
                .findFirst()
                .orElse(null);

        if (missionDepartmentLink != null) {
            MissionStationLink missionStationLink = missionDepartmentLink.getStationLinks().stream()
                    .filter(item -> item.getStation().equals(vehicle.getUnitLink().getUnit().getStation()))
                    .findFirst()
                    .orElse(null);

            if (missionStationLink != null) {
                MissionUnitLink missionUnitLink = missionStationLink.getUnitLinks().stream()
                        .filter(item -> item.getUnit().equals(vehicle.getUnitLink().getUnit()))
                        .findFirst()
                        .orElse(null);

                if (missionUnitLink != null) {
                    missionUnitLink.linkVehicle(vehicle);
                }
            }
        }
    }

}